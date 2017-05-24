package com.eyun.jybfreightscan.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.jdbc.jdbcUtil.BaseDaoServer;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.jybfreightscan.AppUser;
import com.eyun.jybfreightscan.product.dao.VehicleDispathStateDao;
import com.eyun.jybfreightscan.product.entity.VehicleDispathForwardedState;
import com.eyun.jybfreightscan.product.entity.VehicleDispathState;
import com.eyun.jybfreightscan.product.service.impl.VehicleDispathServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/21.
 */

public class VehicleDispathStateDaoImpl implements VehicleDispathStateDao {
    private static VehicleDispathStateDaoImpl vehicleDispathStateDao = null;

    public static VehicleDispathStateDaoImpl getInstance() {
        if (vehicleDispathStateDao == null) {
            synchronized (new VehicleDispathStateDaoImpl()) {
                if (vehicleDispathStateDao == null) {
                    vehicleDispathStateDao = new VehicleDispathStateDaoImpl();
                }
            }
        }
        return vehicleDispathStateDao;
    }

    @Override
    public String add(VehicleDispathState mo,int recType,long vehNumber,long vehDrNumber) {

        List<String> list=new ArrayList<String>();
        if(recType==1||recType==4)//始发装车/终点卸车
        {
            int state=0;
            String whereState="";
            String whereCarState="";
            int currentState=VehicleDispathDaoImpl.getInstance().GetState(mo.getVehDispNumber());
            int carState=0;
            if(recType==1)//始发装车
            {
                state=1;
                carState=2;
                whereState="0";
                whereCarState="1";
                if(currentState==1)
                    return "已确认";
            }
            else
            {
                state=3;
                carState=0;
                whereState="1";
                whereCarState="2";
                if(currentState==3)
                    return "已确认";
            }

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("VehDispNumber", SqlStringUtils.GetQuotedString(mo.getVehDispNumber()));
            map.put("VehDispState", state);
            map.put("CreateUserID", mo.getCreateUserId());
            map.put("CreateComID", mo.getCreateComId());
            map.put("CreateComBrID", mo.getCreateComBrId());
            map.put("CreateIp", SqlStringUtils.GetQuotedString(mo.getCreateIp()));

            String sqlAdd = SqlStringUtils.GetConstructionInsert("VehicleDispathState", map);
            list.add(sqlAdd);

            String sqlVehicle=VehicleDaoImpl.getInstance().GetSqlUpdate(vehNumber,carState,whereCarState);
            list.add(sqlVehicle);

            String sqlVehicleDr=VehicleDaoImpl.getInstance().GetSqlUpdate_Driver(vehDrNumber,carState,whereCarState);
            list.add(sqlVehicleDr);

            String sqlState= VehicleDispathDaoImpl.getInstance().GetSqlUpdateState(mo.getVehDispNumber(),state);
            list.add(sqlState);
        }
        else
        {
            int state=recType;

            VehicleDispathForwardedState moForState=new VehicleDispathForwardedState();
            moForState.setVehDispNumber(mo.getVehDispNumber());
            moForState.setCreateComBrId(mo.getCreateComBrId());
            moForState.setVehDispNumber(mo.getVehDispNumber());
            moForState.setCreateComId(mo.getCreateComId());
            moForState.setCreateIp(mo.getCreateIp());
            moForState.setCreateUserId(mo.getCreateUserId());
            moForState.setLocationLat(mo.getLocationLat());
            moForState.setLocationLng(mo.getLocationLng());
            moForState.setLowerDestinationComCusID(TypeConvert.toLong(0));
            moForState.setLowerDestinationAddress("");
            moForState.setLowerDestinationName("");
            moForState.setLowerDestinationPhone("");
            moForState.setVehDispForwardedState(state);

            String sqlAdd =VehicleDispathForwardedStateDaoImpl.getInstance().GetSqlAdd(moForState);
            list.add(sqlAdd);

            String sqlState= VehicleDispathDaoImpl.getInstance().GetSqlUpdateStateForard(mo.getVehDispNumber(),state);
            list.add(sqlState);

        }

        if(BaseDaoServer.getInstance().excuteTransaction(list))
        {
            return "";
        }
        return "状态添加失败";
    }
}
