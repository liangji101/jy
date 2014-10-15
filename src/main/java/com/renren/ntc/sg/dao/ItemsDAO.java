package com.renren.ntc.sg.dao;

import com.renren.ntc.sg.bean.Shop;
import com.renren.ntc.video.utils.Constants;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = Constants.DB_TOKEN)
public interface ItemsDAO {
    static final String TABLE_NAME= "shop";
    static final String FIELDS = "name,owner,owner_phone,head_url,shop_url,create_time,updatetime" ;

	@SQL("select "+ FIELDS +" from asyncJob where id =:1")
	public Shop getShop(long shop_id);
}
