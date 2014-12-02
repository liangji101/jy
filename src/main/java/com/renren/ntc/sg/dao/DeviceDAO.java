package com.renren.ntc.sg.dao;

import com.renren.ntc.sg.bean.Device;
import net.paoding.rose.jade.annotation.SQL;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-2
 * Time: 下午1:10
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceDAO {
    static final String TABLE_NAME= "device";
    static final String FIELDS = "id, name,owner_phone,head_url,shop_url,lng,lat" ;
    static final String INSERT_FIELDS = "name,owner_phone,head_url,shop_url,lng,lat" ;

    @SQL("select * from " + TABLE_NAME +" where id =:1")
    public Device getDev(long pid ) ;


    @SQL("update " + TABLE_NAME +" set status = :2 where id =:1")
    public Device update(long pid, int status) ;


}
