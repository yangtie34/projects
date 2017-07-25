package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.product.dao.ConsignDao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ConsignDaoImpl implements ConsignDao {

    private static ConsignDaoImpl consignDao = null;

    public static ConsignDaoImpl getInstance() {
        if (consignDao == null) {
            synchronized (new ConsignDaoImpl()) {
                if (consignDao == null) {
                    consignDao = new ConsignDaoImpl();
                }
            }
        }
        return consignDao;
    }


    @Override
    public int GetProQuantity(String recNumber) {
        String sql="Select ProNumber from Consign where RecNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&& list.size()==1)
        {
            return TypeConvert.toInteger(list.get(0).get("ProNumber"));
        }
        return 0;
    }
}
