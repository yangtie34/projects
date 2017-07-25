package com.eyun.jybStorageScan.product.service.impl;

import com.eyun.jybStorageScan.product.dao.impl.VehicleDaoImpl;
import com.eyun.jybStorageScan.product.entity.Vehicle;
import com.eyun.jybStorageScan.product.service.VehicleService;

/**
 * Created by Administrator on 2017/3/20.
 */

public class VehicleServiceImpl implements VehicleService {
    private static VehicleServiceImpl vehicleService = null;

    public static VehicleServiceImpl getInstance() {
        if (vehicleService == null) {
            synchronized (new VehicleServiceImpl()) {
                if (vehicleService == null) {
                    vehicleService = new VehicleServiceImpl();
                }
            }
        }
        return vehicleService;
    }
    @Override
    public Vehicle getVehicle(String vehicleCode) {
        return VehicleDaoImpl.getInstance().getVehicle(vehicleCode);
    }
}
