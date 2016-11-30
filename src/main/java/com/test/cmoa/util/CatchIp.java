package com.test.cmoa.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/*获取IP地址*/  
public class CatchIp   
{  
    private InetAddress LocalIP = null;  
    private InetAddress ServerIP = null;  
    public static void main(String[] args)   
    {  
        CatchIp mytest;  
        mytest = new CatchIp();  
        System.out.println("LocalHost IP is:"+mytest.catchLocalIP());  
        System.out.println("Server IP is:"+mytest.catchServerIP());  
    }  
    //取得本机IP地址  
    public InetAddress catchLocalIP()  
    {  
        try   
        {  
            LocalIP = InetAddress.getLocalHost();  
        } catch (UnknownHostException e)   
        {  
              
        }  
        return LocalIP;  
          
    }  
    //取得服务器网络地址  
    public InetAddress catchServerIP()  
    {  
        try {  
            ServerIP = InetAddress.getByName("www.baidu.com");  
            //取得www.sina.com.cn的IP地址  
        } catch (UnknownHostException e)   
        {  
            System.out.println(e.getMessage());  
        }  
        return ServerIP;  
          
    }  
      
}  