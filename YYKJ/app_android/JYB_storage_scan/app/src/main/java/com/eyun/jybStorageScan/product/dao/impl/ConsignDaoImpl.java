package com.eyun.jybStorageScan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybStorageScan.product.dao.ConsignDao;
import com.eyun.jybStorageScan.product.entity.Consign;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */

public class ConsignDaoImpl implements ConsignDao {

    private static ConsignDaoImpl ConsignDao = null;

    public static ConsignDaoImpl getInstance() {
        if (ConsignDao == null) {
            synchronized (new ConsignDaoImpl()) {
                if (ConsignDao == null) {
                    ConsignDao = new ConsignDaoImpl();
                }
            }
        }
        return ConsignDao;
    }
    @Override
    public Consign getConsign(String ConsignCode) {
        String sqlSelect="Select * from storage_Consign where recNumber="+ SqlStringUtils.GetQuotedString(ConsignCode);
        List<Consign> list= BaseDao.getInstance().query(sqlSelect,Consign.class);
        if(list!=null&&list.size()==1)
            return list.get(0);
        return  null;
    }

    @Override
    public boolean updateConsign(String recNumber,int RecState,int RecForwardedState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecState",RecState);
        map.put("RecForwardedState",RecForwardedState);
        String where=" where RecNumber="+SqlStringUtils.GetQuotedString(recNumber);
        String sqlUpdate=SqlStringUtils.GetConstructionUpdate("storage_Consign",map,where);
        return BaseDao.getInstance().excute(sqlUpdate);
    }
}
