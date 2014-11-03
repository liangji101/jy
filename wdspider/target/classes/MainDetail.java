import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.paoding.rose.scanning.context.RoseAppContext;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bean.OrderInfo;
import dao.WPOrderDAO;

public class MainDetail {
	public static Logger logger = LoggerFactory.getLogger(MainDetail.class);

	public static String URL = "http://koudaitong.com/v2/trade/order/detail.html?order_no=%s";
	public static String KEY = "(\\$\\{\\w*\\})";
	// keys pattern is "${\w*}" not blank
	public static Pattern patternKeys = Pattern.compile(KEY);

	public static String RULE = "rule=";

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		RoseAppContext rose = new RoseAppContext();
		WPOrderDAO wpDao = rose.getBean(WPOrderDAO.class);
		List<String> nos = wpDao.getOrderNo();
		if (null == nos) {
			return;
		}
		for (String no : nos) {
			byte[] text = HttpClient.getURLData(String.format(URL, no), "koudaitong.com");
			String reponse = toString(text);
			String info = parse(reponse);
			wpDao.updateinfo(info, no);
		}

	}

	private static String toString(byte[] text) {
		try {
			return new String(text, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String parse(String html) {
		html = repare(html);
		
//		 String[] rules = readFileByLines("d:/workspace/wdspider/src/main/resources/rule.tl");
		String[] rules = readFileByLines("/opt/SMS_Wrapper/WEB-INF/classes/rule.tl");
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		String temp = "#address#=${address}&#fruit#=";
		String Utemp = "#phone#=${phone}|#fruit#=";
		int t = 0;
		for (String rule : rules) {
			RuleMap r = preprocess(rule);
			Pattern pattern = Pattern.compile(r.getRule());
			Matcher matchers = pattern.matcher(html);
			int a = 0;
			while (matchers.find()) {
				String tempD = "${sg_name} ${sg_count}${unit}";
				int count = matchers.groupCount();
				for (int i = 1; i <= count; i++) {
					String key = r.getKeys().get(i - 1);
					String value = matchers.group(i).trim();
					if (t == 0) {
						if ("address".equals(key)) {
							String phone = getphone(value);
							Utemp = Utemp.replace("${phone}", phone);
							sb2.append(Utemp);
						}
						temp = formTMessage(temp, key, value);
					} else {
						if ("sg_name".equals(key)) {
							String unit = "斤";
							if (-1 != value.indexOf("每个")) {
								unit = "个";
							}else if (-1 != value.indexOf("每盒")) {
								unit = "盒";
							} else {
								unit = "斤";
							}
							tempD = tempD.replace("${unit}", unit);
							value = value.split("，")[0];
						}
						tempD = formTMessage(tempD, key, value);
					}
				}
				if (t != 0) {
					if(a != 0)
					{
					sb.append("，");
				    sb2.append("，");
				     }
					sb.append(tempD);
					sb2.append(tempD);
				}
				a++;
			}
			if (t == 0) {
				sb.append(temp);
			}
			t++;
		}
		return sb.append("|").append(sb2).toString();
	}

	private static String getphone(String value) {
		String[] vv = value.split(" ");
		int length = vv.length;
		for (int i = length - 1; i >= 0; i--) {
			String k = vv[i];
			if (isMPhone(k)) {
				return k;
			}
		}
		return "none";
	}

	private static boolean isMPhone(String k) {
		if (k.length() == 11 && k.startsWith("1")) {
			try {
				Long.valueOf(k);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
	}

	private static String formTMessage(String temp, String key, String value) {
		return temp.replace(p(key), value);

	}

	private static CharSequence p(String key2) {
		return "${" + key2 + "}";
	}

	private static String repare(String html) {
		html = html.replaceAll("<!--.*?-->", "");
		html = html.replaceAll("<script.*?<\\script>", "");
		html = html.replaceAll("<SCRIPT.*?<\\SCRIPT>", "");
		// html = html.replaceAll(" +", "");
		html = html.replaceAll("\\t*", "");
		html = html.replaceAll("\\n|\\r", "");
		System.out.println(html);
		return html;
	}

	private static RuleMap preprocess(String rule) {
		Matcher matcher = patternKeys.matcher(rule);
		List<String> keys = new ArrayList<String>();
		while (matcher.find()) {
			int count = matcher.groupCount();
			for (int i = 1; i <= count; i++) {
				keys.add(offShell(matcher.group(i)));
			}
		}

		String tmpStr = rule.replaceAll(KEY, "(.*?)");
		return new RuleMap(tmpStr, keys);
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
