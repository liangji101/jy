package com.renren.ntc.sg.dao;

import com.renren.ntc.sg.bean.Item;
import com.renren.ntc.sg.bean.Order;
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
public interface OrdersDAODAO {
    static final String TABLE_NAME= "orders";
    static final String FIELDS = "id, order_id,shop_id,user_id,phone,address_id,remarks ,snapshot,status,price,create_time,update_time" ;
    static final String INSERT_FIELDS = " order_id,shop_id,user_id,phone,address_id,remarks ,snapshot,status,price,create_time,update_time" ;

	@SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where user_id =:1")
	public List<Order> getOrder(long user_id);


    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where shop_id =:1")
    public List<Order> getOrderByShop(long shop_id);


}
