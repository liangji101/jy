import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.OrderInfo;
import bean.Shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import dao.ShopDAO;
import dao.WPOrderDAO;

public class SMS {
	public static String URL = "http://v.juhe.cn/sms/send";
	public static String APPKEY = "99209217f5a5a1ed2416e5e6d2af87fd";
	public static String TID = "791";

	public static String USER_TID = "794";
	
	public static Logger logger = LoggerFactory.getLogger(SMS.class);

	public static String KEY = "(\\$\\{\\w*\\})";
	public static Pattern patternKeys = Pattern.compile(KEY);

	public static String RULE = "rule=";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//       System.out.println(new Date(working()).toLocaleString());
//		if(true){
//		   return ;
//		}
		RoseAppContext rose = new RoseAppContext();
		WPOrderDAO wpDao = rose.getBean(WPOrderDAO.class);
		ShopDAO shopDao = rose.getBean(ShopDAO.class);
		Shop shop = shopDao.getShop(1);
		List<OrderInfo> values = wpDao.getSMSNo();
		for (OrderInfo value : values) {
			byte[] re = null ;
			String v = null;
			String url;
			String mobile ="";
			String response = null;
			long current = System.currentTimeMillis();
			v = getShValue(value.getInfo());
			System.out.println(String.format("currentTime %s , workstar : %s ,workend %s ", current +"", workstart()+"",workend()+ " " ));
			if(current < workend() && current > workstart()){
			mobile = shop.getOwner_phone();
			// send SMS to Shop owner
			v = getShValue(value.getInfo());
			url = forURL(URL, APPKEY, TID, mobile, v);
			System.out.println(String.format("Post mobile %s %s ,%s ",mobile, value.getOrder_id(), url));
			re = HttpClient.getURLData(url, "");
			response = toString(re);
			System.out.println(String.format("Post Shop SMS message No. %s : %s , %s  %s ", value.getOrder_id(), response , mobile , url));
			if (!isOk(response)){
				return ;
			 }
			}
			wpDao.update(2, value.getOrder_id());
			
			// Send SMS to moniter
			Shop m = shopDao.getShop(-1);
			mobile = m.getOwner_phone();
			url = forURL(URL, APPKEY, TID, mobile, v);
			System.out.println(String.format("Post mobile %s %s ,%s ",mobile, value.getOrder_id(), url));
			re = HttpClient.getURLData(url, "");
			response = toString(re);
			System.out.println(String.format("Post Moniter SMS message No. %s : %s %s %s", value.getOrder_id(), response, mobile , url));
			if(current < workend() && current > workstart()){
			// send SMS to User
			mobile = getPhone(value.getInfo());
			v = getUserValue(value.getInfo());
			url = forURL(URL, APPKEY, USER_TID, mobile, v);
			System.out.println(String.format("Post mobile %s %s ,%s ",mobile, value.getOrder_id(), url));
			re = HttpClient.getURLData(url, "");
			response = toString(re);
			System.out.println(String.format("Post User SMS message No. %s : %s , %s , %s", value.getOrder_id(), response,mobile,url));
		    }
		}
	}

	private static long workend() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.HOUR_OF_DAY, 19);  
		calendar.set(Calendar.MINUTE, 30); 
		calendar.set(Calendar.SECOND, 0);     
		Date date = calendar.getTime();
		return date.getTime();
	}
	private static long workstart() {
		Calendar calendar = Calendar.getInstance(); 
		calendar.set(Calendar.HOUR_OF_DAY, 7);  
		calendar.set(Calendar.MINUTE, 0); 
		calendar.set(Calendar.SECOND, 0);     
		Date date = calendar.getTime();
		return date.getTime();
	}

	private static boolean isOk(String response) {
		if(null == response){
			return false; 
		}
		JSONObject ob = JSON.parseObject(response);
		if ("0".equals(ob.getString("error_code"))) {
			return true;
		}
		return false;
	}

	private static String getPhone(String info) {
		String[] e = info.split("\\|");
		if(e.length != 3){
			return null;
		}
		return e[1].replace("#phone#=", "");
	}
	
	private static String getShValue(String address) {
		String[] e = address.split("\\|");
		if(e.length != 3){
			return null;
		}
		return URLEncoder.encode(e[0]);
	}
	
	private static String getUserValue(String address) {
		String[] e = address.split("\\|");
		if(e.length != 3){
			return null;
		}
		return URLEncoder.encode(e[2]);
	}

	private static String forURL(String url, String appkey, String tid, String mobile, String value) {
		return url + "?key=" + appkey + "&dtype=json&mobile=" + mobile + "&tpl_id=" + tid + "&tpl_value=" + value;

	}

	private static String toString(byte[] text) {
		try {
			return new String(text, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String formTMessage(String temp, String key, String value) {
		return temp.replace(p(key), value);

	}

	private static CharSequence p(String key2) {
		return "${" + key2 + "}";
	}

	private static String repare(String html) {
		html = html.replaceAll("<!--.*?-->", "");
		html = html.replaceAll("<script>.*?<\\script>", "");
		html = html.replaceAll("<SCRIPT>.*?<\\SCRIPT>", "");
		// html = html.replaceAll(" +", "");
		html = html.replaceAll("\\t*", "");
		html = html.replaceAll("\\n|\\r", "");
		System.out.println(html);
		return html;
	}

	private static String offShell(String group) {
		group = group.substring(group.indexOf("{") + 1, group.indexOf("}"));
		return group;
	}

	public static String[] readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> rules = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (StringUtils.isEmpty(tempString) && !tempString.startsWith(RULE)) {
					System.out.println(String.format("error info: %s ", tempString));
				}
				tempString = tempString.substring(tempString.indexOf(RULE) + RULE.length());
				rules.add(tempString);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return rules.toArray(new String[0]);
			}
		}
		return rules.toArray(new String[0]);
	}
	
	
}
