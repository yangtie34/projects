package com.eyun.jybfreightscan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybfreightscan.product.dao.ProductDao;
import com.eyun.jybfreightscan.product.entity.Product;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/4.
 */

public class ProductDaoImpl implements ProductDao {

    private static ProductDaoImpl productDao = null;

    public static ProductDaoImpl getInstance() {
        if (productDao == null) {
            synchronized (new ProductDaoImpl()) {
                if (productDao == null) {
                    productDao = new ProductDaoImpl();
                }
            }
        }
        return productDao;
    }

    @Override
    public Product LoadData(String barCode) {
        String sqlSelect="Select * from Product where ProBarCode="+ SqlStringUtils.GetQuotedString(barCode);
        List<Product> list= BaseDao.getInstance().query(sqlSelect,Product.class);
        if(list!=null&&list.size()>0)
            return list.get(0);
        return null;
    }

    @Override
    public  boolean IsExist(long proID)
    {
        String sqlExists="Select 1 from Product where ProID="+proID;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sqlExists);
        if(list!=null&&list.size()==1)
            return  true;
        return  false;
    }
}
