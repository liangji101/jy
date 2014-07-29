package com.renren.ntc.video.utils;

import com.renren.ntc.video.biz.model.UploadWorker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;

public class UploadUsersUtil {
	private static final Random random = new Random();
	private static InputStream filePath = UploadUsersUtil.class.getResourceAsStream("/account.properties");
	private static HashMap<String, UploadWorker> map = new HashMap<String, UploadWorker>();
	private static int i = 0;

	static {
		loadWorker();
	}

	public static void loadWorker() {
		try {
			//InputStream filePath = new FileInputStream(
				//	"D:\\WorkSpace\\ntc-video\\src\\main\\resources\\account.properties");
			String line; // 用来保存每行读取的内容
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					filePath));
			line = reader.readLine();
			while (line != null) {
				String[] res = line.split(" ");
				
				System.out.println(String.format("load %s %s ;" ,res[0], res[1]));
				map.put(Integer.toString(i++),
						new UploadWorker(Integer.valueOf(res[0]), res[1]));
				
				line = reader.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static UploadWorker getWorker() {

		int a = (int) (Math.random() * 10000);
		a = a % map.size();
		return map.get(a + "");
	}

/*public static void main(String[] args) {
		loadWorker();
		 System.out.println(getWorker().getName());
	}*/
}