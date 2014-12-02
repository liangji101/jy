package com.renren.ntc.sg.dao;

import com.renren.ntc.sg.bean.Item;
import com.renren.ntc.sg.bean.Shop;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/*
CREATE TABLE `items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT ,
  `shop_id` bigint(20) NOT NULL DEFAULT 0 ,
  `name` varchar(24) NOT NULL DEFAULT '' ,
  `count` int(11) NOT NULL DEFAULT 0 ,
  `head_url` varchar(256) NOT NULL DEFAULT '' ,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   PRIMARY KEY (`id`),
   KEY shop_id(`shop_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


 */


@DAO(catalog = "ABC")
public interface ItemsDAO {
    static final String TABLE_NAME= "items";
    static final String FIELDS = "id, shop_id,name,count,head_url,create_time,updatetime" ;

	@SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where shop_id =:1")
	public List<Item> getItems(long shop_id);


    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where id =:1")
    public Item getItem(long shop_id);

    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where id =:1")
    public List<Item> getItems(List <Long> items);
}
