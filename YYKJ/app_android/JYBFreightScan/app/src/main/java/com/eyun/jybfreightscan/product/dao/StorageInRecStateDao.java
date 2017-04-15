package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.StorageInRec;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface StorageInRecStateDao {
    /**
     * 获取订单状态
     */
    int getStorageInRecState(String recNumber);
    /**
     * 添加入库单状态信息
     * @param recNumber
     * @param recState
     * @return
     */
    boolean addStorageInRecState(String recNumber, int recState);
    /**
     * 更新入库单状态信息
     * @param recNumber
     * @param recState
     * @return
     */
    boolean updateStorageInRecState(String recNumber, int recState);

    /**
     * 获取分拣未入仓单据
     * @return
     */
    StorageInRec getFjNoRCRec();
}
