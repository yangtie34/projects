package com.eyun.jybfreightscan.product.dao.impl;

import android.media.JetPlayer;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.jybfreightscan.product.dao.VehicleDispathForwardedStateDao;
import com.eyun.jybfreightscan.product.entity.VehicleDispathForwardedState;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VehicleDispathForwardedStateDaoImpl implements VehicleDispathForwardedStateDao {
    private static VehicleDispathForwardedStateDaoImpl vehicleDispathForwardedStateDao = null;

    public static VehicleDispathForwardedStateDaoImpl getInstance() {
        if (vehicleDispathForwardedStateDao == null) {
            synchronized (new VehicleDispathForwardedStateDaoImpl()) {
                if (vehicleDispathForwardedStateDao == null) {
                    vehicleDispathForwardedStateDao = new VehicleDispathForwardedStateDaoImpl();
                }
            }
        }
        return vehicleDispathForwardedStateDao;
    }


    @Override
    public String GetSqlAdd(VehicleDispathForwardedState mo) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("CreateComBrId", mo.getCreateComBrId());
        map.put("CreateUserId",mo.getCreateUserId());
        map.put("CreateComId",mo.getCreateComId());
        map.put("Remark",SqlStringUtils.GetQuotedString(mo.getRemark()));
        map.put("CreateIp",SqlStringUtils.GetQuotedString(mo.getCreateIp()));
        map.put("LocationLat",mo.getLocationLat());
        map.put("LocationLng",mo.getLocationLng());
        map.put("LowerDestinationAddress",SqlStringUtils.GetQuotedString(mo.getLowerDestinationAddress()));
        map.put("LowerDestinationComCusID",mo.getLowerDestinationComCusID());
        map.put("LowerDestinationName",SqlStringUtils.GetQuotedString(mo.getLowerDestinationName()));
        map.put("LowerDestinationPhone",SqlStringUtils.GetQuotedString(mo.getLowerDestinationPhone()));
        map.put("VehDispForwardedState",mo.getVehDispForwardedState());
        map.put("VehDispNumber",SqlStringUtils.GetQuotedString(mo.getVehDispNumber()));

        return SqlStringUtils.GetConstructionInsert("VehicleDispathForwardedState",map);
    }
}
