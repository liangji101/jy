package com.renren.ntc.sg.controllers.sg;

import com.alibaba.fastjson.JSONObject;
import com.renren.ntc.sg.bean.Device;
import com.renren.ntc.sg.bean.OrderInfo;
import com.renren.ntc.sg.dao.DeviceDAO;
import com.renren.ntc.sg.dao.SWPOrderDAO;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.util.Constants;
import com.renren.ntc.sg.util.SUtils;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
            LoggerUtils.getInstance().log(String.format("pid illegal error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        Device dev  = deviceDAO.getDev(pid);
        if(null == dev){
            LoggerUtils.getInstance().log(String.format("dev is null error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        if (!dev.getToken().equals(token)){
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token ,dev.getToken() ) );
            return "@" + Constants.PARATERERROR;
        }

        List<OrderInfo> orderinfo = swpOrderDAO.getOrder2Print();

        deviceDAO.update(pid,"looping") ;
        JSONObject jb =  new JSONObject() ;
        jb.put("code" ,0);
        jb.put("data" , SUtils.from(orderinfo));
        return "@" + jb.toJSONString();
	}

    @Get("fb")
    @Post("fb")
    public String fb( Invocation inv,@Param("pid") long  pid , @Param("token")String token, @Param("orderId")String orderId, @Param("re") String re, @Param("msg") String msg )  {
        // 验证
        LoggerUtils.getInstance().log(String.format("fb request param  %d ,%s  ,re: %s , msg : %s ", pid, token  , re, msg ) );
        if ( 0 > pid) {
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        Device dev  = deviceDAO.getDev(pid);
        if(null == dev){
            LoggerUtils.getInstance().log(String.format("error param  %d ,%s", pid, token  ) );
            return "@" + Constants.PARATERERROR;
        }
        if (!dev.getToken().equals(token)){
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token ,dev.getToken() ) );
            return "@" + Constants.PARATERERROR;
        }
        int r = 0 ;
        if("true".equals(re)) {
          r = swpOrderDAO.update(3,orderId);
        }
        if (r != 1){
            LoggerUtils.getInstance().log(String.format("update order %s  pid  %d  token %s",orderId, pid, token  ) );
        }
        return "@" + Constants.DONE;
    }

    @Get("update")
    @Post("update")
    public String update( Invocation inv,@Param("pid") long pid , @Param("token")String token,  @Param("status") String status)  {
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
        if (!dev.getToken().equals(token)){
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token ,dev.getToken() ) );
            return "@" + Constants.PARATERERROR;
        }
        deviceDAO.update(pid,status) ;
        return  "@" +Constants.DONE;
    }

    @Get("rp")
    @Post("rp")
    public String rp( Invocation inv,@Param("pid") long pid , @Param("token")String token,  @Param("status") String status)  {
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
        if (!dev.getToken().equals(token)){
            LoggerUtils.getInstance().log(String.format("token illegal error param  %d ,%s ,%s ", pid, token ,dev.getToken() ) );
            return "@" + Constants.PARATERERROR;
        }
        LoggerUtils.getInstance().log(String.format("pinter %d  , fb :%s , ",pid,status));
        return  "@" + Constants.DONE;
    }


}
