package com.renren.ntc.video.utils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 @Description:TODO
 @Author:dapeng.zhou 
 @Email:dapeng.zhou@renren-inc.com
 @Date:2012-12-4 下午05:03:52
 */

//xn工程需要使用
public class UploadFileUtil {
	private static final String FILE_UPLOAD_PREFIX_PATH = "/data/web/uploadFiles/";
	private static final DateFormat FORMAT_YEAR_MONTH_DAY = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat FORMAT_HOUR_MINUTE_SECOND = new SimpleDateFormat("HH-mm-ss");
	public static boolean upload(MultipartFile file){
		String date = FORMAT_YEAR_MONTH_DAY.format(new Date());
		String destination = FILE_UPLOAD_PREFIX_PATH + date ;
		File des = new File(destination);
		if (!des.exists()){
			des.mkdir();
		}
		String newFilePath = destination + "/" + file.getOriginalFilename();
		File desFile = new File(newFilePath);
		try {
			if (desFile.exists()){
				String[] strs = parseFileName(file.getOriginalFilename());
				if (strs == null){
					return false;
				}
				//如果发生命名冲突，则重命名 ，如abc.txt如果存在则重命名为 abc-13-06-10-1354683871875.txt
				newFilePath = new StringBuilder(destination).append("/").append(strs[0]).append("-").append(FORMAT_HOUR_MINUTE_SECOND.format(new Date())).append("-").append(System.currentTimeMillis()).append(strs[1]).toString();
				desFile = new File(newFilePath);
			}
			file.transferTo(desFile);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
	
	private static String[] parseFileName(String fileName){
		String[] strs = new String[2];
		int index = fileName.lastIndexOf(".");
		if (index == -1){
			return strs;
		}
		strs[0] = fileName.substring(0, index);
		strs[1] = fileName.substring(index);
		return strs;
	}
	
	public static void main(String[] args) {
		String[] strs = parseFileName("hello.txt");
		for(String str:strs){
			System.out.println(str);
		}
		System.out.println(System.currentTimeMillis());
	}
}

