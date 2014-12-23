package com.renren.ntc.sg.biz.dao;

import com.renren.ntc.sg.bean.Item;
import com.renren.ntc.sg.bean.Product;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-23
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
@DAO(catalog = "ABC")
public interface ProductDAO {

    static final String TABLE_NAME= "product";
    static final String FIELDS = "id, serialNo,name,pic_url,category_id,category_sub_id ,create_time ,update_time" ;
    static final String INSERT_FIELDS = "serialNo,name,pic_url,category_id,category_sub_id ,create_time ,update_time" ;

    @SQL("select "+ FIELDS +" from " + TABLE_NAME + " where category_id=:1 limit :2,:3 ")
    public List<Product> geProducts(long category_id ,int from ,int offset );

    @SQL("select "+ FIELDS +" from " + TABLE_NAME + "  limit :1,:2 ")
    public List<Product> geProducts(int from ,int offset );


}
