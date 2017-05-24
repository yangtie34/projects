package com.eyun.eyunstorage.product.dao;

import com.eyun.eyunstorage.product.entity.StorageLocation;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface StorageLocationChangeDao {
    /**
     * 仓库库位变化
     * @param ChangeType
     * @param storageLocation
     * @param ProID
     * @param ProNumber
     * @return
     */
    boolean addStorageLocationChange(int ChangeType,
                                  StorageLocation storageLocation,
                                  Long ProID,
                                  int ProNumber
    );
}
