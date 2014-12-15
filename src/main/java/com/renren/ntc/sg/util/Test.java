package com.renren.ntc.sg.util;

import java.net.URLEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-14
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String [] args){
        System.out.println(URLEncoder.encode("[{\"item_id\":5173,\"count\":10},{\"item_id\":5174,\"count\":10}]"));
    }
}
