package util;

/**
 * Created by allen on 11/9/14.
 */
public class Constants {
    public static  final int  SHOP_ID = 10010;
    public static  final String HEADER ="^F1";
    public static  final String TITLE ="^H2";
    public static  final String BR ="\n";
    public static  final String SPILT  ="********************************";
    public static  final String SPILT2 ="================================";

    public static  final String ORDER_ADDRESS = "配送地址：";

    public static  final String ORDER_DETAIL = " 订单明细：";
    public static  final String ORDER_HEAD =   " 名称               数量  价格  ";
    public static  final String ORDER_TOTAL  = " 总计                  ：{total}元";
    public static  final String DCODE_TITLE = " 点击下面的链接关注我们：";
    public static  final String WEIXIN_URL = "^Q +http://weixin.qq.com/r/xxxx";
    public static  final String FOOTER = "^H2 喵喵一下 ，便利到家";

    public static final String ORDER_TEMP = "^H2{shop_name}\n"  +
                                            "********************************" +
                                            "配送地址：\n" +
                                            "{address}\n" +
                                            "********************************" +
                                            " 订单明细：\n" +
                                            " 名称               数量  价格 \n" +
                                            "{item}" +
                                            "================================" +
                                            " 总计                  ：{total}元\n"+
                                            "********************************" +
                                            "^Q +http://weixin.qq.com/r/xxxx";

    public static final String ORDER_TEMP_ITEM = " {name} {count} {price}  ";


}
