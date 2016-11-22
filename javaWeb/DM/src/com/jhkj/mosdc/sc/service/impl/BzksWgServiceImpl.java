package com.jhkj.mosdc.sc.service.impl;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.newoutput.util.DateUtils;
import com.jhkj.mosdc.sc.job.BzxMailSenderService;
import com.jhkj.mosdc.sc.service.BzksWgService;

public class BzksWgServiceImpl extends BzxServiceImpl implements BzksWgService {
	private BzxMailSenderService mailSender;
	
	public void setMailSender(BzxMailSenderService mailSender) {
		this.mailSender = mailSender;
	}
	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		
		String rq = "";
		if(!json.containsKey("RQ")){
			// 获取昨天的日期
			Date date = DateUtils.getNextDay(new Date(),-1);
			rq = DateUtils.getDateFormat(date, "yyyy-MM-dd");
		}else{
			
			rq = json.getString("RQ");
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		long nd = 1000*24*60*60;//一天的毫秒数
		//获得两个时间的毫秒时间差异
		long diff=0l;
		try {
			diff = new Date().getTime() - sd.parse(rq).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day = diff/nd;//计算差多少天
		String wgsql ="SELECT ZZJG.MC AS YX,ZZJG.ID,COUNT(*) as RS FROM TB_YKT_TEMP_WG WGMD " +
				"LEFT JOIN TB_JXZZJG ZZJG ON TO_CHAR(ZZJG.ID) = WGMD.YX_ID " +
				"WHERE ZZJG.MC IS NOT NULL AND WGMD.SKSJ LIKE '"+rq+"%' GROUP BY ZZJG.MC,ZZJG.ID";
		Calendar c = Calendar.getInstance();
		int month = c.getTime().getMonth();
		String xsSql ="SELECT  JX.ID,JX.MC AS YX,XSZT.MC AS XSZT,COUNT(*) AS RS FROM TB_XJDA_XJXX XJXX "+
				 "LEFT JOIN TB_JXZZJG JX ON JX.DM = XJXX.YX_ID AND JX.CCLX='YX' "+
				 "LEFT JOIN DM_ZXBZ.TB_ZXBZ_XSZT XSZT ON XSZT.DM = XJXX.XSZT_ID " +
				"WHERE XJZT_ID = 1000000000000101 AND XSZT_ID = 1 and substr(xjxx.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy')" + 
				"GROUP BY JX.ID ,JX.MC,XSZT.MC ORDER BY YX";
		
		List<Map> yxxss = baseDao.querySqlList(xsSql);// 院系正常在校学生数
		
		List<Map> bzxxs = baseDao.querySqlList(wgsql);
		Map<String,Integer> wgMap = new HashMap<String,Integer>();
		for(Map yxrsTemp : bzxxs){
			String yx  = yxrsTemp.get("YX").toString();
			int wgrs = Integer.parseInt(yxrsTemp.get("RS").toString());
			wgMap.put(yx, wgrs);
		}
		
		for(Map temp : yxxss){
			String yx = temp.get("YX").toString();// 院系
			String yxid = temp.get("ID").toString();
			int zjxs = Integer.parseInt(temp.get("RS").toString());
			int wgxs = wgMap.containsKey(yx)?wgMap.get(yx):0;
			String tempstr = rq;
			if(day==1l){
				tempstr+="(昨天)";
			}else if(day==0){
				tempstr+="(今天)";
			}
			temp.put("RQ",tempstr);
			temp.put("ZCZX",zjxs);
			temp.put("WGRS",wgxs);
			// 计算占比
			DecimalFormat df = new DecimalFormat("0.00");
			float num= zjxs==0?0:(float)wgxs/zjxs*100;
			temp.put("ZB", df.format(num));
			
			// 获取消息通知状态
			String xxztSql ="SELECT * FROM TB_YKT_TEMP_WG_MAIL MAIL WHERE JXZZJGID="+yxid +" AND DYRQ='"+rq+"'";
			List<Map> xxzt = baseDao.querySqlList(xxztSql);
			if(xxzt.size()==0){
				temp.put("XXTSZT", "0");
			}else{
				Map result = xxzt.get(0);
				String ztStr = result.get("SFZT").toString();
				temp.put("XXTSZT", ztStr);
			}
			
		}
		
		Map backval = new HashMap();
		backval.put("data", yxxss);
		backval.put("success", true);
		backval.put("count", yxxss.size());
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String queryWgMd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		Map paramsMap = Utils4Service.packageParams(params);
		String yxid = json.get("yxid").toString();
		String rq = json.get("RQ").toString();
		String tcSql = " AND 1=1 ";
		if(json.containsKey("xm")) {
			String name = json.getString("xm");
			tcSql =" AND XJ.XM like '"+name+"%' ";
		}
		String wgMdSql ="SELECT T.* FROM (SELECT WGMD.XH,ZZJG.MC YX,ZZJG.ID YX_ID,XJ.LXDH,ZY.MC AS ZY,ZSXX.ZSDD,XJ.XM,WGMD.SKSJ SCMJSJ,WGMD.TJSJ "+ 
				" FROM TB_YKT_TEMP_WG WGMD LEFT JOIN TB_JXZZJG ZZJG ON TO_CHAR(ZZJG.ID) = WGMD.YX_ID  "+
				" LEFT JOIN TB_JXZZJG ZY ON ZY.ID = WGMD.ZY_ID AND ZY.CCLX='ZY' "+
				" LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = WGMD.XH  "+
				" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
				"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = XJ.XH "+
				" WHERE ZZJG.MC IS NOT NULL AND WGMD.SKSJ LIKE '"+rq+"%' AND ZZJG.ID = "+yxid+tcSql+" ORDER BY YX,ZY,XH DESC)T";
		
		Map result = baseDao.queryTableContentBySQL(wgMdSql,paramsMap);
		
		List<Map> queryList = (List)result.get("queryList");
		for(Map map : queryList){
			String tjsj = map.get("TJSJ").toString();
			map.put("tjsj",tjsj);
		}
		Map backval = new HashMap();
		backval.put("count", result.get("count"));
		backval.put("data", queryList);
		backval.put("success", true);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public HSSFWorkbook getWgMdExport(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String rq = json.get("RQ").toString();
		if(json.containsKey("dcqb")){
			String yxSql ="SELECT * FROM TB_JXZZJG T WHERE T.CCLX='YX'";
			List<Map> yxList = baseDao.querySqlList(yxSql);
			
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFCellStyle headerStyle = ExportUtil.createContentStyle(workbook);
			HSSFCellStyle contentStyle = ExportUtil.createContentStyle(workbook);
			contentStyle.setWrapText(true);// 自动换行
			
			for(Map temp : yxList){
				String name = temp.get("MC").toString();
				String yx_id = temp.get("ID").toString();
				String wgMdSql ="SELECT T.* FROM (SELECT WGMD.XH,ZZJG.MC YX,ZZJG.ID YX_ID,XJ.LXDH,ZY.MC AS ZY,ZSXX.ZSDD,XJ.XM,WGMD.SKSJ SCMJSJ,WGMD.TJSJ "+ 
						" FROM TB_YKT_TEMP_WG WGMD LEFT JOIN TB_JXZZJG ZZJG ON TO_CHAR(ZZJG.ID) = WGMD.YX_ID  "+
						" LEFT JOIN TB_JXZZJG ZY ON ZY.ID = WGMD.ZY_ID AND ZY.CCLX='ZY' "+
						" LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = WGMD.XH  "+
						" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
						"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = XJ.XH "+
						" WHERE ZZJG.MC IS NOT NULL AND WGMD.SKSJ LIKE '"+rq+"%' AND ZZJG.ID = "+yx_id+" ORDER BY YX,ZY,XH DESC)T";
				List<Map> xsList = baseDao.querySqlList(wgMdSql);
				List<Object> values = new ArrayList<Object>();
				for(Map xsMap : xsList){
					String xh = xsMap.get("XH").toString();
					String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
					String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
					String scxfsj = xsMap.get("SCXFSJ")==null?"":xsMap.get("SCXFSJ").toString();
					String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
					String zsdd =  xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
					String tjsj = xsMap.get("TJSJ").toString();
					XsmdExport export = new XsmdExport();
					export.setSccrsj(sccrsj);
					export.setXh(xh);
					export.setXm(xm);
					export.setYx(name);
					export.setZy(zy);
					export.setTbsj(tjsj);
					export.setZsdd(zsdd);
					values.add(export);
				}
				String[] strs = {"学号","姓名","院系","专业","宿舍","门禁刷卡时间","统计时间"};
				if(!"".equals(name)){
					HSSFSheet sheet = workbook.createSheet(name);
					
					// 创建表标题
					HSSFRow header = sheet.createRow(0);
					for (int i = 0; i < strs.length; i++) {
						HSSFCell cell = header.createCell(i+1);
						cell.setCellStyle(headerStyle);
						cell.setCellValue(strs[i]);
					}
					sheet.setColumnWidth(0, 200);
					for(int i=1;i<=strs.length;i++){
						sheet.setColumnWidth(i, 5600);
					}

					int lineNumber = 1;
			    	for(Object obj : values){
			    		HSSFRow contentRow = sheet.createRow(lineNumber++);
			    		contentRow.setHeight((short) 300);   // 设置行高
			    		/**/
			    		Field[] fields = obj.getClass().getDeclaredFields();
			    		for (int i = 0; i < fields.length; i++) {
			    			Field field = fields[i];
			    			HSSFCell cell = contentRow.createCell(i+1);
			    			cell.setCellStyle(contentStyle);
			    			// 反射调用返回该字段值的字符串形式。
			    			String strValue = Utils4Service.getValueByField(obj, field);
			    			cell.setCellValue(strValue);
			    		}
			    	}
				}
		    	
			}
			return workbook;
		}else{
			String yx_id = json.getString("yxid");
			String yxSql ="SELECT * FROM TB_JXZZJG T WHERE T.CCLX='YX' AND ID = "+yx_id;
			List<Map> yxList = baseDao.querySqlList(yxSql);
			String yxmc = yxList.get(0).get("MC").toString();
			String wgMdSql ="SELECT T.* FROM (SELECT WGMD.XH,ZZJG.MC YX,ZZJG.ID YX_ID,XJ.LXDH,ZY.MC AS ZY,ZSXX.ZSDD,XJ.XM,WGMD.SKSJ SCMJSJ,WGMD.TJSJ "+ 
					" FROM TB_YKT_TEMP_WG WGMD LEFT JOIN TB_JXZZJG ZZJG ON TO_CHAR(ZZJG.ID) = WGMD.YX_ID  "+
					" LEFT JOIN TB_JXZZJG ZY ON ZY.ID = WGMD.ZY_ID AND ZY.CCLX='ZY' "+
					" LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = WGMD.XH  "+
					" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
					"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = XJ.XH "+
					" WHERE ZZJG.MC IS NOT NULL AND WGMD.SKSJ LIKE '"+rq+"%' AND ZZJG.ID = "+yx_id+" ORDER BY YX,ZY,XH DESC)T";
			List<Map> xsList = baseDao.querySqlList(wgMdSql);
			List<Object> values = new ArrayList<Object>();
			for(Map xsMap : xsList){
				String xh = xsMap.get("XH").toString();
				String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
				String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
				String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
				String zsdd =  xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
				String tjsj = xsMap.get("TJSJ").toString();
				XsmdExport export = new XsmdExport();
				export.setSccrsj(sccrsj);
				export.setXh(xh);
				export.setXm(xm);
				export.setYx(yxmc);
				export.setZy(zy);
				export.setTbsj(tjsj);
				export.setZsdd(zsdd);
				values.add(export);
			}
			String[] strs = {"学号","姓名","院系","专业","宿舍","门禁出入时间","统计时间"};
			HSSFWorkbook workbook = ExportUtil.getHSSFWorkbook(strs, yxmc, values);
			return workbook;
		}
	}
	public class XsmdExport{
		private String xh;
		private String xm;
		private String yx;
		private String zy;
		private String zsdd;
		private String sccrsj;// 上次出入时间
		private String tbsj;// 统计时间
		
		public String getZsdd() {
			return zsdd;
		}
		public void setZsdd(String zsdd) {
			this.zsdd = zsdd;
		}
		public String getXh() {
			return xh;
		}
		public void setXh(String xh) {
			this.xh = xh;
		}
		public String getXm() {
			return xm;
		}
		public void setXm(String xm) {
			this.xm = xm;
		}
		public String getYx() {
			return yx;
		}
		public void setYx(String yx) {
			this.yx = yx;
		}
		public String getZy() {
			return zy;
		}
		public void setZy(String zy) {
			this.zy = zy;
		}
		public String getSccrsj() {
			return sccrsj;
		}
		public void setSccrsj(String sccrsj) {
			this.sccrsj = sccrsj;
		}
		public String getTbsj() {
			return tbsj;
		}
		public void setTbsj(String tbsj) {
			this.tbsj = tbsj;
		}
		
	}
	
	@Override
	public String queryYxglyJszt(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String yxid = json.get("yxid").toString();
		String sql ="SELECT T.* FROM (SELECT YX.ID AS YXID,JZG.DZXX,JZG.LXDH,YX.MC AS YX,JZG.ZGH,JZG.XM,"+
			" CASE WHEN FJLB.SFZT IS NULL THEN '0' ELSE FJLB.SFZT END AS JSZT,FJLB.DYRQ AS JSRQ FROM TB_YXGLY GLY "+ 
			" 	 LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH "+
			" 	 LEFT JOIN tb_ykt_temp_wg_mail FJLB ON FJLB.JXZZJGID = GLY.YX_ID  "+
			" 	 LEFT JOIN TB_JXZZJG YX ON YX.ID = GLY.YX_ID  WHERE GLY.YX_ID ="+yxid +" ORDER BY JSRQ DESC)T";
		List<Map> results = baseDao.querySqlList(sql);
		Map backval = new HashMap();
		backval.put("count", results.size());
		backval.put("data", results);
		backval.put("success", true);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String sendMail(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String yxid = json.getString("yxid");
		String rq = json.getString("RQ");
		
		boolean flag =mailSender.sendMail4WG(yxid,rq);;
		return "{success:"+flag+"}";
	}
}
