package com.jhkj.mosdc.framework.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbBjxx;
import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.framework.po.TbXxjbxx;
import com.jhkj.mosdc.framework.po.TbZyxx;
import com.jhkj.mosdc.framework.po.TcXxbzdmjg;
import com.jhkj.mosdc.framework.po.TcXz;
import com.jhkj.mosdc.framework.po.TsCurrentXnxq;
import com.jhkj.mosdc.framework.po.TsStsx;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Converter;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.EhcacheUtil;
import com.jhkj.mosdc.framework.util.EntityUtil;
import com.jhkj.mosdc.framework.util.ExcelReader;
import com.jhkj.mosdc.framework.util.Node;
import com.jhkj.mosdc.framework.util.ReflectInvoke;
import com.jhkj.mosdc.framework.util.ServletUtils;
import com.jhkj.mosdc.framework.util.SqlParamsChange;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.jwgl.po.TbJwKcbzxtJxrl;
import com.jhkj.mosdc.jwgl.po.TbJwPkzb;
import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.Tree;
import com.jhkj.mosdc.permission.po.TreeNode;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
import com.jhkj.mosdc.xggl.xjgl.po.TbXjdaXjxx;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjZbjZzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.jhkj.mosdc.xxzy.po.TbXxzyJxzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyXq;
import com.jhkj.mosdc.xxzy.po.TbXxzyXzzzjg;
import com.jhkj.mosdc.xxzy.po.TbXxzyYx;
import com.jhkj.mosdc.xxzy.po.TbXxzyZyxx;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("rawtypes")
public class BaseServiceImpl implements BaseService {

	private final String stsx_cacheName = "system_ts_stsx";// 实体属性cache
	private final String bmb_cacheName = "system_bmb"; // 编码表cache

	private final static Logger logger = Logger
			.getLogger(BaseServiceImpl.class);

	protected BaseDao baseDao;	

	private ObjectMapper objectMapper = new ObjectMapper();

	private Map stsxMap = new HashMap<String, Object>();
	
	@Override
	public Long getRoleIdByDm(String roleDm){
		return this.getBmbidByDmAndStSx(roleDm, "TsJs", "jslxId");
	}
	@Override
	public String getRoleDmById(Long roleId){
		return this.getDmByBmbidAndStSx(roleId, "TsJs", "jslxId");
	}
	
