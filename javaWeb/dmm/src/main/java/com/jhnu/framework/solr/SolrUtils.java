package com.jhnu.framework.solr;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrDocument;
/**
 * solr搜索工具类
 * @author Administrator
 *
 */
public class SolrUtils {

    //处理搜索引擎高亮字段
    public static SolrDocument dealHighLightFilds (String highLightFilds,
    		SolrDocument doc,Map<String, Map<String, List<String>>> hightlightMap){
    	String docId = (String)doc.get("id");
    	 //hightlight的键为Item的id，值唯一
    	if(StringUtils.isNotBlank(highLightFilds)&& hightlightMap!=null && hightlightMap.get(docId)!=null){
    		String highLightFildArray [] = highLightFilds.split(",");
    		for (int i = 0; i < highLightFildArray.length; i++) {
				if(highLightFildArray[i]!=null){
				 String curFild = highLightFildArray[i].toString();
		         String hlString = hightlightMap.get(docId).get(curFild)!=null? hightlightMap.get(docId).get(curFild).toString():"";
		         if(StringUtils.isNotBlank(hlString)){
//		              System.out.println(hlString);
		              doc.setField(curFild, hlString);
		         }
			  }
			}
    	}
    	return doc;
    			 
    }
}
