package com.eyun.jybStorageScan.product.dao;

import com.eyun.jybStorageScan.product.entity.Product;

/**
 * Created by Administrator on 2017/3/5.
 */
public interface ProductDao {

    /**
     * 根据商品条码获取商品
     * @param ProBarCode
     * @return
     */
    Product LoadData(String ProBarCode);

    /**
     * 判断商品信息是否存在
     * @param proID
     * @return
     */
    boolean IsExist(long proID);
}
