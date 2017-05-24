package com.eyun.eyunstorage.product.dao;

import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.entity.StorageTakeRecScan;

import java.util.List;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageTakeRecScanDao {

    /**
     * 根据入库单和差异表批量插入各商品的初始化扫描信息
     * @param storageInRec
     * @return
     */
    boolean createScansByStorageTakeRecDetails(StorageTakeRec storageInRec, int ScanType);

    /**
     * 根据入库单编号和商品id和扫描类型获取入库单扫描明细
     * @return
     */
    StorageTakeRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType);
    /**
     * 根据入库单编号和商品id和扫描类型获取入库单扫描明细
     * @return
     */
    List<StorageTakeRecScan> getStorageTakeRecScans(String RecNumber, int ScanType);
    /**
     * 插入扫描信息
     * @return
     */
    boolean addScanInfo(StorageTakeRecScan mo);

    /**
     *修改扫描信息（更新数量）
     * @param mo
     * @return
     */
    boolean updateScanInfo(StorageTakeRecScan mo);

}
