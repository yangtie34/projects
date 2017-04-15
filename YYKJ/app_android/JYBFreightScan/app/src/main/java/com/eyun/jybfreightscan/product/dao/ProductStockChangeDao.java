package com.eyun.jybfreightscan.product.dao;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface ProductStockChangeDao {
    /**
     * 新增商品库存变化
     * @param ChangeType
     * @param RecNumber
     * @param RecTime
     * @param ProID
     * @param ProNumber
     * @return
     */
    boolean addProductStockChange(int ChangeType,
                                  String RecNumber,
                                  String RecTime,
                                  Long ProID,
                                  int ProNumber
    );
}
