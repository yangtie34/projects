package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.jybfreightscan.product.dao.impl.StorageLocationDaoImpl;
import com.eyun.jybfreightscan.product.entity.StorageLocation;
import com.eyun.jybfreightscan.product.service.StorageLocationService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StorageLocationServiceImpl implements StorageLocationService {
    private static StorageLocationServiceImpl storageLocationService = null;

    public static StorageLocationServiceImpl getInstance() {

        if (storageLocationService == null) {
            synchronized (new StorageLocationServiceImpl()) {
                if (storageLocationService == null) {
                    storageLocationService = new StorageLocationServiceImpl();
                }
            }
        }
        return storageLocationService;
    }
    @Override
    public StorageLocation getStorageLocatioin(String LocaBarCode) {
        return StorageLocationDaoImpl.getInstance().getStorageLocatioin(LocaBarCode);
    }

    @Override
    public List<Map<String, Object>> getStorageLocationList(long ComID, long ComBrID) {
        return StorageLocationDaoImpl.getInstance().getStorageLocation(ComID,ComBrID);
    }
}
