package com.eyun.jybStorageScan.product.dao;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathDao {
    /**
     * 更新车辆单状态
     * @param recNumber
     * @param RecState
     * @param RecForwardedState
     * @return
     */
    boolean updateVehicleDispath(String recNumber,int RecState,int RecForwardedState);

    /**
     * 判断是否存在此车辆调度
     * @param recNumber
     * @param VehNumber
     * @return
     */
    boolean hasThisDispath(String recNumber,long VehNumber);
}
