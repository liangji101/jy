package com.renren.ntc.sg.service;

import com.renren.ntc.sg.bean.Address;
import com.renren.ntc.sg.dao.AddressDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-8
 * Time: 下午12:09
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AddressService {

    @Autowired
    private AddressDAO addressDAO;



    public List<Address> getAddresses(long user_id   ) {
        return addressDAO.getAddresses(user_id);
    }

    public int  addAddress(Address address) {
        return addressDAO.addAddress(address);
    }

    public int updateAddress(Address address) {
        return addressDAO.updateAddress(address);
    }

    public Address getAddress(long address_id ) {
        return addressDAO.getAddress(address_id);
    }

    public int delAddress(long address_id ) {
        return addressDAO.delAddress(address_id);
    }


}