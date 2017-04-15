package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.Consign;

public interface ConsignDao {

    /**
     * 根据条码获取托运单信息
     * @return
     */
    Consign getConsign(String vehicleCode);

    /**
     * 更新托运单状态
     * @param recNumber
     * @param RecState
     * @param RecForwardedState
     * @return
     */
    boolean updateConsign(String recNumber, int RecState, int RecForwardedState);
}