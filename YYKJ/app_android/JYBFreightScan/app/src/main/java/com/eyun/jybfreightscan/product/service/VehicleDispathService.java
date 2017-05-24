package com.eyun.jybfreightscan.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathService {

    /**
     * 获取调度编号
     * @param vehNumber
     * @param state
     * @return
     */
    String GetVehDispNumber(long vehNumber,int state);

    /**
     * 获取司机编号
     * @param recNumber
     * @return
     */
    Long GetVehDrNumber(String recNumber);

}
