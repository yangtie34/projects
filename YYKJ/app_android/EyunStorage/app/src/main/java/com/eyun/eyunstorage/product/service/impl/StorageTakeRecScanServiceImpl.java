package com.eyun.eyunstorage.product.service.impl;

import com.eyun.framework.entity.ResultMsg;
import com.eyun.framework.util.common.TypeConvert;
import com.eyun.eyunstorage.AppPublic;
import com.eyun.eyunstorage.R;
import com.eyun.eyunstorage.product.dao.impl.ProductStockDaoImpl;
import com.eyun.eyunstorage.product.dao.impl.StorageLocationProductDaoImpl;
import com.eyun.eyunstorage.product.dao.impl.StorageTakeRecDetailDaoImpl;
import com.eyun.eyunstorage.product.dao.impl.StorageTakeRecScanDaoImpl;
import com.eyun.eyunstorage.product.dao.impl.StorageTakeRecStateDaoImpl;
import com.eyun.eyunstorage.product.entity.Product;
import com.eyun.eyunstorage.product.entity.StorageLocation;
import com.eyun.eyunstorage.product.entity.StorageTakeRec;
import com.eyun.eyunstorage.product.entity.StorageTakeRecScan;
import com.eyun.eyunstorage.product.service.StorageTakeRecScanService;
import com.eyun.eyunstorage.support.SoundSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/8.
 */

public class StorageTakeRecScanServiceImpl implements StorageTakeRecScanService {

    private static StorageTakeRecScanServiceImpl storageTakeRecScanService = null;

    public static StorageTakeRecScanServiceImpl getInstance() {

        if (storageTakeRecScanService == null) {
            synchronized (new StorageTakeRecScanServiceImpl()) {
                if (storageTakeRecScanService == null) {
                    storageTakeRecScanService = new StorageTakeRecScanServiceImpl();
                }
            }
        }
        return storageTakeRecScanService;
    }


    @Override
    public ResultMsg getScanbyRecNumberAndProBarCode(StorageTakeRecScan mo, String ProBarCode,int number) {
        Product product = ProductServiceImpl.getInstance().LoadData(ProBarCode);
        if (product==null) {
            ResultMsg resultMsg = new ResultMsg();
            resultMsg.setMsg("商品信息不存在");
            resultMsg.setTure(false);
            SoundSupport.play(R.raw.wcxx);
            return resultMsg;
        }
        Long proId=product.getProId();
        mo.setProId(proId);
        mo.setScanNumber(number);
        return Scan(mo);

    }



    @Override
    public StorageTakeRecScan getScanbyRecNumberAndProID(String RecNumber, String ProID, int ScanType) {
        return StorageTakeRecScanDaoImpl.getInstance().getScanbyRecNumberAndProID(RecNumber,ProID,ScanType);
    }

    public ResultMsg Scan(StorageTakeRecScan mo) {
        ResultMsg resultMsg = new ResultMsg();

        boolean isSucc  = StorageTakeRecScanDaoImpl.getInstance().isExist(mo);

        if (!isSucc) {
            isSucc = StorageTakeRecScanDaoImpl.getInstance().addScanInfo(mo);
        } else {
            isSucc = StorageTakeRecScanDaoImpl.getInstance().updateScanInfo(mo);
        }
        if (isSucc) {
            resultMsg.setMsg("");
            resultMsg.setTure(true);
            resultMsg.setObject(getScanbyRecNumberAndProID(mo.getRecNumber(),mo.getProId().toString(),mo.getScanType()));
            SoundSupport.play(R.raw.ok);
            return resultMsg;
        } else {
            resultMsg.setMsg("数据保存失败，请重试！");
            SoundSupport.play(R.raw.fail);
            resultMsg.setTure(false);
            return resultMsg;
        }
    }
    @Override
    public ResultMsg okScan(StorageTakeRec storageTakeRec,StorageLocation storageLocation) {
        ResultMsg resultMsg=new ResultMsg();
        StorageTakeRecServiceImpl.getInstance().addStorageTakeRec(storageTakeRec);
        StorageTakeRecStateDaoImpl.getInstance().addStorageTakeRecState(storageTakeRec.getRecNumber(),storageTakeRec.getRecState());
       if(storageTakeRec.getRecState()==AppPublic.TakeRecState.first){
           StorageTakeRecDetailDaoImpl.getInstance().initStorageTakeRecDetail(storageTakeRec);
       }
        List<StorageTakeRecScan> list=StorageTakeRecScanDaoImpl.getInstance().getStorageTakeRecScans(storageTakeRec.getRecNumber(),storageTakeRec.getRecState());
        List<Map<String, Object>> slps =StorageLocationProductDaoImpl.getInstance().getStorageLocationProducts(storageLocation.getCode());
        List<StorageTakeRecScan> list_=new ArrayList<>();
        if(slps.size()>list.size())
        for (int i = 0; i < slps.size(); i++) {
            boolean bool=false;
            for (int j = 0; j < list.size(); j++) {
                if(list.get(j).getProId().toString().equalsIgnoreCase(slps.get(i).get("ProID").toString())){
                    bool=true;
                    break;
                }
            }
            if(bool==false){
                StorageTakeRecScan strs=new StorageTakeRecScan();
                strs.setScanNumber(0);
                strs.setProId(TypeConvert.toLong(slps.get(i).get("ProID")));
                strs.setRecNumber(storageTakeRec.getRecNumber());
                list_.add(strs);
            }
        }
        list.addAll(list_);
        for (int i = 0; i < list.size(); i++) {
            StorageTakeRecScan storageTakeRecScan=list.get(i);
            StorageTakeRecDetailDaoImpl.getInstance().updateStorageTakeRecDetail(storageTakeRecScan);
            StorageLocationProductDaoImpl.getInstance().updateStorageLocationProduct(storageLocation,storageTakeRecScan.getProId(),storageTakeRecScan.getScanNumber());
            ProductStockDaoImpl.getInstance().updateProductStock(storageTakeRecScan.getProId());
        }
        resultMsg.setTure(true);
        return resultMsg;
    }
}
