package com.renren.ntc.sg.controllers.sg;

import com.renren.ntc.sg.bean.*;
import com.renren.ntc.sg.dao.ItemsDAO;
import com.renren.ntc.sg.dao.SWPOrderDAO;
import com.renren.ntc.sg.dao.ShopCategoryDAO;
import com.renren.ntc.sg.dao.ShopDAO;
import com.renren.ntc.sg.interceptors.access.NtcHostHolder;
import com.renren.ntc.sg.service.AddressService;
import com.renren.ntc.sg.service.LoggerUtils;
import com.renren.ntc.sg.util.Constants;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Path("shop")
public class AddressController {

    private static int DEFAULT_SHOP_ID = 1;

    @Autowired
    public AddressService addressService;

    @Autowired
    NtcHostHolder  ntcHostHolder;


    @Get("")
    public String get (Invocation inv){
        User u = ntcHostHolder.getUser();
        if ( null ==u ||  0 >= u.getId()){
              return "error";

        }

        List<Address> ls  = addressService.getAddresses(u.getId());
          inv.addModel("addressls",ls);
          return "address" ;
        }

    @Get("del")
    public String del (Invocation inv , long address_id){

        User u = ntcHostHolder.getUser();
        if ( null ==u ||  0 >= u.getId()){
            return "error";
        }
        Address  address  = addressService.getAddress(address_id);
        if (u.getId() !=  address.getUser_id()){
            inv.addModel("msg" ,"无修改权限，请刷新后再试");
            return "error";
        }
        inv.addModel("addressls",address);
        return "address" ;
    }


    @Get("add")
    public String add (Invocation inv ,@Param("phone") String phone,@Param("name") String name ,@Param("address") String address){
        User u = ntcHostHolder.getUser();
        if ( null ==u ||  0 >= u.getId()){
            inv.addModel("msg" ,"请刷新后再试");
            return "error";
        }
        Address add = new Address();
        add.setUser_id(u.getId());
        add.setAddress(address);
        add.setName(name);
        add.setPhone(phone);
        addressService.addAddress(add) ;

        return Constants.DONE;
    }

    @Get("update")
    public String update (Invocation inv ,@Param("address_id") long address_id ,@Param("phone") String phone,@Param("name") String name ,@Param("address") String address){
        User u = ntcHostHolder.getUser();
        if ( null ==u ||  0 >= u.getId()){
            inv.addModel("msg" ,"请刷新后再试");
            return "error";
        }
        Address add = new Address();
        add.setId(address_id);
        add.setUser_id(u.getId());
        add.setAddress(address);
        add.setName(name);
        add.setPhone(phone);
        addressService.updateAddress(add) ;
        return Constants.DONE;
    }
}


