package com.jhnu.product.manager.scientific.service.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhnu.product.manager.scientific.dao.ScientificInputDao;
import com.jhnu.product.manager.scientific.dao.TCodeDao;
import com.jhnu.product.manager.scientific.dao.impl.KyArray;
import com.jhnu.product.manager.scientific.entity.APage;
import com.jhnu.product.manager.scientific.entity.CloumnAs;
import com.jhnu.product.manager.scientific.entity.Dept;
import com.jhnu.product.manager.scientific.entity.TCode;
import com.jhnu.product.manager.scientific.entity.TCodeSubject;
import com.jhnu.product.manager.scientific.entity.TResKylrTemp;
import com.jhnu.product.manager.scientific.excuteUtil.ExcelUtil;
import com.jhnu.product.manager.scientific.service.ScientificService;
import com.jhnu.system.common.BeanMap;
@Scope
@Service("scientificService")
public class ScientificServiceImpl implements ScientificService {
	// 自动注入
		@Autowired
		private TCodeDao tCodeDao;
		@Autowired//scientificInputDao
		private ScientificInputDao scientificInputDao;
		private static List<Map<String, String>> tableList=new LinkedList();
		private static Map<String, String> authMap=new LinkedHashMap();
		private static Map<String, String> tableAuth=new LinkedHashMap();
		private static Map<String, String> codeMap=new LinkedHashMap();
		private static Map<String, Object> entityMap=new LinkedHashMap();
		private static Map<String, Object> nyrMap=new LinkedHashMap();//完整日期
		private static Map<String, Object> nMap=new LinkedHashMap();//年日期
		{
			String[] tableClass=KyArray.tableClass;
			String[] tableas=KyArray.tableas;
			String[] zzClass=KyArray.zzClass;
			for(int i=0;i<tableClass.length;i++){
				Map tmap=new LinkedHashMap();
				String[] tClass=tableClass[i].split(",");
				String[] tas=tableas[i].split(",");
				for(int j=0;j<tClass.length;j++){
					tmap.put(tClass[j], tas[j]);
					authMap.put(tClass[j], zzClass[i]);
				}
				tableList.add(tmap);
			}
			String[] tableAuths=KyArray.tableAuths;
			for(int j=0;j<tableAuths.length;j++){
				tableAuth.put(tableAuths[j], "");
			}
			codeMap=KyArray.codeMap;
			
			String[] nyrdate=KyArray.nyrdate;
			String[] ndate=KyArray.ndate;
			//{ acceptTime=,  endTime]=,  dispatchTime=,  registTime][startTime=,  accreditTime][completeTime=, null[][][conferenceTime][conferenceTime][][awardTime][pressTime][time][awardTime][dispatchTime=}
			
			String date="";
			for(int i=0;i<nyrdate.length;i++){
				String[] nyr=nyrdate[i].split(";");
				for(int j=0;j<nyr.length;j++){
					date+=Arrays.toString(nyr[j].split(","));
				}
			}
			date=date.replace("[]", "");
			date=date.replace("][", ",");
			date=date.replace("]", "");
			date=date.replace("[", "");
			Set set = new HashSet();
			 for(int i=0;i<date.split(",").length;i++)
			 set.add((String)date.split(",")[i]);
			 for(int i=0;i<set.toArray().length;i++)
				 nyrMap.put((String) set.toArray()[i], "");
			 date="";
			 set.clear();
				for(int i=0;i<ndate.length;i++){
					String[] n=ndate[i].split(";");
					for(int j=0;j<n.length;j++){
						date+=Arrays.toString(n[j].split(","));
					}
				}
				date=date.replace("[]", "");
				date=date.replace("][", ",");
				date=date.replace("]", "");
				date=date.replace("[", "");
				 for(int i=0;i<date.split(",").length;i++)
					 set.add((String)date.split(",")[i]);
				 for(int i=0;i<set.toArray().length;i++)
					 nMap.put((String) set.toArray()[i], "");
		}
	@Override
	public List<Map<String, String>> getTablesName() {
		// TODO Auto-generated method stub
		List<Dept> deptList=scientificInputDao.getDept();
		entityMap.put("deptId", deptList);
		entityMap.put("patentDept", deptList);
		List<TCodeSubject> subjectList=scientificInputDao.getXKML();
		entityMap.put("projectId", subjectList);
		return tableList;
	}

