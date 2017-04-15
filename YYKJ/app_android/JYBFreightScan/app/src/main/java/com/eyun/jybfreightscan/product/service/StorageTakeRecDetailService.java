package com.eyun.jybfreightscan.product.service;

import com.eyun.jybfreightscan.product.entity.StorageTakeRec;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageTakeRecDetailService {

    /**
     * 根据盘点单单据编号查询详情
     * @return
     */
    List<Map<String,Object>> getStorageTakeRecDetailByRecNumber(StorageTakeRec storageTakeRec);
    /**
     * 根据盘点单单据编号查询商品总数
     * @return
     */
    int getStorageTakeRecCountsByRecNumber(StorageTakeRec storageTakeRec);


}
