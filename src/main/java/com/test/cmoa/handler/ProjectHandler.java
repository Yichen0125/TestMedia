package com.test.cmoa.handler;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.test.cmoa.entity.Employee;
import com.test.cmoa.entity.Project;
import com.test.cmoa.entity.ProjectGroup;
import com.test.cmoa.entity.User;
import com.test.cmoa.orm.Page;
import com.test.cmoa.service.EmployeeService;
import com.test.cmoa.service.ProjectService;
import com.test.cmoa.service.UserService;
import com.test.cmoa.util.DataProcessUtils;
import com.test.cmoa.util.DataUtils;

@Controller
@RequestMapping("/xm/pro")
public class ProjectHandler {
	@Autowired
	private UserService userService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private EmployeeService employeeService;
	
	//pro/list
	@RequestMapping("/list")
	public String showList(@RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
			HttpServletRequest request){
		
		System.out.println("----------");
		
		Map<String, Object> params = WebUtils.getParametersStartingWith(request, "search_");
		String queryString = DataUtils.encodeParamsToQueryString(params);

		request.setAttribute("queryString", queryString);

		Page<Project> page = projectService.getPage(pageNo, params);

		request.setAttribute("page", page);

		return "project/list";
	}
	
	@RequestMapping("/add")
	public String add(@RequestParam("Myfile") MultipartFile myfile,Project project, HttpServletRequest request,HttpServletResponse response) throws IOException{
		   //如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件
	 System.out.println("----------------------上传开始");
     System.out.println(project);
     //从session 中 获取登陆用户  用来创建存放上传文件的目录
	 		String userName = "syc";
            if(myfile.isEmpty()){  
                System.out.println("文件未上传");  
            }else{
            	String realfilename = myfile.getOriginalFilename();
                System.out.println("文件长度: " + myfile.getSize());
                System.out.println("文件类型: " + myfile.getContentType());  
                System.out.println("文件名称: " + myfile.getName());  
                System.out.println("文件原名: " + myfile.getOriginalFilename());  
                System.out.println("========================================"); 
                String realPath = "d://upload//"+userName;
                //判断目录下是否有重复
                File file = new File("d://upload//"+userName);
                if(!file.exists())   {
                    file.mkdirs();
                  }
                String[] strlist = file.list();
                int i = 0;
                for (; i < strlist.length; i++) {
					if (strlist[i].equals(myfile.getOriginalFilename())){
						System.out.println("已存在");
						
						realfilename = DataProcessUtils.subStr(myfile.getOriginalFilename(),i+1);
						FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath,realfilename ));
						System.out.println("已拷贝");
					}
                }  
                //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
                
                //request.getSession().getServletContext().getRealPath("/upload");
                System.out.println(realPath);
                //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉
                
                FileUtils.copyInputStreamToFile(myfile.getInputStream(), new File(realPath, myfile.getOriginalFilename()));
                if (realPath != null){
                	project.setUrl(DataProcessUtils.toRealPath(realPath,realfilename));
                }
                
                String []empList = request.getParameterValues("empList.employee.empName");
                int j = 0;
                Long l= (long) (Math.random() * 100000000);
        		String groupnum = String.valueOf(l);
                for (; j < empList.length; j++) {
                	
            		long id = Long.parseLong(empList[j]);
            		
                	ProjectGroup group= new ProjectGroup(groupnum, project.getUser(), employeeService.getEmpById((int)id), new Date());
                	String groupnum2 = projectService.saveProjectGroup(group);
                	if(groupnum2 != null){
                		groupnum = groupnum2;
                	}
                	
				}
                
                System.out.println(realPath);
                project.setDate(new Date());
                project.setStatus(0);
                project.setUrl(realPath);
                System.out.println(project);
                
                User user = userService.getByUserName(project.getUser().getName());
                System.out.println(user);
                project.setGroupnum(groupnum);
                project.setUser(user);
                
               	projectService.save(project);
               	
              /* 	PrintWriter out = response.getWriter();
               	out.println(fileId);*/
               	System.out.println("---------------------------------上传结束");
            
        }
		return "project/add";
	}
	
	@RequestMapping("/toAdd")
	public String toadd(Map<String ,Object> map){
		Long l= (long) (Math.random() * 100000000);
		String pronum = String.valueOf(l);
		map.put("pronum",pronum);
		
		List <String > userList = userService.getUserList();
		map.put("userList",userList);

		List <Employee > empList = employeeService.getEmpList();
		map.put("empList",empList);
		return "project/add";
	}
	
}
