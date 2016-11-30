package com.test.cmoa.handler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.test.cmoa.entity.Media;
import com.test.cmoa.service.MediaService;
import com.test.cmoa.util.ChangeVideo;
import com.test.cmoa.util.Constants;
import com.test.cmoa.util.DataProcessUtils;
///media/meer/list

@Controller
@RequestMapping("xm/pro/sp")
public class MediaHandler {
	@Autowired
	private MediaService mediaService;
	
	private List<Integer> idList = new ArrayList<Integer>();
	
	private Logger log = Logger.getLogger("log4j.properties");  
	
	@ResponseBody
	@RequestMapping("/removeById/{id}")
	public String updateById(@PathVariable("id")Integer id){
		mediaService.updateConvertAreaById(id);
		return "1";
	}	
	//查看待转码列表
	@RequestMapping("/unConvertlist")
	public ModelAndView showConvertList(){
		List<Media> unConvertList = mediaService.selectUnConvertList();
		ModelAndView mav = new ModelAndView("media/showUnConvertList");
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		map.put("unConvertList", unConvertList);
		
		mav.addAllObjects(map);
		return mav;
	}
	
	//添加到转码列表
	@ResponseBody
	@RequestMapping("/convertArea/{id}")
	public String convertArea(@PathVariable("id") Integer id){
			mediaService.updateConvertAreaById(id);
			return "1";
	}
	
