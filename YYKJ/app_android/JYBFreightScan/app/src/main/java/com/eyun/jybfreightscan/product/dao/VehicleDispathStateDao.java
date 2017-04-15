package com.eyun.jybfreightscan.product.dao;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface VehicleDispathStateDao {
    /**
     * 根据单创建状态
     * @return
     */
    boolean add(String RecNumber, int recState);
}
