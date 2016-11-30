package com.test.cmoa.convert;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.test.cmoa.entity.Media;
import com.test.cmoa.service.MediaService;
import com.test.cmoa.util.ChangeVideo;
import com.test.cmoa.util.Constants;
import com.test.cmoa.util.DataProcessUtils;

public class FreeConvertStart {
	@Autowired
	private MediaService mediaService;
	
	private Logger log = Logger.getLogger("log4j.properties"); 
	/**
	 * 定时转码开始
	 */
	public void convertStart(){
		System.out.println("start---------------");
		List<Media> unConvertList = mediaService.selectUnConvertList();
		if (unConvertList != null){
		for (Media media : unConvertList) {
			try {
		      	   String inputFile = media.getSrc();
		      	   File dir = new File(Constants.realFilePath+"syc");
		      	   if(!dir.exists()) {
		                 dir.mkdirs();
		               }
		      	   String fileName =   DataProcessUtils.subName(media.getTitle());
		      	   String outputFile = dir+"\\"+fileName;
		      	   
		      	   long start = System.currentTimeMillis();
		      	   boolean convert = ChangeVideo.convert(inputFile, outputFile);
		      	   long end = System.currentTimeMillis();
		      	   long finish = end - start; 
		      	   if (convert){
		      		   Media m = new Media(media.getId(), fileName, outputFile, null, "测试", new Date(), convert ,null,false);
			        	   mediaService.updateConvertByEntity(m);
			        	   while (finish > 0){
			        		   log.info("执行任务成功");
			        		   log.info("任务总时长:" + DataProcessUtils.formatTime(finish));
			        		   
			        		   File folder = new File(Constants.tempFilePath+"syc");
			        			File[] files = folder.listFiles();
			        			for(File file:files){
			        				System.out.println(media.getTitle());
			        				if(file.getName().equals(media.getTitle())){
			        					file.delete();
			        				}
			        			}
			        			
			        		   finish = 0;
			        		   
			        	   	}
		      	   }else{
		      		   log.info("任务失败！请检查:" + media.getTitle());
		      		   break;
		      	   }
				} catch (Exception e) {
					System.out.println(e.getMessage());
					System.out.println("出错了...");
				}
		}		
		}
		
	}
}
