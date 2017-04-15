package com.eyun.jybStorageScan.product.dao;

import com.eyun.jybStorageScan.product.entity.Consign;

public interface ConsignStateDao {

    /**
     * 根据托运单创建状态
     * @return
     */
    boolean add(String RecNumber,int recState);
    
}