package com.eyun.framework.webservice.impl;

import android.os.Handler;
import android.os.Message;

import com.eyun.configure.ServerConfig;
import com.eyun.framework.util.FastJsonTools;
import com.eyun.framework.webservice.BaseWebServiceDao;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BaseWebServiceDaoImpl  implements BaseWebServiceDao {

    private static volatile BaseWebServiceDaoImpl instance;
    private BaseWebServiceDaoImpl() {
    }

    public static BaseWebServiceDaoImpl getInstance() {
        if (instance == null) {
            synchronized (BaseWebServiceDaoImpl.class) {
                if (instance == null) {
                    instance = new BaseWebServiceDaoImpl();
                }
            }
        }
        return instance;
    }

    // 含有3个线程的线程池
    private static final ExecutorService executorService = Executors
            .newFixedThreadPool(3);


    @Override
    public void callWebService(String url, String methodName, HashMap<String, Object> properties, String addMappingKey, Class addMappingClass, WebServiceCallBack webServiceCallBack) {
        callWebService(url, methodName, properties, null,addMappingKey,addMappingClass, webServiceCallBack);
    }

    @Override
    public void callWebService(String url, String methodName, HashMap<String, Object> properties, WebServiceCallBack webServiceCallBack) {
        callWebService(url, methodName, properties, null,null, webServiceCallBack);
    }

    @Override
    public String callWebServiceUI(String url, String methodName, HashMap<String, Object> properties, Element[] header, String addMappingKey, Class addMappingClass) {
        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(ServerConfig.WebService.NAMESPACE, methodName);

        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, Object>> it = properties.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.setOutputSoapObject(soapObject);

        //SoapHeader验证
        if(header!=null){
            soapEnvelope.headerOut = header;
        }
        soapEnvelope.dotNet = true;
        soapEnvelope.implicitTypes = true;
        if(addMappingKey!=null&&addMappingClass!=null){
            soapEnvelope.addMapping(ServerConfig.WebService.NAMESPACE,addMappingKey, addMappingClass);
        }
        httpTransportSE.debug = true;
                SoapObject resultSoapObject = null;
                try {
                    httpTransportSE.call(ServerConfig.WebService.NAMESPACE + methodName, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultSoapObject = (SoapObject) soapEnvelope.getResponse();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {

                }
       return FastJsonTools.createJsonString(resultSoapObject);
    }

    @Override
    public String callWebServiceUI(String url, String methodName, HashMap<String, Object> properties, String addMappingKey, Class addMappingClass) {
        return  callWebServiceUI(url, methodName, properties, ServerConfig.WebService.HEADER,addMappingKey,addMappingClass);
    }

    @Override
    public String callWebServiceUI(String url, String methodName, HashMap<String, Object> properties) {
        return  callWebServiceUI(url, methodName, properties, null,null);
    }

    @Override
    public void callWebService(String url, final String methodName, HashMap<String, Object> properties, Element[] header, String addMappingKey, Class addMappingClass, final WebServiceCallBack webServiceCallBack) {
        // 创建HttpTransportSE对象，传递WebService服务器地址
        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
        // 创建SoapObject对象
        SoapObject soapObject = new SoapObject(ServerConfig.WebService.NAMESPACE, methodName);

        // SoapObject添加参数
        if (properties != null) {
            for (Iterator<Map.Entry<String, Object>> it = properties.entrySet()
                    .iterator(); it.hasNext();) {
                Map.Entry<String, Object> entry = it.next();
                soapObject.addProperty(entry.getKey(), entry.getValue());
            }
        }
        // 实例化SoapSerializationEnvelope，传入WebService的SOAP协议的版本号
        final SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        // 设置是否调用的是.Net开发的WebService
        soapEnvelope.setOutputSoapObject(soapObject);

        //SoapHeader验证
        if(header!=null){
            soapEnvelope.headerOut = header;
        }
        soapEnvelope.dotNet = true;
        soapEnvelope.implicitTypes = true;
        if(addMappingKey!=null&&addMappingClass!=null){
            soapEnvelope.addMapping(ServerConfig.WebService.NAMESPACE,addMappingKey, addMappingClass);
        }
        httpTransportSE.debug = true;

        // 用于子线程与主线程通信的Handler
        final Handler mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                // 将返回值回调到callBack的参数中
                webServiceCallBack.callBack((SoapObject) msg.obj);
            }

        };

        // 开启线程去访问WebService
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                SoapObject resultSoapObject = null;
                try {
                    httpTransportSE.call(ServerConfig.WebService.NAMESPACE + methodName, soapEnvelope);
                    if (soapEnvelope.getResponse() != null) {
                        // 获取服务器响应返回的SoapObject
                        resultSoapObject = (SoapObject) soapEnvelope.bodyIn;
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } finally {
                    // 将获取的消息利用Handler发送到主线程
                    mHandler.sendMessage(mHandler.obtainMessage(0,
                            resultSoapObject));
                }
            }
        });
    }


    /**
     *
     *
     * @author xiaanming
     *
     */
    public interface WebServiceCallBack {
        public void callBack(SoapObject result);
    }

}
