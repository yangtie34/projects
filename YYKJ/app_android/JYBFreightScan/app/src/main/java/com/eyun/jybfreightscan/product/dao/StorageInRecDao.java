package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.StorageInRec;

/**
 * Created by Administrator on 2017/3/4.
 */
public interface StorageInRecDao {
    /**
     * 根据入库单单据编号查询入库单
     * @param RecNumber
     * @return
     */
    StorageInRec getStorageInRecByRecNumber(String RecNumber);

    /**
     * 判断入库单是否存在
     * @param recNumber
     * @return
     */
    boolean IsExist(String recNumber);

    /**
     * 插入入库单并返回id
     * @param storageInRec
     * @return
     */
    String addStorageInRec(StorageInRec storageInRec);

    /**
     * 更新单据状态
     * @param RecNumber
     * @param state
     * @return
     */
    boolean updateState(String RecNumber, int state);
}
