package com.eyun.jybStorageScan.product.dao;


import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/4.
 */
public interface StorageInRecDetailDao {
    /**
     * 根据入库单单据编号查询详情
     * @param RecNumber
     * @return
     */
    List<Map<String,Object>> getStorageInRecDetailByRecNumber(String RecNumber);

    /**
     * 判断此商品编号是否为此入库单商品
     * @param RecNumber
     * @param ProID
     * @return
     */
    boolean isThisDetailProBarCode(String RecNumber, long ProID);

    /**
     * 返回商品明细数量
     * @param RecNumber
     * @param PorID
     * @return
     */
    int getCount(String RecNumber,long PorID);

    /**
     * 返回商品总数
     * @param RecNumber
     * @return
     */
    int getSumCount(String RecNumber);
    /**
     * 根据入库单单据编号判断是否存在此商品
     * @param RecNumber
     * @return
     */
    boolean hasThisProduct(String RecNumber,String ProID);
    /**
     * 根据入库单编号生成盘点单明细
     * @return
     */
    boolean initStorageInRecDetail(String RecNumber);
}
