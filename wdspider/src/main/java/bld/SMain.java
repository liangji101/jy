package bld;

import bean.OrderInfo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dao.SWPOrderDAO;
import net.paoding.rose.scanning.context.RoseAppContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import util.Constants;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SMain {

    public static String URL = "http://koudaitong.com/v2/trade/order/list.json?p=%s&type=all&state=all&orderby=book_time&order=desc";
    public static Logger logger = LoggerFactory.getLogger(SMain.class);

    public static String KEY = "(\\$\\{\\w*\\})";
    public static Pattern patternKeys = Pattern.compile(KEY);

    public static String RULE = "rule=";

    /**
     * @param args
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

        RoseAppContext rose = new RoseAppContext();
        SWPOrderDAO wpDao = rose.getBean(SWPOrderDAO.class);
        for (int t = 1; t < 100; t++) {
            byte[] text = SHttpClient.getURLData(String.format(URL, t + ""), "koudaitong.com");
            String reponse = toString(text);
            JSONObject ob = JSON.parseObject(reponse);
            int code = ob.getInteger("code");
            if (code != 0) {
                System.out.println("Error ....");
            }
            JSONObject o = ob.getJSONObject("data");
            JSONArray jarry = o.getJSONArray("list");
            for (int i = 0; i < jarry.size(); i++) {
                JSONObject job = jarry.getJSONObject(i);
                String order_no = job.getString("order_no");
                OrderInfo oif = new OrderInfo();
                oif.setShop_id(Constants.SHOP_ID + "");
                oif.setInfo("");
                oif.setOrder_id(order_no);
                try {
                    wpDao.insert(oif);
                } catch (Exception e) {
                    if (e instanceof DataIntegrityViolationException) {
                        System.out.println(" order_no wrapper  ready   exit");
                        System.exit(0);
                    }
                    e.printStackTrace();
                }
            }
        }
    }

    private static String toString(byte[] text) {
        try {
            String rep = new String(text, "utf-8");
            System.out.println("response : " + rep);
            return rep;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
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
