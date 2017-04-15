package com.eyun.jybfreightscan.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.jybfreightscan.product.entity.StorageInRecScan;
import com.eyun.jybfreightscan.product.entity.StorageLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/5.
 */
public interface StorageInRecScanService {

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
     * 根据入库单编号和商品id和扫描类型累加扫描信息
     * @return
     */
    ResultMsg Scan(StorageInRecScan mo);
    /**
     * 根据入库单编号和商品条码和扫描类型获取扫描结果
     * @return
     */
    ResultMsg getScanbyRecNumberAndProBarCode(String RecNumber, String ProBarCode, int ScanType, int number);
    /**
     * 入库单入仓
     * @return
     */
    ResultMsg getScanbyRecInStorage(String RecNumber, String ProBarCode, StorageLocation storageLocation, int number);

    /**
     * 根据入库单编号和商品条码和扫描类型获取扫描结果(扫描入库使用)
     * @return
     */
    ResultMsg getScanbyRecNumberAndProBarCodeForScanInRec(String RecNumber, String ProBarCode, int ScanType, long ComSuppID, int number);


    /**
     * 根据商品条码获取首次分拣扫描结果
     * @return ResultMsg.Object=入库单单号(扫描生成入库单使用)
     */
    ResultMsg getFjScanProBarCode(String ProBarCode);

    /**
     * 根据入库单编号和扫描类型获取扫描信息(扫描生成入库单使用)
     * @return
     */
    List<Map<String, Object>> getScanbyRecNumberAndScanType(String RecNumber, int ScanType);
    /**
     * 获取单据已分拣数量
     * @param RecNumber
     * @return
     */
    int getInRecScanNumber(String RecNumber);
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
    int getRecInStorageLocalNumber(String RecNumber, StorageLocation storageLocation);
    /**
     * 分拣扫描最后确认操作
     * @param recNumber
     * @return
     */
    ResultMsg inRecScanOk(String recNumber);
    /**
     * 入库扫描最后确认操作
     * @param recNumber
     * @return
     */
    ResultMsg inStorageScanOk(String recNumber);
    /**
     * 扫描分拣最后确认操作
     * @param recNumber
     * @return
     */
    ResultMsg scanInRecOk(String recNumber);
}
