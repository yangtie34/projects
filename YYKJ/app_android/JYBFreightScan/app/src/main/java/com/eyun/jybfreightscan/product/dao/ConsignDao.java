package com.eyun.jybfreightscan.product.dao;

/**
 * Created by Administrator on 2017/4/6.
 */

public interface ConsignDao {

    /**
     * 根据单号获取数量
     * @param recNumber
     * @return
     */
    public int GetProQuantity(String recNumber);

}
