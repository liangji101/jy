package com.renren.ntc.sg.bean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: yunming.zhu
 * Date: 14-12-8
 * Time: 下午12:25
 * To change this template use File | Settings | File Templates.
 */
public class Category4v {
    private long  id  ;
    private int   type  ;
    private String name;

    public List<Item> getItemls() {
        return itemls;
    }

    public void setItemls(List<Item> itemls) {
        this.itemls = itemls;
    }

    private List<Item> itemls ;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
