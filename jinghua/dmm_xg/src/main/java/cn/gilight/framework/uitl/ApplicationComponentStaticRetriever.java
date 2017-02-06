package cn.gilight.framework.uitl;

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
    	checkApplicationContext();
        return (T)appCtx.getBean(componentName);
    }
    
    /**
     * 根据组件名称获取相应实例。
     * @param <T>
     * @param componentName
     * @param classType
     * @return
     */
    public static Object getComponentByItsName(String componentName)
    {
    	checkApplicationContext();
        return appCtx.getBean(componentName);
    }
    
	/**
	 * 获取spring容器中的bean，并自动转换成对象类型
	 * @param Class<T> cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getComponentByItsName(Class<T> cls){
		checkApplicationContext();
		return (T)appCtx.getBeansOfType(cls);
	}

	/**
	 * 检测 ApplicationContext 是否注入
	 */
	private static void checkApplicationContext(){
		if(appCtx == null){
			/**
			 * 一般在applicationContext.xml配置，如果使用了springMVC，最好在springMVC的配置文件配置。
			 * 因为spring容器的ApplicationContext不能得到 springMVC 中的bean，还不知道详细原因
			 */
			throw new IllegalStateException("getComponentByItsName方法必须在ApplicationContext成功初始化以后调用，" 
                    + "ApplicationComponentStaticRetriever可能没有在ApplicationContext中配置ApplicationComponentStaticRetriever组件！");
		}
	}
}
