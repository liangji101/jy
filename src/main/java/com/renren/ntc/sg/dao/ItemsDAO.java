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
    static final String FIELDS = "id, shop_id,name,category_id,category_sub_id,score ,price,price_new ,count,pic_url,create_time,update_time" ;
    static final String INSERT_FIELDS = "shop_id,name,category_id,category_sub_id,score, price,price_new,count,pic_url,create_time,update_time" ;

    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where shop_id =:1")
    public List<Item> getItems(long shop_id);


    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where id =:2")
    public  Item getItem(long shop_id, long id );

    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where shop_id =:1 and category_id=:2 order by score asc limit :3 , :4")
    public List<Item> getItems(long shop_id, int category_id ,int from, int offset );

    @SQL("update " + TABLE_NAME + " set count = count-:3  where id =:2")
    public void decr(long s_id, long i_id, int count);

    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where  shop_id=:1  order by score  desc limit :2,:3")
    public  List<Item> hot(long shop_id, int flag ,int offset );


    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  where  shop_id=:1 and name like =:2")
    public  List<Item> search(long shop_id, String key);
}
