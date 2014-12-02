package bld;

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

import dao.SWPOrderDAO;
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
import util.Constants;

public class Printer {
	public static String URL = "http://printer.showutech.com/api/2/service/add_order/sutp010639/{id}/";

	
	public static Logger logger = LoggerFactory.getLogger(Printer.class);

	public static String KEY = "(\\$\\{\\w*\\})";
	public static Pattern patternKeys = Pattern.compile(KEY);

	public static String RULE = "rule=";

	/**
	 * @param args
	 * @throws java.io.IOException
	 */
	public static void main(String[] args) throws IOException {
//       System.out.println(new Date(working()).toLocaleString());
//		if(true){
//		   return ;
//		}
		RoseAppContext rose = new RoseAppContext();
		SWPOrderDAO wpDao = rose.getBean(SWPOrderDAO.class);
		ShopDAO shopDao = rose.getBean(ShopDAO.class);
		Shop shop = shopDao.getShop(Constants.SHOP_ID);
		List<OrderInfo> values = wpDao.getPrintNo();
		for (OrderInfo value : values) {
			byte[] re = null ;
			String v = null;
			String url;
			String mobile ="";
			String response = null;
			long current = System.currentTimeMillis();
            url = URL.replace("{id}",value.getId()+"");
			// send SMS to Shop owner
			v = getbldValue(shop.getName(), value.getInfo());
            byte [] b = SHttpClient.sendPostRequest(url,  v);
            response = toString(b);
			System.out.println(String.format("Post Shop SMS message No. %s : %s ,  %s ,data:%s", value.getOrder_id(), response  , url,v));
			if(isOk(response) ){
                wpDao.update(2, value.getOrder_id());
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
        int i = response.indexOf("code");
        int i2 = response.indexOf("&");
		if ("1".equals(response.substring(1 + 4, i2))) {
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
	
	private static String getbldValue(String shop_name ,String address) {
        //#address#=北京市 市辖区 西城区 1分种 朱，x x 18600326217 100086&#orderDetail#=德芙巧克力，盒装*|6*|35.00r|
        StringBuffer sb = new StringBuffer();
//        sb.append(Constants.HEADER);
        sb.append(Constants.TITLE);
        sb.append(shop_name);
        sb.append(Constants.BR);
        sb.append(Constants.SPILT);
        sb.append(Constants.ORDER_ADDRESS);
        sb.append(Constants.BR);
        String adr = getAdress(address);
        sb.append(" " + adr);
        sb.append(Constants.BR);
        sb.append(Constants.SPILT);
        sb.append(Constants.ORDER_DETAIL);
        sb.append(Constants.BR);
        sb.append(Constants.ORDER_HEAD);
        sb.append(Constants.BR);

        String[] ods = getOrders(address);
        sb.append(Constants.BR);
        int sum = 0 ;
        for (String od: ods) {
            int a = getItem(od);
            sum+=a;
            sb.append(fomat(od));
            sb.append(Constants.BR);
        }
        sb.append(Constants.SPILT2);
        sb.append(total(Constants.ORDER_TOTAL,sum));
        sb.append(Constants.BR);
        sb.append(Constants.SPILT);
        sb.append(Constants.DCODE_TITLE);
        sb.append(Constants.BR);
//        sb.append(Constants.WEIXIN_URL);
        sb.append(Constants.BR);
        sb.append(Constants.FOOTER);
        sb.append(Constants.BR);
        return sb.toString();
	}

    private static String  total(String orderTotal, int sum) {
        return orderTotal.replace("{total}",sum/100 + "");
    }


    private static String getAdress(String address){
        int index = address.indexOf("#address#");
        int index2 = address.indexOf("&#orderDetail#");
        if(-1== index||-1 == index ){
            return "";

        }
        return address.substring(index+10,index2);
    }
    private static String fomat(String od){
        StringBuffer sb = new StringBuffer();
        String[] ss =  od.split("\\*\\|");
        sb.append(" ");
        int d = 0 ;
        for (String s :ss){
            sb.append(s);
            if (0 ==d) {
                String sbb = sb.toString();
                int a = 19 - length(s);
                if (a < 0) {
                    sb.append(" ");
                } else {
                    for (int i = 0; i < a; i++)
                        sb.append(" ");
                }
            }else if (d ==1) {
                 String sbb = sb.toString();
                int a = 6 - s.length();
                if (a < 0||length(sbb)>25) {
                    sb.append(" ");
                } else {
                    for (int i = 0; i < a; i++)
                        sb.append(" ");
                }
            }
            d++;
        }
        return sb.toString();
    }

    private static int length(String s) {
        char [] cs = s.toCharArray();
        int length = 0;
        for (char c: cs){
             if(isChinese(c)){
                 length += 2;
             }
             else{
                 length += 1;
             }
        }
        return length;
    }

    private static String[] getOrders(String address) {

        int index = address.indexOf("#orderDetail#");
        if(-1 == index ){
            return new String[0];

        }
        String order = address.substring(index+14,address.length());
        String[]  ords = order.split("r\\|");
        return ords;
    }

    private static int getItem(String ob) {
        String[] obs = ob.split("\\*\\|");
        String sum = obs[obs.length-1];
        if (-1 ==sum.indexOf("\\.")){
            sum = sum+"00";
        }else{
            sum.replace("\\.","");
        }
        int a = 0 ;
        try {
             a = Integer.valueOf(sum);
         }catch(Exception e){
             e.printStackTrace();
         }
         return a;

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

    private static boolean isChinese(char c) {

        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);

        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A

                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B

                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION

                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS

                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {

            return true;

        }
        return false;
    }




}
