package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.VehicleDispathForwardedState;

/**
 * Created by Administrator on 2017/4/12.
 */

public interface VehicleDispathForwardedStateDao {

    /**
     * 获取中转状态插入语句
     * @param mo
     * @return
     */
    String GetSqlAdd(VehicleDispathForwardedState mo);
}
