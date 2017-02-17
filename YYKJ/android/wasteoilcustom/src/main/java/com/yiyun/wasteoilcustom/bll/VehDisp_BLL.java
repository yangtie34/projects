package com.yiyun.wasteoilcustom.bll;

import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.Convert;
import com.example.fornet.WebServiceUtils;
import com.yiyun.wasteoilcustom.model.TransferRec_Model;
import com.yiyun.wasteoilcustom.model.VehicleDispathForwardedState_Model;
import com.yiyun.wasteoilcustom.model.VehicleDispathState_Model;
import com.yiyun.wasteoilcustom.model.VehicleDispath_Model;
import com.yiyun.wasteoilcustom.util.WastoilWebServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class VehDisp_BLL {
    static String webUrl = "http://125.46.79.254:8211/Offer/VehDisp.asmx";
    static HashMap<String, Object> properties  = new HashMap<>();

    /**
     * 调度-加载数据
     * @param recNumber
     * @return
     */
    public static void VehDisp_LoadData(String recNumber)
    {
        final String methodName = "VehDisp_LoadData";

        
        properties.clear();
        properties.put("recNo", recNumber);


        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, "TransferRec_Model", TransferRec_Model.class, new WebServiceUtils.WebServiceCallBack() {
            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject regSopa) {
                VehicleDispath_Model mo=new VehicleDispath_Model();
                int cout = regSopa.getPropertyCount();
                if (cout > 0) {
                    for (int i = 0; i < regSopa.getPropertyCount(); i++) {
                        String value = regSopa.getProperty(i).toString();
                        if (value.contentEquals("anyType{}")) {
                            value = "";
                        }

                        mo.setProperty(i, value);
                    }
                    mo.setExist(true);
                }
                Scope.activity.scope.key("VehDisp_LoadData").val(mo);
            }
        });
    }

    public static void methodPub(Object model, String operateVehDispNumber,final String methodName){

        properties.clear();
        properties.put("isConfirm","true");
        properties.put("moForwardedState",model);
        properties.put("operateVehDispNumber",operateVehDispNumber);

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, model.getClass().getName(), model.getClass(), new WebServiceUtils.WebServiceCallBack() {

            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject result) {
                String resatult = result.toString();
                if(resatult.equals("anyType{}"))
                {
                    resatult="";
                }
                Scope.activity.scope.key(methodName).val(resatult);
            }
        });
    }
    /**
     * 中转确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispForwardedState_ZhongZhuan(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
         String methodName = "ConfirmProcess_VehDispForwardedState_ZhongZhuan";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 中转卸车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispForwardedState_XieChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        String methodName = "ConfirmProcess_VehDispForwardedState_XieChe";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 中转到车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispDispForwardedState_DaoChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
         String methodName = "ConfirmProcess_VehDispDispForwardedState_DaoChe";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 中转发车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispForwardedState_FaChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispForwardedState_FaChe";

        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 中转装车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispForwardedState_ZhuangChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispForwardedState_ZhuangChe";

        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 中转封车
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispForwardedState_FengChe(VehicleDispathForwardedState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispForwardedState_FengChe";
        methodPub(model,operateVehDispNumber,methodName);
    }



    /**
     * 启程确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_QiCheng(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_QiCheng";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 受理确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_ShouLi(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_ShouLi";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 到车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_DaoChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_DaoChe";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 装车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_ZhuangChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_ZhuangChe";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 封车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_FengChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_FengChe";

        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 发车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_FaChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_FaChe";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 卸车确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_XieChe(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_XieChe";
        methodPub(model,operateVehDispNumber,methodName);
    }

    /**
     * 完成确认
     * @param model
     * @param operateVehDispNumber
     * @return
     */
    public static void ConfirmProcess_VehDispState_Ok(VehicleDispathState_Model model, String operateVehDispNumber)
    {
        final String methodName = "ConfirmProcess_VehDispState_Ok";

        methodPub(model,operateVehDispNumber,methodName);
    }


    /**
     *  订单查询
     * @param Condition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static void VehDisp_Select(String Condition, int pageIndex, int pageSize)
    {




        final String methodName = "VehDisp_Select";
        

        properties.clear();

         properties.put("pageIndex",pageIndex);
         properties.put("pageSize",pageSize);
         properties.put("Condition","["+Condition+"]");
         properties.put("orderField","");
         properties.put("orderType","");

        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties,null,null, new WebServiceUtils.WebServiceCallBack() {

            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject result) {
                List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
                String jsonStr=result.getProperty(methodName+"Result").toString();
                try{

                    if(!jsonStr.equals("anyType{}")){

                        JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();

                            if (jsonObject.has("VehDispNumber")) {
                                hashMap.put("VehDispNumber", jsonObject.get("VehDispNumber"));
                            } else {
                                continue;

                            }
                            if (jsonObject.has("VehDispTime")) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String t=format.format(Convert.ToDate(jsonObject.get("VehDispTime").toString()));
                                hashMap.put("VehDispTime",t);
                            } else {
                                hashMap.put("VehDispTime", "");
                            }

                            if (jsonObject.has("VehDispState")) {

                                hashMap.put("VehDispState", jsonObject.get("VehDispStateName"));
                            } else {
                                continue;
                            }

                            if (jsonObject.has("VehDispForwardedState")) {
                                hashMap.put("VehDispForwardedState", jsonObject.get("VehDispForwardedStateName"));
                            } else {
                                hashMap.put("VehDispForwardedState", "--");
                            }


                            if (jsonObject.has("CarriageMode")) {
                                hashMap.put("CarriageMode",jsonObject.get("CarriageModeName").toString());
                            } else {
                                hashMap.put("CarriageMode", "--");
                            }

                            if (jsonObject.has("CarriageMoney")) {
                                hashMap.put("CarriageMoney",jsonObject.get("CarriageMoney").toString());
                            } else {
                                hashMap.put("CarriageMoney", "--");
                            }

                            if (jsonObject.has("Remark")) {
                                hashMap.put("Remark", jsonObject.get("Remark"));
                            } else {
                                hashMap.put("Remark", "");
                            }

                            if (jsonObject.has("CreateComBrName")) {
                                hashMap.put("CreateComBrName", jsonObject.get("CreateComBrName"));
                            } else {
                                hashMap.put("CreateComBrName", "");
                            }

                            if (jsonObject.has("CreateUserFullnameCreateUserFullname")) {
                                hashMap.put("CreateUserFullname", jsonObject.get("CreateUserFullname"));
                            } else {
                                hashMap.put("CreateUserFullname", "");
                            }

                            if (jsonObject.has("VehLicensePlateNumber")) {
                                hashMap.put("VehLicensePlateNumber", jsonObject.get("VehLicensePlateNumber"));
                            } else {
                                hashMap.put("VehLicensePlateNumber", "");
                            }

                            if (jsonObject.has("VehDrName")) {
                                hashMap.put("VehDrName", jsonObject.get("VehDrName"));
                            } else {
                                hashMap.put("VehDrName", "");
                            }

                            if (jsonObject.has("VehDrPhone")) {
                                hashMap.put("VehDrPhone", jsonObject.get("VehDrPhone"));
                            } else {
                                hashMap.put("VehDrPhone", "");
                            }
                            list.add(hashMap);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                Scope.activity.scope.key(methodName).val(list);
            }
        });

    }

    /**
     *  查询调度明细
     * @return
     */
    public static void VehDis_LoadInfo(String vehDisNumber) {

        final String methodName = "VehDis_LoadInfo";
        properties.clear();
        properties.put("recNo", vehDisNumber);


        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, null, null, new WebServiceUtils.WebServiceCallBack() {

            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject result) {
                List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                String jsonStr = result.toString();
                try {

                    if (!jsonStr.equals("anyType{}")) {

                        JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                            HashMap<String, Object> hashMap = new HashMap<String, Object>();

                            if (jsonObject.has("VehDispState")) {
                                hashMap.put("VehDispState", jsonObject.get("VehDispState"));
                            } else {
                                continue;

                            }
                            if (jsonObject.has("VehDispStateName")) {

                                hashMap.put("VehDispStateName", jsonObject.get("VehDispStateName"));
                            } else {
                                continue;
                            }

                            if (jsonObject.has("VehDispTime")) {
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                String t = format.format(Convert.ToDate(jsonObject.get("VehDispTime").toString()));
                                hashMap.put("VehDispTime", t);
                            } else {
                                hashMap.put("VehDispTime", "");
                            }

                            if (jsonObject.has("VehDispForwardedState")) {
                                hashMap.put("VehDispForwardedState", jsonObject.get("VehDispForwardedState"));
                            } else {
                                continue;
                            }

                            if (jsonObject.has("VehDispForwardedStateName")) {
                                hashMap.put("VehDispForwardedStateName", jsonObject.get("VehDispForwardedStateName"));
                            } else {
                                hashMap.put("VehDispForwardedStateName", "--");
                            }


                            if (jsonObject.has("Address")) {
                                hashMap.put("Address", jsonObject.get("Address").toString());
                            } else {
                                hashMap.put("Address", "--");
                            }


                            if (jsonObject.has("Remark")) {
                                hashMap.put("Remark", jsonObject.get("Remark"));
                            } else {
                                hashMap.put("Remark", "");
                            }

                            if (jsonObject.has("LicensePlateNumber")) {
                                hashMap.put("LicensePlateNumber", jsonObject.get("LicensePlateNumber"));
                            } else {
                                hashMap.put("LicensePlateNumber", "");
                            }

                            if (jsonObject.has("VehDrName")) {
                                hashMap.put("VehDrName", jsonObject.get("VehDrName"));
                            } else {
                                hashMap.put("VehDrName", "");
                            }

                            if (jsonObject.has("VehDrPhone")) {
                                hashMap.put("VehDrPhone", jsonObject.get("VehDrPhone"));
                            } else {
                                hashMap.put("VehDrPhone", "");
                            }
                            list.add(hashMap);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                Scope.activity.scope.key(methodName).val(list);
            }
        });

    }
}
