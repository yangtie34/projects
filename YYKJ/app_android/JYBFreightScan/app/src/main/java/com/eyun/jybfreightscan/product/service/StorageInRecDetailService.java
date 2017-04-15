package com.eyun.jybfreightscan.product.service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageInRecDetailService {

    /**
     * 根据入库单单据编号查询详情
     * @param RecNumber
     * @return
     */
    List<Map<String,Object>> getStorageInRecDetailByRecNumber(String RecNumber);
    /**
     * 根据入库单单据编号查询商品总数
     * @param RecNumber
     * @return
     */
    int getStorageInRecCountsByRecNumber(String RecNumber);
    /**
     * 根据入库单单据编号判断是否存在此商品
     * @param RecNumber
     * @return
     */
    boolean hasThisProduct(String RecNumber, String ProID);
}


