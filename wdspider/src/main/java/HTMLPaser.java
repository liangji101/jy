import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;

public class HTMLPaser {

	public JSONArray ListPaser(String content) {
		String regex_str = getTl() ;
		
		Pattern pattern = Pattern.compile(regex_str, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			for (int i = 0; i < matcher.groupCount(); i++) {
				System.out.println(" "+ i + " " + matcher.group(i));
			}
		}
		return null;
	}

	private String getTl() {
		return ReadFromFile.readFileByBytes("d:\\workspace\\wdspider\\target\\classes\\file.tl");
	}

	public static void main(String[] args) {

	}

}
