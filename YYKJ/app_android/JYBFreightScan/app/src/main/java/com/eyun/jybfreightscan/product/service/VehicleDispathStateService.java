package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.VehicleDispathState;

/**
 * Created by Administrator on 2017/4/12.
 */

public interface VehicleDispathStateService {
    /**
     * 根据单创建状态
     * @return
     */
    String add(VehicleDispathState mo, int recType, long vehNumber, long vehDrNumber);
}
