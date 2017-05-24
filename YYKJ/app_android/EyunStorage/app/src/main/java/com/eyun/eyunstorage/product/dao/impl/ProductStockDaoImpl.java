package com.eyun.eyunstorage.product.dao.impl;

import com.eyun.framework.common.util.SqlStringUtils;
import com.eyun.framework.jdbc.jdbcUtil.BaseDao;
import com.eyun.framework.util.common.DateUtils;
import com.eyun.eyunstorage.AppUser;
import com.eyun.eyunstorage.product.dao.ProductStockDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ProductStockDaoImpl implements ProductStockDao {
    private static ProductStockDaoImpl productStockDao = null;

    public static ProductStockDaoImpl getInstance() {
        if (productStockDao == null) {
            synchronized (new ProductStockDaoImpl()) {
                if (productStockDao == null) {
                    productStockDao = new ProductStockDaoImpl();
                }
            }
        }
        return productStockDao;
    }
    @Override
    public boolean updateProductStock(long ProID, int ProNumber) {
        String sql="Select *  from storage_StockProduct where ProID="+ ProID;
        List<Map<String,Object>> list= BaseDao.getInstance().queryForList(sql);
        HashMap<String,Object> map=new HashMap<String,Object>();
        if(list.size()>0){
            map.put("ProNumber", "ProNumber+"+ProNumber);
            String where=" where ProID="+ProID;
            sql=SqlStringUtils.GetConstructionUpdate("storage_StockProduct",map,where);

        }else{
            //map.put("ComID", AppUser.comId);
            map.put("ComBrID", AppUser.comBrId);
            map.put("ProID", ProID);
            map.put("ProNumber", ProNumber);
            map.put("ProNumberTime",SqlStringUtils.GetQuotedString(DateUtils.getCurrentTime()));
            sql=SqlStringUtils.GetConstructionInsert("storage_StockProduct",map);
        }
        return BaseDao.getInstance().excute(sql);
    }
    @Override
    public boolean updateProductStock(long ProID) {
        String sql="update storage_StockProduct set ProNumber=(select ProNumber from storage_StorageLocationProduct where proid="+ ProID+")  where proid="+ ProID;
        return BaseDao.getInstance().excute(sql);
    }
}
