package com.eyun.jybfreightscan.product.dao;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ConsignScanDao {
    /**
     * 监测是否存在扫描数据 如果不存在初始化
     */
    boolean initConsignScan(String recNumber, int proNumber, int scanType, Long vehNumber);

    /**
     * 获取已经扫描多少件
     * @param recNumber
     * @param scanType
     * @return
     */
    int getConsignScanCounts(String recNumber, int scanType, Long vehNumber);
    /**
     * 更新扫描数量
     */
    boolean updateConsignScan(String recNumber, int number, int scanType, Long vehNumber);
}
