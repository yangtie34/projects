package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathDetailDaoImpl;
import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathStateDaoImpl;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;
import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;
import com.eyun.jybfreightscan.product.service.VehicleDispathService;

/**
 * Created by Administrator on 2017/3/20.
 */

public class VehicleDispathServiceImpl implements VehicleDispathService {

    private static VehicleDispathServiceImpl VehicleDispathService = null;

    public static VehicleDispathServiceImpl getInstance() {
        if (VehicleDispathService == null) {
            synchronized (new VehicleDispathServiceImpl()) {
                if (VehicleDispathService == null) {
                    VehicleDispathService = new VehicleDispathServiceImpl();
                }
            }
        }
        return VehicleDispathService;
    }

    @Override
    public String GetVehDispNumber(long vehNumber, int recType) {
        String stateStr="";
        switch (recType)
        {
            case 1:
            case 2:
            case 3:
                stateStr="0,1";
                break;
            case 4:
                stateStr="1,2";
                break;
        }
       return VehicleDispathDaoImpl.getInstance().GetVehDispNumber(vehNumber,stateStr);
    }

    @Override
    public Long GetVehDrNumber(String recNumber) {
        return VehicleDispathDaoImpl.getInstance().GetVehDrNumber(recNumber);
    }
}
