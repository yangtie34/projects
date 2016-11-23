package com.jhkj.mosdc.framework.scheduling;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.jhkj.mosdc.framework.scheduling.po.MessageConfig;

/**
 * 信息处理所需要的初始化的静态数据
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2013-12-6
 * @TIME: 上午11:23:58
 */
public class MessageCallConfigParser implements FactoryBean,InitializingBean {
	private String inputFile;
	private List<MessageConfig> listConfig = null;
	

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		this.parserXml();
		
	}
	public void parserXml() throws Exception{
		 List<MessageConfig> list = new ArrayList<MessageConfig>();
		 URL path = this.getClass().getClassLoader().getResource(inputFile);
		 SAXReader saxReader = new SAXReader();
		 Document document = saxReader.read(new File(path.getPath()));
		 Element root = document.getRootElement();
		 Iterator<Element> iter = root.elementIterator();
		 while(iter.hasNext()){
			 Element me = iter.next();
			 Iterator<Element> meIter = me.elementIterator();
			 MessageConfig config = new MessageConfig();
			 while(meIter.hasNext()){
				 Element le = meIter.next();
				 String type = le.getName();
				 String txt = le.getTextTrim();
				 if(type.equals("service")){
					config.setService(txt);
				 }else if(type.equals("method")){
					config.setMethod(txt);
				 }else if(type.equals("name")){
					config.setName(txt);
				 }else if(type.equals("description")){
					config.setName(txt);
				 }
			 }
			 list.add(config);
		 }
		 listConfig = list;
	}


	@Override
	public Object getObject() throws Exception {
		// TODO Auto-generated method stub
		return listConfig;
	}


	@Override
	public Class getObjectType() {
		return List.class;
	}


	@Override
	public boolean isSingleton() {
		return true;
	}
	

}
