package com.eyun.jybfreightscan.product.service.impl;

import com.eyun.jybfreightscan.product.dao.impl.ProductDaoImpl;
import com.eyun.jybfreightscan.product.entity.Product;
import com.eyun.jybfreightscan.product.service.ProductService;


/**
 * Created by Administrator on 2017/3/6.
 */

public class ProductServiceImpl implements ProductService{
    private static ProductServiceImpl productService = null;

    public static ProductServiceImpl getInstance() {
        if (productService == null) {
            synchronized (new ProductServiceImpl()) {
                if (productService == null) {
                    productService = new ProductServiceImpl();
                }
            }
        }
        return productService;
    }
    @Override
    public Product LoadData(String barCode) {
        return ProductDaoImpl.getInstance().LoadData(barCode);
    }
}
