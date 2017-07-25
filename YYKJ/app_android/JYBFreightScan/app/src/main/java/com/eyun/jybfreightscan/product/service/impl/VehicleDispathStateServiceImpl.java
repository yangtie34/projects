package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.jybfreightscan.product.dao.impl.VehicleDispathStateDaoImpl;
import com.eyun.jybfreightscan.product.entity.VehicleDispathState;
import com.eyun.jybfreightscan.product.service.VehicleDispathStateService;

/**
 * Created by Administrator on 2017/4/12.
 */

public class VehicleDispathStateServiceImpl implements VehicleDispathStateService {

    private static VehicleDispathStateServiceImpl vehicleDispathStateService = null;

    public static VehicleDispathStateServiceImpl getInstance() {
        if (vehicleDispathStateService == null) {
            synchronized (new VehicleDispathStateServiceImpl()) {
                if (vehicleDispathStateService == null) {
                    vehicleDispathStateService = new VehicleDispathStateServiceImpl();
                }
            }
        }
        return vehicleDispathStateService;
    }

    @Override
    public String add(VehicleDispathState mo, int recType, long vehNumber, long vehDrNumber) {
        return VehicleDispathStateDaoImpl.getInstance().add(mo,recType,vehNumber,vehDrNumber);
    }
}
