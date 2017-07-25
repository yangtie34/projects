package com.eyun.jybStorageScan.product.dao;


import com.eyun.jybStorageScan.product.entity.StorageInRec;
import com.eyun.jybStorageScan.product.entity.StorageOutRec;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageOutRecDao {

    /**
     * 根据出库单单据编号查询出库单
     * @param RecNumber
     * @return
     */
    StorageOutRec getStorageOutRecByRecNumber(String RecNumber);

    /**
     * 判断出库单是否存在
     * @param recNumber
     * @return
     */
    boolean IsExist(String recNumber);

    /**
     * 插入出库单并返回id
     * @param mo
     * @return
     */
    String addStorageOutRec(StorageOutRec mo);
    /**
     * 更新单据状态
     * @param RecNumber
     * @param state
     * @return
     */
    boolean updateState(String RecNumber,int state);
}
