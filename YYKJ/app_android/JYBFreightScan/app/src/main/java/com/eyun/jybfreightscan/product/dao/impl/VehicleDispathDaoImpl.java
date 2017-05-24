package com.eyun.jybfreightscan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.BaseDaoServer;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.product.dao.VehicleDispathDao;
import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VehicleDispathDaoImpl implements VehicleDispathDao {

    private static VehicleDispathDaoImpl vehicleDispathDao = null;

    public static VehicleDispathDaoImpl getInstance() {
        if (vehicleDispathDao == null) {
            synchronized (new VehicleDispathDaoImpl()) {
                if (vehicleDispathDao == null) {
                    vehicleDispathDao = new VehicleDispathDaoImpl();
                }
            }
        }
        return vehicleDispathDao;
    }

    @Override
    public String GetSqlUpdateState(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("VehDispState",recState);
        String where=" where VehDispNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        String sqlUpdate= SqlStringUtils.GetConstructionUpdate("VehicleDispath",map,where);
        return sqlUpdate;
    }

    @Override
    public String GetSqlUpdateStateForard(String recNumber, int recState) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("VehDispForwardedState",recState);
        String where=" where VehDispNumber="+ SqlStringUtils.GetQuotedString(recNumber);
        String sqlUpdate= SqlStringUtils.GetConstructionUpdate("VehicleDispath",map,where);
        return sqlUpdate;
    }

    @Override
    public boolean IsExists(String recNumber, long VehNumber) {
        String sqlSelect="Select * from  VehicleDispath where VehNumber="+VehNumber +
                " and VehDispNumber="+ SqlStringUtils.GetQuotedString(recNumber);

        List<VehicleDispathDetail> list= BaseDaoServer.getInstance().query(sqlSelect,VehicleDispathDetail.class);
        if(list!=null&&list.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public int GetState(String recNumber)
    {
        String sqlSelect="Select  VehDispState from  VehicleDispath where VehDispNumber="
                +SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDaoServer.getInstance().queryForList(sqlSelect);
        if(list!=null&&list.size()==1)
            return TypeConvert.toInteger(list.get(0).get("VehDispState").toString());
        return  0;
    }

    @Override
    public int GetStateForward(String recNumber)
    {
        String sqlSelect="Select  VehDispForwardedState from  VehicleDispath where VehDispNumber="
                  +SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDaoServer.getInstance().queryForList(sqlSelect);
        if(list!=null&&list.size()==1)
            return TypeConvert.toInteger(list.get(0).get("VehDispForwardedState").toString());
        return  0;
    }

    @Override
    public String GetVehDispNumber(long VehNumber,String StateStr) {

        String sqlSelect="Select top 1 VehDispNumber from  VehicleDispath where VehNumber="
                +VehNumber+" and VehDispState in("+StateStr+") order by CreateTime desc";
        List<Map<String,Object>> list= BaseDaoServer.getInstance().queryForList(sqlSelect);
        if(list!=null&&list.size()==1)
            return list.get(0).get("VehDispNumber").toString();
        return  "";
    }

    @Override
    public long GetVehDrNumber(String recNumber) {

        String sqlSelect="Select VehDrNumber from  VehicleDispath where VehDispNumber="+SqlStringUtils.GetQuotedString(recNumber);
        List<Map<String,Object>> list= BaseDaoServer.getInstance().queryForList(sqlSelect);
        if(list!=null&&list.size()==1)
            return TypeConvert.toLong(list.get(0).get("VehDrNumber").toString());
        return   TypeConvert.toLong(0);
    }
}
