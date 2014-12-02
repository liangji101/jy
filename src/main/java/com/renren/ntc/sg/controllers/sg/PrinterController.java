package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.Device;
import com.renren.ntc.sg.bean.Item;
import com.renren.ntc.sg.bean.OrderInfo;
import com.renren.ntc.sg.bean.Shop;
import com.renren.ntc.sg.dao.DeviceDAO;
import com.renren.ntc.sg.dao.ItemsDAO;
import com.renren.ntc.sg.dao.SWPOrderDAO;
import com.renren.ntc.sg.dao.ShopDAO;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Path("p")
public class PrinterController {

    private static int DEFAULT_S = 1;

    @Autowired
    public DeviceDAO deviceDAO;

    @Autowired
    public SWPOrderDAO swpOrderDAO ;

	/**
	 * 用于处理用户喜欢和不喜欢的ajax请求，成功返回1，失败返回0
	 * @return
	 */
    @Get("get")
	@Post("get")
	public String get( Invocation inv,@Param("pid") long  pid ,@Param("token")String token )  {
        // 验证
        if ( 0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        Device dev  = deviceDAO.getDev(pid);
        if(null == dev){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        if (dev.getToken() !=  token){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }

         List<OrderInfo> orderinfo = swpOrderDAO.getOrder2Print();
        JSONObject jb =  new JSONObject() ;
        jb.put("code" ,0);
        jb.put("date" , SUtils.from(orderinfo));
        return "@" + jb.toJSONString();
	}

    @Get("ack")
    @Post("ack")
    public String get( Invocation inv,@Param("pid") long  pid , @Param("token")String token, @Param("order_id")String orderId, @Param("status") int status )  {
        // 验证
        if ( 0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        Device dev  = deviceDAO.getDev(pid);
        if(null == dev){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        if (dev.getToken() !=  token){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        int re = swpOrderDAO.update(status,orderId);
        if (re != 1){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        return Constants.DONE;
    }

    @Get("update")
    @Post("update")
    public String update( Invocation inv,@Param("pid") long pid , @Param("token")String token, int status)  {
        // 验证
        if ( 0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        Device dev  = deviceDAO.getDev(pid);
        if(null == dev){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        if (dev.getToken() !=  token){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        deviceDAO.update(pid,status) ;
        return Constants.DONE;
    }

}
