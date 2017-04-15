package com.eyun.jybStorageScan.product.dao;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface StorageTakeRecStateDao {

    /**
     * 添加盘点单状态信息
     * @param recNumber
     * @param recState
     * @return
     */
    boolean addStorageTakeRecState(String recNumber, int recState);

}
