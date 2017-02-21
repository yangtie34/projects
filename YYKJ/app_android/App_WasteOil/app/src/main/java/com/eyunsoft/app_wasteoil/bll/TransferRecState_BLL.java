package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.Model.TransferRecState_Model;
import com.eyunsoft.app_wasteoil.Publics.Convert;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class TransferRecState_BLL {

    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";


    /**
     * 抢单
     * @param model
     * @param operateRecNumber
     * @return
     */
    public static String ConfirmProcess_RecState_Accept(TransferRecState_Model model, String operateRecNumber)
    {
        String methodName = "ConfirmProcess_RecState_Accept";
        String webUrl = "http://125.46.79.254:8211/Offer/TransferRecState.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("isConfirm","true");
        soapObject.addProperty("moState",model);
        soapObject.addProperty("operateRecNumber",operateRecNumber);


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
        envelope.addMapping(NAMESPACE,"TransferRecState_Model",TransferRecState_Model.class);
        envelope.setOutputSoapObject(soapObject);

        String resatult = "确认失败";
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            System.out.println("获取到数据:" + object.toString());
            resatult = object.toString();
            if(resatult.equals("anyType{}"))
            {
                resatult="";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }
    /**
     *  订单查询
     * @param Condition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static List<HashMap<String,Object>> TransRec_Select(String Condition, int pageIndex, int pageSize)
    {


        List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();

        String methodName = "TransferRecState_Select";
        String webUrl = "http://125.46.79.254:8211/Offer/TransferRecState.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);

        soapObject.addProperty("pageIndex",pageIndex);
        soapObject.addProperty("pageSize",pageSize);
        soapObject.addProperty("Condition","["+Condition+"]");
        soapObject.addProperty("orderField","");
        soapObject.addProperty("orderType","");

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
            System.out.println("查询条件:" + Condition);
            System.out.println("获取到数据:" + object.toString());
            String jsonStr=object.toString();

            if(!jsonStr.equals("anyType{}"))
            {

                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("table1");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    HashMap<String, Object> hashMap = new HashMap<String, Object>();

                    if (jsonObject.has("RecNumber")) {
                        hashMap.put("RecNumber", jsonObject.get("RecNumber"));
                    } else {
                        continue;

                    }
                    if (jsonObject.has("RecTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("RecTime").toString()));
                        hashMap.put("RecTime",t);
                    } else {
                        hashMap.put("RecTime", "");
                    }

                    if (jsonObject.has("ApproveTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("ApproveTime").toString()));
                        hashMap.put("ApproveTime",t);
                    } else {
                        hashMap.put("ApproveTime", "");
                    }

                    if (jsonObject.has("AcceptTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("AcceptTime").toString()));
                        hashMap.put("AcceptTime",t);
                    } else {
                        hashMap.put("AcceptTime", "");
                    }

                    if (jsonObject.has("ProCategoryName")) {

                        hashMap.put("ProCategoryName", jsonObject.get("ProCategoryName"));
                    } else {
                        continue;
                    }

                    if (jsonObject.has("ProShapeName")) {
                        hashMap.put("ProShapeName", jsonObject.get("ProShapeName"));
                    } else {
                        hashMap.put("ProShapeName", "--");
                    }


                    if (jsonObject.has("ProNumber")) {
                        hashMap.put("ProNumber", jsonObject.get("ProNumber").toString()+jsonObject.get("ProMeasureUnitName").toString());
                    } else {
                        hashMap.put("ProNumber", "--");
                    }

                    if (jsonObject.has("Remark")) {
                        hashMap.put("Remark", jsonObject.get("Remark"));
                    } else {
                        hashMap.put("Remark", "");
                    }

                    if (jsonObject.has("RecComBrName")) {
                        hashMap.put("CreateComBrName", jsonObject.get("RecComBrName"));
                    } else {
                        hashMap.put("CreateComBrName", "");
                    }

                    if (jsonObject.has("RecComCusComName")) {
                        hashMap.put("CreateUserFullname", jsonObject.get("RecComCusComName"));
                    } else {
                        hashMap.put("CreateUserFullname", "");
                    }

                    if (jsonObject.has("ProHazardNature")) {
                        hashMap.put("ProHazardNature", jsonObject.get("ProHazardNature"));
                    } else {
                        hashMap.put("ProHazardNature", "");
                    }

                    if (jsonObject.has("TransferAddress")) {
                        hashMap.put("TransferAddress", jsonObject.get("TransferAddress"));
                    } else {
                        hashMap.put("TransferAddress", "");
                    }

                    if (jsonObject.has("RecStateName")) {
                        hashMap.put("RecStateName", jsonObject.get("RecStateName"));
                    } else {
                        hashMap.put("RecStateName", "");
                    }

                    if (jsonObject.has("TransferTime")) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String t=format.format(Convert.ToDate(jsonObject.get("TransferTime").toString()));
                        hashMap.put("TransferTime", t);
                    } else {
                        hashMap.put("TransferTime", "");
                    }



                    list.add(hashMap);
                }
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
