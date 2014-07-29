package com.renren.ntc.video.utils.renren.services;

import com.renren.ntc.video.utils.renren.RenrenApiInvoker;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


/**
 * 
 * @author DuYang (yang.du@renren-inc.com) 2011-12-14
 *
 */
public abstract class BaseService {
    protected RenrenApiInvoker invoker;

    public BaseService(RenrenApiInvoker invoker) {
        this.invoker = invoker;
    }

    protected String getResultValue(TreeMap<String, String> params, String propertyName) {
        String ret = this.invoker.sendPostRestRequest(params);
        JSONObject obj = JSONObject.fromObject(ret);
        Object o = obj.get(propertyName);
        if (o == null)
            return "";
        return o.toString();
    }

    protected int getResultInt(TreeMap<String, String> params) {
        String str = this.getResultValue(params, "result");
        return Integer.parseInt(str.trim());
    }

    protected int getResultInt(TreeMap<String, String> params, String key) {
        String str = this.getResultValue(params, key);
        return Integer.parseInt(str.trim());
    }

    protected boolean getResultBoolean(TreeMap<String, String> params) {
        int r = this.getResultInt(params);
        if (r == 1) {
            return true;
        }
        return false;
    }

    protected JSONObject getResultJSONObject(TreeMap<String, String> params) {
        String ret = this.invoker.sendPostRestRequest(params);
        System.out.println("//return--- " + ret);
        return JSONObject.fromObject(ret);
    }

    protected JSONArray getResultJSONArray(TreeMap<String, String> params) {
        String ret = this.invoker.sendPostRestRequest(params);
        System.out.println("//return--- " + ret);
        return JSONArray.fromObject(ret);
    }

    protected List<Integer> getResultIntList(TreeMap<String, String> params) {
        JSONArray array = this.getResultJSONArray(params);
        List<Integer> ints = new ArrayList<Integer>();
        for (int i = 0; i < array.size(); i++) {
            ints.add(Integer.parseInt(array.get(i).toString()));
        }
        return ints;
    }

    protected List<String> getResultStringList(TreeMap<String, String> params) {
        JSONArray array = this.getResultJSONArray(params);
        List<String> strs = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            strs.add(array.get(i).toString());
        }
        return strs;
    }
}
