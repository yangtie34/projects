package com.eyun.eyunstorage.product.dao;

import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageOutRec;
import com.eyun.eyunstorage.product.entity.StorageOutRecScan;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageOutRecScanDao {

    /**
     * 根据入库单和差异表批量插入各商品的初始化扫描信息
     * @param storageInRec
     * @return
     */
    boolean createScansByStorageOutRecDetails(StorageOutRec storageInRec, int ScanType);

    /**
     * 根据入库单编号和商品id和扫描类型获取入库单扫描明细
     * @return
     */
    StorageOutRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType, StorageLocation storageLocation);
    /**
     *扫描数量是否等于出库单数量
     * @return
     */
    boolean isFullNumber(String RecNumber,String ProID,int ScanType,int number);
    /**
     * 插入扫描信息
     * @return
     */
    boolean addScanInfo(StorageOutRecScan mo);

    /**
     *修改扫描信息（更新数量）
     * @param mo
     * @return
     */
    boolean updateScanInfo(StorageOutRecScan mo);

    /**
     * 返回商品扫描数量
     * @param RecNumber
     * @param ProID
     * @param ScanType
     * @return
     */
    int getCount(String RecNumber,long ProID,int ScanType);
    /**
     * 获取单据已出仓数量
     * @param recNumber
     * @return
     */
    int getOutRecScanNumber(String recNumber);
    /**
     * 获取单据已出仓库位数量
     * @param RecNumber
     * @return
     */
    int getRecOutStorageLocalNumber(String RecNumber,Long localCode);

    /**
     * 获取单据已出仓数量
     * @param recNumber
     * @return
     */
    int getRecOutStorageNumber(String recNumber);
    /**
     *是否存在扫描数据
     * @param mo
     * @return
     */
    boolean isExist(StorageOutRecScan mo);
    List<StorageOutRecScan> getStorageOutRecScan(String recNumber, int scanType);
}
