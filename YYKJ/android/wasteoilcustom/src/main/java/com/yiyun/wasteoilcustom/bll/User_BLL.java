package com.yiyun.wasteoilcustom.bll;


import com.chengyi.android.angular.core.Scope;
import com.chengyi.android.util.Convert;
import com.chengyi.android.util.PreferenceUtils;
import com.example.fornet.WebServiceUtils;
import com.yiyun.wasteoilcustom.AppUser;
import com.yiyun.wasteoilcustom.model.SysLogin_CompanyCustomer;
import com.yiyun.wasteoilcustom.util.WastoilWebServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/22.
 */

public class User_BLL {
    public static String isFirstRunStr="isFirstRun";
    public static String isRememberStr="isRemember";
    public static String userNameStr="userName";
    public static String userPwdStr="userPwd";

    public static final String msg   = String.valueOf(Scope.getId());
    public static AppUser appUser= AppUser.getInstance();
    public static void Login(SysLogin_CompanyCustomer model, long comID) {
        String webUrl = "http://125.46.79.254:8211/Login/Login.asmx";
        final String methodName = "SysLogin_CompanyCustomer";
        appUser.setUserPwd(model.getLoginPwd());

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("mo", model);
        //properties.put("comID", comID);
        properties.put("frameType", "-1");
        //properties.put("mess", "");


        //通过工具类调用WebService接口
        WastoilWebServiceUtil.callWebService(webUrl, methodName, properties, "SysLogin_CompanyCustomer", SysLogin_CompanyCustomer.class, new WebServiceUtils.WebServiceCallBack() {

            //WebService接口返回的数据回调到这个方法中
            @Override
            public void callBack(SoapObject result) {
                Scope.activity.scope.key(msg).val(result);
            }
        });
    }
    public static Boolean init(SoapObject result) {
        //SysLoginResponse{SysLoginResult=anyType{}; mess=登录失败,请检查登录账户和密码!; }
        boolean bool=false;
        if (!result.getProperty("mess").toString().contains("anyType{}")){//登录失败
            String errMsg = result.getProperty("mess").toString();
            PreferenceUtils.clearPreference();
           Scope.activity.scope.key("errMsg").val(errMsg);
        } else {//登录成功


            String jsonStr = result.getProperty("SysLoginResult").toString();
            try {

                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                JSONObject jsonObject = (JSONObject) jsonArray.get(0);

                String fullName = jsonObject.getString("Fullname");
                String userNumber = jsonObject.getString("ComCusNumber");
                String combrName = jsonObject.getString("ComBrName");
                long userID = Convert.ToInt64(jsonObject.getString("UserID"));
                long comBrID = Convert.ToInt64(jsonObject.getString("ComBrID"));
                long comID = Convert.ToInt64(jsonObject.getString("ComCusID"));

                boolean isVeh=false;
                long vehDrNumber=jsonObject.getLong("VehicleDriverNumber");

                if(vehDrNumber>0)
                    isVeh=true;
                appUser.setUserNumber(userNumber);
                appUser.setCompanyUserID(userID);
                appUser.setSysComBrID(comBrID);
                appUser.setSysComID(comID);
                appUser.setUserFullname(fullName);
                appUser.setComBrName(combrName);
                appUser.setVehDriverNumber(vehDrNumber);
                appUser.setIsVehicleDriver(isVeh);

                bool=true;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    return bool;
    }

}
