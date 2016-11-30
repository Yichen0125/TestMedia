package com.test.cmoa.convert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class FreeConvertStop {
	private Logger log = Logger.getLogger("log4j.properties"); 
	public void convertStop(){
		List <String >command = new ArrayList<String>();
		command.add("taskkill");
		command.add("-f");
		command.add("-im");
		command.add("ffmpeg.exe");
		ProcessBuilder pb = new ProcessBuilder(command);
		try {
			Process pr = pb.start();
			//通过Process 获取时时状态信息
			BufferedReader br = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			String str = "";
			while ((str = br.readLine()) != null) {
				System.out.println(str);
			}
			System.out.println("进程终止:"+new Date());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
