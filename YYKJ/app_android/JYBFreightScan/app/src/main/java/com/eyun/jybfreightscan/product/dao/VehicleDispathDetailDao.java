package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.VehicleDispathDetail;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathDetailDao {
    /**
     * 判断单据是否已在调度明细中存在
     */
    boolean IsExists(String recNumber,String vehDispNumber);

    /**
     * 获取添加调度明细语句
     * @param mo
     * @return
     */
    String GetSqlAdd(VehicleDispathDetail mo);

}
