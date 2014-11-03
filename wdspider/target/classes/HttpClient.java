import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {
	private static final int CONN_TIMEOUT = 10000;
	private static final int READ_TIMEOUT = 10000;
	private static final int RETRY = 5;

	public static Logger logger = LoggerFactory.getLogger(HttpClient.class);

	private static byte[] getStreamData(InputStream is) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] bufferByte = new byte[1024 * 30];
		int l = -1;
		int downloadSize = 0;
		try {
			while ((l = is.read(bufferByte)) > -1) {
				downloadSize += l;
				out.write(bufferByte, 0, l);
				out.flush();
			}
		} catch (Exception e) {
			logger.info("getStreamData Exception!!");
			logger.info(e.getMessage());
			e.printStackTrace();
			throw e;
		}
		byte[] btemp = out.toByteArray();
		logger.info("download size: " + downloadSize + ", byte array size: " + out.size());
		out.close();
		is.close();
		return btemp;
	}

	private static String _GetCookie() {
		return "KDTSESSIONID=pqpaoav724sdebgj9cq4cr62g3; CNZZDATA5351147=cnzz_eid%3D823593287-1414036180-http%253A%252F%252Fcn.bing.com%252F%26ntime%3D1414062506; captcha_invalid_count=1; captcha_response=dca4ab3dfb7687eb980d5c08dbda608b38f7a948; captcha_valid_count=1; user_id=881361; user_weixin=13581861097; user_nickname=%E5%B8%85%E7%82%9C; kdtnote_fans_id=0; login_auth_key=9a4e82076efbeefc7eac9d4db035aa45; check_time=1414062544; Hm_lvt_61f180f659535f6bde14d3b908c3a7d0=1414036181,1414062507; Hm_lpvt_61f180f659535f6bde14d3b908c3a7d0=1414062545; kdt_id=389162; team_auth_key=3987cc57f9a528c9fbb4950cb7396ecc";
	}

	
	
	public static byte[] getURLData(String url, String host) throws IOException {
		byte[] b = null;
		boolean bsuccess = false;
		int retry = 0;
		while (!bsuccess && retry < RETRY) {
			try {
				URL imageURL = new URL(url);
				URLConnection c = null;
				c = imageURL.openConnection();
				c.setConnectTimeout(CONN_TIMEOUT);
				c.setReadTimeout(READ_TIMEOUT);
				c.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
				c.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0");
				c.setRequestProperty("Host", host);
				c.setRequestProperty("Cookie", HttpClient._GetCookie());
				c.setDoOutput(true);
				c.setDoInput(true);
				logger.info("use to download url: " + url);
				b = getStreamData(c.getInputStream());
				bsuccess = true;
			} catch (Exception e) {
				retry++;
				e.printStackTrace();
			}
		}
		return b;
	}

}
