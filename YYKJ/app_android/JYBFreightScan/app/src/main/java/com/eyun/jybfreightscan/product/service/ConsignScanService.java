package com.eyun.jybfreightscan.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybfreightscan.product.entity.Consign;
import com.eyun.jybfreightscan.product.entity.Vehicle;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ConsignScanService {
    /**
     * 获取已经扫描多少件
     * @param scanType
     * @return
     */
    int getConsignScanCounts(Consign consign, int scanType);
    /**
     * 获取已经扫描多少件
     * @param scanType
     * @return
     */
    int getConsignScanCounts(Consign consign, int scanType, Vehicle vehicle);

    /**
     * 扫描商品
     * @param consign
     * @param scanType
     * @param vehicle
     * @param code
     * @return
     */
    ResultMsg getScanProduct(Consign consign, int scanType, Vehicle vehicle, String code, int number);

    /**
     * 提交扫描数据
     * @param recNumber
     * @return
     */
    ResultMsg consignScanOk(String recNumber, int scanType);
}
