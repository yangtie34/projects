package com.eyun.eyunstorage.product.service;

import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageTakeRecService {


    /**
     * 根据库位查询两天之内的盘点单信息
     * @return
     */
    StorageTakeRec getStorageTakeRecByStorLocaCode(StorageLocation storageLocation);
    /**
     * 更新盘点单信息
     * @return
     */
    StorageTakeRec addStorageTakeRec(StorageTakeRec storageTakeRec);

    /**
     * 清空库位此种扫描状态下的单据扫描信息
     * @param storageTakeRec
     * @return
     */
    boolean clearByStorageTakeRec(StorageTakeRec storageTakeRec);
}
