package com.eyun.jybStorageScan.product.dao;

import com.eyun.jybStorageScan.product.entity.StorageOutRec;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface StorageOutRecStateDao {
    /**
     * 获取订单状态
     */
    int getStorageOutRecState(String recNumber);
    /**
     * 添加出库单状态信息
     * @param recNumber
     * @param recState
     * @return
     */
    boolean addStorageOutRecState(String recNumber,int recState);
    /**
     * 更新出库单状态信息
     * @param recNumber
     * @param recState
     * @return
     */
    boolean updateStorageOutRecState(String recNumber,int recState);

    /**
     * 检测是否有出仓未分拣单据
     * @return
     */
    StorageOutRec getCCNoFJRec();
}
