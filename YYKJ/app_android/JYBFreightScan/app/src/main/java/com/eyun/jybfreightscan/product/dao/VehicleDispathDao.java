package com.eyun.jybfreightscan.product.dao;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathDao {

    /**
     * 更新车辆状态
     * @param recNumber
     * @param recState
     * @return
     */
    String GetSqlUpdateState(String recNumber, int recState);

    /**
     * 更新车辆中转状态
     * @param recNumber
     * @param recStateForward
     * @return
     */
    String GetSqlUpdateStateForard(String recNumber, int recStateForward);

    /**
     * 判断是否存在此车辆调度
     * @param recNumber
     * @param VehNumber
     * @return
     */
    boolean IsExists(String recNumber, long VehNumber);

    /**
     * 返回状态
     * @param recNumber
     * @return
     */
    int GetState(String recNumber);

    /**
     * 返回中转状态
     * @param recNumber
     * @return
     */
    int GetStateForward(String recNumber);

    /**
     * 获取调度编号
     * @param VehNumber
     * @param StateStr
     * @return
     */
    String GetVehDispNumber(long VehNumber,String StateStr);

    /**
     * 获取司机编号
     * @param recNumber
     * @return
     */
    long GetVehDrNumber(String recNumber);

}
