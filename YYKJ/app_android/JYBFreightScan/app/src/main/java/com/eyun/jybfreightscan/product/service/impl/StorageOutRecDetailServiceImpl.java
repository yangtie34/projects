package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.jybfreightscan.product.dao.impl.StorageOutRecDetailDaoImpl;
import com.eyun.jybfreightscan.product.service.StorageOutRecDetailService;

import java.util.List;
import java.util.Map;

/**
 * Created by ministrator on 2017/3/8.
 */

public class StorageOutRecDetailServiceImpl implements StorageOutRecDetailService {

    private static StorageOutRecDetailServiceImpl storageOutRecDetailService = null;

    public static StorageOutRecDetailServiceImpl getInstance() {

        if (storageOutRecDetailService == null) {
            synchronized (new StorageOutRecDetailServiceImpl()) {
                if (storageOutRecDetailService == null) {
                    storageOutRecDetailService = new StorageOutRecDetailServiceImpl();
                }
            }
        }
        return storageOutRecDetailService;
    }

    @Override
    public int getStorageOutRecCountsByRecNumber(String RecNumber) {

        return StorageOutRecDetailDaoImpl.getInstance().getSumCount(RecNumber);
    }


    @Override
    public List<Map<String, Object>> getStorageOutRecDetailByRecNumber(String RecNumber) {
        return StorageOutRecDetailDaoImpl.getInstance().getStorageOutRecDetailByRecNumber(RecNumber);
}
}
