package com.eyun.eyunstorage.product.service.impl;

import com.eyun.eyunstorage.product.dao.impl.StorageOutRecStateDaoImpl;
import com.eyun.eyunstorage.product.entity.StorageOutRec;
import com.eyun.eyunstorage.product.service.StorageOutRecStateService;

/**
 * Created by Administrator on 2017/3/15.
 */

public class StorageOutRecStateServiceImpl implements StorageOutRecStateService {
    private static StorageOutRecStateServiceImpl storageOutRecStateService = null;

    public static StorageOutRecStateServiceImpl getInstance() {

        if (storageOutRecStateService == null) {
            synchronized (new StorageOutRecStateServiceImpl()) {
                if (storageOutRecStateService == null) {
                    storageOutRecStateService = new StorageOutRecStateServiceImpl();
                }
            }
        }
        return storageOutRecStateService;
    }

    @Override
    public int getStorageOutRecState(String recNumber) {
        return StorageOutRecStateDaoImpl.getInstance().getStorageOutRecState(recNumber);
    }

    @Override
    public boolean addStorageOutRecState(String recNumber, int recState) {
        return StorageOutRecStateDaoImpl.getInstance().addStorageOutRecState(recNumber,recState);
    }

    @Override
    public boolean updateStorageOutRecState(String recNumber, int recState) {
        return StorageOutRecStateDaoImpl.getInstance().updateStorageOutRecState(recNumber,recState);
    }

    @Override
    public StorageOutRec getCCNoFJRec() {
        return StorageOutRecStateDaoImpl.getInstance().getCCNoFJRec();
    }
}
