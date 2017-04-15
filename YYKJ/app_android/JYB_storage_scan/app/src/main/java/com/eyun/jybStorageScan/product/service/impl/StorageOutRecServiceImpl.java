package com.eyun.jybStorageScan.product.service.impl;

import com.eyun.jybStorageScan.product.dao.impl.StorageOutRecDaoImpl;
import com.eyun.jybStorageScan.product.entity.StorageOutRec;
import com.eyun.jybStorageScan.product.entity.StorageOutRecScan;
import com.eyun.jybStorageScan.product.service.StorageOutRecService;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StorageOutRecServiceImpl implements StorageOutRecService {

    private static StorageOutRecServiceImpl storageOutRecService = null;

    public static StorageOutRecServiceImpl getInstance() {
        if (storageOutRecService == null) {
            synchronized (new StorageOutRecServiceImpl()) {
                if (storageOutRecService == null) {
                    storageOutRecService = new StorageOutRecServiceImpl();
                }
            }
        }
        return storageOutRecService;
    }

    @Override
    public StorageOutRec getStorageOutRecByRecBarCode(String recBarrCode) {

        return StorageOutRecDaoImpl.getInstance().getStorageOutRecByRecNumber(recBarrCode);
    }
}
