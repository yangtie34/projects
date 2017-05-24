package com.eyun.eyunstorage.product.service.impl;

import com.eyun.framework.util.common.StringUtils;
import com.eyun.eyunstorage.product.dao.impl.StorageLocationProductDaoImpl;
import com.eyun.eyunstorage.product.dao.impl.StorageTakeRecDetailDaoImpl;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.service.StorageTakeRecDetailService;

import java.util.List;
import java.util.Map;

/**
 * Created by ministrator on 2017/3/8.
 */

public class StorageTakeRecDetailServiceImpl implements StorageTakeRecDetailService {

    private static StorageTakeRecDetailServiceImpl storageTakeRecDetailService = null;

    public static StorageTakeRecDetailServiceImpl getInstance() {

        if (storageTakeRecDetailService == null) {
            synchronized (new StorageTakeRecDetailServiceImpl()) {
                if (storageTakeRecDetailService == null) {
                    storageTakeRecDetailService = new StorageTakeRecDetailServiceImpl();
                }
            }
        }
        return storageTakeRecDetailService;
    }

    @Override
    public int getStorageTakeRecCountsByRecNumber(StorageTakeRec storageTakeRec) {
        if(StringUtils.hasLength(storageTakeRec.getRecNumber())){
            return StorageTakeRecDetailDaoImpl.getInstance().getSumCount(storageTakeRec.getRecNumber());
        }else{
            return StorageLocationProductDaoImpl.getInstance().getLocationProNumber(storageTakeRec.getStorLocaCode());
        }
    }

    @Override
    public List<Map<String, Object>> getStorageTakeRecDetailByRecNumber(StorageTakeRec storageTakeRec) {
//        if (StringUtils.hasLength(storageTakeRec.getRecNumber())) {
//            return StorageTakeRecDetailDaoImpl.getInstance().getStorageTakeRecDetailByRecNumber(storageTakeRec.getRecNumber());
//        } else {
            return StorageLocationProductDaoImpl.getInstance().getStorageLocationProducts(storageTakeRec.getStorLocaCode());
        //}

    }
}
