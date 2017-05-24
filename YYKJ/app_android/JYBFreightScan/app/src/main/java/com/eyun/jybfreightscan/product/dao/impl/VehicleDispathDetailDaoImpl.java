package com.eyun.jybfreightscan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.BaseDaoServer;
import com.eyun.jybfreightscan.product.dao.VehicleDispathDetailDao;
import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VehicleDispathDetailDaoImpl implements VehicleDispathDetailDao {
    private static VehicleDispathDetailDaoImpl vehicleDispathDetailDao = null;

    public static VehicleDispathDetailDaoImpl getInstance() {
        if (vehicleDispathDetailDao == null) {
            synchronized (new VehicleDispathDetailDaoImpl()) {
                if (vehicleDispathDetailDao == null) {
                    vehicleDispathDetailDao = new VehicleDispathDetailDaoImpl();
                }
            }
        }
        return vehicleDispathDetailDao;
    }

    @Override
    public String GetSqlAdd(VehicleDispathDetail mo) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("VehDispNumber",SqlStringUtils.GetQuotedString(mo.getVehDispNumber()));
        map.put("ConsignRecNumber",SqlStringUtils.GetQuotedString(mo.getConsignRecNumber()));
        map.put("CreateComBrId",mo.getCreateComBrId());
        map.put("CreateComId",mo.getCreateComId());
        map.put("CreateIp",SqlStringUtils.GetQuotedString(mo.getCreateIp()));
        map.put("CreateUserId",mo.getCreateUserId());
        map.put("DistPlanNumber",SqlStringUtils.GetQuotedString(mo.getDistPlanNumber()));
        map.put("Remark",SqlStringUtils.GetQuotedString(mo.getRemark()));


        return SqlStringUtils.GetConstructionInsert("VehicleDispathDetail",map);

    }

    @Override
    public boolean IsExists(String recNumber, String vehDispNumber) {

        String sql="Select 1 from VehicleDispathDetail where VehDispNumber="+SqlStringUtils.GetQuotedString(vehDispNumber)
                +" and ConsignRecNumber="+SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDaoServer.getInstance().queryForList(sql);
        if(list!=null&&list.size()>0)
            return true;
        return false;
    }
}
