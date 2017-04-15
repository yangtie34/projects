package com.eyun.jybfreightscan.product.dao;

import com.eyun.jybfreightscan.product.entity.StorageInRecScan;
import com.eyun.jybfreightscan.product.entity.StorageLocation;

import java.util.List;

/**
 * Created by Administrator on 2017/3/5.
 */
public interface StorageInRecScanDao {
    /**
     * 根据入库单批量插入各商品的初始化扫描信息
     * @return
     */
    boolean initScansByStorageInRecDetails(String RecNumber, int ScanType);

    /**
     * 根据入库单编号和商品id和扫描类型获取入库单扫描明细
     * @return
     */
    StorageInRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation);

    /**
     * 插入扫描信息
     * @return
     */
    boolean addScanInfo(StorageInRecScan mo);

    /**
     *修改扫描信息（更新数量）
     * @param mo
     * @return
     */
    boolean updateScanInfo(StorageInRecScan mo);
    /**
     *扫描数量自增1
     * @return
     */
    boolean addScanNumber(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation, int number);
    /**
     *插入一条入仓数据
     * @return
     */
    boolean addScanInfo(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation, int number);
    /**
     *扫描数量是否等于入库单数量
     * @return
     */
    boolean isFullNumber(String RecNumber, String ProID, int ScanType, int number);
    /**
     * 返回商品扫描数量
     * @param RecNumber
     * @param ProID
     * @param ScanType
     * @return
     */
    int getCount(String RecNumber, long ProID, int ScanType);
    /**
     *是否存在扫描数据
     * @param mo
     * @return
     */
    boolean isExist(StorageInRecScan mo);

    /**
     * 获取单据已入仓数量
     * @param RecNumber
     * @return
     */
    int getRecInStorageNumber(String RecNumber);
    /**
     * 获取单据已入仓库位数量
     * @param RecNumber
     * @return
     */
    int getRecInStorageLocalNumber(String RecNumber, Long localCode);
    /**
     * 获取单据已分拣数量
     * @param RecNumber
     * @return
     */
    int getInRecScanNumber(String recNumber);
     List<StorageInRecScan> getStorageInRecScan(String recNumber, int scanType);
}
