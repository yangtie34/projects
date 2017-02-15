package com.eyunsoft.app_wasteoil.Publics;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;

import java.util.ArrayList;

/**
 * Created by Administrator on 2014-12-11.
 */
public class KSoapWebService {

    // 命名空间
    private static final String NAMESPACE   = "http://tempuri.org/";
    // SOAPACTION
    private static String   SOAP_ACTION = "http://tempuri.org";

    /**
     *调用Webservice
     * @param urlWebservice地址
     * @param methodName方法名称
     * @param Parameters参数
     * @param ParValues返回值
     * @return
     */
    public static ArrayList<String> ExcuteWebServre (String url,String methodName, ArrayList<String> Parameters, ArrayList<String> ParValues )
    {
        ArrayList<String> list=new ArrayList<String>();
        SoapObject rpc = new SoapObject(NAMESPACE, methodName);
        for(int i=0;i<Parameters.size();i++) {
            rpc.addProperty(Parameters.get(i),ParValues.get(i));
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(rpc);
        SoapObject detail=null;
        try {

            HttpTransportSE ht = new HttpTransportSE(url);

            ht.debug = true;
            ht.call(SOAP_ACTION+"/"+methodName, envelope);
            detail = (SoapObject) envelope.getResponse();
        }
        catch (Exception e)
        {
            System.out.println("异常："+e.toString());
        }

        if(detail!=null) {
            for (int i = 0; i < detail.getPropertyCount(); i++) {

                list.add(detail.getProperty(i).toString());
                 System.out.println("detail.getProperty(" + i + ")："  + detail.getProperty(i));

            }
        }
        else
        {
            System.out.println("获取到数据");
        }
        return list;

    }

    /**
     *调用带有SoapHeader验证的Webservice
     * @param urlWebservice地址
     * @param methodName方法名称
     * @param Parameters参数
     * @param ParValues返回值
     * @return
     */
    public static ArrayList<String> ExcuteWebServreHeader (String url,String methodName, ArrayList<String> Parameters, ArrayList<String> ParValues )
    {
        ArrayList<String> list=new ArrayList<String>();
        SoapObject rpc = new SoapObject(NAMESPACE, methodName);
        for(int i=0;i<Parameters.size();i++) {
            rpc.addProperty(Parameters.get(i),ParValues.get(i));
        }

        Element[] header = new Element[1];
        header[0] = new Element().createElement(NAMESPACE, "MySoapHeader");

        Element username = new Element().createElement(NAMESPACE, "UserName");
        username.addChild(Node.TEXT, "eyunsoft_jyun_bang");
        header[0].addChild(Node.ELEMENT, username);

        Element pass = new Element().createElement(NAMESPACE, "PassWord");
        pass.addChild(Node.TEXT, "utyjh-luirf-576gfg-wqerw");
        header[0].addChild(Node.ELEMENT, pass);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = rpc;
        envelope.headerOut=header;
        envelope.dotNet = true;

        envelope.setOutputSoapObject(rpc);
        SoapObject detail=null;
        try {

            HttpTransportSE ht = new HttpTransportSE(url);

            ht.debug = true;
            ht.call(SOAP_ACTION+"/"+methodName, envelope);
            detail = (SoapObject) envelope.getResponse();
        }
        catch (Exception e)
        {
            System.out.println("异常："+e.toString());
        }

        if(detail!=null) {
            for (int i = 0; i < detail.getPropertyCount(); i++) {

                list.add(detail.getProperty(i).toString());
                System.out.println("detail.getProperty(" + i + ")："  + detail.getProperty(i));

            }
        }
        else
        {
            System.out.println("获取到数据");
        }
        return list;

    }


}
