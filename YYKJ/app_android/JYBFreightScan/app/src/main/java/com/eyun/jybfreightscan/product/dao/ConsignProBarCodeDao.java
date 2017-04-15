package com.eyun.jybfreightscan.product.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/20.
 */

public interface ConsignProBarCodeDao {
    /**
     * 是否为此托运单商品
     */
    boolean hasThisProduct(String recNumber, String barCode);
    /**
     * 获取托运单商品
     */
    List<Map<String, Object>> getProducts(String recNumber);
}
