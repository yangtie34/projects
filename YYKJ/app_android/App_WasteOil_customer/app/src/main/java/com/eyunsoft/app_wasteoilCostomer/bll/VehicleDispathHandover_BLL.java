package com.eyunsoft.app_wasteoilCostomer.bll;

import com.eyunsoft.app_wasteoilCostomer.Model.NameToValue;
import com.eyunsoft.app_wasteoilCostomer.Model.VehicleDispathHandover_Model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */

public class VehicleDispathHandover_BLL {
    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";

    /**
     * 交接客户
     * @param vehDispNumber
     * @return
     */
    public static ArrayList<NameToValue>  GetVehicleDispath_CompanyCustomer(String vehDispNumber)
    {
        String methodName = "GetVehicleDispath_CompanyCustomer";
        String webUrl = "http://125.46.79.254:8211/Offer/VehicleDispathHandover.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("vehDispNumber",vehDispNumber);

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        String resatult = null;
        ArrayList<NameToValue> list=new ArrayList<NameToValue>();
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            String jsonStr=object.toString();
            System.out.println("GetVehicleDispath_CompanyCustomer获取到数据:" + object.toString());
            if(!jsonStr.trim().equals("anyType{}"))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("ComName")) {
                        value.InfoName=jsonObject.get("ComName").toString();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("ComCusID")) {
                        value.InfoValue=jsonObject.get("ComCusID").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * 交接分公司
     * @param vehDispNumber
     * @return
     */
    public static ArrayList<NameToValue>  GetVehicleDispath_CompanyBranch(String vehDispNumber)
    {
        String methodName = "GetVehicleDispath_CompanyBranch";
        String webUrl = "http://125.46.79.254:8211/Offer/VehicleDispathHandover.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("vehDispNumber",vehDispNumber);

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        String resatult = null;
        ArrayList<NameToValue> list=new ArrayList<NameToValue>();
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            String jsonStr=object.toString();
            System.out.println("GetVehicleDispath_CompanyBranch获取到数据:" + object.toString());
            if(!jsonStr.trim().equals("anyType{}"))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("ComBrName")) {
                        value.InfoName=jsonObject.get("ComBrName").toString().trim();
                    } else {
                        continue;

                    }
                    if (jsonObject.has("ComBrID")) {
                        value.InfoValue=jsonObject.get("ComBrID").toString().trim();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    /**
     *  查询转运明细
     * @return
     */
    public static List<HashMap<String,Object>> GetVehicleDispath_Summary(long comCusID, long comBrID, String operateVehDispNumber)
    {


        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "GetVehicleDispath_Summary";
        String webUrl = "http://125.46.79.254:8211/Offer/VehicleDispathHandover.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("comCusID",comCusID);
        soapObject.addProperty("comBrID",comBrID);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");


        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);
        envelope.implicitTypes = true;

        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;

            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("GetVehicleDispath_Summary获取到数据:" + object.toString());
            String jsonStr=object.toString();

            if(!jsonStr.equals("anyType{}"))
            {

                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();

                    if (jsonObject.has("ProCategory")) {
                        hashMap.put("ProCategory", jsonObject.get("ProCategory"));
                    } else {
                        continue;

                    }
                    if (jsonObject.has("ProCategoryName")) {

                        hashMap.put("ProCategoryName", jsonObject.get("ProCategoryName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("ProMeasureUnitName")) {
                        hashMap.put("ProMeasureUnitName", jsonObject.get("ProMeasureUnitName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("ProNumber")) {
                        hashMap.put("ProNumber", jsonObject.get("ProNumber"));
                    } else {
                        hashMap.put("ProNumber", "--");
                    }

                    hashMap.put("ActualProNumber",0);
                    hashMap.put("Remark","");


                    list.add(hashMap);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     *  查询转运明细
     * @return
     */
    public static List<HashMap<String,Object>> GetDispathHandover_Info(int pageIndex,int pageSize,  String operateVehDispNumber)
    {


        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "GetDispathHandover_Info";
        String webUrl = "http://125.46.79.254:8211/Offer/VehicleDispathHandover.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("pageIndex",pageIndex);
        soapObject.addProperty("pageSize",pageSize);
        soapObject.addProperty("operateVehDispNumber",operateVehDispNumber);


        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");


        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);
        envelope.implicitTypes = true;

        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("GetDispathHandover_Info获取到数据:" + object.toString());
            String jsonStr=object.toString();

            if(!jsonStr.equals("anyType{}"))
            {

                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();


                    if (jsonObject.has("ProCategoryName")) {

                        hashMap.put("ProCategoryName", jsonObject.get("ProCategoryName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("ProMeasureUnitName")) {
                        hashMap.put("ProMeasureUnitName", jsonObject.get("ProMeasureUnitName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("ProNumber")) {
                        hashMap.put("ProNumber", jsonObject.get("ProNumber"));
                    } else {
                        hashMap.put("ProNumber", "--");
                    }

                    if (jsonObject.has("ActualProNumber")) {
                        hashMap.put("ActualProNumber", jsonObject.get("ActualProNumber"));
                    } else {
                        hashMap.put("ActualProNumber", "--");
                    }

                    if (jsonObject.has("HandoverTime")) {
                        hashMap.put("HandoverTime", jsonObject.get("HandoverTime"));
                    } else {
                        hashMap.put("HandoverTime", "--");
                    }


                    if (jsonObject.has("HandoverMoney")) {
                        hashMap.put("HandoverMoney", jsonObject.get("HandoverMoney"));
                    } else {
                        hashMap.put("HandoverMoney", "--");
                    }

                    if (jsonObject.has("HandoverPaidMoney")) {
                        hashMap.put("HandoverPaidMoney", jsonObject.get("HandoverPaidMoney"));
                    } else {
                        hashMap.put("HandoverPaidMoney", "--");
                    }

                    if (jsonObject.has("HandoverActualMoney")) {
                        hashMap.put("HandoverActualMoney", jsonObject.get("HandoverActualMoney"));
                    } else {
                        hashMap.put("HandoverActualMoney", "--");
                    }

                    list.add(hashMap);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }

    /**
     * 添加交接记录
     * @param model
     * @return
     */
    public static String Add(VehicleDispathHandover_Model model, String jsonStr)
    {
        String methodName = "Add";
        String webUrl = "http://125.46.79.254:8211/Offer/VehicleDispathHandover.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("mo",model);
        soapObject.addProperty("detailJson",jsonStr);

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;
        envelope.addMapping(NAMESPACE,"VehicleDispathHandover_Model",VehicleDispathHandover_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "添加失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))//执行成功
            {
                return  "";
            }
            else
            {
                resatult=resatult;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }

    /**
     * 验证验证码
     * @param verifyType
     * @return
     */
    public static String IsVerifyCode( int verifyType, int verifySort, String verifyCode, String verifyVehDispNumber)
    {
        String methodName = "IsVerifyCode";
        String webUrl = "http://125.46.79.254:8211/Offer/VehicleDispathHandover.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("verifyType",verifyType);
        soapObject.addProperty("verifySort",verifySort);
        soapObject.addProperty("verifyCode",verifyCode);
        soapObject.addProperty("verifyVehDispNumber",verifyVehDispNumber);

        //SoapHeader验证
        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_wasteoil");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "rtyrhgs-klyuirhj-34qfdasd-nbfghj");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.headerOut = header;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(soapObject);

        String resatult = "添加失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))//执行成功
            {
                return  "";
            }
            else
            {
                resatult=resatult;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }


}


