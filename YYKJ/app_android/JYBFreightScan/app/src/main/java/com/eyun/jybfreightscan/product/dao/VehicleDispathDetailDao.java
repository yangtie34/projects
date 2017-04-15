package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathDetailDao {
    /**
     * 根据托运单查询VehicleDispathDetail
     */
    VehicleDispathDetail getVehicleDispathDetailByConsignRecNumber(String RecNumber);
}
