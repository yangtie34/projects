package com.eyun.eyunstorage.product.dao;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/7.
 */

public interface StorageOutRecDetailDao {
    /**
     * 根据出库单单据编号查询详情
     * @param RecNumber
     * @return
     */
    List<Map<String,Object>> getStorageOutRecDetailByRecNumber(String RecNumber);

    /**
     * 判断此商品编号是否为此出库单商品
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
     * 根据出库单编号生成盘点单明细
     * @return
     */
    boolean initStorageOutRecDetail(String RecNumber);
    /**
     * 根据出库单单据编号查询和商品id总数
     * @param RecNumber
     * @return
     */
    int getProNumber(String RecNumber,long proId);

}
