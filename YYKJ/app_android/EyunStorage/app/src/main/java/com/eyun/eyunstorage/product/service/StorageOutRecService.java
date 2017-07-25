package com.eyun.eyunstorage.product.service;

import com.eyun.eyunstorage.product.entity.StorageOutRec;

/**
 * Created by Administrator on 2017/3/8.
 */

public interface StorageOutRecService {


    /**
     * 根据出库单条码查询出库单
     * @param recBarrCode
     * @return
     */
    StorageOutRec getStorageOutRecByRecBarCode(String recBarrCode);
}
