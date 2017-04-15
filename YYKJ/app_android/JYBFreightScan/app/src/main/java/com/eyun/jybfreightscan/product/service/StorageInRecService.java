package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.StorageInRec;

/**
 * Created by Administrator on 2017/3/4.
 */
public interface StorageInRecService {

    /**
     * 根据入库单条码查询入库单
     * @param recBarrCode
     * @return
     */
    StorageInRec getStorageInRecByRecBarCode(String recBarrCode);

    /**
     * 插入入库单并返回id
     * @param storageInRec
     * @return
     */
    String addStorageInRec(StorageInRec storageInRec);
}
