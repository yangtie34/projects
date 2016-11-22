package com.jhnu.framework.solr.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jhnu.framework.solr.MySolrServer;
import com.jhnu.framework.solr.SolrUtils;
import com.jhnu.framework.solr.entity.SolrQueryEntity;
import com.jhnu.framework.solr.service.MenuSolrService;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.menu.entity.Menu;
import com.jhnu.system.menu.service.MenuService;


@Service(value="menuSolrService")
public class MenuSolrServiceImpl implements MenuSolrService {
	
	private static final Logger logger = LoggerFactory.getLogger(MenuSolrServiceImpl.class);
	
	@Autowired MenuService menuService;
	
    private HttpSolrServer server;

    public void initSolrServer() {
         server = MySolrServer.getInstance(null);
    }
    
	@Override
	@SuppressWarnings("rawtypes")
    public boolean addOrUpdateMenuDoc(Menu t,Map<String,Object> updateFiledMap) {
    	boolean res = true;
    	try{	
				//判断必填字段是否为空
				if(t==null || StringUtils.isEmpty(t.getId())
		//				||StringUtils.isEmpty(travelInfo.getTongHangUser().getId())
						){
					res = false;
				}else{
					//初始化solr server
					initSolrServer();
			        //创建doc文档  对solr而言,如果 id 相同，其它的属性值不相同的话，后者会更新前者。
			        SolrInputDocument doc = new SolrInputDocument();
			        
			        if(updateFiledMap!=null){//只更新Map里面的filed字段
			        	SolrDocument queryDoc = getMenuDocById(t.getId().toString());
			        	if(queryDoc==null){
			        		return false;
			        	}
			        	doc = ClientUtils.toSolrInputDocument(queryDoc);
			        	Iterator<Entry<String, Object>> iter = updateFiledMap.entrySet().iterator(); 
			        	while (iter.hasNext()) { 
							Map.Entry entry = (Map.Entry) iter.next(); 
			        	    Object key = entry.getKey();
			        	    Object val = entry.getValue();
			        	    if(key!=null ){//&& doc.containsKey(key.toString()) //不用判断是否有key，没有也不会报错
			        	    	doc.removeField(key.toString());//如果有就必须先remove再add
			        	    	doc.addField(key.toString(), val);
			        	    }
			        	} 
			        }else{//根据TravelInfo t对象更新或创建索引
				        doc.addField("id", t.getId());
				        doc.addField("name", t.getName_());
				        doc.addField("desc", t.getDescription());
				        doc.addField("keyword", t.getKeyWord());
			        }
			        //添加一个doc文档
			        UpdateResponse response = server.add(doc);
			        // commit后才保存到索引库
			        server.commit();
			        Integer status = response.getStatus();
			        if(status!=0){
			        	res = false;
			        }
				}
	     }catch(Exception e){
	    	 e.printStackTrace();
	    	 res = false;
	    	 throw new IllegalArgumentException("添加或更新索引失败");
	     }
		 return res;
    }
	
    @Override
    public Page queryMenuDoc(SolrQueryEntity solrQueryEntity,Long userId,Page page){
    	//初始化solr server
		initSolrServer();
		
		String keyWords = solrQueryEntity.getKeyWords();
		List<String> queryTypes = solrQueryEntity.getQueryType();
		if(StringUtils.isEmpty(keyWords) || StringUtils.isEmpty(queryTypes)){
			return null;
		}
        SolrQuery query = new SolrQuery();
        
        //正确的使用查询
        if(StringUtils.isEmpty(keyWords)){
        	keyWords = "*";
        }
        String q="";
        for (int i = 0; i < queryTypes.size(); i++) {
        	q+=queryTypes.get(i)+":("+keyWords+") ";
		}
        
        query.set("q", q);//注意不加*
       
        //查询返回的字段 支持*，*代表全部返回
        query.setFields("*");
        
        //====添加用户菜单权限控制开始====
        List<Menu> menu=menuService.getMenuByThis(new Menu(1), userId);
        String fq="";
        for (int i = 0; i < menu.size(); i++) {
        	fq+="id:"+menu.get(i).getId()+" ";
		}
        query.addFilterQuery(fq);
        //====添加用户菜单权限控制结束====
        
        query.set("start", page.getStartIndex()); //起始数
        query.set("rows", page.getNumPerPage()); //每页条数
        
        /**
         * 分片查询在某些统计关键字的时候还是很有用的，可以统计关键字出现的次数，可以通过统计的关键字来搜索相关文档的信息
         * query.setFacet(true);//设置分片
           query.setFields("name");
         */
        query.setHighlight(true);//开启高亮组件
        String highLightFilds = "name,keyword,desc";//高亮字段不支持*，只能用展现字段来表示
        query.addHighlightField(highLightFilds);//添加高亮字段
        query.setHighlightSimplePre("<font color='red' >");//高亮关键字前缀
        query.setHighlightSimplePost("</font>");//高亮关键字后缀
        query.setHighlightSnippets(2);//结果分片数，默认为1
        SolrDocumentList list = null;
        
        try {
            QueryResponse response = server.query(query);
            //可以将返回回来的结果集 转化成JavaBeanList
//          List<Entity> entityList = response.getBeans(Entity.class);
            
            //输出查询结果集
            list = response.getResults();
            Map<String, Map<String, List<String>>> hightlightMap = response.getHighlighting();
            for (int i = 0; i < list.size(); i++) {
                SolrDocument doc = list.get(i);
                //出来高亮字段
                doc = SolrUtils.dealHighLightFilds(highLightFilds, doc, hightlightMap);
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
            logger.error("查询出现异常");
            throw new IllegalArgumentException("查询出现异常");
           
        }
        page.setSolrList(list);
        page.setTotalRows(Integer.valueOf(list.getNumFound()+""));
        page.setTotalPages();
        page.setLastIndex();
        return page;
    }
    
    @Override
    public boolean delMenuDocByChanPinId(String menuId) {
	    boolean res = true;
    	try {
    		if(!StringUtils.isEmpty(menuId)){
    			//初始化solr server
    			initSolrServer();
    			UpdateResponse response = server.deleteById(menuId);
    			server.commit();
    			Integer status = response.getStatus();
    	        if(status!=0){
    	        	res = false;
    	        }
            }
    		
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("删除索引失败");
		}
    	 return res;
     }
    
    
    @Override
    public SolrDocument getMenuDocById(String id) {
    	SolrDocument doc = null;
        try {
        	//初始化solr server
    		initSolrServer();
    		SolrQuery query = new SolrQuery();
            query.setQuery("id:" + id);
            QueryResponse rsp = server.query(query);
            SolrDocumentList docs = rsp.getResults();
            Iterator<SolrDocument> iter = docs.iterator();
            while (iter.hasNext()) {
                doc = iter.next();
            }
        }catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("根据id查询索引失败");
		}
        return doc;
    }
}

