package com.eyun.eyunstorage.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageOutRecScan;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageOutRecScanService {

    /**
     * 根据入库单编号和商品id和扫描类型获取入库单扫描明细
     * @return
     */
    StorageOutRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType,StorageLocation storageLocation);

    /**
     * 出库分拣扫描
     * @return
     */
    ResultMsg Scan(StorageOutRecScan mo);
    /**
     * 根据入库单编号和商品条码和扫描类型获取扫描结果
     * @return
     */
    ResultMsg getScanbyRecNumberAndProBarCode(String RecNumber, String ProBarCode, int ScanType,StorageLocation storageLocation,int number);
    /**
     * 根据商品条码获取首次分拣扫描结果
     * @return ResultMsg.Object=入库单单号
     */
    ResultMsg getCCScanProBarCode(String ProBarCode,StorageLocation storageLocation);
    /**
     * 根据出库单编号和扫描类型获取扫描信息(扫描生成出库单使用)
     * @return
     */
    List<Map<String, Object>> getScanbyRecNumberAndScanType(String RecNumber, int ScanType);
    /**
     * 获取单据已出仓数量
     * @return
     */
    int getRecOutStorageNumber(String recNumber);
    /**
     * 获取单据已出仓库位数量
     * @param RecNumber
     * @return
     */
    int getRecOutStorageLocalNumber(String RecNumber,StorageLocation storageLocation);
    /**
     * 获取单据已分拣数量
     * @param RecNumber
     * @return
     */
    int getOutRecScanNumber(String RecNumber);
    /**
     * 出仓分拣扫描最后确认操作
     * @param recNumber
     * @return
     */
    ResultMsg outRecScanOk(String recNumber);
    /**
     * 出仓扫描最后确认操作
     * @param recNumber
     * @return
     */
    ResultMsg outStorageScanOk(String recNumber);
    /**
     * 扫描出仓最后确认操作
     * @param recNumber
     * @return
     */
    ResultMsg scanOutRecOk(String recNumber);
}
