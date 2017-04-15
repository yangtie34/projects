package com.eyunsoft.app_wasteoil.bll;

import com.eyunsoft.app_wasteoil.App;
import com.eyunsoft.app_wasteoil.Model.CompanyUser_Model;
import com.eyunsoft.app_wasteoil.Model.NameToValue;

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

import static android.R.id.list;

/**
 * Created by Administrator on 2016/12/23.
 */

public class Category_BLL {

    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";
    public static void init(){
        productCategory=null;
    }
    private static ArrayList<NameToValue> productCategory;
    /**
     * 返回回收类别
     * @param
     * @return
     */
    public static ArrayList<NameToValue> GetProductCategory(long comId)
    {
        if(productCategory!=null)return new ArrayList<>(productCategory);
        String methodName = "GetProductCategory";
        String webUrl = "http://125.46.79.254:8211/Product/Category.asmx";
        long[] parmas=new long[2];
        if(App.getInstance().userType==0){//员工
            parmas[0]=App.getInstance().getSysComBrID(); parmas[1]=0;
        }else{
            parmas[0]=0; parmas[1]=App.getInstance().getCompanyUserID();
        }

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        if(GetProductCategory(parmas[0],parmas[1])){
            methodName="GetProductCategoryOften";
            soapObject = new SoapObject(NAMESPACE, methodName);
            soapObject.addProperty("comBrID",parmas[0]);
            soapObject.addProperty("comCusID",parmas[1]);
        }

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
            System.out.println("获取到数据:" + object.toString());
            if(!jsonStr.trim().equals(""))
            {
                JSONArray jsonArray = new JSONObject(jsonStr).getJSONArray("Table");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                    NameToValue value=new NameToValue();

                    if (jsonObject.has("Name")) {
                        value.InfoName=jsonObject.get("Name").toString().replace("\uE5E5","    ");
                    } else {
                        continue;

                    }
                    if (jsonObject.has("Code")) {
                        value.InfoValue=jsonObject.get("Code").toString();
                    } else {
                        continue;
                    }
                    list.add(value);
                }
                productCategory=list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
    /**
     * 返回回收类别
     * @param
     * @return
     */
    public static boolean GetProductCategory(long comId,long cusId)
    {
        String methodName = "ProductCategoryOften_IsRecord";
        String webUrl = "http://125.46.79.254:8211/Product/Category.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("comBrID",comId);
        soapObject.addProperty("comCusID",cusId);
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
       boolean bool=false;
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            String jsonStr=object.toString();
            System.out.println("获取到数据:" + object.toString());
            if(jsonStr.contains("false")){bool=false;
            }else{
                bool=true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bool;
    }
    /**
     * 返回回收类别
     * @param code
     * @return
     */
    public static String GetHazardNature(long code)
    {
        String methodName = "GetHazardNature";
        String webUrl = "http://125.46.79.254:8211/Product/Category.asmx";

        SoapObject soapObject = new SoapObject(NAMESPACE, methodName);
        soapObject.addProperty("code",code);

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
        try {

            HttpTransportSE httpTransportSE = new HttpTransportSE(webUrl);
            httpTransportSE.debug = true;
            httpTransportSE.call(SOAP_ACTION + "/" + methodName, envelope);
            envelope.bodyOut = httpTransportSE;

            Object object = envelope.getResponse();
            String jsonStr=object.toString();
            System.out.println("获取到数据:" + object.toString());
            if(!jsonStr.trim().equals("anyType{}"))
            {
               resatult=jsonStr;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return resatult;
    }
}
