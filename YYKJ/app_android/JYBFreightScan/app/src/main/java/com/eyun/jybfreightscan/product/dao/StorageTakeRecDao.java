package com.eyun.jybfreightscan.product.dao;


import com.eyun.jybfreightscan.product.entity.StorageTakeRec;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageTakeRecDao {


    /**
     * 根据库位查询两天之内的盘点单信息
     * @return
     */
    StorageTakeRec getStorageTakeRecByStorLocaCode(long StorLocaCode);
    /**
     * 更新盘点单信息
     * @return
     */
    boolean addStorageTakeRec(StorageTakeRec storageTakeRec);

    boolean updateStorageTakeRec(StorageTakeRec storageTakeRec);
    /**
     * 清空库位此种扫描状态下的单据扫描信息
     * @return
     */
    boolean clearByStorageTakeRec(String RecNumber, int RecState);
}
