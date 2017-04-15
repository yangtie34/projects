package com.eyun.jybStorageScan.product.service;

import com.eyun.jybStorageScan.product.entity.StorageLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageLocationService {

    /**
     * 根据库位条码获取库位信息
     * @param LocaBarCode
     * @return
     */
    StorageLocation getStorageLocatioin(String LocaBarCode);

    /**
     * 返回对应总公司/分公司管辖库位
     * @param ComID
     * @param ComBrID
     * @return
     */
    List<Map<String,Object>> getStorageLocationList(long ComID, long ComBrID);
}
