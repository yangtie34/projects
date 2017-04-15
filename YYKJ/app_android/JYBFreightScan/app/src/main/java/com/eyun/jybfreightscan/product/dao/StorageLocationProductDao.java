package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.StorageLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface StorageLocationProductDao {
    /**
     * 更新商品库存
     * @param ProID
     * @param ProNumber
     * @return
     */
    boolean updateStorageLocationProduct(StorageLocation storageLocation, long ProID, int ProNumber);
    /**
     * 更新商品库存(出入库)
     * @param ProID
     * @param ProNumber
     * @return
     */
    boolean addStorageLocationProduct(StorageLocation storageLocation, long ProID, int ProNumber);

    /**
     * 获取库位商品总数
     * @return
     */
    int getLocationProNumber(long StorLocaCode);
    /**
     * 获取库位商品详情
     * @return
     */
    List<Map<String, Object>> getStorageLocationProducts(long StorLocaCode);
}