	@Override
	public Map<String, Map> getCloumn(String clas) {
		Class claKY=null;
		Class claAuth=null;//作者表
		Class claTemp=null;
		try {
			claKY=Class.forName("com.jhnu.product.manager.scientific.entity."+clas);
			claAuth=Class.forName("com.jhnu.product.manager.scientific.entity."+authMap.get(clas));
			claTemp=Class.forName("com.jhnu.product.manager.scientific.entity.TResKylrTemp");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//科研表code,entity,nyr,n,remark
		//作者表code,
		Field[] fields=claKY.getDeclaredFields();
		Field[] fieldsAuth=claAuth.getDeclaredFields();
		Field[] fieldsTemp=claTemp.getDeclaredFields();
		List kyList=new LinkedList();
		List tempList=new LinkedList();
		for(int i=0;i<fieldsTemp.length;i++){
			String coNameAs=fieldsTemp[i].getAnnotation(CloumnAs.class).name();
			String coName=fieldsTemp[i].getName();
			Map clumnMap=new LinkedHashMap();
			Map clumninfoMap=new LinkedHashMap();
			List List=new LinkedList();
			String type="";
			if(codeMap.containsKey(coName)){
				type="code";
				 List=tCodeDao.getType(codeMap.get(coName));
			}
			clumninfoMap.put("code", coName);
			clumninfoMap.put("name", coNameAs);
			clumninfoMap.put("type", type);
			clumnMap.put("info", clumninfoMap);
			clumnMap.put("data", List);
			tempList.add(clumnMap);
		}
		for(int i=0;i<fields.length;i++){
			if(i==1&&Arrays.binarySearch(new String[]{"TResPatent","TResThesis"},clas)>=0){
				Map clumnMap=new LinkedHashMap();
				Map clumninfoMap=new LinkedHashMap();
				clumninfoMap.put("code", "xmcc");
				clumninfoMap.put("name", "项目产出");
				clumninfoMap.put("type", "xmcc");
				clumnMap.put("info", clumninfoMap);
				clumnMap.put("data", null);
				kyList.add(clumnMap);
			}		
			String coNameAs=fields[i].getAnnotation(CloumnAs.class).name();
			String coName=fields[i].getName();
			String key=coName+"-"+coNameAs+"-";
			
			Map clumnMap=new LinkedHashMap();
			Map clumninfoMap=new LinkedHashMap();
			clumninfoMap.put("code", coName);
			clumninfoMap.put("name", coNameAs);
			List List=new LinkedList();
			String type=null;
			if(codeMap.containsKey(coName)){
				type="code";
				 List=tCodeDao.getType(codeMap.get(coName));
			}else if(entityMap.containsKey(coName)){
				type="entity";
				if("projectId".equalsIgnoreCase(coName)){
					 List=scientificInputDao.getXKML();
				}else{
					List=scientificInputDao.getDept();
				}
			}else if(tableAuth.containsKey(coName)){
				type="auth";
			}else if(nyrMap.containsKey(coName)){
				type="nyr";
			}else if(nMap.containsKey(coName)){
				type="n";
			}else if("remark".equalsIgnoreCase(coName)){
				type="remark";
			}else{
				type="text";
			}
			clumninfoMap.put("type", type);
			clumnMap.put("info", clumninfoMap);
			clumnMap.put("data", List);
			kyList.add(clumnMap);
		}
		List authList=new LinkedList();
		for(int i=0;i<fieldsAuth.length;i++){
			String coNameAs=fieldsAuth[i].getAnnotation(CloumnAs.class).name();
			String coName=fieldsAuth[i].getName();
			String key=coName+"-"+coNameAs+"-";
			Map clumnMap=new LinkedHashMap();
			Map clumninfoMap=new LinkedHashMap();
			clumninfoMap.put("code", coName);
			clumninfoMap.put("name", coNameAs);
			List List=new LinkedList();	
			String type=null;
			switch(coName){
			case"peopleName":
				type="text";
				break;
			case"teaNo":
				type="text";
				break;
			case"peopleIdentityCode":
				type="code";
				 List=tCodeDao.getType(codeMap.get(coName));
				break;
			default:type="hide";
			if(coName.endsWith("RoleCode")){
				type="code";
				 List=tCodeDao.getType(codeMap.get(coName));
			}
				break;
			}
			clumninfoMap.put("type", type);
			clumnMap.put("info", clumninfoMap);
			clumnMap.put("data", List);
			authList.add(clumnMap);
			}
		Map map=new LinkedHashMap();
		map.put("ky", kyList);
		map.put("kyAuth", authList);
		map.put("kyTemp", tempList);
		return map;
	}
	public Map strTomap(String str){
		ObjectMapper mapper = new ObjectMapper();  
		Map map=null;
		try {
			map = mapper.readValue(str,LinkedHashMap.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return map;
	}
	@Override
	public boolean insert(String map) {
		List<List> lists=mapTolist(strTomap(map));
		for(int i=0;i<lists.size();i++){
			if(!scientificInputDao.insert(lists.get(i))){
				return false;
			}
		}
		return true;
	}
public List mapTolist(Map map){
	Class o=null;
	Class auth=null;
	try {
		o=Class.forName("com.jhnu.product.manager.scientific.entity."+map.get("clas"));
		auth=Class.forName("com.jhnu.product.manager.scientific.entity."+authMap.get(map.get("clas")));
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	List<Map<String,Map<String,String>>> list=(List) map.get("list");
	List listky=new LinkedList();
	List listauth=new LinkedList();
	List listxmcc=new LinkedList();//项目产出
	List listtemp=new LinkedList();
	List listcode=new LinkedList();
	List lists=new LinkedList();
	for(int i=0;i<list.size();i++){
		for(String key : list.get(i).get("ky").keySet()){
			try {
				List lm=scientificInputDao.getAuthorInfo(list.get(i).get("ky").get("id"),auth.newInstance());
				if(lm.size()>0)
				scientificInputDao.delete(BeanMap.toBeanList(auth,lm ));
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(key.equalsIgnoreCase("xmcc")){
				String proid=list.get(i).get("ky").get(key);
				String id=scientificInputDao.selectxmccId(list.get(i).get("ky").get("id"),map.get("clas").toString());
				if(id==null)
				scientificInputDao.excutexmcc(proid,list.get(i).get("ky").get("id"),map.get("clas").toString());
			}
			String val="";
			if(codeMap.containsKey(key)){
				val=list.get(i).get("ky").get(key);
				if(val.startsWith("new")){
					TCode code=new TCode();
					String c[]=val.split(",");
					code.setCode(c[0].substring(3, c[0].length()));
					code.setCodeCategory("XB");
					code.setCodeType(tCodeDao.getCodeType(codeMap.get(key)));
					code.setCodetypeName("");
					code.setName(c[1]);
					code.setIstrue("1");
					code.setOrder((short)1);
					listcode.add(code);
					val=c[0].substring(3, c[0].length());
				}else{
					val=val.split(",")[0];
				}
				list.get(i).get("ky").put(key, val);
			}
		}
		listky.add(BeanMap.toBean(o, list.get(i).get("ky")));
		List auths=(List) list.get(i).get("auth");
		for(int j=0;j<auths.size();j++){
			listauth.add(BeanMap.toBean(auth, (Map) auths.get(j)));
		}
			listtemp.add(BeanMap.toBean(new TResKylrTemp().getClass(), list.get(i).get("temp")));
	}
	lists.add(listky);
	if(listauth.size()>0)lists.add(listauth);
	lists.add(listtemp);
	if(listcode.size()>0)lists.add(listcode);
	return lists;
}
	@Override
	public boolean update(String map) {
		List<List> lists=mapTolist(strTomap(map));
		for(int i=0;i<lists.size();i++){
			if(!scientificInputDao.update(lists.get(i))){
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean delete(String map) {
		List<List> lists=mapTolist(strTomap(map));
		for(int i=0;i<lists.size();i++){
			if(!scientificInputDao.delete(lists.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public String getCodeStr(List list,String str){
		String name=null;
		for(int i=0;i<list.size();i++){
			if(((String) ((Map) list.get(i)).get("code")).equalsIgnoreCase(str)){
				name=(String) ((Map)list.get(i)).get("name");
			}
		}
		return name;
	}

	@Override//输出数据
	public List view(String clas,String flag) {
		APage apage= new APage();
		TResKylrTemp temp= new TResKylrTemp();
		temp.setFlagCode(flag);
		Object ky= null;
		try {
			try {
				ky= Class.forName("com.jhnu.product.manager.scientific.entity."+clas).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		APage page=scientificInputDao.getKyInfo(apage, ky, temp, authMap);
		Map<String, Map> map=getCloumn(clas);
		Map<String,List<Map<String, String>>> pagemap=page.getMap();
		List list=new LinkedList();
		for(int i=0;i<pagemap.get("ky").size();i++){
			if(Arrays.binarySearch(new String[]{"TResPatent","TResThesis"},clas)>=0){
				String id=scientificInputDao.selectxmccId(pagemap.get("ky").get(i).get("id"),clas);
				if(id!=null)
				pagemap.get("ky").get(i).put("xmcc", id);
			}
			Map mapall=new LinkedHashMap();
			Map kymap=new LinkedHashMap();
			List listauth=new LinkedList();
			Map kytemp=new LinkedHashMap();
			for(int j=0;j<pagemap.get("auth").size();j++){
				for(String key : pagemap.get("auth").get(j).keySet()){
					if(!key.equalsIgnoreCase("id")&&key.matches(".*Id")&&pagemap.get("auth").get(j).get(key).equals(pagemap.get("ky").get(i).get("id"))){
						listauth.add(pagemap.get("auth").get(j));
						break;
					}
				}
			}
			for(int j=0;j<pagemap.get("temp").size();j++){
				int k=0;
				for(String key : pagemap.get("temp").get(j).keySet()){
					if( pagemap.get("temp").get(j).get("kyId").equalsIgnoreCase(pagemap.get("ky").get(i).get("id"))){
						kytemp=pagemap.get("temp").get(j);
						k=1;
						break;
					}
				}
				if(k==1)break;
			}
			kymap=pagemap.get("ky").get(i);
			mapall.put("ky", kymap);
			mapall.put("auth", listauth);
			mapall.put("temp", kytemp);
			list.add(mapall);
		}

		return list;
	}

	@Override
	public Map findOut(String str) {
		//{type:auth,items:{name:"",type}}
		ObjectMapper mapper = new ObjectMapper();  
		String result="";
		Map map=new LinkedHashMap();
	      try {
			JsonNode data = mapper.readTree(str);
			String type = data.get("type").asText();  
			JsonNode items = data.path("items"); 
			List list=null;
			String field="";
			switch(type){
			case "auth":
				field="ID 名字";
				String name = items.get("name").asText();  
				list=scientificInputDao.getAuthorInfo(items.get("name").asText(), items.get("type").asText());
				break;
			case "ky":
				field="ID 名称";
				list=scientificInputDao.getkyId(items.get("name").asText(), items.get("type").asText());
				break;
			}
			
			map.put("field", field);
			map.put("list", list);
			
			result=map.toString();
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return map;
	}
	//查询出一个id
	public String selectAId(String str) {
		return scientificInputDao.selectAId();
	}
	@Override
	public String viewFindName(String str) {
		ObjectMapper mapper = new ObjectMapper();  
		JsonNode data = null;
		try {
			data = mapper.readTree(str);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String id = data.get("id").asText();  
		String type = data.get("type").asText();  
		return scientificInputDao.getTheName(id,type);
		
	}
	@Override
	public HSSFWorkbook getXls(String titles,String sheetName) {
		HSSFWorkbook hssf=ExcelUtil.getXls(titles, sheetName);
		return hssf;
	}
	@Override
	public List<Map> readXls(MultipartFile file) {
		List<Map> list=null;
		try {
			list= ExcelUtil.readXls(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
