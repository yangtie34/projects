package com.eyun.jybStorageScan.product.dao;

/**
 * Created by Administrator on 2017/3/15.
 */

public interface ProductStockDao {
    /**
     * 更新商品库存
     * @param ProID
     * @param ProNumber
     * @return
     */
    boolean updateProductStock(long ProID,int ProNumber);
    /**
     * 更新商品库存(盘点完成)
     * @param ProID
     * @return
     */
    boolean updateProductStock(long ProID);

}
