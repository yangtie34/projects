package com.eyun.jybStorageScan.product.dao;

import com.eyun.jybStorageScan.product.entity.StorageTakeRec;
import com.eyun.jybStorageScan.product.entity.StorageTakeRecScan;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageTakeRecDetailDao {


    /**
     * 根据盘点单生成盘点单明细
     * @return
     */
    boolean initStorageTakeRecDetail(StorageTakeRec storageTakeRec);
    boolean updateStorageTakeRecDetail(StorageTakeRecScan storageTakeRecScan);
    /**
     * 返回商品总数
     * @param RecNumber
     * @return
     */
    int getSumCount(String RecNumber);
    /**
    * 根据盘点单单据编号查询详情
    * @param RecNumber
    * @return
            */
    List<Map<String,Object>> getStorageTakeRecDetailByRecNumber(String RecNumber);
}
