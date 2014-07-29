package com.renren.ntc.video.utils.renren.utils;

import com.renren.ntc.video.utils.Constants;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static final Logger logger = Logger.getLogger(ConfigUtil.class);

    public ConfigUtil(){}
    private static Properties props = new Properties(); 
    static{
        try {
            props.load(ConfigUtil.class.getResourceAsStream("/apiconfig.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getValue(String key){
        return props.getProperty(key);
    }

    public static void updateProperties(String key,String value) {    
            props.setProperty(key, value); 
    } 
}
