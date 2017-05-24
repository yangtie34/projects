package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.L;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.product.dao.ConsignProBarCodeDao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
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
    public int GetCount(String barCode) {
        String sql="Select count(1) Count from ConsignProBarCode where BarCode="+ SqlStringUtils.GetQuotedString(barCode);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("Count"));
        }
        return 0;
    }

    @Override
    public String GetRecNumber(String barCode) {

        String sql="Select top 1 RecNumber from ConsignProBarCode where BarCode="+ SqlStringUtils.GetQuotedString(barCode);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toString(list.get(0).get("RecNumber"));
        }
        return "";
    }

    @Override
    public List<Map<String, Object>> GetSimpleInfo(String barCode) {
        String sql="Select distinct a.RecNumber,b.ComBrNameDelivery,b.DeliveryName,b.ReceiveName,b.ProNumber from ConsignProBarCode a left join Consign b on a.RecNumber=b.RecNumber"
                  +" where a.BarCode="+ SqlStringUtils.GetQuotedString(barCode);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        return list;
    }

    @Override
    public int GetSumCount(String recNumber) {
        String sql = "Select Count(1) Count from ConsignProBarCode where RecNumber=" + SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String, Object>> list = BaseDao.getInstance().queryForList(sql);
        if (list != null && list.size() > 0) {
            return TypeConvert.toInteger(list.get(0).get("Count").toString());
        }
        return -1;
    }
}
