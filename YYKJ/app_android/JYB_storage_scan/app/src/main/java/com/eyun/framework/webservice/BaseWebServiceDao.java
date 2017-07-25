package com.eyun.framework.webservice;



import com.eyun.framework.webservice.impl.BaseWebServiceDaoImpl.WebServiceCallBack;

import org.ksoap2.serialization.SoapObject;
import org.kxml2.kdom.Element;

import java.util.HashMap;

public interface BaseWebServiceDao {
    /**
     *
     * @param url
     *            WebService服务器地址
     * @param methodName
     *            WebService的调用方法名
     * @param properties
     *            WebService的参数
     * @param webServiceCallBack
     *            回调接口
     */
     void callWebService(String url, final String methodName,
                                      HashMap<String, Object> properties,
                                      Element[] header,
                                      String addMappingKey,
                                      Class addMappingClass,
                                      WebServiceCallBack webServiceCallBack) ;

     void callWebService(String url, final String methodName,
                                      HashMap<String, Object> properties,
                                      String addMappingKey,
                                      Class addMappingClass,
                                      WebServiceCallBack webServiceCallBack);

     void callWebService(String url, final String methodName,
                                      HashMap<String, Object> properties,
                                      WebServiceCallBack webServiceCallBack);

    String callWebServiceUI(String url, final String methodName,
                            HashMap<String, Object> properties,
                            Element[] header,
                            String addMappingKey,
                            Class addMappingClass) ;

    String callWebServiceUI(String url, final String methodName,
                        HashMap<String, Object> properties,
                        String addMappingKey,
                        Class addMappingClass);

    String callWebServiceUI(String url, final String methodName,
                        HashMap<String, Object> properties);
}
