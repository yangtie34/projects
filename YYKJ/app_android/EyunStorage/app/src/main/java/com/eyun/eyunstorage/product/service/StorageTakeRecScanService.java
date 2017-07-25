package com.eyun.eyunstorage.product.service;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.entity.StorageTakeRecScan;


/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageTakeRecScanService {

    /**
     * 根据盘点单编号和商品id和扫描类型获取盘点单扫描明细
     * @return
     */
    StorageTakeRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType);

    /**
     * 根据盘点单编号和商品条码和扫描类型获取扫描结果
     * @return
     */
    ResultMsg getScanbyRecNumberAndProBarCode(StorageTakeRecScan mo, String ProBarCode,int number);

    ResultMsg okScan(StorageTakeRec storageTakeRec,StorageLocation storageLocation);


}
