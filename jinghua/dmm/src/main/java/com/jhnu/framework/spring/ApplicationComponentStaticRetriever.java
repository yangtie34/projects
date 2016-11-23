package com.jhnu.framework.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 应用程序组件静态获取器。
 * 
 * <p>ApplicationComponentStaticRetriever通过Spring的回调方法获取ApplicationContext，从而将ApplicationContext中
 * 的组件通过静态方法提供给调用者。
 * 
 * @author xiayh
 * @since Dec 3, 2007
 */
public class ApplicationComponentStaticRetriever implements ApplicationContextAware,InitializingBean
{
    private static final Log logger = LogFactory.getLog(ApplicationComponentStaticRetriever.class);

    private static ApplicationContext appCtx;
    
    /**
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        appCtx = applicationContext;
        if (logger.isInfoEnabled() )
            logger.info("ApplicationContext has been successfully set to ApplicationComponentStaticRetriever, now ready for static call...");
    }
    
    
	public void afterPropertiesSet() throws Exception {
    	if (logger.isInfoEnabled() )
    		logger.info("ApplicationContext已经初始化完毕!");
	}


    /**
     * 根据组件名称获取相应实例。
     * @param <T>
     * @param componentName
     * @param classType
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getComponentByItsName(String componentName, Class<T> classType)
    {
        if ( appCtx == null )
            throw new IllegalStateException("getComponentByItsName方法必须在ApplicationContext成功初始化以后调用，" 
                    + "ApplicationComponentStaticRetriever可能没有在ApplicationContext中配置ApplicationComponentStaticRetriever组件！");
        
        return (T)appCtx.getBean(componentName);
    }
    
    /**
     * 根据组件名称获取相应实例。
     * @param <T>
     * @param componentName
     * @param classType
     * @return
     */
    public static Object  getComponentByItsName(String componentName)
    {
        if ( appCtx == null )
            throw new IllegalStateException("getComponentByItsName方法必须在ApplicationContext成功初始化以后调用，" 
                    + "ApplicationComponentStaticRetriever可能没有在ApplicationContext中配置ApplicationComponentStaticRetriever组件！");
        
        return appCtx.getBean(componentName);
    }
}
