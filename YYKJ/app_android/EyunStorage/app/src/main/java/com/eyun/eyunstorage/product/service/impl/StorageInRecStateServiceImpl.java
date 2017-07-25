package com.eyun.eyunstorage.product.service.impl;

import com.eyun.eyunstorage.product.dao.impl.StorageInRecStateDaoImpl;
import com.eyun.eyunstorage.product.entity.StorageInRec;
import com.eyun.eyunstorage.product.service.StorageInRecStateService;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageInRecStateServiceImpl implements StorageInRecStateService {
    private static StorageInRecStateServiceImpl storageInRecStateService = null;

    public static StorageInRecStateServiceImpl getInstance() {

        if (storageInRecStateService == null) {
            synchronized (new StorageInRecStateServiceImpl()) {
                if (storageInRecStateService == null) {
                    storageInRecStateService = new StorageInRecStateServiceImpl();
                }
            }
        }
        return storageInRecStateService;
    }

    @Override
    public int getStorageInRecState(String recNumber) {
        return StorageInRecStateDaoImpl.getInstance().getStorageInRecState(recNumber);
    }

    @Override
    public boolean addStorageInRecState(String recNumber, int recState) {
        return StorageInRecStateDaoImpl.getInstance().addStorageInRecState(recNumber, recState);
    }

    @Override
    public boolean updateStorageInRecState(String recNumber, int recState) {
        return StorageInRecStateDaoImpl.getInstance().updateStorageInRecState(recNumber, recState);
    }

    @Override
    public StorageInRec getFjNoRCRec() {
        return StorageInRecStateDaoImpl.getInstance().getFjNoRCRec();
    }
}
