package com.jhnu.framework.solr;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.util.StringUtils;

import com.jhnu.util.common.PropertiesUtils;
/**
 *solr 全局配置，主要管理 server的初始化参数 及 关闭回收资源操作
 */
public class MySolrServer {

    private volatile static  MySolrServer my = null;  
    private static String url = "";  
    private HttpSolrServer httpSolrServer;
    
    private MySolrServer() {
    	
    }
  
  
    /**
     * SolrServerClient 是线程安全的 需要采用单例模式 
     * 此处实现方法适用于高频率调用查询 
     * @param type 如果type等于空则读取配置文件数据，否则读取type的连接地址
     * @return SolrServerClient
     */
    public static HttpSolrServer getInstance(String type) {
    	if(!StringUtils.isEmpty(type)){
    		url=type;
    	}
    	synchronized (MySolrServer.class) {
	        if (my == null) {  
	            	my = new MySolrServer();
	        }
	        if(StringUtils.isEmpty(url)){
	        	url=PropertiesUtils.getDBPropertiesByName("solr.url");
	        }
	        return my.getServer(url);
    	}
    }  
    /** 
     * 初始化的HttpSolrServer 对象,并获取此唯一对象 
     * 配置按需调整 
     * @param url 是字符串url 是SolrServer类的静态变量
     * @return 此server对象 
     */  
    private HttpSolrServer getServer(String url) { 
    	if (httpSolrServer == null) {  
            	httpSolrServer = new HttpSolrServer(url); 
            	httpSolrServer.setMaxRetries(1); // defaults to 0. > 1 not recommended.
            	httpSolrServer.setConnectionTimeout(5000); // 5 seconds to establish TCP
            	httpSolrServer.setSoTimeout(1000); // socket read timeout
            	httpSolrServer.setDefaultMaxConnectionsPerHost(100);
            	httpSolrServer.setMaxTotalConnections(100);
            	httpSolrServer.setFollowRedirects(false); // defaults to false
            	httpSolrServer.setAllowCompression(true);
            }  
    	
		return httpSolrServer;
    }  
}