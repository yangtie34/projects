package com.eyun.eyunstorage.product.service.impl;

import com.eyun.eyunstorage.product.dao.impl.StorageInRecDetailDaoImpl;
import com.eyun.eyunstorage.product.service.StorageInRecDetailService;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public class StorageInRecDetailServiceImpl implements StorageInRecDetailService {
    private static StorageInRecDetailServiceImpl storageInRecDetailService = null;

    public static StorageInRecDetailServiceImpl getInstance() {
        if (storageInRecDetailService == null) {
            synchronized (new StorageInRecDetailServiceImpl()) {
                if (storageInRecDetailService == null) {
                    storageInRecDetailService = new StorageInRecDetailServiceImpl();
                }
            }
        }
        return storageInRecDetailService;
    }


    @Override
    public List<Map<String, Object>> getStorageInRecDetailByRecNumber(String RecNumber) {
        return StorageInRecDetailDaoImpl.getInstance().getStorageInRecDetailByRecNumber(RecNumber);
    }

    @Override
    public int getStorageInRecCountsByRecNumber(String RecNumber) {

        return StorageInRecDetailDaoImpl.getInstance().getSumCount(RecNumber);
    }

    @Override
    public boolean hasThisProduct(String RecNumber,String ProID) {
        return StorageInRecDetailDaoImpl.getInstance().hasThisProduct(RecNumber,ProID);
    }

}
