package com.renren.ntc.sg.util;

import com.renren.ntc.sg.bean.Item;
import net.paoding.rose.scanning.context.RoseAppContext;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;

import com.renren.ntc.sg.dao.ItemsDAO;


public class ReadExcel {
    XSSFWorkbook xwb;

    public ReadExcel() throws IOException {
        xwb = new XSSFWorkbook("E:\\lelin.xlsx");
    }

    public XSSFSheet get() {
        return xwb.getSheetAt(0);
    }

    public static void main(String[] args) throws IOException {
        RoseAppContext rose = new RoseAppContext();
        ItemsDAO itemDao = rose.getBean(ItemsDAO.class);
        // 读取第一章表格内容
        ReadExcel xwb = new ReadExcel();
        XSSFSheet sheet = xwb.get();
        // 定义 row、cell
        XSSFRow row;
        String cell;


        for (int i = sheet.getFirstRowNum(); i < sheet.getPhysicalNumberOfRows(); i++) {
            if (i == 0){
                continue;
            }
            row = sheet.getRow(i);
            Item it  = new Item() ;
            long  shop_id = 1;
            it.setShop_id(shop_id);
            it.setCategory_id(22);
            for (int j = row.getFirstCellNum(); j < row.getPhysicalNumberOfCells(); j++) {
                // 通过 row.getCell(j).toString() 获取单元格内容，
                cell = row.getCell(j).toString().trim();
                System.out.print(cell + "-" );
                if(j == 2){

                   it.setSerialNo(cell);
                }
                if(j == 3){
                    if (StringUtils.isEmpty(cell)) {
                        break;
                    }
                    it.setName(cell);
                }
                if(j == 5){
                    it.setPrice(toPrice(cell));
                }
                if(j == 6){
                    it.setPrice_new(toPrice(cell));
                }

            }
            System.out.println("");
            if (!StringUtils.isEmpty(it.getName()) && !StringUtils.isEmpty(it.getSerialNo()) ) {
                itemDao.insert(SUtils.generTableName(shop_id) ,it);
            }
        }
    }


    private static int toPrice(String cell) {
        Float r = Float.valueOf(cell);
        return (int)(r*100);
    }
}