package com.renren.ntc.video.biz.model;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Chris Gao
 * @version Create Time：2011-4-20 下午06:04:17
 * 
 */
@Component
public class MapsBean {

    /** 人人视频分类Map **/
    private Map<Integer, String> channelsMap;
    

    
    /** 56视频分类Map **/
    private final static Map<Integer, String> channel56Map = new HashMap<Integer, String>();
    static {
        channel56Map.put(1, "娱乐");
        channel56Map.put(2, "热点");
        channel56Map.put(3, "原创");
        channel56Map.put(4, "搞笑");
        channel56Map.put(8, "动漫");

        channel56Map.put(10, "科教");
        channel56Map.put(11, "女性");
        channel56Map.put(14, "体育");
        channel56Map.put(25, "电影");
        channel56Map.put(26, "游戏");
        channel56Map.put(27, "旅游");
        channel56Map.put(28, "汽车");

        channel56Map.put(34, "母婴");
        channel56Map.put(35, "电视剧");
        channel56Map.put(41, "音乐");
    }
    
    private final static Map<Integer, Integer> renrenTo56 = new HashMap<Integer, Integer>();
    static{
        //renrenTo56.put(renren, 56);
        renrenTo56.put(1, 1);
        renrenTo56.put(3, 3);// 乐活 没有对应分类
        renrenTo56.put(5, 4);
        renrenTo56.put(9, 8);
        renrenTo56.put(10, 26);
        renrenTo56.put(14, 41);
        renrenTo56.put(15, 14);
        renrenTo56.put(21, 10);
        renrenTo56.put(22, 25);
        renrenTo56.put(24, 3); // 财富 没有对应分类
        renrenTo56.put(25, 10);
        renrenTo56.put(26, 28);
        renrenTo56.put(27, 11);
        renrenTo56.put(29, 2);
        renrenTo56.put(30, 35);
        renrenTo56.put(31, 1); // 综艺 --- > 娱乐
        renrenTo56.put(32, 2); // 风尚 ---> 热点
        renrenTo56.put(99, 3); 
    }
    
    private final static Map<Integer, Integer> toRenRen = new HashMap<Integer, Integer>();
    static{
        //renrenTo56.put(56, renren);
        toRenRen.put(1, 1);
        toRenRen.put(2, 29);
        toRenRen.put(3, 99);
        toRenRen.put(4, 5);
        toRenRen.put(8, 9);
        
        toRenRen.put(10, 21);
        toRenRen.put(11, 27);
        toRenRen.put(14, 15);
        toRenRen.put(25, 22);
        toRenRen.put(26, 10);
        toRenRen.put(27, 99); // 旅游 没有对应分类
        toRenRen.put(28, 26);
        
        toRenRen.put(34, 99); // 母婴 没有对应分类
        toRenRen.put(35, 30);
        toRenRen.put(41, 14);
    }
    
    /**
     * 通过人人分类得到56分类
     * 
     * @param renrenChannel
     * @return
     */
    public int get56Channel(int renrenChannel) {
        if (renrenTo56.containsKey(renrenChannel)) {
            return renrenTo56.get(renrenChannel);
        }
        return 1;
    }
    
    /**
     * 通过56分类得到人人分类
     * 
     * @param channel56
     * @return
     */
    public int getRenRenChannel(int channel56) {
        if(toRenRen.containsKey(channel56)){
            return toRenRen.get(channel56);
        }
        return 99;
    }

    public Map<Integer, String> getChannelsMap() {
        return channelsMap;
    }

    public void setChannelsMap(Map<Integer, String> channelsMap) {
        this.channelsMap = channelsMap;
    }

    public String getChannel(int id) {
        return this.channelsMap.get(id);
    }
    
    /**
     * 判断是否有该channelId
     * @param channelId 分类Key
     */
    public boolean contains(int channelId){
    	return  channelsMap.containsKey(channelId);
    }

}