	@Override
	public Integer getZcNumOfCurrentXnxq(){
		Map currentXnxq = this.getCurrentXnxq();
		Long xnId = Long.valueOf(String.valueOf(currentXnxq.get("xnId")));
		Long xqId = Long.valueOf(String.valueOf(currentXnxq.get("xqId")));
		Map jxrlInfo=this.getJxrlInfo(xnId, xqId);
		if(jxrlInfo==null){
			return -1;
		}
		String kssj=(String)jxrlInfo.get("kssj");
		String jssj=(String)jxrlInfo.get("jssj");		
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return DateUtils.getZcByDateFromBeginDate(format.parse(kssj), format.parse(jssj));
		}catch(Throwable e){
			return -1;
		}
	}

	@Override
	public Integer getCurrentZc()  {
		Map currentXnxq = this.getCurrentXnxq();
		Long xnId = Long.valueOf(String.valueOf(currentXnxq.get("xnId")));
		Long xqId = Long.valueOf(String.valueOf(currentXnxq.get("xqId")));
		Map jxrlInfo=this.getJxrlInfo(xnId, xqId);
		if(jxrlInfo==null){
			return -1;
		}
		String kssj=(String)jxrlInfo.get("kssj");
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try{
			return DateUtils.getZcByDateFromBeginDate(format.parse(kssj), new Date());
		}catch(Throwable e){
			return -1;
		}
	}

	public Object getCurrentZc(String str) throws Throwable {
		Integer zc = this.getCurrentZc();
		Map resultMap = new HashMap();
		resultMap.put("zc", zc);
		return JSONObject.fromObject(resultMap);
	}
	
	@Override
	public Map getJxrlInfo(Long xnId,Long xqId){
		Long zzjgId=null;
		TbXxzyJxzzjg zzjg = new TbXxzyJxzzjg();
		zzjg.setCclx("XX");
		zzjg = (TbXxzyJxzzjg) this.baseDao.loadLastEqual(zzjg);
		if(zzjg!=null){
			zzjgId=zzjg.getId();
		}else{
			TbXxzyXzzzjg xxzzjg=new TbXxzyXzzzjg();
			xxzzjg.setCclx("XX");
			xxzzjg=(TbXxzyXzzzjg)this.baseDao.loadLastEqual(xxzzjg);
			if(xxzzjg!=null){
				zzjgId=xxzzjg.getId();
			}
		}
		if(zzjgId!=null){
			TbJwKcbzxtJxrl jxrl = new TbJwKcbzxtJxrl();
			jxrl.setJxzzjgId(zzjgId);
			jxrl.setXnId(xnId);
			jxrl.setXqId(xqId);
			jxrl = (TbJwKcbzxtJxrl) this.baseDao.loadLastEqual(jxrl);
			Map jxrlMap=new HashMap();
			jxrlMap.put("xnId", jxrl.getXnId());
			jxrlMap.put("xqId", jxrl.getXqId());
			jxrlMap.put("kssj", jxrl.getKssj());
			jxrlMap.put("jssj", jxrl.getJssj());
			return jxrlMap;
		}else{
			return null;
		}
		
		
	}
	
	
	
	public Map getPreXnxq() {
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		String xqDm = this.getDmByBmbidAndStSx(xnxq.getXqId(), "TsCurrentXnxq",
				"xqId");
		Long preXnId = null;
		Long preXqId = null;
		String preXnMc = null;
		String preXqMc = null;
		String xnDm = null;
		if ("1".equals(xqDm)) {
			// 如果是第一学期，则学年-1 学期+1
			String curXnDm = this.getDmByBmbidAndStSx(xnxq.getXnId(),
					"TsCurrentXnxq", "xnId");
			int xn = Integer.valueOf(curXnDm);
			preXnId = this.getBmbidByDmAndStSx(String.valueOf(xn - 1),
					"TsCurrentXnxq", "xnId");
			preXqId = this.getBmbidByDmAndStSx("2", "TsCurrentXnxq", "xqId");
			preXnMc = this.getMcByBmbdmAndStSx(String.valueOf(xn - 1),
					"TsCurrentXnxq", "xnId");
			preXqMc = this.getMcByBmbdmAndStSx("2", "TsCurrentXnxq", "xqId");
		} else {
			// 如果是第二学期，则仅仅学期-1
			preXnId = xnxq.getXnId();
			preXnMc = xnxq.getXnMc();
			preXqId = this.getBmbidByDmAndStSx("1", "TsCurrentXnxq", "xqId");
			preXqMc = this.getMcByBmbdmAndStSx("1", "TsCurrentXnxq", "xqId");
		}
		Map map = new HashMap();
		map.put("xnId", preXnId);
		map.put("xqId", preXqId);
		map.put("xnMc", preXnMc);
		map.put("xqMc", preXqMc);
		return map;
	}

	public Object getPreXnxq(String str) {
		Map preXnxqMap = this.getPreXnxq();
		return JSONObject.fromObject(preXnxqMap);
	}
	
	

	/**
	 * 获取当前学年学期 返回map
	 * 
	 * @return
	 */
	public Map getCurrentXnxq() {
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		Map map = new HashMap();
		map.put("xnId", xnxq.getXnId());
		map.put("xqId", xnxq.getXqId());
		map.put("xnMc", xnxq.getXnMc());
		map.put("xqMc", xnxq.getXqMc());
		map.put("xnDm", this.getDmByBmbidAndStSx(xnxq.getXnId(),
				"TsCurrentXnxq", "xnId"));
		map.put("xqDm", this.getDmByBmbidAndStSx(xnxq.getXqId(),
				"TsCurrentXnxq", "xqId"));
		return map;
	}
	
	@Override
	public Long getCurrentZsjd() {
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		String xqdm = this.getDmByBmbidAndStSx(xnxq.getXqId(), "TsCurrentXnxq", "xqId");
		Long zsjdId = this.baseDao.queryIdByBm(xqdm, "XXDM-ZSJD");
		return zsjdId;
	}
	@Override
	public Long getCurrentRxnjId() {
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		String xndm = this.getDmByBmbidAndStSx(xnxq.getXnId(), "TsCurrentXnxq", "xnId");
		Long rxnjId = this.baseDao.queryIdByBm(xndm, "XXDM-RXNJ");
		return rxnjId;
	}

	public Map getNextXnxq() {
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		String xqDm = this.getDmByBmbidAndStSx(xnxq.getXqId(), "TsCurrentXnxq",
				"xqId");
		Long nextXnId = null;
		Long nextXqId = null;
		String nextXnMc = null;
		String nextXqMc = null;
		String nextXnDm = null;
		String nextXqDm = null;
		if ("1".equals(xqDm)) {
			// 如果是第一学期， 仅仅是学期+1
			nextXnId = xnxq.getXnId();
			nextXnDm = this.getDmByBmbidAndStSx(nextXnId, "TsCurrentXnxq",
					"xnId");
			nextXqDm = "2";
			nextXqId = this.getBmbidByDmAndStSx("2", "TsCurrentXnxq", "xqId");
			nextXnMc = xnxq.getXnMc();
			nextXqMc = this.getMcByBmbdmAndStSx("2", "TsCurrentXnxq", "xqId");
		} else {
			// 如果是第二学期，则学年+1 学期-1
			String curXnDm = this.getDmByBmbidAndStSx(xnxq.getXnId(),
					"TsCurrentXnxq", "xnId");
			int xn = Integer.valueOf(curXnDm);
			nextXnDm = String.valueOf(xn + 1);
			nextXnId = this.getBmbidByDmAndStSx(nextXnDm, "TsCurrentXnxq",
					"xnId");
			nextXqId = this.getBmbidByDmAndStSx("1", "TsCurrentXnxq", "xqId");
			nextXqDm = "1";
			nextXnMc = this.getMcByBmbdmAndStSx(String.valueOf(xn + 1),
					"TsCurrentXnxq", "xnId");
			nextXqMc = this.getMcByBmbdmAndStSx("1", "TsCurrentXnxq", "xqId");
		}
		Map map = new HashMap();
		map.put("xnId", nextXnId);
		map.put("xqId", nextXqId);
		map.put("xnMc", nextXnMc);
		map.put("xqMc", nextXqMc);
		map.put("xnDm", nextXnDm);
		map.put("xqDm", nextXqDm);
		return map;
	}

	public Object getNextXnxq(String str) {
		Map nextXnxqMap = this.getNextXnxq();
		return JSONObject.fromObject(nextXnxqMap);
	}

	/**
	 * 获取当前学年学期，返回json
	 * 
	 * @param str
	 * @return
	 */
	public Object getCurrentXnxq(String str) {
		Map xnxq = this.getCurrentXnxq();
		return JSONObject.fromObject(xnxq);
	}
	
	public synchronized Object updateOrGetPkzb(Long xnId,Long xqId,String xqkssj,String xqjssj){
		TbJwPkzb pkzb = new TbJwPkzb();
		pkzb.setXnId(xnId);
		pkzb.setXqId(xqId);
		TbJwPkzb tmpPkzb=(TbJwPkzb)this.baseDao.loadFirstEqual(pkzb);
		if(tmpPkzb==null){
			pkzb.setXqkssj(xqkssj);
			pkzb.setXqjssj(xqjssj);
			pkzb.setCjsj(DateUtils.date2String(new Date()));
			this.baseDao.insert(pkzb);
			return pkzb;
		}else{
			tmpPkzb.setXqkssj(xqkssj);
			tmpPkzb.setXqjssj(xqjssj);
			tmpPkzb.setXgsj(DateUtils.date2String(new Date()));
			this.baseDao.update(tmpPkzb);
			return tmpPkzb;
		}
	}

	/**
	 * 切换学年学期
	 * 
	 * @param str
	 * @return
	 * @throws Throwable
	 */
	public synchronized Object updateSwitchXnxq(String str) throws Throwable {
		Map resultMap = new HashMap();
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		// 先获取当前存的学年学期
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		// 获取学校对应的组织结构
		TbJwPkzb pkzb = new TbJwPkzb();
		pkzb.setXnId(xnxq.getXnId());
		pkzb.setXqId(xnxq.getXqId());
		pkzb=(TbJwPkzb)this.baseDao.loadFirstEqual(pkzb);
//		if(pkzb!=null){
//			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			Date kssj = format.parse(pkzb.getXqkssj());
//			Date jssj = format.parse(pkzb.getXqjssj());
//			Date now = new Date();
//			if (now.after(kssj) && now.before(jssj)) {
//				resultMap.put("success", false);
//				resultMap.put("msg", xnxq.getXnMc() + "学年" + xnxq.getXqMc()
//						+ "还没结束，不能切换学年学期");
//				return JSONObject.fromObject(resultMap);
//			}
//		}
			
		JSONObject json = JSONObject.fromObject(str);
		Long to_xnId = Long.valueOf(String.valueOf(json.get("xnId")));
		Long to_xqId = Long.valueOf(String.valueOf(json.get("xqId")));
		String to_year = this.getDmByBmbidAndStSx(to_xnId, "TsCurrentXnxq",
				"xnId"); // 要切换的学年 的年
		String to_xq = this.getDmByBmbidAndStSx(to_xqId, "TsCurrentXnxq",
				"xqId"); // 要切换 的学期

		String cur_year = this.getDmByBmbidAndStSx(xnxq.getXnId(),
				"TsCurrentXnxq", "xnId"); // 库里标示的当前学年 的年
		String cur_xq = this.getDmByBmbidAndStSx(xnxq.getXqId(),
				"TsCurrentXnxq", "xqId");
		String xqkssj = json.getString("xqkssj");
		String xqjssj = json.getString("xqjssj");
		int toYear = Integer.valueOf(to_year);
		int toXq = Integer.valueOf(to_xq);
		int curYear = Integer.valueOf(cur_year);
		int curXq = Integer.valueOf(cur_xq);

		this.switchXnxq(to_xnId, to_xqId, xnxq);
		if (toYear > curYear) {
			this.switchXnxq(to_xnId, to_xqId, xnxq);
		} else if (toYear == curYear && toXq > curXq) {
			this.switchXnxq(to_xnId, to_xqId, xnxq);
		} else {
			// 只能切换到当前时间的前一个学期，当前时间是假期
			if (toYear == curYear && toXq < curXq) {
				this.switchXnxq(to_xnId, to_xqId, xnxq);
			} else if (toYear < curYear && toXq > curXq) {
				this.switchXnxq(to_xnId, to_xqId, xnxq);
			} else {
				resultMap.put("success", false);
				resultMap.put("msg", "不允许切换到所选学年学期");
				return JSONObject.fromObject(resultMap);
			}
		}
		TbJwPkzb toPkzb = new TbJwPkzb();
		toPkzb.setXnId(to_xnId);
		toPkzb.setXqId(to_xqId);
		List pkzbList=this.baseDao.loadEqual(toPkzb);
		if(pkzbList.size()==0){
			toPkzb.setXqkssj(xqkssj);
			toPkzb.setXqjssj(xqjssj);
			this.baseDao.insert(toPkzb);
		}else{
			toPkzb=(TbJwPkzb)pkzbList.get(0);
			toPkzb.setXqkssj(xqkssj);
			toPkzb.setXqjssj(xqjssj);
			this.baseDao.update(toPkzb);
			
		}
		resultMap.put("success", true);
		return JSONObject.fromObject(resultMap);
	}
	
	public Object getXnxqSjd(String str){
		JSONObject json = JSONObject.fromObject(str);
		Long to_xnId = Long.valueOf(String.valueOf(json.get("xnId")));
		Long to_xqId = Long.valueOf(String.valueOf(json.get("xqId")));
		TbJwPkzb toPkzb = new TbJwPkzb();
		toPkzb.setXnId(to_xnId);
		toPkzb.setXqId(to_xqId);
		toPkzb=(TbJwPkzb)this.baseDao.loadFirstEqual(toPkzb);
		Map resultMap = new HashMap();
		resultMap.put("success", false);
		if(toPkzb!=null){
			resultMap.put("success", true);
			resultMap.put("xqkssj", toPkzb.getXqkssj());
			resultMap.put("xqjssj", toPkzb.getXqjssj());
		}
		return JSONObject.fromObject(resultMap);
	}

	public Object updateAutoSwitchXnxq() throws Throwable {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();

		TbXxzyJxzzjg zzjg = new TbXxzyJxzzjg();
		zzjg.setCclx("XX");
		zzjg = (TbXxzyJxzzjg) this.baseDao.loadLastEqual(zzjg);
		// 获取学校的教学日历
		TbJwKcbzxtJxrl jxrl = new TbJwKcbzxtJxrl();
		jxrl.setJxzzjgId(zzjg.getId());
		jxrl = (TbJwKcbzxtJxrl) this.baseDao.loadLastEqual(jxrl);
		Date kssj = format.parse(jxrl.getKssj());
		Date jssj = format.parse(jxrl.getJssj());
		if (now.after(kssj) && now.before(jssj)) {
			TsCurrentXnxq xnxq = new TsCurrentXnxq();
			xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
			if (!jxrl.getXnId().equals(xnxq.getXnId())
					&& !jxrl.getXqId().equals(xnxq.getXqId())) {
				DateFormat fullFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				xnxq.setXnId(jxrl.getXnId());
				xnxq.setXqId(jxrl.getXqId());
				xnxq.setXnMc(this.getMcByBmbidAndStSx(xnxq.getXnId(),
						"TsCurrentXnxq", "xnId"));
				xnxq.setXqMc(this.getMcByBmbidAndStSx(xnxq.getXqId(),
						"TsCurrentXnxq", "xqId"));
				xnxq.setXgsj(fullFormat.format(new Date()));
				xnxq.setXgr("system_job");
				this.baseDao.update(xnxq);
			}
		}
		return true;
	}
	
	public Object getXnBmbList(String str){
		List dataList=this.getBmbDataListByStmAndSx("TsCurrentXnxq", "xnId");
		return JSONArray.fromObject(dataList);
	}
	
	public Object getXqBmbList(String str){
		List dataList=this.getBmbDataListByStmAndSx("TsCurrentXnxq", "xqId");
		return JSONArray.fromObject(dataList);
	}

	public TbJwPkzb getCurrentXnxqPkzb() {
		TsCurrentXnxq xnxq = new TsCurrentXnxq();
		xnxq = (TsCurrentXnxq) this.baseDao.loadLastEqual(xnxq);
		TbJwPkzb pkzb = new TbJwPkzb();
		pkzb.setXnId(xnxq.getXnId());
		pkzb.setXqId(xnxq.getXqId());
		pkzb = (TbJwPkzb) this.baseDao.loadLastEqual(pkzb);
		return pkzb;
	}

	private void switchXnxq(Long xnId, Long xqId, TsCurrentXnxq xnxq) {
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		DateFormat fullFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		xnxq.setXnId(xnId);
		xnxq.setXqId(xqId);
		xnxq.setXnMc(this.getMcByBmbidAndStSx(xnxq.getXnId(), "TsCurrentXnxq",
				"xnId"));
		xnxq.setXqMc(this.getMcByBmbidAndStSx(xnxq.getXqId(), "TsCurrentXnxq",
				"xqId"));
		xnxq.setXgsj(fullFormat.format(new Date()));
		xnxq.setXgr(userInfo.getZwm());
		this.baseDao.update(xnxq);
	}

	/**
	 * 获取班级所在的校区
	 * 
	 * @param bjId
	 * @return
	 * @throws Throwable
	 */
	public TbXxzyXq getXqOfBj(Long bjId) throws Throwable {
		Long fjdId = null;
		TbXxzyBjxxb bjxxb = (TbXxzyBjxxb) this.baseDao.queryById(bjId,
				TbXxzyBjxxb.class.getName());
		/*
		 * 如果班级不存在，则默认传的是大班的id，即班级组织结构中的大班的id
		 */
		if (bjxxb == null) {
			TbXxzyBjZbjZzjg zbjzzjg = (TbXxzyBjZbjZzjg) this.baseDao.queryById(
					bjId, TbXxzyBjZbjZzjg.class.getName());
			fjdId = zbjzzjg.getFjdId();
		} else {
			fjdId = bjxxb.getFjdId();
		}
		TbXxzyJxzzjg jxzzjg = (TbXxzyJxzzjg) this.baseDao.queryById(fjdId,
				TbXxzyJxzzjg.class.getName());
		if ("ZY".equals(jxzzjg.getCclx())) {
			TbXxzyZyxx zy = (TbXxzyZyxx) this.baseDao.queryById(fjdId,
					TbXxzyZyxx.class.getName());
			TbXxzyXq xq = (TbXxzyXq) this.baseDao.queryById(zy.getXqId(),
					TbXxzyXq.class.getName());
			return xq;
		} else if ("YX".equals(jxzzjg.getCclx())) {
			TbXxzyYx yx = (TbXxzyYx) this.baseDao.queryById(fjdId,
					TbXxzyYx.class.getName());
			TbXxzyXq xq = (TbXxzyXq) this.baseDao.queryById(yx.getXqId(),
					TbXxzyXq.class.getName());
			return xq;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */

	public List queryTableContent(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues,
			String[] oerderBy, String[] orderByValue, PageParam pageParam) {
		return baseDao.queryTableContent(entityName, paramNames, values,
				likeParamNames, likeValues, oerderBy, orderByValue, pageParam);
	}

	/**
	 * {@inheritDoc}
	 */
	public List queryTableContent(String entityName, String[] paramNames,
			Object[] values, String[] likeParamNames, Object[] likeValues) {
		return baseDao.queryTableContent(entityName, paramNames, values,
				likeParamNames, likeValues);

	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public HSSFWorkbook exprotEntityExcel(String entityName) throws Exception {
		// 建立新HSSFWorkbook对象
		HSSFWorkbook wb = new HSSFWorkbook();
		List headerList = baseDao.querySearchHeader(entityName, null, null,
				null, null);
		// 建立新的sheet对象(不能为中文)
		HSSFSheet sheet = wb.createSheet("invoice");
		// 建立新行
		HSSFRow row = sheet.createRow(0);
		List sxList = new ArrayList();
		// 放入标题
		for (int i = 0; i < headerList.size(); i++) {
			// 创建一个记录
			HSSFCell csCell = row.createCell((short) i);
			// 放入标题
			TsStsx stsx = (TsStsx) headerList.get(i);

			csCell.setCellValue(stsx.getSxzwm());
			sxList.add(stsx.getSx());
		}
		// 不分页
		List list = baseDao.queryTableContentForMap(entityName);
		if (null == list || list.size() <= 0) {
			// 记录为空
			return wb;
		}
		int rowNum = 1;
		for (Iterator it = list.iterator(); it.hasNext();) {
			// 建立新行
			HSSFRow row2 = sheet.createRow(rowNum);
			Map map = (HashMap) it.next();
			for (int k = 0; k < sxList.size(); k++) {
				String tmp = (String) sxList.get(k);
				String storeValue = (String) map.get(tmp.trim());
				HSSFCell csCell = row2.createCell((short) k);
				csCell.setCellValue(storeValue);
			}
			rowNum = rowNum + 1;
		}
		return wb;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void importExcel(File dataFile) throws Exception {
		// 读取Excel数据
		ExcelReader readExcel = new ExcelReader(dataFile);
		try {
			readExcel.open();
		} catch (Exception e) {
			System.out.println("------读取excel文件出错,原因：" + e);
		}
		readExcel.setSheetNum(0); // 设置读取索引为0的工作表
		// 总行数
		int count = readExcel.getRowCount();

		@SuppressWarnings("unused")
		boolean isImportSuccess = true;
		//
		String[] titles = readExcel.readExcelLine(0);
		String sheetName = readExcel.getSheetName();
		List list = baseDao
				.querySearchHeader(sheetName, null, null, null, null);
		List tmpList = new ArrayList();
		Map tmpMap = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			TsStsx tsStsx = (TsStsx) list.get(i);
			String sxzwm = tsStsx.getSxzwm();
			String sx = tsStsx.getSx();
			tmpMap.put(sxzwm, sx);
		}
		for (int i = 0; i < count; i++) {
			String[] cols = readExcel.readExcelLine(i);
			for (int k = 0; k < titles.length; k++) {
				String sx = (String) tmpMap.get(titles[k]);
				String tmp[] = new String[] { sx, cols[k] };
				tmpList.add(tmp);
			}
			this.save(sheetName, tmpList);
		}
		// 文件上传并导入数据库后，需要把该文件删除
		if (dataFile.exists()) {
			dataFile.delete();
		}
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryById(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			String entityNameString = ServletUtils.entityPath(entityName);
			if (!"null".equals(entityNameString)) {
				String id = obj.getString("id");
				Object tmp = baseDao.queryById(id, entityNameString);
				return Struts2Utils.objects2Json(tmp);
			} else {
				return handleException(new Exception());
			}
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryEntityNameById(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String id = obj.getString("id");
			String entityName = obj.getString("entityName");
			Object tmp = baseDao.queryById(id, entityName);
			return Struts2Utils.bean2json(tmp, entityName);
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String update(String param) throws Exception {
		String params = param.replace("\n", "\\n");
		JSONObject obj = JSONObject.fromObject(params);
		String id = obj.getString("id");
		String entityName = obj.getString("entityName");
		List list = Struts2Utils.requestJsonToLists(params);
		baseDao.update(entityName, id, list);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryForAddByEntityName(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			//获取实体属性表的字段记录。
			List<TsStsx> stsxList = baseDao.getStsx("TsStsx");
			//遍历实体属性记录查询与实体属性相关的KEY
			JSONObject jsonObj = new JSONObject();
			for(TsStsx tsStsx:stsxList){
				if(obj.containsKey(tsStsx.getSx()) && !obj.containsKey("entityName")){
					jsonObj.put(tsStsx.getSx(), obj.get(tsStsx.getSx()));
				}
			}
			Map map = SqlParamsChange.getSQLParams(jsonObj);
//			UserInfo userInfo = UserPermissionUtil.getUserInfo();
			List list = baseDao.querySearchHeader(entityName,map);
//			List list = baseDao.querySearchHeader(entityName, null, null, null,
//					null);
			for (Object o : list) {
				TsStsx tsStsx = (TsStsx) o;
				String bmbfwm = tsStsx.getBmbfwm();
				 String bmstm = tsStsx.getBmbstm();
				 String sxzjlx = tsStsx.getSxzjlx();
				// 如果实体属性表中配置该字段的编码数据服务的话，反射该服务获取编码数据，
				// 那么，使用编码表和编码表标准代码配置解析编码数据。
				// 注意：在通过反射方式获取数据出现异常时，将按照编码表和标准代码的方式解析数据。
				if(bmbfwm!=null && !"".equals(bmbfwm)){
					String[] beanffm = bmbfwm.split("\\.");
					String beanid = beanffm[0],
						   methodName = beanffm[1];
					try{
						// 获得对象
						Object bean = ApplicationComponentStaticRetriever.getComponentByItsName(beanid);
						Object invokeResult = ReflectInvoke.reflectInvokeInitData(bean, methodName, new Object[]{});
						tsStsx.setBmsj(invokeResult);
						continue;
					}catch(Exception e){
						e.printStackTrace();
						logger.error("1.beanId.methodName形式配置服务。");
						logger.error("2.确保beanId在spring容器中是存在的。");
						logger.error("3.方法的返回值类型要是List。");
					}
				}
				if (null != bmstm && !"".equals(bmstm.trim())) {
					String bmdm = tsStsx.getBmbbzdm();
					if (null != bmdm && !"".equals(bmdm.trim())&& !bmdm.equals("0")) {
						if ("tree".equals(sxzjlx)) { // 增加基联下拉数据
							List bmsjList = getBmsj(bmstm, bmdm, null);
							tsStsx.setBmsj(bmsjList);
						} else {
							List bmsj = baseDao.queryBM(bmstm, bmdm, null);
							tsStsx.setBmsj(bmsj);
						}
					} else if ("treecombobox".equals(sxzjlx)) {// 增加树形下拉菜单表头数据
						Object tree = buildTree(bmstm, bmdm, null);
						tsStsx.setBmsj(tree);
					} else if (bmdm == null || "".equals(bmdm.trim())) {
						List bmsj = baseDao.queryTableContentForDm(bmstm,null);
						tsStsx.setBmsj(bmsj);
					}
				}
			}
			String json = Struts2Utils.objects2Json(list);
			return json;
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * 查询教师的班级对应所有父节点
	 * 
	 * @param permissIds
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getFjdIds(String permissIds) {
		if (permissIds.equals("")) {
			return null;
		}
		// 数据权限字符串分割为数组
		String[] permissArray = permissIds.split(",");
		// 把当前用户的数据权限赋给fjdIds变量
		String fjdIds = permissIds;
		// 遍历数据权限数组
		for (int i = 0; i < permissArray.length; i++) {
			// 把数组中的元素赋给fjdId变量
			Long nodeId = Long.valueOf(permissArray[i].toString());
			TbXxzyJxzzjg jxNode = null;
			TbXxzyXzzzjg xzNode = null;
			if (nodeId != 0) {
				jxNode = (TbXxzyJxzzjg) baseDao.get(TbXxzyJxzzjg.class, nodeId);
				xzNode = (TbXxzyXzzzjg) baseDao.get(TbXxzyXzzzjg.class, nodeId);
			}
			Long fjdId = nodeId == 0 ? 0 : (jxNode == null ? xzNode.getFjdId()
					: jxNode.getFjdId());
			// fjdId不等于0时，循环遍历查询父节点
			while (fjdId != 0) {
				TbXxzyJxzzjg jxFjdNode = null;
				if (jxNode != null && jxNode.getFjdId() != 0)
					jxFjdNode = (TbXxzyJxzzjg) baseDao.get(TbXxzyJxzzjg.class,
							fjdId);
				TbXxzyXzzzjg xzFjdNode = null;
				if (xzNode != null && xzNode.getFjdId() != 0)
					xzFjdNode = (TbXxzyXzzzjg) baseDao.get(TbXxzyXzzzjg.class,
							fjdId);
				// 如果jxfjdList为空或xzfjdList为空，退出循环
				if (jxFjdNode != null) {// 如果教学组织机构List不为空，获取fjdObj
					fjdId = jxFjdNode.getFjdId();// 把当前节点的父节点赋给fjdId
					int count = fjdIds.indexOf(jxFjdNode.getId().toString());
					if (count == -1) {// 当前fjdIds中不存在此节点ID时，加入当前节点ID
						fjdIds = fjdIds.concat(","
								+ jxFjdNode.getId().toString());
					}

				} else if (xzFjdNode != null) {// 如果行政组织机构List不为空，获取fjdObj
					fjdId = xzFjdNode.getFjdId();// 把当前节点的父节点赋给fjdId
					int count = fjdIds.indexOf(xzFjdNode.getId().toString());
					if (count == -1) {// 当前fjdIds中不存在此节点ID时，加入当前节点ID
						fjdIds = fjdIds.concat(","
								+ xzFjdNode.getId().toString());
					}
				} else {
					break;
				}
			}
		}
		return fjdIds;
	}

	/**
	 * 带checked的树形菜单
	 * 
	 * @param bmstm
	 * @return
	 */
	private Tree buildTreeCheck(String bmstm, String fjdId, Tree tree,
			String permissIds) {
		List list = baseDao.getTreeJson(bmstm, fjdId);
		if (tree.root == null) {
			tree = new Tree();
			TreeNode root = new TreeNode(new Long(0), new Long(-1), "", "");
			tree.setRoot(root);
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				JSONObject nodeJson = JSONObject.fromObject(list.get(i));
				TreeNode node = new TreeNode(Long.valueOf(nodeJson.get("id")
						.toString()), Long.valueOf(nodeJson.get("fjdId")
						.toString()), false, false, nodeJson.get("mc")
						.toString(), (nodeJson.get("sfyz").equals("0") ? false
						: true));
				tree.addNode(node);
				buildTreeCheck(bmstm, nodeJson.get("id").toString(), tree,
						permissIds);
				if (i == list.size() - 1) {
					return tree;
				}
			}
		}
		return tree;
	}

	/**
	 * 不带复选框的树形菜单
	 * 
	 * @param bmstm
	 * @return
	 */
	private MenuTree buildTreeNoCheck(String bmstm, String fjdId,
			MenuTree tree, String permissIds) {
		// List list = baseDao.getTreeJson(bmstm, fjdId);
		String hql = " from " + bmstm
				+ " t where t.sfky =1 order by t.fjdId,t.pxh";
		List list = baseDao.getTreeData(hql);
		if (tree.root == null) {
			tree = new MenuTree();
			MenuNode root = new MenuNode(new Long(0), new Long(-1), "", "");
			tree.setRoot(root);
			if (bmstm.equals("TbXzzzjg") || bmstm.equals("TsCdzy")) {
				MenuNode node = new MenuNode(new Long(1), new Long(0), "组织机构",
						"");
				tree.addNode(node);
			}
		}
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				JSONObject nodeJson = JSONObject.fromObject(list.get(i));
				Long pId = Long.valueOf(nodeJson.get("fjdId").toString());
				if ((bmstm.equals("TbXzzzjg") || bmstm.equals("TsCdzy"))
						&& pId == 0) {
					pId = tree.getRoot().children.get(0).getId();
				}
				MenuNode node = new MenuNode(Long.valueOf(nodeJson.get("id")
						.toString()), pId, false,
						nodeJson.get("mc").toString(), (nodeJson.get("sfyzjd")
								.equals("0") ? false : true));
				tree.addNode(node);
				/*
				 * buildTreeNoCheck(bmstm, nodeJson.get("id").toString(), tree,
				 * permissIds);
				 */
				if (i == list.size() - 1) {
					return tree;
				}
			}
		}
		// return new MenuTree();
		return tree;
	}

	private Object buildTree(String bmstm, String bmdm, String permissIds) {
		Tree tree = new Tree();
		MenuTree menuTree = new MenuTree();
		if (bmdm == null || bmdm.equals("")) {// 标准代码是否为空
			menuTree = buildTreeNoCheck(bmstm, "0");
		} else {
			tree = buildTreeCheck(bmstm, "0");
		}
		return tree.root == null ? menuTree.root : tree.root;
	}

	// ---------------------------------------------------------2013-05-20-----------------------------------------------
	/**
	 * 创建并组装不带选择框的树形菜单
	 * 
	 * @param bmstm
	 *            　编码实体名
	 * @param fjdId
	 *            　父节点ID
	 * @return　　返回无checked树
	 */
	public MenuTree buildTreeNoCheck(String bmstm, String fjdId) {
		MenuTree tree = new MenuTree();
		// 判断当前实体名是否为qxTree并调用固定方法查询结果，并返回数据　(2013-05-21高东杰修改)
		if (bmstm.equals("xzTree")) {
			return tree = (MenuTree) queryZzjgTree(Long.valueOf(fjdId), false);
		}
		// 查询当前表中所有节点记录
		String hql = " from " + bmstm
				+ " t where t.sfky =1 ";
		// 根据sql查询树形结构的所有节点。
		List list = baseDao.getTreeData(hql);
		// 定义菜单树对象
		// 定义顶级节点
		MenuNode menuNode = new MenuNode(new Long(0), new Long(-1), true, "", false);
		// 顶级节点set树中
		tree.setRoot(menuNode);
		//
		Map<Long,MenuNode> maps = new HashMap<Long,MenuNode>(); 
		maps.put(menuNode.getId(), menuNode);
		// 判断查询的结果是否为空
		if (!list.isEmpty() && list.size() != 0 && !list.get(0).equals(null)) {
			// 遍历所有的节点，并组装成一棵树
			maps = changeMenuMap(list, maps);
			changeMenu(maps);
		}
		return tree;
	}

	/**
	 * 创建并组装带选择框的树形菜单
	 * 
	 * @param bmstm
	 *            　编码实体名
	 * @param fjdId
	 *            　父节点ID
	 * @return　　返回无checked树
	 */
	public Tree buildTreeCheck(String bmstm, String fjdId) {
		Tree tree = new Tree();
		// 判断当前实体名是否为qxTree并调用固定方法查询结果，并返回数据　(2013-05-21高东杰修改)
		if (bmstm.equals("qxTree")) {
			return tree = (Tree) queryZzjgTree(Long.valueOf(fjdId), true);
		}else if(bmstm.equals("jxzzTree")){
			return tree = (Tree) queryJxzzjgTree(Long.valueOf(fjdId),true);
		}
		// ---------------------------------------------------
		// 查询当前表中所有节点记录
		String hql = " from " + bmstm
				+ " t where t.sfky =1 ";
		// 根据sql查询树形结构的所有节点。
		List list = baseDao.getTreeData(hql);
		// 定义菜单树对象
		// 定义顶级节点
		TreeNode treeNode = new TreeNode(new Long(0), new Long(-1), true, "", false);
		// 顶级节点set树中
		tree.setRoot(treeNode);
		Map<Long,TreeNode> maps = new HashMap<Long,TreeNode>();
		maps.put(treeNode.getId(), treeNode);
		// 判断查询的结果是否为空
		if (!list.isEmpty() && list.size() != 0 && !list.get(0).equals(null)) {
			// 遍历所有的节点，并组装成一棵树
			maps = changeTreeMap(list, maps);
			changeTree(maps);
		}
		return tree;
	}

	// -----------------------------------------------------------------------------------------------------------

	// ----------------------------------------------2013-05-21-------------------------------------------------------
	private String getUserPermiss(){
		UserInfo user = UserPermissionUtil.getUserInfo();//获取用户权限
		String permissIds = user.getPermissJxzzIds() !=null && user.getPermissJxzzIds().length()>0 ?user.getPermissJxzzIds():"";//教学组织机构ID
		permissIds = permissIds.length() > 0 ? permissIds.concat((user.getPermissXzzzIds() != null && user.getPermissXzzzIds().length()>0)?",".concat(user.getPermissXzzzIds()):""):"";
		String roleIds = user.getRoleIds();//角色
		//学生角色ID
		Long adminRoleId = baseDao.getBzdmIdByDm("XXDM-QXJSLX", "1");
		boolean flag = false;
		if(roleIds.indexOf(adminRoleId.toString()) != -1){//系统管理员
			flag = true;
		}
		if(user.getBmId() == null && flag== true){
			return "";
		}else{
			return permissIds;
		}
	}
	/**
	 * 通过父节点查询所有子节点
	 * 
	 * @param pId
	 *            　父节点
	 * @return
	 */
	private Object queryZzjgTree(Long pId, boolean flag) {
		String permissIds = getUserPermiss();//获取权限组织机构树节点ID
		List<TbXxzyJxzzjg> jxzzList = null;
		List<TbXxzyXzzzjg> xzzzList = null;
		List<TbXxzyBjxxb>  bjxxList = null;
		if(permissIds.length() <= 0){//判断当前用户是否超级管理 员如果permissIds长度为0调用原始方法
			jxzzList = baseDao.queryJxzzjgTree(pId);
			xzzzList = baseDao.queryZzjgTree();
			bjxxList = baseDao.queryEntityList("TbXxzyBjxxb", " sfky = 1 ");
		}else{
			jxzzList = baseDao.queryJxzzjgTree(permissIds);
			xzzzList = baseDao.queryZzjgTree(permissIds);
			bjxxList = baseDao.queryEntityList("TbXxzyBjxxb", MessageFormat.format(" sfky = 1 and fjdId in ({0}) ", permissIds));
		}
		MenuTree menuTree = new MenuTree();
		Tree tree = new Tree();
		TreeNode treeNode = new TreeNode(new Long(0), new Long(-1), "", "");
		MenuNode menuNode = new MenuNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(treeNode);
		menuTree.setRoot(menuNode);
		Map<Long,TreeNode> treeMap = new HashMap<Long, TreeNode>();
		treeMap.put(treeNode.getId(), treeNode);
		Map<Long,MenuNode> menuMap = new HashMap<Long, MenuNode>();
		menuMap.put(menuNode.getId(), menuNode);
		//组装教学组织机构
		if(flag == true){
			changeTreeMap(jxzzList, treeMap);
			changeTreeMap(bjxxList,treeMap);
		}else {
			changeMenuMap(jxzzList,menuMap);
			changeMenuMap(bjxxList,menuMap);
		}
		// 行政组织机构
		if(flag == true){
			changeTreeMap(xzzzList, treeMap);
			changeTree(treeMap);
		}else {
			changeMenuMap(xzzzList,menuMap);
			changeMenu(menuMap);
		}
		return flag == true ? tree : menuTree;

	}
	
	/**
	 * 遍历并组装带Checkbox的树
	 * @param tree
	 * @param maps
	 * @return
	 */
	private Tree changeTree(Map maps){
		Tree tree = new Tree();
		Long rootId = 0L;
		Iterator it =maps.keySet().iterator();
		while(it.hasNext()){
			long id = (Long)it.next();
			TreeNode node = (TreeNode) maps.get(id);
			//判断当前节点下是否有子节点，如果没有设为true(叶子节点)
			if(node.children.size() == 0)
				node.setLeaf(true);
			long pid = node.getPid();
			boolean isEx = maps.containsKey(pid);
			if(isEx){
				TreeNode pNode = (TreeNode) maps.get(pid);
				//判断父节点原来是否为子节点，现设置为false(非叶子节点)
				if(pNode.children.size() == 0){
					pNode.setLeaf(false);
				}
				//将当前节点加入到父节点的children中去
				pNode.getChildren().add(node);
			}else {
				rootId = id;
			}
		}
		if(tree.getRoot() == null)
			tree.root = (TreeNode) maps.get(rootId);
		return tree;
	}
	/**
	 * 遍历并组装非Checkbox的树
	 * @param tree
	 * @param maps
	 * @return
	 */
	private MenuTree changeMenu(Map maps){
		MenuTree tree = new MenuTree();
		Long rootId = 0L;
		Iterator it =maps.keySet().iterator();
		while(it.hasNext()){
			long id = (Long)it.next();
			MenuNode node = (MenuNode) maps.get(id);
//			System.out.println(node.getText());
			//判断当前节点下是否有子节点，如果没有设为true(叶子节点)
			if(node.children.size() == 0)
				node.setLeaf(true);
			long pid = node.getPid();
			boolean isEx = maps.containsKey(pid);
			if(isEx){
				MenuNode pNode = (MenuNode) maps.get(pid);
				//判断父节点原来是否为子节点，现设置为false(非叶子节点)
				if(pNode.children.size() == 0){
					pNode.setLeaf(false);
				}
				//将当前节点加入到父节点的children中去
				pNode.getChildren().add(node);
				//判断当前的节点是否为大班级
			}else if(node.getCclx() !=null && node.getCclx().equals("BJ") && node.getPid() == null){
				//大班级如果不存在上级节点，上级节点设置为学校节点
				node.setPid(1L);
				//获取学校节点
				MenuNode pmNode = (MenuNode) maps.get("1");
				//将当前节点加入到学校节点下面
				pmNode.getChildren().add(node);
			}else{
				rootId = id;
			}
		}
		if(tree.getRoot() == null)
			tree.root = (MenuNode) maps.get(rootId);
		return tree;
	}
	/**
	 * 组装带checkbox的树
	 * @param zzjgList
	 * @param tree
	 * @return
	 */
	private Map changeTreeMap(List zzjgList,Map map){
		// 行政组织机构
		for (int i = 0; i < zzjgList.size(); i++) {
			JSONObject  tsZzjg = JSONObject.fromObject(zzjgList.get(i));
			TreeNode treeNode = new TreeNode(tsZzjg.getLong("id"), tsZzjg.getLong("fjdId"), false, false,
					tsZzjg.getString("mc"), (tsZzjg.containsKey("sfyzjd")?(tsZzjg.getString("sfyzjd").equals("0") ? false : true):true));
			map.put(treeNode.getId(), treeNode);
		}
		return map;
	}
	
	/**
	 * 组装带checkbox的树
	 * @param zzjgList
	 * @param tree
	 * @return
	 */
	private Map changeMenuMap(List zzjgList,Map map){
		// 行政组织机构
		for (int i = 0; i < zzjgList.size(); i++) {
			JSONObject  tsZzjg = JSONObject.fromObject(zzjgList.get(i));
//			System.out.println(tsZzjg.get("mc"));
			MenuNode menuNode = new MenuNode(tsZzjg.getLong("id"),
					tsZzjg.getLong("fjdId"), false,
					tsZzjg.getString("mc"), (tsZzjg.containsKey("sfyzjd")?(tsZzjg.getString("sfyzjd").equals(
							"0") ? false : true):true),(tsZzjg.containsKey("cclx")?tsZzjg.getString("cclx"):""));// 将json对象转化为TreeNode
			map.put(menuNode.getId(), menuNode);
		}
		return map;
	}
	
	private Object queryJxzzjgTree(Long pId, boolean flag) {
		List<TbXxzyJxzzjg> jxzzList = baseDao.queryJxzzjgTree(pId);
		MenuTree menuTree = new MenuTree();
		Tree tree = new Tree();
		TreeNode treeNode = new TreeNode(new Long(0), new Long(-1), "", "");
		MenuNode menuNode = new MenuNode(new Long(0), new Long(-1), "", "");
		tree.setRoot(treeNode);
		menuTree.setRoot(menuNode);
		Map<Long,TreeNode> treeMap = new HashMap<Long, TreeNode>();
		treeMap.put(treeNode.getId(), treeNode);
		Map<Long,MenuNode> menuMap = new HashMap<Long, MenuNode>();
		menuMap.put(menuNode.getId(), menuNode);
		// 教学组织机构
		if(jxzzList != null && jxzzList.size() >0){
			if(flag == true){
				changeTreeMap(jxzzList, treeMap);
				changeTree(treeMap);
			}else {
				changeMenuMap(jxzzList,menuMap);
				changeMenu(menuMap);
			}
		}
		/*for (int i = 0; i < jxzzList.size(); i++) {
			TbXxzyJxzzjg tbJxzzjg = (TbXxzyJxzzjg) jxzzList.get(i);
			treeNode = new TreeNode(tbJxzzjg.getId(), tbJxzzjg.getFjdId(),
					false, false, tbJxzzjg.getMc(),
					((tbJxzzjg.getSfyzjd().toString().equals("0") || tbJxzzjg
							.getSfyzjd() == null) ? false : true));
			menuNode = new MenuNode(tbJxzzjg.getId(), tbJxzzjg.getFjdId(),
					false, tbJxzzjg.getMc(),
					((tbJxzzjg.getSfyzjd().toString().equals("0") || tbJxzzjg
							.getSfyzjd() == null) ? false : true));
			
			 * if(tbJxzzjg.getCc() == 2){ treeNode = new
			 * TreeNode(tbJxzzjg.getId(
			 * ),0l,false,false,tbJxzzjg.getMc(),(tbJxzzjg
			 * .getSfyzjd().equals("0") ? false:true)); }
			 
			tree.addNode(treeNode);
			menuTree.addNode(menuNode);
		}*/
//		Tree.getNodeById(tree, id);
		// MenuTree tree = UserPermissionUtil.getJxzzjgTree();
		return flag == true ? tree : menuTree;
	}

	// ----------------------------------------------------------------------------------------------------------------

	private List getBmsj(String bmbstm, String bmdm, String permissIds) {
		List list = baseDao.queryNodes(bmbstm, bmdm, permissIds);
		List<TreeNode> nodeList = new ArrayList<TreeNode>();
		for (Object o : list) {
			JSONObject map = JSONObject.fromObject(o);
			if(bmbstm.equals("TcRxccBycc")){//暂时用此方式解决
				nodeList.add(new TreeNode(Long.valueOf(map.get("id").toString()),
						Long.valueOf(map.get("fjdId").toString()), String
								.valueOf(map.get("mc")), Long.valueOf(map.get("cc")
								.toString()),map.getString("dm")));
			}else{
				nodeList.add(new TreeNode(Long.valueOf(map.get("id").toString()),
						Long.valueOf(map.get("fjdId").toString()), String
						.valueOf(map.get("mc")), Long.valueOf(map.get("cc")
								.toString())));
			}
		}
		return nodeList;
	}

	/**
	 * @param e
	 * @return
	 */
	private String handleException(Exception e) {
		if (logger.isInfoEnabled()) {
			e.printStackTrace();
		}
		logger.error(e);
		return SysConstants.JSON_SUCCESS_FALSE;
	}

	@SuppressWarnings("unchecked")
	public String save(String param) throws Exception {

		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		JSONObject obj = JSONObject.fromObject(param);
		String entityName = obj.getString("entityName");
		// 当前页面的菜单ID
		boolean permiss = true;// UserPermissionUtil.checkPermiss(pageId,
								// methodId);
		if (permiss == false || "null".equals(userInfo)) {
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		// 获取对象参数
		List list = Struts2Utils.requestJsonToLists(param);
		this.save(entityName, list);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	@Override
	public String getTreeJson(String params) throws Exception {
		JSONObject obj = JSONObject.fromObject(params);
		String entityName = obj.getString("entityName");
		String checked = obj.getString("checkbox");
		String permissIds = getPermissParam(entityName);
		// 如果checkbox为1代表有复选框
		if (checked.equals("1")) {
			Tree tree = new Tree();
			tree = buildTreeCheck(entityName, "0", tree, permissIds);
			return Struts2Utils.objects2Json(tree.root);
		} else {
			MenuTree tree = new MenuTree();
			tree = buildTreeNoCheck(entityName, "0", tree, permissIds);
			return Struts2Utils.objects2Json(tree.root);
		}
	}

	// private String getTreeJson(String params) {
	// JSONObject obj = JSONObject.fromObject(params);
	// String entityName = obj.getString("entityName");
	// String checked = obj.getString("checkbox");
	// }

	public String saveOrUpdate(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			JSONArray datas = obj.getJSONArray("data");
			for (int i = 0; i < datas.size(); i++) {
				// 去除大括号 且替换null为空字符
				String par = datas.get(i).toString();
				par = par.substring(1, par.length() - 1).replaceAll("null",
						"\"\"");
				String objStr = "{" + par + "}";
				JSONObject jsonObj = JSONObject.fromObject(objStr);
				String idStr = null;
				// 从JSON对象中获取ID,新增时ID不存在，修改时ID存在
				idStr = jsonObj.containsKey("id") ? jsonObj.getString("id")
						: "";
				// List list =
				// objectMapper.readValue("{\"entityName\":\""+entityName+"\","+par+"}",List.class);
				List list = null;
				list = Struts2Utils.requestJsonToLists(objStr);
				ActionContext act = ActionContext.getContext();
				// UserInfo userInfo = (UserInfo)
				// act.getSession().get("userInfo");
				// String cjr = userInfo.getZwm();
				// String cjr = "hanking";
				// String cjsj = DateUtils.date2String(new Date());
				// String xxdm = userInfo.getXxdm();
				// 判断ID是否为空，为空时新增一条数据，如果ID存在时更新一条记录
				if ("".equals(idStr) || idStr == null || idStr.length() != 16) {
					// list.add(new String[]{"CJR", cjr});
					// list.add(new String[]{"CJSJ", cjsj});
					this.save(entityName, list);
				} else {
					// list.add(new String[]{"XGR", cjr});
					// list.add(new String[]{"XGSJ", cjsj});
					baseDao.update(entityName, idStr, list);
				}
			}
			return SysConstants.JSON_SUCCESS_TRUE;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			return SysConstants.JSON_SUCCESS_FALSE;
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String deleteByIds(String param) throws Exception {

		JSONObject obj = JSONObject.fromObject(param);
		String entityName = obj.getString("entityName");
		String ids = obj.getString("ids");
		String entityNameString = ServletUtils.entityPath(entityName);
		baseDao.deleteByIds(ids, entityNameString);
		return SysConstants.JSON_SUCCESS_TRUE;
	}

	/**
	 * {@inheritDoc}
	 */

	public String queryTree(String param) {
		JSONObject obj = JSONObject.fromObject(param);
		String entityName = obj.getString("entityName");
		String rootName = obj.get("rootName") == null ? "" : obj
				.get("rootName").toString();
		List list = baseDao.queryTree(entityName);
		com.jhkj.mosdc.framework.util.Tree tree = new com.jhkj.mosdc.framework.util.Tree();
		Node root = new Node(new Long(0), new Long(-1), rootName, "");
		tree.setRoot(root);
		for (int i = 0; i < list.size(); i++) {
			Node node = (Node) list.get(i);
			tree.addNode(node);
		}
		tree.setTreeLeaf(tree.root);
		return Struts2Utils.objects2Json(tree.root);
	}

	/**
	 * {@inheritDoc}
	 */

	public String queryBzdmTree(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			String bzdm = obj.getString("bzdm");
			List list = baseDao.queryTree(entityName, bzdm);
			Tree tree = new Tree();
			TreeNode root = new TreeNode(new Long(0), new Long(-1), "", "");
			tree.setRoot(root);
			for (int i = 0; i < list.size(); i++) {
				TreeNode node = (TreeNode) list.get(i);
				tree.addNode(node);
			}
			return Struts2Utils.objects2Json(tree.root);
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryMenuTree(String param) {
		List list = baseDao.queryMenuTree();
		Tree tree = new Tree();
		TreeNode root = new TreeNode(new Long(0), new Long(-1), "中职平台", "");
		tree.setRoot(root);
		for (int i = 0; i < list.size(); i++) {
			TreeNode node = (TreeNode) list.get(i);
			tree.addNode(node);
		}
		try {
			return Struts2Utils.objects2Json(tree.root);
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/*
	 * public String queryPermissMenuTree(String param) {
	 * 
	 * List list = baseDao.queryMenuTree(); Tree tree = new Tree(); Node root =
	 * new Node(new Long(0), new Long(-1), "中职平台", ""); tree.setRoot(root); for
	 * (int i = 0; i < list.size(); i++) { Node node = (Node) list.get(i);
	 * tree.addNode(node); } try { return Struts2Utils.objects2Json(tree.root);
	 * } catch (Exception e) { return handleException(e); } finally {
	 * 
	 * } }
	 */

	/**
	 * {@inheritDoc}
	 */
	public String querySearchHeader(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			String[] paramNames = new String[] { "sfcxzd" };
			Object[] values = new String[] { "0" };
			List list = baseDao.querySearchHeader(entityName, paramNames,
					values, null, null);
			for (Object o : list) {
				TsStsx tsStsx = (TsStsx) o;
				if (null != tsStsx.getBmbstm()
						&& !"".equals(tsStsx.getBmbstm().trim())) {
					String bmstm = tsStsx.getBmbstm();
					String permissIds = getPermissParam(bmstm);
					String bmdm = tsStsx.getBmbbzdm();
					if (null != bmdm && !"".equals(bmdm.trim())) {
						List bmsj = baseDao.queryBM(bmstm, bmdm);
						tsStsx.setBmsj(bmsj);
					} else {
						List bmsj = baseDao.queryTableContentForDm(bmstm,
								permissIds);
						tsStsx.setBmsj(bmsj);
					}
				}
			}
			String json = Struts2Utils.objects2Json(list);
			return json;
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	public String querySelectOne(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			// 是否类别显示字段
			String[] paramNames = new String[] { "sflbxszd" };
			Object[] values = new String[] { "0" };
			List list = baseDao.querySearchHeader(entityName, paramNames,
					values, null, null);
			for (Object o : list) {
				TsStsx tsStsx = (TsStsx) o;
				if (null != tsStsx.getBmbstm()
						&& !"".equals(tsStsx.getBmbstm().trim())) {
					String bmstm = tsStsx.getBmbstm();
					String permissIds = getPermissParam(bmstm);
					String bmdm = tsStsx.getBmbbzdm();
					if (null != bmdm && !"".equals(bmdm.trim())) {
						List bmsj = baseDao.queryBM(bmstm, bmdm);
						tsStsx.setBmsj(bmsj);
					} else {
						List bmsj = baseDao.queryTableContentForDm(bmstm,
								permissIds);
						tsStsx.setBmsj(bmsj);
					}
				}
			}
			String json = Struts2Utils.objects2Json(list);
			return json;
		} catch (Exception e) {
			return handleException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryTable(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			String sflbzdStr = "0";
			try {
				sflbzdStr = obj.getString("sflbzd");
			} catch (Exception e) {

			}
			List list = new ArrayList();
			sflbzdStr = null == sflbzdStr ? "0" : sflbzdStr;
			Long sflbzd = new Long(sflbzdStr);
			list = baseDao.queryTableHeader(entityName, sflbzd);

			String json1 = Struts2Utils.objects2Json(list);
			String proxy = "{\"url\":\"baseAction!queryTableContent.action?entityName="
					+ entityName
					+ "\",\"root\":\"data\",\"totalProperty\":\"count\"}";
			String json = "{\"fields\":" + json1 + ",\"proxy\":" + proxy + "}";
			return json;
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryView(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String viewName = obj.getString("viewName");
			String sflbzdStr = "0";
			try {
				sflbzdStr = obj.getString("sflbzd");
			} catch (Exception e) {
			}
			String requestName = "";
			try {
				requestName = obj.getString("requestName");
			} catch (Exception e) {
			}
			List list = new ArrayList();
			sflbzdStr = null == sflbzdStr ? "0" : sflbzdStr;
			Long sflbzd = new Long(sflbzdStr);
			list = baseDao.queryTableHeader(viewName, sflbzd);

			String json1 = Struts2Utils.objects2Json(list);
			String proxy = "{\"url\":\"baseAction!queryViewContent.action?viewName="
					+ viewName
					+ "&requestName="
					+ requestName
					+ "\",\"root\":\"data\",\"totalProperty\":\"count\"}";
			String json = "{\"fields\":" + json1 + ",\"proxy\":" + proxy + "}";
			return json;
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public String queryTableContentSingle(String param) {
		try {
			JSONObject jsonObj = new JSONObject().fromObject(param);
			// 获取实体名
			String entityName = jsonObj.getString("entityName");
			Map paramList = this.getSQLParams(jsonObj);
			// 定义查询条件集合
			List<String> retList = null;
			// 判断是否为空
			if (paramList.size() >= 0 && paramList.get(0) == null) {
				retList = (List<String>) paramList.get("list");
			}
			// 参数赋值
			// 高级查询条件
			String seniorStr = (String) paramList.get("senior");
			// 根据条件获取数据
			List list = baseDao.queryTableContentSingle(entityName, retList,
					seniorStr, null, null, null);
			// 转化为json数据
			// String json = Struts2Utils.list2json(list, entityName);
			String json = Struts2Utils.objects2Json(list);
			return json;
		} catch (Exception e) {
			return handleException(e);
		} finally {
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public String queryTableContentBySql(String sql, String params,
			String sqlEnd) throws Exception {
		JSONObject jsonObj = new JSONObject().fromObject(params);
		Map paramList = SqlParamsChange.getSQLParams(jsonObj, true);
		String startStr = "";
		String limitStr = "";
		List<String> retList = null;
		if (paramList.size() >= 0 && paramList.get(0) == null) {
			startStr = (String) paramList.get("start");
			limitStr = (String) paramList.get("limit");
			retList = (List<String>) paramList.get("list");
		}
		int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
		int start = null == startStr ? 0 : new Integer(startStr).intValue();
		if (start == 0 && limit == 0) {
			limit = 25;
		}
		// 高级查询条件
		String seniorStr = (String) paramList.get("senior");
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if (null != retList && retList.size() != 0
				&& retList.get(0).length() >= 1) {
			for (int i = 0; i < retList.size(); i++) {
				sb.append(" and " + retList.get(i));
			}
		}
		// 判断高级查询是否存在
		if (seniorStr != null && !seniorStr.equals("")) {
			sb.append(" and " + seniorStr);
		}
		if (null != sqlEnd && !"".equals(sqlEnd.trim())) {
			sb.append(" " + sqlEnd);
		}
		Map pageMap = new HashMap();
		pageMap.put("start", start);
		pageMap.put("limit", limit);
		// 根据条件获取数据
		Map map = baseDao.queryTableContentBySQL(sb.toString(), pageMap);
		// 转化为json数据
		List list = (List) map.get("queryList");
		String json = Struts2Utils.objects2Json(Converter
				.changListClomn2Field(list));
		String tmp = "{success:true,\"data\":" + json + " ,\"count\":\""
				+ map.get("count") + "\"}";
		return tmp;
	}

	/**
	 * 根据SQL和参数执行查询方法
	 * 
	 * @param sql
	 * @param obj
	 * @param sqlEnd
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryTableContentBySql(String sql, JSONObject obj,
			String sqlEnd, Boolean isAlias) throws Exception {
		Map paramList = SqlParamsChange.getSQLParams(obj, isAlias);
		String startStr = "";
		String limitStr = "";
		List<String> retList = null;
		if (paramList.size() >= 0 && paramList.get(0) == null) {
			startStr = (String) paramList.get("start");
			limitStr = (String) paramList.get("limit");
			retList = (List<String>) paramList.get("list");
		}
		int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
		int start = null == startStr ? 0 : new Integer(startStr).intValue();
		if (start == 0 && limit == 0) {
			limit = 25;
		}
		// 高级查询条件
		String seniorStr = (String) paramList.get("senior");
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if (null != retList && retList.size() != 0
				&& retList.get(0).length() >= 1) {
			for (int i = 0; i < retList.size(); i++) {
				sb.append(" and " + retList.get(i));
			}
		}
		// 判断高级查询是否存在
		if (seniorStr != null && !seniorStr.equals("")) {
			sb.append(" and " + seniorStr);
		}
		if (null != sqlEnd && !"".equals(sqlEnd.trim())) {
			sb.append(" " + sqlEnd);
		}
		Map pageMap = new HashMap();
		pageMap.put("start", start);
		pageMap.put("limit", limit);
		// 根据条件获取数据
		Map map = baseDao.queryTableContentBySQL(sb.toString(), pageMap);
		// 转化为json数据
		List list = (List) map.get("queryList");
		return SysConstants.successInfo(list, Integer.valueOf(map.get("count").toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public String queryTableContentByHql(String hql, String params,
			String sqlEnd) throws Exception {
		JSONObject jsonObj = new JSONObject().fromObject(params);
		Map paramList = this.getSQLParams(jsonObj);
		String startStr = "";
		String limitStr = "";
		List<String> retList = null;
		if (paramList.size() >= 0 && paramList.get(0) == null) {
			startStr = (String) paramList.get("start");
			limitStr = (String) paramList.get("limit");
			retList = (List<String>) paramList.get("list");
		}
		int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
		int start = null == startStr ? 0 : new Integer(startStr).intValue();
		if (start == 0 && limit == 0) {
			limit = 25;
		}
		// 高级查询条件
		String seniorStr = (String) paramList.get("senior");
		PageParam pageParam = new PageParam(start, limit);
		// 根据条件获取数据
		List list = baseDao.queryTableContentByHql(hql, sqlEnd, retList,
				seniorStr, pageParam);
		// 转化为json数据
		String json = Struts2Utils.objects2Json(list);
		String tmp = "{success:true,\"data\":" + json + " ,\"count\":\""
				+ pageParam.getRecordCount() + "\"}";
		return tmp;
	}

	@SuppressWarnings("unchecked")
	public void queryViewContent(String param) {
		ActionContext act = ActionContext.getContext();
		Map requestMap = (HashMap) act.getApplication().get(
				SysConstants.REQUEST_SERVICES);
		try {
			JSONObject obj = JSONObject.fromObject(param);
			// 请求名称
			String requestName = obj.getString("requestName");
			// 参数
			Map serviceMap = (HashMap) requestMap.get(requestName);
			String service = (String) serviceMap
					.get(SysConstants.REQUEST_SERVICE_NAME);
			String methodName = (String) serviceMap
					.get(SysConstants.REQUEST_SERVICE_METHOD);
			// 获取服务对象
			Object serviceBean = ApplicationComponentStaticRetriever
					.getComponentByItsName(service);
			Class cls = serviceBean.getClass();
			Class[] paraTypes = new Class[] { String.class };
			// 反射方法
			Method method = cls.getMethod(methodName, paraTypes);
			Object[] paraValues = new Object[] { "" };
			// 调用方法
			Object json = method.invoke(serviceBean, paraValues);
			Struts2Utils.objects2Json(json);
		} catch (Exception e) {
			logger.error(e);
			Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String queryBM(String param) {
		try {
			JSONObject obj = JSONObject.fromObject(param);
			String entityName = obj.getString("entityName");
			// String parentId = obj.getString("pId");
			String bzdm = obj.getString("bzdm");
			List list = baseDao.queryBM(entityName, bzdm);
			String json = Struts2Utils.objects2Json(list);
			return json;
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void save(String entityName, List filedList) throws Exception {
		if (null == filedList || filedList.size() <= 0) {
			return;
		}
		String entityPack = ServletUtils.entityPath(entityName);
		Class cls = Class.forName(entityPack);
		Method[] methods = cls.getDeclaredMethods();
		// 创建对象
		Object obj = cls.newInstance();
		// 判断是否有id
		boolean idFlag = false;
		for (int k = 0; k < methods.length; k++) {
			String methodName = methods[k].getName();
			if (methodName.startsWith("get")) {
				String fieldName = methodName.substring(3, 4).toLowerCase()
						+ methodName.substring(4);
				for (int i = 0; i < filedList.size(); i++) {
					String[] tmp = (String[]) filedList.get(i);
					if (fieldName.equals(tmp[0])) {
						if ("id".equals(tmp[0]) && tmp[1].trim().length() > 0) {
							idFlag = true;
						}
						Column colAnn = methods[k]
								.getAnnotation(javax.persistence.Column.class);
						// String name = "";
						if (null != colAnn) {
							// name = colAnn.name();
							// 简单类型
							String setMethodName = "s"
									+ methodName.substring(1,
											methodName.length());
							// 方法对象
							Class clazz = methods[k].getReturnType();
							Method setMethod = cls.getMethod(setMethodName,
									new Class[] { clazz });
							// 转换类型
							String className = clazz.getName();

							Object aaa = null;
							if (className.equalsIgnoreCase("java.lang.String")) {
								aaa = tmp[1];
							} else if (className
									.equalsIgnoreCase("java.lang.Long")
									|| className
											.equalsIgnoreCase("java.lang.long")) {
								aaa = new Long("".equals(tmp[1]) ? "0" : tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Byte")) {
								aaa = new Byte(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Double")
									|| className
											.equalsIgnoreCase("java.lang.double")) {
								aaa = new Double(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Integer")
									|| className
											.equalsIgnoreCase("java.lang.int")) {
								aaa = new Integer(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Boolean")
									|| className
											.equalsIgnoreCase("java.lang.boolean")) {
								aaa = new Boolean(tmp[1].equals("1") ? true
										: false);
							} else if (className
									.equalsIgnoreCase("java.lang.Float")) {
								aaa = new Float(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.lang.Short")) {
								aaa = new Short(tmp[1]);
							} else if (className
									.equalsIgnoreCase("java.math.BigDecimal")) {
								aaa = new BigDecimal(tmp[1]);
							}

							// set值
							setMethod.invoke(obj, new Object[] { aaa });
						} else {
							JoinColumn colAjn = methods[k]
									.getAnnotation(javax.persistence.JoinColumn.class);
							// name = colAjn.name();
							// 主从表关联
							if (null != colAjn) {
								Class glb = methods[k].getReturnType();
								// 主键都是long型
								Class[] paraTypes = new Class[] { Long.class };
								// 创建关联表对象
								Object idObj = glb.newInstance();
								Method method = glb.getMethod("setId",
										paraTypes);
								// 保存关联表id
								method.invoke(idObj, new Object[] { new Long(
										tmp[1]) });
								// 方法名
								String setMethodName = "s"
										+ methodName.substring(1,
												methodName.length());
								// 方法对象
								Method setMethod = cls.getMethod(setMethodName,
										new Class[] { methods[k]
												.getReturnType() });
								// set值
								setMethod.invoke(obj, new Object[] { idObj });
							}
						}
					}
				}
			}
		}
		// 判断是否有id
		if (!idFlag) {
			// 传过来的参数中没有id，保存id
			Method setIdMethod = cls.getMethod("setId",
					new Class[] { Long.class });
			setIdMethod.invoke(obj, new Object[] { baseDao.getId() });
		}
		baseDao.insert(obj);
	}

	/**
	 * 查询导出头
	 * 
	 * @param entityName
	 * @param type
	 * @return
	 * @author LJH
	 */
	public List<TsStsx> queryExportTableHeader(String entityName, String params)
			throws Exception {
		List<TsStsx> headerList = null;
		if ("'all'".equalsIgnoreCase(params)) {// 判断参数是否为"all"
			headerList = baseDao.queryExportTableHeaders(entityName, null);// 查询所有列字段，除

		} else {
			// String[]
			headerList = baseDao.queryExportTableHeaders(entityName, params);// 只查询显示的列字段
		}
		for (TsStsx tsStsx : headerList) {// 遍历表头数据
			if ((tsStsx.getSxzjlx().equals("combobox")
					|| tsStsx.getSxzjlx().equals("treecombobox") // 判断组件类型是否为combobox,treecombobox,tree和代码表名是否为null
			|| tsStsx.getSxzjlx().equals("tree"))
					&& tsStsx.getBmbstm() != null) {
				List list = baseDao.queryBMForIdMc(tsStsx.getBmbstm(),
						tsStsx.getBmbbzdm(), tsStsx.getSxzjlx());// 查询下拉数据
				Map map = new HashMap<Long, String>();
				for (int i = 0; i < list.size(); i++) {// 遍历下拉数据存储为map
					Object[] obj = (Object[]) list.get(i);
					map.put(Long.valueOf(obj[0].toString()), obj[1].toString());
				}
				tsStsx.setBmsj(map);// 存入实体属性实体的bmsj字段中
			}
		}
		return headerList;
	}

	/**
	 * 查询导出内容
	 * 
	 * @param entityName
	 * @param headers
	 * @param params
	 * @return
	 * @author LJH
	 */
	public List<String> queryExportTableContent(String entityName,
			List<TsStsx> headers, String params) {
		// 定义存储表头的变量
		String header = "";
		// 遍历表头数据，取字段名
		for (int i = 0; i < headers.size(); i++) {
			TsStsx tsStsx = headers.get(i);
			header = header.concat(tsStsx.getSx());
			if (i != headers.size() - 1) {// 判断当前i值是否等于headers.size()-1，如果不等于，就加上逗号
				header = header.concat(",");
			}
		}
		JSONObject jsonObj = JSONObject.fromObject(params);
		if (jsonObj.size() > 0) { // 按参数导
			if (jsonObj.keySet().contains("seniorQuery")) {
				String seniorQuery = jsonObj.getString("seniorQuery");
				seniorQuery = "["
						+ seniorQuery.substring(2, seniorQuery.length() - 2)
						+ "]";
				JSONArray jsonArray = JSONArray.fromObject(seniorQuery);
				try {
					String sql = this.seniorSQL(jsonArray);
					List<String> content = baseDao.queryExportTableContent(
							Converter.entityToTable(entityName), header, sql);
					return content;
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			} else {
				StringBuffer sqlSB = new StringBuffer();
				for (Object key : jsonObj.keySet()) {
					String[] fields = ((String) key).split("\\.");
					String field = fields[1];
					sqlSB.append("t." + field);
					sqlSB.append("='");
					sqlSB.append(jsonObj.get(key) + "'");
					sqlSB.append(" and ");
				}
				String sql = sqlSB.substring(0, sqlSB.lastIndexOf("and"));
				List<String> content = baseDao.queryExportTableContent(
						Converter.entityToTable(entityName), header, sql); // Converter.entityToTable(
				return content;
			}
		} else { // 无参
			List<String> content = baseDao.queryExportTableContent(
					Converter.entityToTable(entityName), header, null);
			return content;
		}

		return null;

	}

	/**
	 * 拼装高级查询条件
	 * 
	 * @param seniorArray
	 * @return
	 * @throws Exception
	 */
	private String seniorSQL(JSONArray seniorArray) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 实现SQL拼装
		for (int i = 0; i < seniorArray.size(); i++) {
			Map objMap = (Map) seniorArray.get(i);
			String fullField = objMap.get("field").toString();
			// 去掉实体名
			String[] fieldName = fullField.split("\\.");
			sb.append(" ( t." + fieldName[0] + " ");
			String value = Struts2Utils.isoToUTF8(objMap.get("value")
					.toString());
			// 操作符为like的加上　％％，同时把汉字转码为utf-8
			if (objMap.get("operator").equals("like")) {
				sb.append(objMap.get("operator") + " '%" + value + "%')");
			} else {
				sb.append(objMap.get("operator") + " '" + value + "')");
			}
			// 增加 and或or关联
			if (objMap.get("logical") != null && objMap.get("logical") != "") {
				sb.append(" " + objMap.get("logical"));
			}
		}
		return sb.toString();
	}

	/**
	 * 拼装高级查询条件
	 * 
	 * @param seniorArray
	 * @return
	 * @throws Exception
	 */
	private String seniorSQLV2(JSONArray seniorArray) throws Exception {
		StringBuffer sb = new StringBuffer();
		// 实现SQL拼装
		for (int i = 0; i < seniorArray.size(); i++) {
			Map objMap = (Map) seniorArray.get(i);
			String fullField = objMap.get("field").toString();
			// 去掉实体名
			String[] fieldName = fullField.split("\\.");
			sb.append(" ( " + Converter.fieldToColumn(fieldName[0]) + " ");
			String value = Struts2Utils.isoToUTF8(objMap.get("value")
					.toString());
			// 操作符为like的加上　％％，同时把汉字转码为utf-8
			if (objMap.get("operator").equals("like")) {
				sb.append(objMap.get("operator") + " '%" + value + "%')");
			} else {
				sb.append(objMap.get("operator") + " '" + value + "')");
			}
			// 增加 and或or关联
			if (objMap.get("logical") != null && objMap.get("logical") != "") {
				sb.append(" " + objMap.get("logical"));
			}
		}
		return sb.toString();
	}

	@Override
	public List queryBmByEntityNameAndFieldName(String obj) {
		JSONObject json = JSONObject.fromObject(obj);
		String entityName = json.getString("entityName");
		String fieldName = json.getString("fieldName");
		TsStsx stsx = new TsStsx();
		stsx.setSsstm(entityName);
		stsx.setSx(fieldName);
		List l = this.baseDao.loadEqual(stsx);
		if (l == null || l.size() == 0) {
			throw new RuntimeException("没有对应的实体属性值");
		}
		stsx = (TsStsx) l.get(0);

		String bmbstm = stsx.getBmbstm(); // 编码表实体名，简单名字
		String bmbstmClassName = ServletUtils.entityPath(bmbstm);
		Object bmbst = null;
		try {
			bmbst = Class.forName(bmbstmClassName).newInstance();
			// 获取设置bzdm值的方法
			Method setMethod = bmbst.getClass().getMethod("setBzdm",
					String.class);
			setMethod.invoke(bmbst, stsx.getBmbbzdm());// 设置编码表实体对象的bzdm属性值
			List bmbList = this.baseDao.loadEqual(bmbst);
			return JSONArray.fromObject(bmbList);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */

	public String getId(String param) {
		try {
			Long id = baseDao.getId();
			return Struts2Utils.objects2Json(id);
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/**
	 * {@inheritDoc}
	 */

	public void insert(Object object) {
		baseDao.insert(object);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void insert(List<Object> list) throws Exception{
		baseDao.save(list);
	}

	@Override
	public void updateObj(Object object) {
		baseDao.update(object);
	}


	/**
	 * {@inheritDoc}
	 */

	public Long getId() {
		try {
			Long id = baseDao.getId();
			return id;
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			}
			logger.error(e);
			return null;
		} finally {

		}
	}

	/**
	 * 通过action传入的参数解析为对应的条件参数,同时获取start,limit参数
	 * 
	 * @param maps
	 * @return 参数和条件Map列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getSQLParams(Map maps) throws Exception {
		Map<String, Object> map = new HashMap();
		Set<String> key = maps.keySet();
		List<String> retList = new ArrayList<String>();
		Iterator<String> iter = key.iterator();
		String startStr = "";
		String limitStr = "";
		// String pageStr = "";
		String seniorStr = "";
		StringBuffer rqStr = new StringBuffer();
		while (iter.hasNext()) {
			String field = iter.next();
			String value = "";
			if ("seniorQuery".equals(field)) {
				value = String.valueOf(maps.get(field));
				// 高级查询条件
				JSONArray jsonArray = JSONArray.fromObject(value);
				// 判断jsonArray是否有值
				if (jsonArray.size() > 0) {
					seniorStr = this.seniorSQL(jsonArray);
				}
				// 判断条件，并组装相应SQL
			} else {
				String[] paramFields = null;
				if (field.indexOf(".") > 0) {
					paramFields = field.split("\\.");
				}
				StringBuffer str = new StringBuffer();
				value = String.valueOf(maps.get(field));
				if (value.startsWith("[") && value.endsWith("]")
						&& value.contains(",")) {
					List list = (List) maps.get(field);
					value = (String) list.get(0);
				}
				value = Struts2Utils.isoToUTF8(value);
				// 目前可确定的以下几种类型不是查询条件
				if ("start".equals(field)) {
					startStr = value;
					continue;
				} else if ("limit".equals(field)) {
					limitStr = value;
					continue;
				} else if ("page".equals(field)) {
					continue;
				} else if ("error".equals(field)) {
					continue;
					// pageStr = value;
				} else if ("entityName".equals(field)) {
					continue;
				} else if ("menuId".equals(field)) {
					continue;
				} else if ("buttonId".equals(field)) {
					continue;
				} else if ("singleReturnNoComponent".equals(field)) {
					continue;
				} else if (paramFields != null) {
					if (paramFields[1].equals("equals")) {
						str.append(paramFields[0].toString() + " ");
						str.append("= '" + value + "' ");
					} else if (paramFields[1].equals("like")) {
						str.append(paramFields[0].toString() + " ");
						str.append("like '%" + value + "%' ");
					} else if (("date1").equals(paramFields[1])) {
						rqStr.append(" and " + paramFields[0].toString() + " "
								+ ">= '" + value + "'");
					} else if (("date2").equals(paramFields[1])) {
						rqStr.append(" and " + paramFields[0].toString() + " "
								+ " <= '" + value + "'");
					} else if ("in".equals(paramFields[1])) {
						str.append(paramFields[0].toString() + " ");
						str.append(" in ( " + value + " )");
					} else if ("notequals".equals(paramFields[1])) {
						str.append(paramFields[0].toString() + " ");
						str.append(" <>  " + value + " ");
					} else {
						str.append(paramFields[0].toString() + " ");
						str.append("= '" + value + "' ");
					}

				} else if (!value.equals("")) {
					str.append(field + " = '" + value + "' ");
				}
				if (str.toString().length() != 0) {
					retList.add(str.toString());
				}
			}
		}
		if (rqStr.toString().length() != 0) {
			retList.add(rqStr.toString().replaceFirst("and", ""));
		}
		if (!"".equals(startStr) && !"".equals(limitStr)) {
			map.put("start", startStr);
			map.put("limit", limitStr);
			map.put("list", retList);
		} else {
			map.put("list", retList);
		}
		map.put("senior", seniorStr);
		return map;
	}

	/**
	 * 通过action传入的参数解析为对应的条件参数,同时获取start,limit参数
	 * 
	 * @param maps
	 * @return 参数和条件Map列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getSQLParamsV2(Map maps) throws Exception {
		Map<String, Object> map = new HashMap();
		Set<String> key = maps.keySet();
		List<String> retList = new ArrayList<String>();
		Iterator<String> iter = key.iterator();
		String startStr = "";
		String limitStr = "";
		// String pageStr = "";
		String seniorStr = "";
		StringBuffer rqStr = new StringBuffer();
		while (iter.hasNext()) {
			String field = iter.next();
			String value = "";
			if ("seniorQuery".equals(field)) {
				value = String.valueOf(maps.get(field));
				// 高级查询条件
				JSONArray jsonArray = JSONArray.fromObject(value);
				// 判断jsonArray是否有值
				if (jsonArray.size() > 0) {
					seniorStr = this.seniorSQLV2(jsonArray);
				}
				// 判断条件，并组装相应SQL
			} else {
				String[] paramFields = null;
				if (field.indexOf(".") > 0) {
					paramFields = field.split("\\.");
				}
				StringBuffer str = new StringBuffer();
				value = String.valueOf(maps.get(field));
				if (value.startsWith("[") && value.endsWith("]")
						&& value.contains(",")) {
					List list = (List) maps.get(field);
					value = (String) list.get(0);
				}
				value = Struts2Utils.isoToUTF8(value);
				// 目前可确定的以下几种类型不是查询条件
				if ("start".equals(field)) {
					startStr = value;
					continue;
				} else if ("limit".equals(field)) {
					limitStr = value;
					continue;
				} else if ("page".equals(field)) {
					continue;
				} else if ("error".equals(field)) {
					continue;
					// pageStr = value;
				} else if ("entityName".equals(field)) {
					continue;
				} else if ("menuId".equals(field)) {
					continue;
				} else if ("buttonId".equals(field)) {
					continue;
				} else if ("singleReturnNoComponent".equals(field)) {
					continue;
				} else if (paramFields != null) {
					if (paramFields[1].equals("equals")) {
						str.append(Converter.fieldToColumn(paramFields[0]
								.toString()) + " ");
						str.append("= '" + value + "' ");
					} else if (paramFields[1].equals("like")) {
						str.append(Converter.fieldToColumn(paramFields[0]
								.toString()) + " ");
						str.append("like '%" + value + "%' ");
					} else if (("date1").equals(paramFields[1])) {
						rqStr.append(" and "
								+ Converter.fieldToColumn(paramFields[0]
										.toString()) + " " + ">= '" + value
								+ "'");
					} else if (("date2").equals(paramFields[1])) {
						rqStr.append(" and "
								+ Converter.fieldToColumn(paramFields[0]
										.toString()) + " " + " <= '" + value
								+ "'");
					} else if ("in".equals(paramFields[1])) {
						str.append(Converter.fieldToColumn(paramFields[0]
								.toString()) + " ");
						str.append(" in ( " + value + " )");
					} else if ("notequals".equals(paramFields[1])) {
						str.append(Converter.fieldToColumn(paramFields[0]
								.toString()) + " ");
						str.append(" <>  " + value + " ");
					} else {
						str.append(Converter.fieldToColumn(paramFields[0]
								.toString()) + " ");
						str.append("= '" + value + "' ");
					}

				} else if (!value.equals("")) {
					str.append(Converter.fieldToColumn(field) + " = '" + value
							+ "' ");
				}
				if (str.toString().length() != 0) {
					retList.add(str.toString());
				}
			}
		}
		if (rqStr.toString().length() != 0) {
			retList.add(rqStr.toString().replaceFirst("and", ""));
		}
		if (!"".equals(startStr) && !"".equals(limitStr)) {
			map.put("start", startStr);
			map.put("limit", limitStr);
			map.put("list", retList);
		} else {
			map.put("list", retList);
		}
		map.put("senior", seniorStr);
		return map;
	}

	/**
	 * 根据当前节点查询上级节点对象
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map findParentObject(String param) throws Exception {
		// 获取当前实体以及实体类,对应的实体类
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> maps = null;
		try {
			maps = objectMapper.readValue(param, Map.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			handleException(e);
		}
		String entityName = maps.get("entityName");
		String id = maps.get("id");
		TbXxzyJxzzjg tbJxzzjg = (TbXxzyJxzzjg) baseDao
				.queryById(id, entityName);

		// 获取教学组织机构层次类型
		TcXxbzdmjg tcXxbzdmjg = (TcXxbzdmjg) baseDao.queryById(
				String.valueOf(tbJxzzjg.getCclx()), "TcXxbzdmjg");
		String name = tcXxbzdmjg.getBzdmmc();
		TbXxjbxx tbXxjbxx = null;
		TbZyxx tbZydm = null;
		TbBjxx tbBjxx = null;
		// 获取父节点的对象
		Map<String, Comparable> parentObject = new HashMap();
		// 判断学校
		if ("学校".equals(name)) {
			tbXxjbxx = (TbXxjbxx) baseDao.queryById(id, "TbXxjbxx");
			if (tbXxjbxx.getId() != null && !"".equals(tbXxjbxx.getXxmc())) {
				parentObject.put("id", tbXxjbxx.getId());
				parentObject.put("name", tbXxjbxx.getXxmc());
			}
		} else if ("院系".equals(name)) {
		} else if ("教研室".equals(name)) {

		} else if ("专业".equals(name)) {
			tbZydm = (TbZyxx) baseDao.queryById(id, "TbZydm");
			if (tbZydm.getId() != null && !"".equals(tbZydm.getMc())) {
				parentObject.put("id", tbZydm.getId());
				parentObject.put("name", tbZydm.getMc());
			}
		} else if ("年级".equals(name)) {

		} else if ("班级".equals(name)) {
			tbBjxx = (TbBjxx) baseDao.queryById(id, "TbBjxx");
			if (tbBjxx.getId() != null && !"".equals(tbBjxx.getBjmc())) {
				parentObject.put("id", tbBjxx.getId());
				parentObject.put("name", tbBjxx.getBjmc());
			}
		}
		// 判断院系
		// 判断专业
		// 判断年级
		// 判断班级

		// 获取当前对象的父ID
		// Object parentObj = baseDao.queryById(id, entityName);
		// 通过父ID查询上级父节点对象
		// String strRecord = (String) baseDao.queryById(id, entityName);
		return parentObject;
	}

	/**
	 * 根据多条件,批量更新数据
	 * 
	 * @param param
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateRecordsBySql(String param) throws Exception {
		// 获取当前实体以及实体类,对应的实体类
		StringBuffer setUpdate = new StringBuffer();
		String entityName = null;
		Map<String, String> maps = null;
		try {
			maps = objectMapper.readValue(param, Map.class);
			entityName = maps.get("entityName");
		} catch (Exception e) {
			handleException(e);
		}
		// 获取更新参数和值
		String updatStr = maps.get("update");
		Map updateMap = null;
		try {
			updateMap = objectMapper.readValue(updatStr, Map.class);
			setUpdate.append(" update " + entityName + " set ");
			Map updateSet = getSQLParams(updateMap);
			String[] strList = (String[]) updateSet.get("list");
			setUpdate.append(strList.toString());
		} catch (Exception e) {
			handleException(e);
		}
		// 获取更新查询参数
		String whereStr = maps.get("where");
		// 解析where条件
		Map whereMap = null;
		StringBuffer whereSB = new StringBuffer();
		try {
			whereMap = objectMapper.readValue(whereStr, Map.class);
			whereSB.append(" where ");
			Map whereSet = getSQLParams(whereMap);
			String[] whereList = (String[]) whereSet.get("list");
			whereSB.append(whereList.toString());
		} catch (Exception e) {
			handleException(e);
		}
		// 获取表名或实体名
		// 参数解析
		// 组织更新语句
		String sql = setUpdate.toString() + whereSB.toString();
		// 调用baseDAO的update方法
		baseDao.updateSqlExec(sql);
	}

	@SuppressWarnings("unused")
	@Override
	public void serviceLogger(String operationTypeName, String content)
			throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		String ip = request.getHeader("x-forwarded-for");
//
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//
//		UserInfo userInfo = UserPermissionUtil.getUserInfo();
//
//		String userName = null;
//		String userXm = null;
//		Long userId = null;
//		if (userInfo != null) {
//			userName = userInfo.getUsername();
//			userId = userInfo.getId();
//		} else {
//			return;
//		}
//
//		if (userInfo != null) {
//			userXm = userInfo.getZwm();
//		} else {
//			return;
//		}
//
//		TbLzRz rz = new TbLzRz();
//		rz.setIp(ip);
//		rz.setUserName(userName);
//		rz.setType(operationTypeName);
//		rz.setContent(content);
//		rz.setCurrTime(new Date());
//		rz.setUserId(userId);
//
//		RzDao rzDao = (RzDao) ApplicationComponentStaticRetriever
//				.getComponentByItsName("rzDao");
//		rzDao.insertRz(rz);
	}

	/**
	 * 单表 新增增日志 注意：单表如果需要增加日志，请在实体表上增加EntityLog注解 content
	 * 中允许加属性变量，如：content="用户名：{userName}"
	 * 
	 * @param entityName
	 *            操作对象
	 * @author evan
	 * @version 2012-06-12
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	private void entityLogger(Object obj) {
//		Class cls = obj.getClass();
//		if (!cls.isAnnotationPresent(EntityLog.class))
//			return;
//		EntityLog entityLog = (EntityLog) cls.getAnnotation(EntityLog.class);
//		String name = entityLog.name();
//		String content = entityLog.content();
//		List<String> fieldList = this.logFieldList(content);
//		try {
//			content = this.logContentValue(content, fieldList, obj);
//			this.serviceLogger("新增", "操作对象：" + name + " " + content);
//		} catch (Exception e) {
//			logger.error("单表日志方法 记录新增" + obj.getClass().getSimpleName()
//					+ "日志过程中出错!");
//			e.printStackTrace();
//		}
	}

	/**
	 * 单表增、删、改日志记录 注意：单表如果需要增加日志，请在实体表上增加EntityLog注解 content
	 * 中允许加属性变量，如：content="用户名：{userName}"
	 * 
	 * @param type
	 *            操作类型 新增、修改、删除
	 * @param entityName
	 *            操作实体名
	 * @param ids
	 *            操作对象标识
	 * @author evan
	 * @version 2012-06-12
	 */
	private void entityLogger(String type, String entityName, String ids) {
//		String entityPack = ServletUtils.entityPath(entityName);
//		try {
//			Class cls = Class.forName(entityPack);
//			/**
//			 * 判定实体是否 存在日志注解
//			 */
//			if (!cls.isAnnotationPresent(EntityLog.class))
//				return;
//			EntityLog entityLog = (EntityLog) cls
//					.getAnnotation(EntityLog.class);
//			String name = entityLog.name();
//			String content = entityLog.content();
//			String[] idArr = null;
//			if (null != ids && !"".equals(ids.trim())) {
//				idArr = ids.split(",");
//			} else {
//				return;
//			}
//			/**
//			 * 解析content中的 变量
//			 */
//			List<String> fieldList = this.logFieldList(content);
//			/**
//			 * 判断是否是批量操作
//			 */
//			if (idArr != null && idArr.length > 1) {
//				type = "批量" + type;
//			}
//			StringBuffer logContentStrBuffer = new StringBuffer("操作对象：" + name
//					+ " ");
//			for (int i = 0; i < idArr.length; i++) {
//				String contentTpl = content;
//				Object obj = baseDao.queryById(idArr[i], cls.getName());// 查询实体
//				contentTpl = this.logContentValue(contentTpl, fieldList, obj);
//				logContentStrBuffer.append(contentTpl).append(";");
//			}
//			this.serviceLogger(type, logContentStrBuffer.toString());
//
//		} catch (Exception e) {
//			logger.error("单表日志方法没有找到" + entityName + "类，或者查询过程出错!");
//			e.printStackTrace();
//		}
	}

	/**
	 * 获取日志注解中的变量部分
	 * 
	 * @param content
	 * @return
	 */
	private List<String> logFieldList(String content) {
		/**
		 * 解析content中的变量
		 */
		List<String> fieldList = new ArrayList();
		Pattern pattern = Pattern.compile("\\{[a-zA-Z0-9]+\\}",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			String field = matcher.group().replace("{", "").replace("}", "");
			fieldList.add(field);
		}
		return fieldList;
	}

	/**
	 * 替换日志content 中的变量
	 * 
	 * @param content
	 *            日志content
	 * @param fieldList
	 *            变量列表
	 * @param obj
	 *            单个对象
	 * @return
	 * @throws Exception
	 */
	private String logContentValue(String content, List<String> fieldList,
			Object obj) throws Exception {
		for (int i = 0; i < fieldList.size(); i++) {
			String firstLetter = fieldList.get(i).substring(0, 1).toUpperCase();
			String getMethod = "get" + firstLetter
					+ fieldList.get(i).substring(1);
			Method method = obj.getClass().getMethod(getMethod, new Class[] {});
			Object value = method.invoke(obj, new Object[] {});
			content = content.replace("{" + fieldList.get(i) + "}",
					value.toString());
		}
		return content;
	}

	/*
	 * public static void main(String args[]){ Map map = new HashMap();
	 * map.put("start", 0); map.put("xm.like", "王"); map.put("xh.equal",
	 * "6023234"); map.put("limit", 20); map.put("cksj.date2","2012-06-22");
	 * map.put("cksj.date1","2012-06-04"); map.put("id.in", "232,3434,3434");
	 * try { Map list = getSQLParams(map); System.out.println(list.toString());
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	@SuppressWarnings("unused")
	@Override
	public String queryXnxqId(String params) throws Exception {
		// 获取当前学年
		Map xnxqMap = this.getCurrentXnxq();
		Long xnId = Long.valueOf(String.valueOf(xnxqMap.get("xnId")));
		Long xqId = Long.valueOf(String.valueOf(xnxqMap.get("xqId")));
		xnxqMap.put("xndm", this.getDmByBmbidAndStSx(xnId, "TsCurrentXnxq", "xnId"));
		xnxqMap.put("xqdm", this.getDmByBmbidAndStSx(xnId, "TsCurrentXnxq", "xqId"));
		return Struts2Utils.objects2Json(xnxqMap);
		
//		Date date = new Date();
//		Calendar time = Calendar.getInstance();
//		time.setTime(date);
//		int nowyear = time.get(Calendar.YEAR);
//		int lastyear = time.get(Calendar.YEAR) - 1;
//		int nextyear = time.get(Calendar.YEAR) + 1;
//		String yearstr = nowyear + "-" + nextyear + "年度";
//		// 获取当前学期
//		String xqmc = "第一学期";
//		String xqrq = nowyear + "-09-01";
//		String qmrq = nowyear + "-03-01";
//		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//		Date xqdate = sf.parse(xqrq);
//		Date qmdate = sf.parse(qmrq);
//		// 判断当前日期是否在第二学期
//		String xqdm = "1";
//		/*
//		 * if(date.before(xqdate) && date.after(qmdate)){ yearstr =
//		 * lastyear+"-"+nowyear+"年度"; xqmc = "第二学期"; xqdm = "2"; }
//		 */
//		// 判断 当前是当前学年还是上一学年
//		String xndm = Integer.toString(nowyear);// Integer.toString(nowyear-1);
//		if (date.after(xqdate)) {
//			xndm = Integer.toString(nowyear);
//		}
//		// 获取学年ID;
//		Long xnId = baseDao.queryIdByBm(xndm, "XXDM-XN");
//		// 获取学期ID
//		Long xqId = baseDao.queryIdByBm(xqdm, "XXDM-XQ");
//		Map map = new HashMap<String, String>();
//		map.put("xnId", xnId);
//		map.put("xqId", xqId);
//		map.put("xqdm", xqdm);
//		map.put("xndm", xndm);
//		return Struts2Utils.objects2Json(map);
	}

	

	/**
	 * 获取下一学期的学年学期
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map getDefXnxq(String params) throws Exception {
		return this.getCurrentXnxq();
	}

	public Object getDmByIdAndStsx(Object bmbId,String ssstm,String sx){
		TsStsx stsx = getStsx(ssstm, sx);
		return getDM(stsx, bmbId);
	}
	
	@Override
	public String getDmByBmbidAndStSx(Long bmbId, String ssstm, String sx) {
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getStsx(ssstm, sx);
		return getDM(stsx, bmbId);
	}

	@Override
	public Long getBmbidByDmAndStSx(String dm, String ssstm, String sx) {
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getStsx(ssstm, sx);
		return (Long)getBMBId(stsx, dm);
	}
	
	public Object getBmbidByDmAndStsx(String dm, String ssstm, String sx){
		TsStsx stsx = getStsx(ssstm, sx);
		return getBMBId(stsx, dm);
	}

	public Object getMcByIdAndStsx(Object bmbId, String ssstm, String sx){
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getStsx(ssstm, sx);
		return getMc(stsx, bmbId);
	}
	
	@Override
	public String getMcByBmbidAndStSx(Long bmbId, String ssstm, String sx) {
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getStsx(ssstm, sx);
		return getMc(stsx, bmbId);
	}

	public String getMcByDmAndStsx(String dm, String ssstm, String sx){
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getStsx(ssstm, sx);
		return getMc(stsx, dm);
	}
	
	@Override
	public String getMcByBmbdmAndStSx(String dm, String ssstm, String sx) {
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getStsx(ssstm, sx);
		return getMc(stsx, dm);

	}

	@SuppressWarnings("unchecked")
	public List getBmbDataListByStmAndSx(String ssstm, String sx) {
		TsStsx stsx = getStsx(ssstm, sx);
		List list = this.getBmbList(stsx);
		List dataList = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			Map map = new HashMap();
			Field[] fields = o.getClass().getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				Field f = fields[j];
				String f_name = f.getName();
				String getName = "get" + f_name.substring(0, 1).toUpperCase()
						+ f_name.substring(1);
				try {
					Method getMethod = o.getClass().getMethod(getName, null);
					map.put(f_name, getMethod.invoke(o, null));
				} catch (Throwable e) {
					throw new RuntimeException(e);
				}
			}
			dataList.add(map);
		}
		return dataList;
	}
	
	public Object getBmbDataList(String str){
		JSONObject json=JSONObject.fromObject(str);
		String ssstm=String.valueOf(json.get("ssstm"));
		String sx=String.valueOf(json.get("sx"));
		List l=this.getBmbDataListByStmAndSx(ssstm, sx);
		return JSONArray.fromObject(l);
	}

	@Override
	public List getStsxByName(String ssstm) {
		return getStsx(ssstm);
	}

	/**
	 * 根据实体属性，编码表的id，获取编码表的代码
	 * 
	 * @param stsx
	 *            实体属性
	 * @param id
	 *            编码表的id
	 * @return 编码表的代码
	 */
	private String getMc(TsStsx stsx, Object bmbId) {
		if (EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()
				+ "_" + stsx.getBmbbzdm()) == null) {
			this.setBmbCache(stsx);
		}
		List bmbList = (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());
		Method getIdMethod=null;
		for (int i = 0; i < bmbList.size(); i++) {
			Object o = bmbList.get(i);
			try {
				if(getIdMethod==null){
					getIdMethod=this.getIdMethod(stsx, o.getClass());
				}
				Object tempId = getIdMethod.invoke(o, null);
				if (tempId.equals(bmbId)) {
					Method getMcMethod = this.getMcMethod(stsx, o.getClass());
					return (String) getMcMethod.invoke(o, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;

		/*
		 * 获取审核状态对应的编码表的实体名，完整的类名
		 */
		// String bmbstm = stsx.getBmbstm(); // 简单名字，不带package包
		// String bmbstmClassName = ServletUtils.entityPath(bmbstm); // 获取完整的类名
		// Object bmbst = null;
		// try {
		// bmbst = this.baseDao.queryById(String.valueOf(bmbId),
		// bmbstmClassName); // 根据完整的类名，生成类对应的实例对象
		// Method getMcMethod = bmbst.getClass().getMethod("getMc"); //
		// 获取编码表实体对象的id
		// return (String) getMcMethod.invoke(bmbst, null);
		//
		// } catch (Throwable e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
	}

	/**
	 * 根据实体属性，编码表的dm，获取编码表的代码
	 * 
	 * @param stsx
	 *            实体属性
	 * @param id
	 *            编码表的id
	 * @return 编码表的代码
	 */
	private String getMc(TsStsx stsx, String dm) {
		if (EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()
				+ "_" + stsx.getBmbbzdm()) == null) {
			this.setBmbCache(stsx);
		}
		List bmbList = (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());
		for (int i = 0; i < bmbList.size(); i++) {
			Object o = bmbList.get(i);
			try {
				Method getDmMethod = o.getClass().getMethod("getDm");
				Object tempDm = getDmMethod.invoke(o, null);
				if (dm.equals(tempDm)) {
					Method getMcMethod = o.getClass().getMethod("getMc");
					return (String) getMcMethod.invoke(o, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;

		/*
		 * 获取审核状态对应的编码表的实体名，完整的类名
		 */
		// String bmbstm = stsx.getBmbstm(); // 简单名字，不带package包
		// String bmbstmClassName = ServletUtils.entityPath(bmbstm); // 获取完整的类名
		// Object bmbst = null;
		// Long bmbId = null;
		// try {
		// bmbst = Class.forName(bmbstmClassName).newInstance(); //
		// 根据完整的类名，生成类对应的实例对象
		// // 获取设置bzdm值的方法
		// Method setMethod = bmbst.getClass().getMethod("setBzdm",
		// String.class);
		// setMethod.invoke(bmbst, stsx.getBmbbzdm());// 设置编码表实体对象的bzdm属性值
		// setMethod = bmbst.getClass().getMethod("setDm", String.class);
		// setMethod.invoke(bmbst, dm);
		// List bmbList = this.baseDao.loadEqual(bmbst); // 获取对应的编码表中，给定编码和名称的对象
		// if (bmbList != null && bmbList.size() > 0) {
		// bmbst = bmbList.get(0);
		// } else {
		// throw new RuntimeException("数据库数据异常");
		// }
		// Method getMcMethod = bmbst.getClass().getMethod("getMc"); //
		// 获取编码表实体对象的id
		// return (String) getMcMethod.invoke(bmbst, null);
		//
		// } catch (Throwable e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
	}

	/**
	 * 根据实体属性，编码表的id，获取编码表的代码
	 * 
	 * @param stsx
	 *            实体属性
	 * @param id
	 *            编码表的id
	 * @return 编码表的代码
	 */
	private String getDM(TsStsx stsx, Object bmbId) {
		if (EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()
				+ "_" + stsx.getBmbbzdm()) == null) {
			this.setBmbCache(stsx);
		}
		List bmbList = (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());
		Method getIdMethod=null;
		for (int i = 0; i < bmbList.size(); i++) {
			Object o = bmbList.get(i);
			try {
				if(getIdMethod==null){
					getIdMethod=this.getIdMethod(stsx, o.getClass());
				}
				Object tempId = getIdMethod.invoke(o, null);
				if (tempId.equals(bmbId)) {
					Method getDmMethod = this.getDmMethod(stsx, o.getClass());
					return (String) getDmMethod.invoke(o, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;

		/*
		 * 获取审核状态对应的编码表的实体名，完整的类名
		 */
		// String bmbstm = stsx.getBmbstm(); // 简单名字，不带package包
		// String bmbstmClassName = ServletUtils.entityPath(bmbstm); // 获取完整的类名
		// Object bmbst = null;
		// try {
		// bmbst = this.baseDao.queryById(String.valueOf(bmbId),
		// bmbstmClassName); // 根据完整的类名，生成类对应的实例对象
		// Method getDMMethod = bmbst.getClass().getMethod("getDm"); //
		// 获取编码表实体对象的id
		// return (String) getDMMethod.invoke(bmbst, null);
		//
		// } catch (Throwable e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
	}

	/**
	 * 根据实体属性，编码表的bzdm，获取编码表的代码
	 * 
	 * @param stsx
	 *            实体属性
	 * @param id
	 *            编码表的id
	 * @return 编码表的代码
	 */
	private List getBmbList(TsStsx stsx) {
		if (EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()
				+ "_" + stsx.getBmbbzdm()) == null) {
			this.setBmbCache(stsx);
		}
		return (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());

		/*
		 * 获取审核状态对应的编码表的实体名，完整的类名
		 */
		// String bmbstm = stsx.getBmbstm(); // 简单名字，不带package包
		// String bmbstmClassName = ServletUtils.entityPath(bmbstm); // 获取完整的类名
		// Object bmbst = null;
		// try {
		// bmbst = Class.forName(bmbstmClassName).newInstance(); //
		// 根据完整的类名，生成类对应的实例对象
		// // 获取设置bzdm值的方法
		// Method setMethod = bmbst.getClass().getMethod("setBzdm",
		// String.class);
		// setMethod.invoke(bmbst, stsx.getBmbbzdm());// 设置编码表实体对象的bzdm属性值
		// Method sfkySetMethod = bmbst.getClass().getMethod("setSfky",
		// String.class);
		// sfkySetMethod.invoke(bmbst, "1");
		// return this.baseDao.loadEqual(bmbst);
		// } catch (Throwable e) {
		// e.printStackTrace();
		// throw new RuntimeException(e);
		// }
	}

	/**
	 * 根据实体属性，编码表的代码，获取编码表的id
	 * 
	 * @param stsx
	 *            实体属性对象
	 * @param dm
	 *            代码
	 * @return 代码表的id
	 */
	@SuppressWarnings("unused")
	private Object getBMBId(TsStsx stsx, String dm) {
		if (EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()
				+ "_" + stsx.getBmbbzdm()) == null) {
			this.setBmbCache(stsx);
		}
		List bmbList = (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());
		Method getDmMethod=null;
		Method getIdMethod=null;
		for (int i = 0; i < bmbList.size(); i++) {
			Object o = bmbList.get(i);
			try {
				if(getDmMethod==null){
					getDmMethod=this.getDmMethod(stsx, o.getClass());
				}
				Object tempDm = getDmMethod.invoke(o, null);
				if (dm.equals(tempDm)) {
					getIdMethod = this.getIdMethod(stsx, o.getClass());
					return  getIdMethod.invoke(o, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;

	}

	/**
	 * 根据代码和实体属性记录获取代码ID
	 * 
	 * @param stsx
	 * @param dm
	 *            代码
	 * @return
	 */
	private Long getBzdmIdByDm(TsStsx stsx, String dm) {
		if (EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()
				+ "_" + stsx.getBmbbzdm()) == null) {
			this.setBmbCache(stsx);
		}
		List bmbList = (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());
		Method getDmMethod=null;
		Method getIdMethod=null;
		for (int i = 0; i < bmbList.size(); i++) {
			Object o = bmbList.get(i);
			try {
				if(getDmMethod==null){
					getDmMethod=this.getDmMethod(stsx, o.getClass());
				}
				Object tempDm = getDmMethod.invoke(o, null);
				if (dm.equals(tempDm)) {
					getIdMethod = this.getIdMethod(stsx, o.getClass());
					return (Long) getIdMethod.invoke(o, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;

	}
	
	private Method getIdMethod(TsStsx stsx,Class clazz) throws Throwable{
		String idField="id";//默认取编码表的id字段
		if(stsx.getBmbglzd()!=null){
			idField=stsx.getBmbglzd();//
		}
		String getIdFieldName="get"+idField.substring(0,1).toUpperCase()+idField.substring(1);
		return clazz.getDeclaredMethod(getIdFieldName);
		
	}
	private Method getDmMethod(TsStsx stsx,Class clazz) throws Throwable{
		String dmField="dm";//默认是编码表的dm字段
		if(stsx.getBmbdmzd()!=null){
			dmField=stsx.getBmbdmzd();
		}
		String getDmFieldName="get"+dmField.substring(0,1).toUpperCase()+dmField.substring(1);
		return clazz.getDeclaredMethod(getDmFieldName);
	}
	private Method getMcMethod(TsStsx stsx,Class clazz) throws Throwable{
		String mcField="mc";
		if(stsx.getBmbxszd()!=null){
			mcField=stsx.getBmbxszd();
		}
		String getMcFieldName="get"+mcField.substring(0,1).toUpperCase()+mcField.substring(1);
		return clazz.getDeclaredMethod(getMcFieldName);
	}

	/**
	 * 根据代码名称和实体属性记录获取代码ID
	 * 
	 * @param stsx
	 * @param mc
	 * @return
	 */
	private Long getBzdmIdByMc(TsStsx stsx, String mc) {
		List bmbList =(List) EhcacheUtil.getObjFromCache(this.bmb_cacheName, stsx.getBmbstm()+ "_" + stsx.getBmbbzdm());
		if ( bmbList == null || bmbList.isEmpty()) {
			this.setBmbCache(stsx);
		}
		bmbList = (List) EhcacheUtil.getObjFromCache(this.bmb_cacheName,
				stsx.getBmbstm() + "_" + stsx.getBmbbzdm());
		Method getIdMethod=null;
		Method getMcMethod=null;
		for (int i = 0; i < bmbList.size(); i++) {
			Object o = bmbList.get(i);
			try {
				if(getMcMethod==null){
					getMcMethod=this.getMcMethod(stsx, o.getClass());
				}
				Object tempMc = getMcMethod.invoke(o, null);
				if (mc.equals(tempMc)) {
					getIdMethod = this.getIdMethod(stsx, o.getClass());
					return (Long) getIdMethod.invoke(o, null);
				}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;

	}

	private void setBmbCache(TsStsx stsx) {
		/*
		 * 获取审核状态对应的编码表的实体名，完整的类名
		 */
		String bmbstm = stsx.getBmbstm(); // 简单名字，不带package包
		Object bmbst = null;
		Long bmbId = null;
		try {
			Class classz=null;			
			Object service=ApplicationComponentStaticRetriever.getComponentByItsName("entityService");
			Method m=service.getClass().getDeclaredMethod("getEntityBySimpleName", String.class);
			classz=(Class)m.invoke(service, bmbstm);
			
			bmbst = classz.newInstance(); // 根据完整的类名，生成类对应的实例对象
			Field field=null;
			try{
				field=classz.getDeclaredField("sfky");
			}catch(Throwable e){
				
			}
			if(field!=null){
				Class type=field.getType();	
				Method setSfkyMethod=null;
				setSfkyMethod=classz.getDeclaredMethod("setSfky", type);
				if(setSfkyMethod!=null){
					if(String.class.equals(type)){
						setSfkyMethod.invoke(bmbst, "1");
					}else if(Boolean.class.equals(type)){
						setSfkyMethod.invoke(bmbst, true);
					}else if(Integer.class.equals(type)){
						setSfkyMethod.invoke(bmbst, 1);
					}else if(Short.class.equals(type)){
						setSfkyMethod.invoke(bmbst, new Short("1"));
					}
				}
				
			}
			if(stsx.getBmblbzd()!=null){//设置了编码表的类别字段
				String methodName="set"+stsx.getBmblbzd().substring(0,1).toUpperCase()+stsx.getBmblbzd().substring(1);
				Method setMethod = bmbst.getClass().getMethod(methodName,
						String.class);
				setMethod.invoke(bmbst, stsx.getBmbbzdm());// 设置编码表实体对象的bzdm属性值
			}else if (stsx.getBmbstm().equals("TcXxbzdmjg")) {
				// 获取设置bzdm值的方法
				Method setMethod = bmbst.getClass().getMethod("setBzdm",
						String.class);
				setMethod.invoke(bmbst, stsx.getBmbbzdm());// 设置编码表实体对象的bzdm属性值
			}
			List bmbList=null;
			Object dmField=null;
			try{
				dmField=classz.getDeclaredField("dm");
			}catch(Throwable e){
				
			}
			if(dmField!=null){
				bmbList = this.baseDao.loadEqualByOrder(bmbst, "dm", "asc"); // 获取对应的编码表中，给定编码和名称的对象
			}else{
				bmbList = this.baseDao.loadEqual(bmbst); // 获取对应的编码表中，给定编码和名称的对象
			}			
			EhcacheUtil.putObjToCache(this.bmb_cacheName, stsx.getBmbstm()
					+ "_" + stsx.getBmbbzdm(), bmbList);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 判断一个实体
	 * @param ssstm
	 * @param sx
	 * @return
	 */
	public Boolean isBmField(String ssstm, String sx){
		TsStsx s=this.getStsx(ssstm, sx);
		return s.getBmbbzdm() != null ? true : false;
	}

	/**
	 * 
	 * @param ssstm
	 *            所属实体名
	 * @param sx
	 *            属性
	 * @return 实体属性对象
	 */
	private TsStsx getStsx(String ssstm, String sx) {
		List list = getStsx(ssstm);
		List<TsStsx> l = (List) EhcacheUtil.getObjFromCache(
				this.stsx_cacheName, ssstm);
		for (TsStsx s : l) {
			if (sx.equals(s.getSx())) {
				return s;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param ssstm
	 *            所属实体名
	 * @param sx
	 *            属性
	 * @return 实体属性对象
	 */
	private TsStsx getBzdmByStsx(String ssstm, String sx) {
		List list = getStsx(ssstm);
		List<TsStsx> l = (List) EhcacheUtil.getObjFromCache(
				this.stsx_cacheName, ssstm);
		for (TsStsx s : l) {
			if (sx.equals(s.getSxzwm())) {
				return s;
			}
		}
		return null;
	}

	private List getStsx(String ssstm) {
		return baseDao.getStsx(ssstm);
	}

	private String getDataPermiss(Long userId, String permissIds,
			String perAllIds) {
		// 根据数据权限查询所包括的下级所有节点对应的班级

		List dataPermissList = baseDao.getUserDataPermiss(userId, permissIds);
		if (dataPermissList != null && dataPermissList.size() > 0) {
			for (int i = 0; i < dataPermissList.size(); i++) {
				JSONArray array = JSONArray.fromObject(dataPermissList.get(i));
				String id = array.getString(0);
				perAllIds = perAllIds.concat(id + ",");
				getDataPermiss(userId, id, perAllIds);
			}
		}
		return perAllIds;
	}

	@Override
	public String queryBzdmByDmOrName(String params) throws Exception {
//		long l = System.currentTimeMillis();
		Map jsonObj = new ObjectMapper().readValue(params, Map.class);
		Map<String, Object> map = new HashMap();

		Set<String> key = jsonObj.keySet();
		String dm = "";
		String mc = "";
		String bzdm = "";
		Iterator<String> iter = key.iterator();
		while (iter.hasNext()) {
			String field = iter.next();
			if (field.equals("dm")) {
				dm = (String) jsonObj.get(field);
			} else if (field.equals("mc")) {
				mc = (String) jsonObj.get(field);
			} else if (field.equals("bzdm")) {
				bzdm = (String) jsonObj.get(field);
			}
		}
		Long id = null;
		if (dm != null && !"".equals(dm.trim())) {
			id = baseDao.getBzdmIdByDm(bzdm, dm);
		}
		if (mc != null && !"".equals(mc.trim())) {
			id = baseDao.getBzdmIdByMc(bzdm, mc);
		}
//		System.out.println("\r<br>queryBzdmByDmOrName执行耗时 : "
//				+ (System.currentTimeMillis() - l) / 1000f + " 秒 ");
		return "{id:" + id.toString() + "}";

	}

	/**
	 * 通过实体名配置数据权限service方法，并且根据service方法获取数据权限IDS
	 * 
	 * @param entityName
	 * @return
	 * @throws Exception
	 */
	private Object queryDataPermissParam(String entityName) throws Exception {
		// 获取当前页面对应service方法。
		ActionContext act = ActionContext.getContext();
		Map requestMap = (HashMap) act.getApplication().get(
				SysConstants.REQUEST_SERVICES);
		Object param = null;
		// 组装request请求名
		String requestName = "entity".concat(entityName);
		Map serviceMap = (HashMap) requestMap.get(requestName);
		if (serviceMap != null) {
			String service = (String) serviceMap
					.get(SysConstants.REQUEST_SERVICE_NAME);
			String methodName = (String) serviceMap
					.get(SysConstants.REQUEST_SERVICE_METHOD);
			// 获取服务对象
			Object serviceBean = ApplicationComponentStaticRetriever
					.getComponentByItsName(service);
			Class cls = serviceBean.getClass();
			Class[] paraTypes = new Class[] { String.class };
			// 反射方法
			Method method = cls.getMethod(methodName, paraTypes);
			// 当前实体作为参数传service方法执行。
			String[] paraValues = new String[] { entityName };
			param = method.invoke(serviceBean, paraValues);
		}
		return param;
	}

	private String getPermissParam(String entityName) throws Exception {
		// 定义变量
		String permissIds = "";
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		String roles = userInfo.getRoleIds();
		// 管理员角色ID
		Long mangaerRoleId = baseDao.getRoleIdByDm("1");
		// 老师角色ID
		Long teacherRoleId = baseDao.getRoleIdByDm("2");
		// 学生角色ID
		Long studentRoleId = baseDao.getRoleIdByDm("3");

		if (entityName.equals("TbZyxx") || entityName.equals("TbJxzzjg")
				|| entityName.equals("TbBjDm") || entityName.equals("TbYxjbxx")) {
			if (userInfo.getPermissJxzzIds() == null
					|| userInfo.getPermissJxzzIds().equals("")) {
				// 获取当前用户的角色。
				if (roles.indexOf(mangaerRoleId.toString()) >= 0) {// 管理员
					return null;
				} else if (roles.indexOf(teacherRoleId.toString()) >= 0) {// 教师
					return userInfo.getBmId().toString();
				} else if (roles.indexOf(studentRoleId.toString()) >= 0) {// 学生
					return userInfo.getYxId().toString() + ","
							+ userInfo.getZyId().toString() + ","
							+ userInfo.getBjId().toString();
				}
			} else {
				permissIds = userInfo.getPermissJxzzIds();
			}
		} else if (entityName.equals("TbXzzzjg")) {
			if (userInfo.getPermissXzzzIds() == null
					|| userInfo.getPermissXzzzIds().equals("")) {
				if (roles.indexOf(mangaerRoleId.toString()) >= 0) {// 管理员
					return null;
				} else if (roles.indexOf(teacherRoleId.toString()) >= 0) {// 教师
					return userInfo.getBmId().toString();
				}
			} else {
				permissIds = userInfo.getPermissXzzzIds();
			}
		} else if (entityName.equals("TbJzgxxDm")) {
			permissIds = permissIds.concat(userInfo.getPermissJxzzIds())
					.concat(userInfo.getPermissXzzzIds() == null ? "" : ","
							+ userInfo.getPermissXzzzIds());
		}

		return permissIds;
	}

	@Override
	public List queryEntityList(String params) {
		JSONObject obj = JSONObject.fromObject(params);
		Set<String> key = obj.keySet();
		Iterator<String> iter = key.iterator();
		String paramHql = "";
		// 遍历参数对象
		while (iter.hasNext()) {
			String field = iter.next();
			if (field.equals("entityName")) {
				paramHql = paramHql.substring(0, paramHql.length() - 4);
				continue;
			}
			String value = String.valueOf(obj.get(field));
			paramHql = paramHql.concat(" " + field + " = ").concat(value);
			if (iter.hasNext()) {
				paramHql = paramHql.concat(" and");
			}
		}
		String entityName = obj.getString("entityName");
		List list = baseDao.queryEntityList(entityName, paramHql);
		return list;
	}

	@Override
	public Long getIdByDm(String entityName, String bzdm, String dm) {
		// 组装参数
		String param = " dm = '" + dm + "' ";
		param =param.concat(" and sfky = 1 ");
		if (entityName.equals("TcXxbzdmjg") && bzdm != null) {
			param = param.concat(" and bzdm ='" + bzdm + "' ");
		}
		// 根据条件查询代码表数据
		List objList = baseDao.queryEntityList(entityName, param);
		Long id = null;
		// 判断 查询的结果是否为null或size大于0
		if (objList != null && objList.size() > 0) {
			JSONObject obj = JSONObject.fromObject(objList.get(0));// 获取第一个对象并转化为JSONObject
			id = obj.getLong("id");// 获取ID并赋给id
		}
		return id;
	}

	// -----------------------------------------2013-05-16--------------------------------------
	/**
	 * 通用公共实体类查询数据记录方法 (高东杰--修改方法)
	 * 
	 * @param param
	 *            　前台传入参数
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableContent(String param) {
		try {
			JSONObject jsonObj = new JSONObject().fromObject(param);
			// 获取实体名
			String entityName = jsonObj.getString("entityName");
			Map paramMap = SqlParamsChange.getSQLParams(jsonObj, false);
			paramMap.put("entityName", entityName);
			PageParam pageParam = new PageParam(0, 25);
			// 根据条件获取数据
			List list = baseDao.queryTableContent(paramMap, pageParam);
			// 转化为json数据
			if (list != null) {
				return SysConstants.successInfo(list,
						pageParam.getRecordCount());
			} else {
				return SysConstants.JSON_SUCCESS_FALSE;
			}
		} catch (Exception e) {
			return handleException(e);
		} finally {

		}
	}

	/*	*//**
	 * 通过实体类查询数据记录 (高东杰--新增方法)
	 * 
	 * @param map
	 *            　参数包括实体类名、查询条件、高级查询条件、分页参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	/*
	 * public Info queryTableDataByHql(Map map,PageParam pageParam) throws
	 * Exception{ List list =baseDao.queryTableContent(map, pageParam); Info
	 * info = new Info(); if(list != null && pageParam.getRecordCount()>0){
	 * info.setCount(pageParam.getRecordCount()); info.setData(list);
	 * info.setSuccess(true); }; return info; }
	 *//**
	 * 通过SQL查询数据记录　(高东杰--新增方法)
	 * 
	 * @param sql
	 *            带有业务逻辑的ＳＱＬ
	 * @param map
	 *            　参数包括查询条件、高级查询条件、分页参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	/*
	 * 
	 * public Info queryTableDataBySql(String sql,Map map,PageParam pageParam)
	 * throws Exception{ Map retMap = new HashMap<String, Object>(); List list
	 * =baseDao.queryTableContent(sql, map, pageParam); Info info = new Info();
	 * if(list != null && pageParam.getRecordCount()>0){
	 * info.setCount(pageParam.getRecordCount()); info.setData(list);
	 * info.setSuccess(true); }; return info; }
	 */
	/**
	 * 通过实体类查询数据记录 (高东杰--新增方法)
	 * 
	 * @param map
	 *            　参数包括实体类名、查询条件、高级查询条件、分页参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	@Override
	public String queryTableDataByHql(Map map) throws Exception {
		PageParam pageParam = new PageParam(0, 25);
		List list = baseDao.queryTableContent(map, pageParam);
		if (list != null) {
			return SysConstants.successInfo(list, pageParam.getRecordCount());
		} else {
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * 通过实体类查询数据记录 (高东杰--新增方法)
	 * 
	 * @param params
	 *            　前台传入的参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	@Override
	public String queryTableDataByHql(String params) throws Exception {
		PageParam pageParam = new PageParam(0, 25);
		JSONObject obj = JSONObject.fromObject(params);
		List list = baseDao.queryTableContent(obj, pageParam);
		if (list != null) {
			return SysConstants.successInfo(list, pageParam.getRecordCount());
		} else {
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * 通过SQL查询数据记录　(高东杰--新增方法)
	 * 
	 * @param sql
	 *            带有业务逻辑的ＳＱＬ
	 * @param map
	 *            　参数包括查询条件、高级查询条件、分页参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	@Override
	public String queryTableDataBySql(String sql, Map map) throws Exception {
		PageParam pageParam = new PageParam(0, 25);
		List list = baseDao.queryTableContent(sql, map, pageParam);
		if (list != null) {
			return SysConstants.successInfo(list, pageParam.getRecordCount());
		} else {
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * 通过SQL查询数据记录　(高东杰--新增方法)
	 * 
	 * @param sql
	 *            带有业务逻辑的ＳＱＬ
	 * @param params
	 *            前台传入的参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	@Override
	public String queryTableDataBySql(String sql, String params)
			throws Exception {
		JSONObject obj = JSONObject.fromObject(params);
		PageParam pageParam = new PageParam(0, 25);
		List list = baseDao.queryTableContent(sql, obj, pageParam);
		if (list != null) {
			return SysConstants.successInfo(list, pageParam.getRecordCount());
		} else {
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	/**
	 * 通过SQL查询数据记录别名 的方法　(王永太--新增方法)
	 * 
	 * @param sql
	 *            带有业务逻辑的ＳＱＬ
	 * @param params
	 *            前台传入的参数
	 * @param pageParam
	 *            　分页实体
	 * @return　返回成功或失败的信息
	 * @throws Exception
	 */
	public String queryTableDataBySqlWithAlias(String sql, String params)
			throws Exception {
		JSONObject obj = JSONObject.fromObject(params);
		PageParam pageParam = new PageParam(0, 25);
		String entityName = null;
		if(obj.containsKey("entityName")){
			entityName = obj.getString("entityName");	
		}
		Map paramMap = SqlParamsChange.getSQLParams(obj,true);
		if(entityName!=null) paramMap.put("entityName", entityName);
		List list = baseDao.queryTableContentWithAlias(sql, paramMap, pageParam);
		if (list != null) {
			return SysConstants.successInfo(list, pageParam.getRecordCount());
		} else {
			return SysConstants.JSON_SUCCESS_FALSE;
		}
	}

	// -----------------------------------------------------2013-05-17-----------------------------------------------------------
	/**
	 * 更新对象数据(新增)
	 * 
	 * @param obj
	 * @param fieldnames
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateEntityPartFields(Object obj, List<String> fieldnames)
			throws Exception {
		return baseDao.updateEntityPartField(obj, fieldnames);
	}

	/**
	 * 更新对象数据(新增)
	 * 
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateEntityPartFields(Object obj, String[] fields)
			throws Exception {
		return baseDao.updateEntityPartField(obj, fields);
	}

	/**
	 * 更新对象数据(新增)
	 * 
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean updateEntityPartFields(Object obj, String fields)
			throws Exception {
		return baseDao.updateEntityPartField(obj, fields);
	}

	/**
	 * 批量更新对象数据 (新增)
	 * 
	 * @param obj
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean bathupdateEntityPartFields(List<Object> list,
			List<String> fields) throws Exception {
		return baseDao.batchUpdateEntityPartField(list, fields);
	}

	/**
	 * 保存对象并返回对象　(高东杰--新增)
	 * 
	 * @param object
	 *            　对象(不带ID)
	 * @return　object 对象(带ID)
	 * @throws Exception
	 */
	public Object save(Object obj) throws Exception {
		return baseDao.save(obj);
	}

	/**
	 * 批量保存对象　(高东杰--新增)
	 * 
	 * @param list
	 *            对象列表(不带ＩＤ)
	 * @return　list 对象列表
	 * @throws Exception
	 */
	public List<Object> save(List<Object> list) throws Exception {
		return baseDao.save(list);
	}

	/**
	 * 批量更新实体(高东杰－－新增)
	 * 
	 * @param list
	 *            批量实体对象
	 * @return　ture或false
	 */
	public boolean update(List<Object> list) throws Exception {
		return baseDao.update(list);
	}

	/**
	 * 根据ids删除记录
	 * 
	 * @param ids
	 *            　String
	 * @param entity
	 *            实体类
	 * @return　删除的记录数
	 */
	@Override
	public void delete(String ids, Class entity) {
		// 获取表名
		String tableName = EntityUtil.getTableName(entity);
		// 删除记录
		baseDao.delete(ids, tableName);
	}

	/**
	 * 根据传入的ids列表删除记录
	 * 
	 * @param list
	 *            　ids<list>
	 * @param entity
	 *            实体类
	 * @return　删除的记录数据
	 */
	@Override
	public void delete(List<Object> list, Class entity) {
		// 获取实体类的表名
		String tableName = EntityUtil.getTableName(entity);
		// 定义删除的记录ids
		String ids = "";
		// 遍历list组装id为String
		for (int i = 0; i < list.size(); i++) {
			ids.concat(list.get(i).toString());
			if ((i + 1) != list.size()) {
				ids.concat(",");
			}
		}
		// 调用删除功能
		baseDao.delete(ids, tableName);
	}

	/**
	 * 根据传入的ids列表删除记录
	 * 
	 * @param str
	 *            []　ids数组
	 * @param entity
	 *            实体类
	 * @return　删除的记录数据
	 */
	@Override
	public void delete(String[] str, Class entity) {
		// 获取实体类的表名
		String tableName = EntityUtil.getTableName(entity);
		// 定义删除的记录ids
		String ids = "";
		// 遍历list组装id为String
		for (int i = 0; i < str.length; i++) {
			ids.concat(str[i].toString());
			if ((i + 1) != str.length) {
				ids.concat(",");
			}
		}
		// 调用删除功能
		baseDao.delete(ids, tableName);
	}

	/**
	 * 根据教学组织机构ID获取班级信息
	 * 
	 * @param jxzzjgId
	 * @return
	 */
	public List<TbXxzyBjxxb> getBjList(Long jxzzjgId) {
		// 调用dao层根据教学组织机构ＩＤ获取班级信息
		return baseDao.getBjList(jxzzjgId);
	}

	/**
	 * 根据SQL查询List<Map>
	 * 
	 * @param sql
	 * @return list<Map>
	 */
	@Override
	public List<Map> queryListMapBySql(String sql) {
		return baseDao.queryListMapBySQL(sql);
	}

	/**
	 * 根据SQL查询数据并返回List<Map>,其中Map的key值为小写
	 * 
	 * @param sqlString
	 *            return List<Map>
	 */
	public List<Map> queryListMapInLowerKeyBySql(String sql) {
		return baseDao.queryListMapInLowerKeyBySql(sql);
	}

	// -------------------------------------------------------------------------------------------------
	/**
	 * 查询sql中的记录个数
	 * 
	 * @param sql
	 * @return
	 */
	public Long queryForLongBySql(String sql) {
		return baseDao.queryForLongBySql(sql);
	}

	@Override
	public TsStsx getBzdmByStmAndSxZwm(String entityName, String fieldZwm) {
		/*
		 * 从实体属性表中获取实体名为ssstm，属性为sx的实体属性对象
		 */
		TsStsx stsx = getBzdmByStsx(entityName, fieldZwm.trim());
		return stsx;
	}

	@Override
	public Long getBzdmIdByValueOrDmAndMap(String value, String dm, TsStsx stsx) {
		// 代码ID
		Long dmId = null;
		// 判断代码是否为空
		if (!dm.trim().isEmpty()) {
			dmId = getBzdmIdByDm(stsx, dm.trim());
		} else if (!value.trim().isEmpty()) {
			dmId = getBzdmIdByMc(stsx, value.trim());
		}
		return dmId;
	}

	@Override
	public List queryBjxxByJxzzjg(String params) {
		// 将参数转化为json对象
		JSONObject obj = JSONObject.fromObject(params);
		// 获取系部ＩＤ
		String xbId = obj.containsKey("yxId")?obj.getString("yxId"):"";
		// 获取专业ID
		String zyId = obj.containsKey("zyId")?obj.getString("zyId"):"";
		// 获取年级ID
		String njId = obj.getString("rxnjId");
		//201406013zyc-迎新分班统计人数据修改
		String tableName = "TB_WELCOME_XS";
		if(obj.containsKey("tableName")){
			tableName = obj.getString("tableName");
		}
		String njParam = "";
		if(!njId.equals("1")){
			njParam =" and b.NJ_ID ="+njId;
		}
		// 定义node节点
		String nodeId = "0";//obj.getString("nodeId");
		if (!zyId.isEmpty()) {
			nodeId = zyId;// 将专业ID赋给nodeId
		} else if (!xbId.isEmpty()) {
			nodeId = xbId;// 将系部ID赋给nodeId
		}
		// 查询教学组织机构的 节点
		TbXxzyJxzzjg jxzzNode = (TbXxzyJxzzjg) baseDao.get(TbXxzyJxzzjg.class,
				Long.valueOf(nodeId));
		// 获取当前节点下所有子ID
		String jxzzjgSql = "select t.ID from TB_JXZZJG t where t.QXM like '"
				+ jxzzNode.getQxm() + "%' ";
		// 关联当前节点下所有班级查询
		String sql = "select b.ID as \"id\",b.BM as \"mc\",b.RS as \"rs\",(select count(s.ID) as \"bjrs\" "
				+ "from "+tableName+" s where s.BJ_ID = b.ID) as \"sjrs\" from ("
				+ jxzzjgSql
				+ ") g left join TB_XXZY_BJXXB b on b.FJD_ID =g.ID where 1=1 "
				+ njParam + " and  b.SFKY = 1　order by  \"rs\",\"sjrs\" desc";
		List list = baseDao.querySqlList(sql);
		return list;
	}
	/**
	 * 待确定完班级增加学习方式之后再修改
	 */
	@Override
	public String queryBjxxByJxzzjgId(String params) {
		// 将参数转化为json对象
		JSONObject obj = JSONObject.fromObject(params);
		// 获取系部ＩＤ
		/*String xbId = obj.containsKey("yxId")?obj.getString("yxId"):"";
		// 获取专业ID
		String zyId = obj.containsKey("zyId")?obj.getString("zyId"):"";*/
		// 获取年级ID
		String njId = obj.containsKey("rxnjId")? obj.getString("rxnjId"):"1";
		String njParam = "";
		if(!njId.equals("1")){
			njParam =" and b.NJ_ID ="+njId;
		}
		// 定义node节点
		String nodeId = obj.getString("nodeId");
		/*if (!zyId.isEmpty()) {
			nodeId = zyId;// 将专业ID赋给nodeId
		} else if (!xbId.isEmpty()) {
			nodeId = xbId;// 将系部ID赋给nodeId
		}*/
		// 查询教学组织机构的 节点
		TbXxzyJxzzjg jxzzNode = (TbXxzyJxzzjg) baseDao.get(TbXxzyJxzzjg.class,
				Long.valueOf(nodeId));
		// 获取当前节点下所有子ID
		String jxzzjgSql = "select t.ID from TB_JXZZJG t where t.QXM like '"
				+ jxzzNode.getQxm() + "%' ";
		// 关联当前节点下所有班级查询
		String sql = "select b.ID as \"id\",b.BM as \"mc\",b.RS as \"rs\",(select count(s.ID) as \"bjrs\" from TB_XJDA_XJXX s where s.BJ_ID = b.ID) as \"sjrs\" from ("
				+ jxzzjgSql
				+ ") g left join TB_XXZY_BJXXB b on b.FJD_ID =g.ID where 1=1 "
				+ njParam + " and b.SFKY = 1　order by  \"rs\",\"sjrs\" desc";
		List list = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(list);
	}
	@Override
	public String queryJxzzzjgAndBigBj(String params) throws Exception{
		//转化参数为JSON
		JSONObject obj = JSONObject.fromObject(params);
		//教学组织机构ID
		Long jxzzjgId = 0l;
		if(obj.containsKey("jxzzjgId")){
			jxzzjgId = obj.getLong("jxzzjgId");
		}
		obj.remove("jxzzjgId");
		//将参数转化为ＳＱＬ条件
		Map map = SqlParamsChange.getSQLParams(obj);
		//根据教学组织机构ID获取记录
		TbXxzyJxzzjg jxzzjg = (TbXxzyJxzzjg) baseDao.get(TbXxzyJxzzjg.class, jxzzjgId);
		//获取当头节点的全息码
		String qxm = jxzzjg.getQxm();
		//定义获取教学组织机构的SQL
		String jxzzHql = " from TbXxzyJxzzjg g where g.sfky =1 and g.qxm like '"+qxm+"%'";
		//查询当前节点下的所有教学组织机构
		List<TbXxzyJxzzjg> list = baseDao.queryHql(jxzzHql);
		//定义大班查询的ＳＱＬ
		String bjxxHql = " select g from TbXxzyBjZbjZzjg g left join TbXxzyJsbjfpb j on g.id = j.bjId where 1=1 ";
		//获取查询条件
		List paramList = (List) map.get("list");
		//遍历查询条件并组装ＳＱＬ
		for(int i = 0 ;i<paramList.size();i++){
			bjxxHql =bjxxHql.concat(" and ").concat(paramList.get(i).toString());
		}
		//获取班级信息
		bjxxHql =bjxxHql.concat(" and j.id is null and g.fjdId in (select g.id ").concat(jxzzHql).concat(" )");
		//定义maps
		Map<Long,MenuNode> maps = new HashMap<Long, MenuNode>();
		//执行班级查询
		List bjList = baseDao.queryHql(bjxxHql);
		MenuTree tree = new MenuTree();
		MenuNode menuNode = new MenuNode(new Long(0), new Long(-1), true, "", false);
		// 顶级节点set树中
		tree.setRoot(menuNode);
		//判断当前教学组织机构列表是否存在
		if(list != null && list.size() >0){
			//获取教学组织机构list转Map
			changeMenuMap(list, maps);
			//将班级List转化Map
			changeMenuMap(bjList, maps);
			//组装树
			changeMenu(maps);
		}
		
		return Struts2Utils.bean2json(tree.root);
	}
	/**
	 * 教学组织机构和班级结构树
	 * @param params
	 * @return
	 */
	@Override
	public String queryJxzzjgAddBjxxMenuTree(String params){
		//解析传入的参数
		JSONObject obj = JSONObject.fromObject(params);
		String sqlParam=" sfky =1 ";
		if(obj.containsKey("nodeId")){
			sqlParam = " and fjdId = "+obj.getString("nodeId");
		} 
		
		//查询教学组织机构
		List<TbJxzzjg> jxzzList = baseDao.queryEntityList("TbJxzzjg", sqlParam);
		List bjList = new ArrayList();
		if(obj.containsKey("rxnjId")){
			String sql = "select id as \"id\",bm as \"mc\",'BJ' as \"cclx\", '1' as \"sfyzjd\", fjd_id as \"fjdId\" "
					+ "from tb_xxzy_bjxxb where nj_id="+obj.getString("rxnjId");
			bjList = baseDao.querySqlList(sql);
		}else{
			//获取所有班级信息
			bjList = baseDao.queryEntityList("TbXxzyBjZbjZzjg", sqlParam);
		}
		
		//定义maps
		Map<Long,MenuNode> maps = new HashMap<Long, MenuNode>();
		//定义Tree对象
		MenuTree tree = new MenuTree();
		//定义顶级节点
		MenuNode menuNode = new MenuNode(new Long(0), new Long(-1), true, "", false);
		// 顶级节点set树中
		tree.setRoot(menuNode);
		//判断jxzzList是否为空
		if(jxzzList != null && jxzzList.size()>0){
			//获取教学组织机构list转Map
			changeMenuMap(jxzzList, maps);
			//将班级List转化Map
			changeMenuMap(bjList, maps);
		}
		//组装树
		tree = changeMenu(maps);
		
		return Struts2Utils.bean2json(tree.root);
	}
	@Override
	public String getBzdmByTreemenu(String params) throws Exception{
		JSONObject obj = JSONObject.fromObject(params);
		List list = null;
		Map map = new HashMap<Long,Object>();
		MenuNode node = new MenuNode(0l, -1l, "", "");
		map.put(0L, node);
		StringBuffer sqlparam = new StringBuffer();
//		if(obj.getInt("fjdId") != 0)
//		sqlparam.append(" fjdId =0").append(" and ");//.append(obj.getString("fjdId")).append(" and ");
		sqlparam.append(" sfky = 1");
		if(obj.containsKey("entityName")){
			list =baseDao.queryEntityList(obj.getString("entityName"), sqlparam.toString());
		}
		changeMenuMap(list, map);
		MenuTree menu = changeMenu(map);
		return Struts2Utils.objects2Json(menu.root);
	}

	@Override
	public String getStuYjbySj(TbXjdaXjxx xs) throws Throwable{
		String yjbysj="";
		//入学年级
		Long rxnjId = xs.getRxnjId();
		// 学制
		Long xzId = xs.getXzId();
		//招生季度
		Long zsjdId = xs.getZsjdId();
		if(rxnjId!=null && xzId!=null && zsjdId!=null){
			//入学年级代码
			TcXxbzdmjg dmjg = (TcXxbzdmjg) baseDao
					.queryById(rxnjId, TcXxbzdmjg.class.getName());
			String bm =dmjg.getDm();
			int rxnj = Integer.parseInt(bm);
			//学制代码
			int xzNx = Integer.parseInt(this.getXzNx(xzId));
			int yjbyn = rxnj+xzNx;
			String zsjdm = baseDao.queryBmById(zsjdId, "XXDM-ZSJD");
			if("1".equals(zsjdm)){
				yjbysj = yjbyn+"-06-30";
			}else if("2".equals(zsjdm)){
				yjbysj = yjbyn+"-01-20";
			}
		}
		return yjbysj;
	}

	@Override
	public String getXzNx(Long xzId) throws Throwable {
		//学制代码
		TcXz xz = (TcXz)baseDao.queryById(xzId, TcXz.class.getName());
		if(xz!=null){
			String zxz = xz.getZxz();
			String[] zxzArray = zxz.split(",");
			int pxxh = Integer.parseInt(xz.getPxh())-1;
			return zxzArray[pxxh];
		}else{
			return "0";
		}
		
	}
	@Override
	public List querySqlList(String sql) {
		return this.baseDao.querySqlList(sql);
	}
	@Override
	public Object queryById(Long id, String entityName) throws Throwable {
		// TODO Auto-generated method stub
		return this.baseDao.queryById(id, entityName);
	}
	
	@Override
	public String getRxnjList(String params){
		return Struts2Utils.list2json(baseDao.queryBM("TcXxbzdmjg", "XXDM-RXNJ"));
	}

}
