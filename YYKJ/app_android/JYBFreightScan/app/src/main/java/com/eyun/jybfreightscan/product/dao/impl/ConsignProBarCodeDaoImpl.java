package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybfreightscan.product.dao.ConsignProBarCodeDao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsignProBarCodeDaoImpl implements ConsignProBarCodeDao {
    private static ConsignProBarCodeDaoImpl consignProBarCodeDao = null;

    public static ConsignProBarCodeDaoImpl getInstance() {
        if (consignProBarCodeDao == null) {
            synchronized (new ConsignProBarCodeDaoImpl()) {
                if (consignProBarCodeDao == null) {
                    consignProBarCodeDao = new ConsignProBarCodeDaoImpl();
                }
            }
        }
        return consignProBarCodeDao;
    }
    @Override
    public boolean hasThisProduct(String recNumber, String barCode) {
        String sql="Select *  from ConsignProBarCode where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber)
                +" and barCode="+ SqlStringUtils.GetQuotedString(barCode);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list.size()>0) {
            return true;
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> getProducts(String recNumber) {
        String sql=" select * from ConsignProBarCode where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        return list;
    }
}
