package com.test.cmoa.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 处理各种数据的工具类
 * @author Creathin
 *
 */
public class DataProcessUtils {
	/**
	 * 毫秒转换为分钟
	 * @param ms
	 * @return
	 */
	public static String formatTime(long ms) {
        
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day; //天
        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
        String strSecond = second < 10 ? "0" + second : "" + second;//秒
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
       
        return strMinute + " 分钟 " + strSecond + " 秒";
}
	
	/**
	 * 修改文件名后缀   用于视频格式转换
	 */
	public static String subName(String name){
		
			int lastIndexOf = name.lastIndexOf(".");
			name = name.substring(0,lastIndexOf)+".mp4";
			return name;
	}
	
	/**
	 * 根据文件url返回目录
	 */
	public static String subPath(String path){
		
		int lastIndexOf = path.lastIndexOf("\\");
		
		return path.substring(0, lastIndexOf).replace("\\", "//");
	}
	
	/**
	 * 重复的文件加 -副本
	 * @param realfilename
	 * @param i
	 * @return
	 */
	 public static String subStr( String realfilename,int i){
		 
         int lastIndex = realfilename.lastIndexOf(".");
         String str1 = realfilename.substring(0, lastIndex);
         String str2 = realfilename.substring(lastIndex,realfilename.length());
         realfilename = str1+"-副本"+i+""+str2;
         return realfilename;
	 }
	 /**
	  * 根据Path 地址转换回名称
	  * @param realPath
	  * @return
	  */
	 public static String subStr(String realPath){
		 int lastIndex = realPath.lastIndexOf("\\");
		 
		 realPath = realPath.substring(lastIndex+1, realPath.length());
		 
		return realPath;
			 
	 }
	 
	 public static String toRealPath(String realPath,String fileName){
		 
		 String fileRealPath =  realPath.replace("//", "\\")+"\\"+fileName;
		 System.out.println(fileRealPath);
		 return fileRealPath;
	 }
	
	/**
	 * 将URL地址后面的参数去掉
	 * @param url
	 * @return
	 */
	public static String catUrl(String servletPath){
		while(true){
		try {
			int lastIndexOf = servletPath.lastIndexOf("/");
			
			String lastPart = servletPath.substring(lastIndexOf+1);
			Integer.parseInt(lastPart);
			//[)半闭半开
			servletPath =  servletPath.substring(0,lastIndexOf);
			
		} catch (NumberFormatException e) {
			break;
		}
		}
		return servletPath;
	}
	
	
	/**
	 * 利用序列化和反序列化来进行深度复制
	 * 传入一个源对象   返回目标对象
	 * 
	 * @param sourceObj
	 * @return
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static Serializable deeplyCopy(Serializable sourceObj){
		
		Serializable targetObj = null;
		//需要用到的 流
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bos = null;
		
		try {
		/* 序列化 */
			//创建字节输出流
			bos = new ByteArrayOutputStream();
			//创建对象的输出流
			oos = new ObjectOutputStream(bos);
			//将源对象写入到 对象输出流  在对象输出流的内部会把序列化的数据写入到字节数组中
			oos.writeObject(sourceObj);
			//通过字节数组输出流获取保存序列化数组的字节数组。
			byte[] byteArray = bos.toByteArray();
		/* 反序列化  */
			//创建字节数组初入流
			bis = new ByteArrayInputStream(byteArray);
			//创建对象的输入流
			ois = new ObjectInputStream(bis);
			//从对象输入流中读取对象  
			targetObj = (Serializable) ois.readObject();
			
			System.out.println(sourceObj);
			System.out.println(targetObj);
			System.out.println(sourceObj.hashCode());
			System.out.println(targetObj.hashCode());
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (oos!=null){
				try {
					oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ois!=null){
				try {
					ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return targetObj;
	}
	
	
	/**
	 * 将图片压缩按本来的长宽比例压缩为100宽度的jpg图片
	 * @param inputStream
	 * @param realPath /surveyLogos目录的真实路径，后面没有斜杠
	 * @return 将生成的文件路径返回 surveyLogos/4198393905112.jpg
	 */
	public static String resizeImages(InputStream inputStream, String realPath) {
		
		OutputStream out = null;
		
		try {
			//1.构造原始图片对应的Image对象
			BufferedImage sourceImage = ImageIO.read(inputStream);

			//2.获取原始图片的宽高值
			int sourceWidth = sourceImage.getWidth();
			int sourceHeight = sourceImage.getHeight();
			
			//3.计算目标图片的宽高值
			int targetWidth = sourceWidth;
			int targetHeight = sourceHeight;
			
			if(sourceWidth > 100) {
				//按比例压缩目标图片的尺寸
				targetWidth = 100;
				targetHeight = sourceHeight/(sourceWidth/100);
				
			}
			
			//4.创建压缩后的目标图片对应的Image对象
			BufferedImage targetImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			
			//5.绘制目标图片
			targetImage.getGraphics().drawImage(sourceImage, 0, 0, targetWidth, targetHeight, null);
			
			//6.构造目标图片文件名
			String targetFileName = System.nanoTime() + ".jpg";
			
			//7.创建目标图片对应的输出流
			out = new FileOutputStream(realPath+"/"+targetFileName);
			
			//8.获取JPEG图片编码器
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			
			//9.JPEG编码
			encoder.encode(targetImage);
			
			//10.返回文件名
			return "surveyLogos/"+targetFileName;
			
		} catch (Exception e) {
			
			return null;
		} finally {
			//10.关闭流
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}

	/**
	 * 将数组转换为字符串的方法
	 * @param contentArr
	 * @return
	 */
	public static String convertToStr(String[] contentArr) {
		if (contentArr == null || contentArr.length == 0){
			return null;
		}
		
		//拼接字符串  用到StringBuilder
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < contentArr.length; i++) {
			String content = contentArr[i];
			//StringBuilder将数组拼接 然后返回自身对象
			
			sb.append(content).append(",");
		}
		//拼接起来的结果   1,2,3,  将会多一个逗号  ，去掉最后一个逗号并返回
		return sb.substring(0, sb.lastIndexOf(","));
	}

}
