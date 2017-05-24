package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.VehicleDispathState;
/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathStateDao {

    /**
     * 根据单创建状态
     * @return
     */
    String add(VehicleDispathState mo,int recType,long vehNumber,long vehDrNumber);
}
