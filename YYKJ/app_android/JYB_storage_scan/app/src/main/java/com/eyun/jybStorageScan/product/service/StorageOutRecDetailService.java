package com.eyun.jybStorageScan.product.service;

import com.eyun.jybStorageScan.product.entity.StorageTakeRec;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageOutRecDetailService {

    /**
     * 根据出库单单据编号查询详情
     * @param RecNumber
     * @return
     */
    List<Map<String,Object>> getStorageOutRecDetailByRecNumber(String RecNumber);

    /**
     * 根据出库单单据编号查询商品总数
     * @param RecNumber
     * @return
     */
    int getStorageOutRecCountsByRecNumber(String RecNumber);


}
