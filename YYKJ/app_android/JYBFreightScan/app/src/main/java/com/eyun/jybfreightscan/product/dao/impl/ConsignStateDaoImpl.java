package com.eyun.jybfreightscan.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybfreightscan.product.dao.ConsignStateDao;
import com.eyun.jybfreightscan.product.entity.ConsignState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/6.
 */

public class ConsignStateDaoImpl implements ConsignStateDao {

    private static ConsignStateDaoImpl consignStateDao = null;

    public static ConsignStateDaoImpl getInstance() {
        if (consignStateDao == null) {
            synchronized (new ConsignStateDaoImpl()) {
                if (consignStateDao == null) {
                    consignStateDao = new ConsignStateDaoImpl();
                }
            }
        }
        return consignStateDao;
    }

    @Override
    public String GetSqlAdd(ConsignState mo) {

        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("RecNumber",SqlStringUtils.GetQuotedString(mo.getRecNumber()));
        map.put("CreateComBrId",mo.getCreateComBrId());
        map.put("CreateComId",mo.getCreateComId());
        map.put("CreateIp",SqlStringUtils.GetQuotedString(mo.getCreateIp()));
        map.put("CreateUserId",mo.getCreateUserId());
        map.put("LocationLat",mo.getLocationLat());
        map.put("LocationLng",mo.getLocationLng());
        map.put("RecState",mo.getRecState());
        map.put("RecType",mo.getRecType());
        map.put("Remark",SqlStringUtils.GetQuotedString(mo.getRemark()));
        map.put("ScanTime",SqlStringUtils.GetQuotedString(mo.getScanTime()));

        return SqlStringUtils.GetConstructionInsert("ConsignState",map);
    }

    @Override
    public boolean IsExists(String recNumber, int state, long comBrID) {

        String sql="Select  1 from ConsignState where RecNumber="+SqlStringUtils.GetQuotedString(recNumber)
                +" and RecState="+state+" and CreateComBrId="+comBrID;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        if(list!=null&&list.size()==1)
            return true;
        return  false;
    }
}
