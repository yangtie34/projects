package com.eyun.eyunstorage.product.dao;

import com.eyun.eyunstorage.product.entity.StorageLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageLocationDao {

    /**
     * 根据库们条码获取库位信息
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
    List<Map<String,Object>> getStorageLocation(long ComID, long ComBrID);
}
