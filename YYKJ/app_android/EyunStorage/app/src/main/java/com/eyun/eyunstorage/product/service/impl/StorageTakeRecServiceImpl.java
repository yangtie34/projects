package com.eyun.eyunstorage.product.service.impl;

import com.eyun.eyunstorage.AppPublic;
import com.eyun.eyunstorage.product.dao.impl.StorageTakeRecDaoImpl;
import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.service.StorageTakeRecService;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StorageTakeRecServiceImpl implements StorageTakeRecService {

    private static StorageTakeRecServiceImpl storageTakeRecService = null;

    public static StorageTakeRecServiceImpl getInstance() {
        if (storageTakeRecService == null) {
            synchronized (new StorageTakeRecServiceImpl()) {
                if (storageTakeRecService == null) {
                    storageTakeRecService = new StorageTakeRecServiceImpl();
                }
            }
        }
        return storageTakeRecService;
    }

    @Override
    public StorageTakeRec getStorageTakeRecByStorLocaCode(StorageLocation storageLocation) {
        return StorageTakeRecDaoImpl.getInstance().getStorageTakeRecByStorLocaCode(storageLocation.getCode());
    }

    @Override
    public StorageTakeRec addStorageTakeRec(StorageTakeRec storageTakeRec) {
        if(storageTakeRec.getRecState()== AppPublic.TakeRecState.first){
           StorageTakeRecDaoImpl.getInstance().addStorageTakeRec(storageTakeRec);
        }else{
            StorageTakeRecDaoImpl.getInstance().updateStorageTakeRec(storageTakeRec);
        }
        return StorageTakeRecDaoImpl.getInstance().getStorageTakeRecByStorLocaCode(storageTakeRec.getStorLocaCode());
    }

    @Override
    public boolean clearByStorageTakeRec(StorageTakeRec storageTakeRec) {
        return   StorageTakeRecDaoImpl.getInstance().clearByStorageTakeRec(storageTakeRec.getRecNumber(),storageTakeRec.getRecState());
    }
}
