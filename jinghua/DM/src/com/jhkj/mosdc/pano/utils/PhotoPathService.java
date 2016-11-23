package com.jhkj.mosdc.pano.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;

/**
 * 学生、教职工照片路径获取类。
 * @ClassName: PhotoService 
 * @author zhangzg 
 * @date 2013年10月30日 下午4:23:54 
 *
 */
public class PhotoPathService extends BaseServiceImpl{
	private static final String ROOTPATH = "phothFile";
	private static final String XSPATH = "student";
	private static final String[] XSKEYS = {"XH","SFZH","ID","KSH"};
	private static final String[] JZGKEYS = {"ZGH","SFZH","ID"};
	private static final String JZGPATH = "teacher";
	private static final String[] SUPPORTLIST = {".jpg",".gif",".png",".jpeg",".bmp"};
	/**
	 * 根据学生id获取学生的照片路径。
	 * @Title: getPhotoPathByXsId 
	 * @param 
	 * @return String
	 * @throws
	 */
	public String getPhotoPathByXsId(long xsId){
		//根据学生id获取学生的学号、身份证号、id、准考证号，分别根据它们在配置的照片目录中寻找照片文件
		//如果找到文件，则返回该文件路径+文件名，如果都没有找到则返回空路径。
		//用身份证进行匹配文件的时候记得全部转成大写。
		//还需要获取学生的入学年份
		boolean exists = false;
		String photoPath = "";
		String sql = "SELECT T1.ID,T1.XH,T1.SFZH,T1.KSH,T2.MC AS RXNJ FROM TB_XS_JBXXB T1,TB_BASE_XXBZDMJG T2 "
				+ "WHERE T1.ID = "+xsId+" AND T1.RXNJ_ID = T2.ID";
		List<Map> results = baseDao.queryListMapBySQL(sql);
		if(results.size()==0){
			return photoPath;
		}else{
			Map map = results.get(0);
			Iterator it = map.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				int index = XSKEYS.toString().indexOf(key);
				if(index>=0){
					for(String suffix : SUPPORTLIST){
						String path = ROOTPATH+"/"+XSPATH+"/"+map.get("RXNJ")+"/"+map.get(key)+suffix;
						File temp = new File(path);
						if(temp.exists()){
							// 保存路径到学生表的照片字段。
							photoPath = path;
							break;
						}
					}
				}
			}
			return photoPath;
		}
		
	}
	/**
	 * 根据教职工id获取教职工的照片路径。
	 * @Title: getPhotoPathByJzgId 
	 * @param 
	 * @return String
	 * @throws
	 */
	public String getPhotoPathByJzgId(long jzgId){
		//根据学生id获取职工号、身份证号、id，分别根据它们在配置的照片目录中寻找照片文件
		//如果找到文件，则返回该文件路径+文件名，如果都没有找到则返回空路径。
		//用身份证进行匹配文件的时候记得全部转成大写。
		//还需要获取教职工的来校年份
		boolean exists = false;
		String photoPath = "";
		String sql = "SELECT T1.ID,T1.ZGH,T1.SFZH,T1.RXRQ FROM TB_JZG_JBXXB T1"
				+ "WHERE T1.ID = "+jzgId;
		List<Map> results= baseDao.queryListMapBySQL(sql);
		if(results.size()==0){
			return photoPath;
		}else{
			Map map = results.get(0);
			Iterator it = map.keySet().iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				int index = JZGKEYS.toString().indexOf(key);
				if(index>=0){
					for(String suffix : SUPPORTLIST){
						String path = ROOTPATH+"/"+JZGPATH+"/"+map.get("RXRQ")+"/"+map.get(key)+suffix;
						File temp = new File(path);
						if(temp.exists()){
							// 保存路径到教职工表的照片字段。
							photoPath = path;
							break;
						}
					}
				}
			}
			return photoPath;
		}
	}
	}