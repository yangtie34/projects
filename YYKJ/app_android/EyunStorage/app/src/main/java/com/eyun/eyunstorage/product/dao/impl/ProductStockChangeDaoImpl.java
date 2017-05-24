package com.eyun.eyunstorage.product.dao.impl;


import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.framework.util.common.NetWorkUtil;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.ProductStockChangeDao;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ProductStockChangeDaoImpl implements ProductStockChangeDao {
    private static ProductStockChangeDaoImpl productStockChangeDao = null;

    public static ProductStockChangeDaoImpl getInstance() {
        if (productStockChangeDao == null) {
            synchronized (new ProductStockChangeDaoImpl()) {
                if (productStockChangeDao == null) {
                    productStockChangeDao = new ProductStockChangeDaoImpl();
                }
            }
        }
        return productStockChangeDao;
    }
    @Override
    public boolean addProductStockChange(int ChangeType, String RecNumber, String RecTime, Long ProID, int ProNumber) {
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("ChangeType", ChangeType);
        map.put("RecNumber", SqlStringUtils.GetQuotedString(RecNumber));
        map.put("RecTime", SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        map.put("ProID", ProID);
        map.put("ProNumber", ProNumber);

        map.put("CreateUserID", AppUser.userId);
        //map.put("CreateComID", AppUser.comId);
        map.put("CreateComBrID", AppUser.comBrId);
        map.put("CreateIp", SqlStringUtils.GetQuotedString(NetWorkUtil.getHostIP()));
        map.put("CreateTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
        String sql=SqlStringUtils.GetConstructionInsert("storage_StockProductChange",map);
        return BaseDao.getInstance().excute(sql);
    }
}
