package com.eyun.jybStorageScan.product.service;

import com.eyun.jybStorageScan.product.entity.Product;

/**
 * Created by Administrator on 2017/3/6.
 */

public interface ProductService {

    /**
     * 根据条码获取产品信息
     * @param barCode 条码
     * @return
     */
    Product LoadData(String barCode);

}
