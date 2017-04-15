package com.eyun.jybStorageScan.product.service.impl;

import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.jybStorageScan.product.dao.impl.StorageInRecDaoImpl;
import com.eyun.jybStorageScan.product.entity.StorageInRec;
import com.eyun.jybStorageScan.product.service.StorageInRecService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/6.
 */

public class StorageInRecServiceImpl implements StorageInRecService {
    private static StorageInRecServiceImpl storageInRecService = null;

    public static StorageInRecServiceImpl getInstance() {
        if (storageInRecService == null) {
            synchronized (new StorageInRecServiceImpl()) {
                if (storageInRecService == null) {
                    storageInRecService = new StorageInRecServiceImpl();
                }
            }
        }
        return storageInRecService;
    }
    @Override
    public StorageInRec getStorageInRecByRecBarCode(String recBarrCode) {
        return StorageInRecDaoImpl.getInstance().getStorageInRecByRecNumber(recBarrCode);
    }

    @Override
    public String addStorageInRec(StorageInRec storageInRec) {
        return StorageInRecDaoImpl.getInstance().addStorageInRec(storageInRec);
    }
}
