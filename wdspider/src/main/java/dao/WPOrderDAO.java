package dao;

import java.util.List;

import bean.OrderInfo;
import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.SQL;

@DAO(catalog = "ABC")
public interface WPOrderDAO {
    static final String TABLE_NAME= "wp_order";
    static final String FIELDS = "id,shop_id,order_id,status,info,create_time,update_time" ;
    static final String INSERT_FIELDS = "shop_id,order_id,status,info,update_time" ;

	@SQL("select order_id  from "  + TABLE_NAME + " where status =0")
	public List<String> getOrderNo();
	
	@SQL("select order_id, info from "  + TABLE_NAME + " where status =1")
	public List<OrderInfo> getSMSNo();
	
	@SQL("insert into " + TABLE_NAME + "(" + INSERT_FIELDS +" ) values"  + " (:1.shop_id,:1.order_id,:1.status,:1.info,:1.updatetime)")
	public int insert (OrderInfo o);
	
	
	@SQL("update  " + TABLE_NAME + " set status=:1 where order_id = :2")
	public int update (int status, String order_id);
	
	@SQL("update  " + TABLE_NAME + " set info=:1 ,status = 1 where order_id = :2")
	public int updateinfo (String info,String order_id);
	
}