package com.eyun.jybfreightscan.product.dao;

public interface ConsignStateDao {

    /**
     * 根据托运单创建状态
     * @return
     */
    boolean add(String RecNumber, int recState);
    
}