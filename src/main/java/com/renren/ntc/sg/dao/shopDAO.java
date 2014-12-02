package com.renren.ntc.sg.dao;

import java.util.List;

import com.renren.ntc.sg.bean.Shop;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "ABC")
public interface ShopDAO {
    static final String TABLE_NAME= "shop";
    static final String FIELDS = "id, name,owner_phone,head_url,shop_url,lng,lat" ;
    static final String INSERT_FIELDS = "name,owner_phone,head_url,shop_url,lng,lat" ;

	@SQL("select " +FIELDS  + "  from "  + TABLE_NAME + " where  lat < :1 and lat > :2 and lng < :3 and lng > :4")
	public List<Shop> getShop(double lat1 ,double lat2, double lng1, double lng2);

    @SQL("select " +FIELDS  + "  from "  + TABLE_NAME + " where id = 1")
    public Shop getShop(int shop_id);
	
	
	@SQL("insert into " + TABLE_NAME + "(" + FIELDS +" ) values"  + " (:1.name,:1.owner_phone,:1.head_url,:1.shop_url,:1.lng,:1,lat)")
	public int insert(Shop o);
	
	
}