	//返回首页
	@RequestMapping("/toIndex")
	public String Convert(){
		return "redirect:media/list";
	}
	//转码
	@ResponseBody
	@RequestMapping("/convert/{id}")
	public int convert(@PathVariable("id") Integer id ,Map<String ,Object> map,Locale locale,HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println(id);
		if (idList.size() < 3){
			idList.add(id);
			if (idList != null && idList.size() > 0 ){
				int i= 0;
				for (;i<idList.size();i++) {
					Media media = mediaService.selectMediaById(idList.get(i));
					
					try {
						String inputFile = media.getSrc();
						File dir = new File(Constants.realFilePath+"syc");
						if(!dir.exists())   {
							dir.mkdirs();
						}
						String fileName =   DataProcessUtils.subName(media.getTitle());
						String outputFile = dir+"\\"+fileName;
						
						long start = System.currentTimeMillis();
						boolean convert = ChangeVideo.convert(inputFile, outputFile);
						long end = System.currentTimeMillis();
						long finish = end - start; 
						if (convert){
							Media m = new Media(idList.get(i), fileName, outputFile, null, "测试", new Date(), convert ,null,false);
							mediaService.updateConvertByEntity(m);
							
							while (finish > 0){
								System.out.println("执行任务成功");
								System.out.println("任务总时长:" + DataProcessUtils.formatTime(finish));
								
								File folder = new File(Constants.tempFilePath+"syc");
								idList.remove(idList.get(i));
								File[] files = folder.listFiles();
								for(File file:files){
									System.out.println(media.getTitle());
									if(file.getName().equals(media.getTitle())){
										file.delete();
									}
								}
								
								finish = 0;
							}return 1;
						}else{
							map.put("message","任务失败！请检查");
							return 0;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						System.out.println("出错了...");
						return 0;
					}
				}
			}
		}else{
			System.out.println("超过10个 -----------------------------------------");
			return 0;
		}
		return 0;
	}
	
//	toUnConvertUI  跳转到转码页面
	@RequestMapping("/toConvertUI")
	public ModelAndView ConvertUI(){
		List<Media > medias = mediaService.selectAllUnConvertUI();
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("medias", medias);
		ModelAndView mav = new ModelAndView("media/ConvertUI",map);
		return mav;
	}
	//上传视频
	@Transactional(readOnly = false)
	@RequestMapping(value="/addAll",method=RequestMethod.POST)
	public String addAll(@RequestParam("myMedias") MultipartFile[] myMedias, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
	
		//-----------------------------------------------
		//"syc"  为当前用户名   直接替换 为user.getName
		//-----------------------------------------------
		
	for (MultipartFile myMedia : myMedias) {
		if (!myMedia.getOriginalFilename().equals("")){
		String realPath = Constants.tempFilePath+"syc";
		  String realfilename = myMedia.getOriginalFilename();
		  File file = new File(Constants.tempFilePath+"syc");
        if(!file.exists())   {
            file.mkdirs();
          }
        String[] strlist = file.list();
        for (int i = 0; i < strlist.length; i++) {
				if (strlist[i].equals(myMedia.getOriginalFilename())){
					System.out.println("已存在");
					realfilename = DataProcessUtils.subStr(myMedia.getOriginalFilename(),i+1);
					FileUtils.copyInputStreamToFile(myMedia.getInputStream(), new File(realPath,realfilename ));
					System.out.println("已拷贝");
					request.setAttribute("message", "上传成功");
				}
			}	
        
        	FileUtils.copyInputStreamToFile(myMedia.getInputStream(), new File(realPath, myMedia.getOriginalFilename()));
        
     
	     Media m = new Media(null,realfilename,DataProcessUtils.toRealPath(realPath,realfilename),null,"测试",new Date(),false,null,false);
	     mediaService.savemedia(m);
	     request.setAttribute("message", "上传成功");
		}
	}
		return "redirect:list";
	}
	
	@RequestMapping("/add")
	public String toAddUI(){
		return "media/add";
	}
	
	@RequestMapping(value="/list")
	public ModelAndView showList(HttpServletRequest request){
		List<Media > medias = mediaService.selectAll();
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("medias", medias);
		ModelAndView mav = new ModelAndView("media/list",map);
		return mav;
	}
	
	
	//------------------------------------------------------------------------------------------------------------------------
	/*@Transactional(readOnly=false)
	@RequestMapping(value = "saveAll", method = RequestMethod.POST)
	public String mediaUp(@RequestParam("myMedias") MultipartFile[] myMedias, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		
		System.out.println("OK");
			//-----------------------------------------------
			//"syc"  为当前用户名   直接替换 为user.getName
			//-----------------------------------------------
			
		for (MultipartFile myMedia : myMedias) {
			if (!myMedia.getOriginalFilename().equals("")){
			String realPath = "d://TempVideo//"+"syc";
			  String realfilename = myMedia.getOriginalFilename();
			  File file = new File("d://TempVideo//"+"syc");
            if(!file.exists())   {
                file.mkdirs();
              }
            String[] strlist = file.list();
            for (int i = 0; i < strlist.length; i++) {
					if (strlist[i].equals(myMedia.getOriginalFilename())){
						System.out.println("已存在");
						realfilename = DataProcessUtils.subStr(myMedia.getOriginalFilename(),i+1);
						FileUtils.copyInputStreamToFile(myMedia.getInputStream(), new File(realPath,realfilename ));
						System.out.println("已拷贝");
					}
				}	
            
            	FileUtils.copyInputStreamToFile(myMedia.getInputStream(), new File(realPath, myMedia.getOriginalFilename()));
            
         
         Media m = new Media(null,realfilename,DataProcessUtils.toRealPath(realPath,realfilename),null,"测试",new Date(),false,null,false);
         
         mediaService.savemedia(m);
         System.out.println("上传成功--" + m.getSrc() + "--------------->");
          //转码 
         
         try {
      	   String inputFile = m.getSrc();
      	   File dir = new File("d:\\RealVideo\\"+"syc");
      	   if(!dir.exists())   {
                 dir.mkdirs();
               }
      	   String outputFile = dir+"\\"+DataProcessUtils.subName(m.getTitle());
      	   
      	   long start = System.currentTimeMillis();
      	   boolean convert = ChangeVideo.convert(inputFile, outputFile);
      	   long end = System.currentTimeMillis();
      	   if (convert){
	        	   long finish = end - start; 
	        	   
	        	   while (finish > 0){
	        		   System.out.println("执行任务成功");
	        		   System.out.println("任务总时长:" + DataProcessUtils.formatTime(finish));
	        		   finish = 0;
	        	   	}
      	   }else{
      		   System.out.println("任务失败！请检查");
      	   }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("出错了...");
			
		}
		}
		}
           return "redirect:/list";
	}*/

	/*@RequestMapping(value = "uploadMedia", method = RequestMethod.POST)
	public String mediaUp(@RequestParam("myMedia") MultipartFile myMedia,Media media ,HttpServletRequest request,
			HttpServletResponse response) throws IOException {
			System.out.println("上传");
			System.out.println(myMedia.getContentType());
			System.out.println(myMedia.getName());
			System.out.println(myMedia.getOriginalFilename());
			System.out.println(myMedia.getSize());
			//-----------------------------------------------
			//"syc"  为当前用户名   直接替换 为user.getName
			//-----------------------------------------------
			  String realPath = "d://TempVideo//"+"syc";
			  String realfilename = myMedia.getOriginalFilename();
			  File file = new File("d://TempVideo//"+"syc");
              if(!file.exists())   {
                  file.mkdirs();
                }
              String[] strlist = file.list();
              for (int i = 0; i < strlist.length; i++) {
					if (strlist[i].equals(myMedia.getOriginalFilename())){
						System.out.println("已存在");
						realfilename = DataProcessUtils.subStr(myMedia.getOriginalFilename(),i+1);
						FileUtils.copyInputStreamToFile(myMedia.getInputStream(), new File(realPath,realfilename ));
						System.out.println("已拷贝");
					}
				}	
           FileUtils.copyInputStreamToFile(myMedia.getInputStream(), new File(realPath, myMedia.getOriginalFilename()));
           
           Media m = new Media(null,realfilename,DataProcessUtils.toRealPath(realPath,realfilename),null,"测试",new Date());
           
           mediaService.savemedia(m);
           System.out.println("上传成功--" + m.getSrc() + "--------------->");
            //转码 
           
           try {
        	   String inputFile = m.getSrc();
        	   File dir = new File("d:\\RealVideo\\"+"syc");
        	   if(!dir.exists())   {
                   dir.mkdirs();
                 }
        	   String outputFile = dir+"\\"+DataProcessUtils.subName(m.getTitle());
        	   
        	   long start = System.currentTimeMillis();
        	   boolean convert = ChangeVideo.convert(inputFile, outputFile);
        	   long end = System.currentTimeMillis();
        	   if (convert){
	        	   long finish = end - start; 
	        	   
	        	   while (finish > 0){
	        		   System.out.println("执行任务成功");
	        		   System.out.println("任务总时长:" + DataProcessUtils.formatTime(finish));
	        		   finish = 0;
	        	   	}
        	   }else{
        		   System.out.println("任务失败！请检查");
        	   }
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("出错了...");
		}
           return "redirect:/index";
	}*/

}
