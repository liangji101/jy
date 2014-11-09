import util.Constants;

import java.io.UnsupportedEncodingException;
import java.util.Random;


public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		//System.out.println(" #address#=北京市 市辖区 萝 1斤 进口红西柚（又称葡萄柚|） 2斤 红富士苹果 1斤".split("\\|").length);
//		System.out.println(URLDecoder.decode("http://v.juhe.cn/sms/send?key=99209217f5a5a1ed2416e5e6d2af87fd&dtype=json&mobile=18600326217&tpl_id=791&tpl_value=%23fruit%23%3D%E8%BF%9B%E5%8F%A3%E5%A4%A7%E8%8F%A0%E8%90%9D+1%E6%96%A4+%E8%BF%9B%E5%8F%A3%E7%BA%A2%E8%A5%BF%E6%9F%9A%EF%BC%88%E5%8F%88%E7%A7%B0%E8%91%A1%E8%90%84%E6%9F%9A%EF%BC%89+2%E6%96%A4+%E7%BA%A2%E5%AF%8C%E5%A3%AB%E8%8B%B9%E6%9E%9C+1%E6%96%A4+"));
		System.out.println("德芙巧克,力盒装".getBytes("utf-8").length );
        System.out.println(Constants.ORDER_HEAD.length());

	}

}
