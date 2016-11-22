package com.jhkj.mosdc.sc.service.impl;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.job.BzxMailSenderService;
import com.jhkj.mosdc.sc.job.impl.ExportBzxXsServiceImpl.XsmdExport;
import com.jhkj.mosdc.sc.service.BzxService;

public class BzxServiceImpl extends ScServiceImpl implements BzxService {
	private static String ZCZX = "正常";
	private static String LXSX = "离校实习";
	private BzxMailSenderService mailSender;
	
	public void setMailSender(BzxMailSenderService mailSender) {
		this.mailSender = mailSender;
	}
	@Override
	public String queryGridContent(String params) {
		String bzxSql ="SELECT YX,COUNT(*) AS RS FROM TB_JXZZJG ZZJG INNER JOIN TB_YKT_BZX BZX ON BZX.YX_ID = ZZJG.ID "+ 
				"INNER JOIN TB_XJDA_XJXX XJXX ON BZX.XH = XJXX.XH AND XJXX.XJZT_ID = 1000000000000101 AND (XJXX.XSZT_ID = 1 OR XJXX.XSZT_ID = 3)"+ 
				"GROUP BY YX ORDER BY YX";
		Calendar c = Calendar.getInstance();
		int month = c.getTime().getMonth();
		String xsSql ="SELECT  JX.ID,JX.MC AS YX,XSZT.MC AS XSZT,COUNT(*) AS RS FROM TB_XJDA_XJXX XJXX "+
				 "LEFT JOIN TB_JXZZJG JX ON JX.DM = XJXX.YX_ID AND JX.CCLX='YX' "+
				 "LEFT JOIN DM_ZXBZ.TB_ZXBZ_XSZT XSZT ON XSZT.DM = XJXX.XSZT_ID " +
				"WHERE XJZT_ID = 1000000000000101 AND (XSZT_ID = 1 OR XSZT_ID = 3) and substr(xjxx.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy')" +
				"GROUP BY JX.ID ,JX.MC,XSZT.MC ORDER BY YX";
		
		String sxxssSql ="select yx.mc ,count(*) zs from tb_sc_leaveschool leave left join tb_xjda_xjxx xj on xj.xh = leave.code "+
				" left join tb_jxzzjg yx on yx.id = xj.yx_id where to_char(leave.endtime,'yyyy-MM-dd')>to_char(sysdate,'yyyy-MM-dd') group by yx.mc";// 各院系实习学生数
		List<Map> sxxxs = baseDao.querySqlList(sxxssSql);
		List<Map> xss = baseDao.querySqlList(xsSql);
		List<Map> bzxxs = baseDao.querySqlList(bzxSql);
		
		Map<String,Integer> bzxMap = new HashMap<String,Integer>();
		for(Map temp : bzxxs){
			String yx = temp.get("YX").toString();
			int count = temp.get("RS")==null ? 0 : Integer.parseInt(temp.get("RS").toString());
			bzxMap.put(yx, count);
		}
		Map<String,Integer> sxMap = new HashMap<String,Integer>();
		for(Map temp : sxxxs){
			String yx = temp.get("MC").toString();
			int count = temp.get("ZS")==null ? 0 : Integer.parseInt(temp.get("ZS").toString());
			sxMap.put(yx, count);
		}
		
		Map<String,Map<String,String>> xsMap = trans(xss);
		for(Map temp : xss){
			String yx = temp.get("YX").toString();// 院系
			String yxid = temp.get("ID").toString();
			String bzxs = bzxMap.get(yx)==null?"0":bzxMap.get(yx).toString();// 不在校人数
			int zjxs = Integer.parseInt(temp.get("RS").toString());
			Map<String,String> xszt = xsMap.get(yx);
			
			String zczx = xszt.get(ZCZX)==null ? "0":xszt.get(ZCZX);// 正常在校人数
			String lxsx = xszt.get(LXSX)==null ? "0":xszt.get(LXSX);// 离校实习人数
			lxsx = sxMap.get(yx)==null?"0":sxMap.get(yx).toString();
			temp.put("LXSX", lxsx);
			temp.put("ZCZX",zczx);
			temp.put("ZJXS",temp.get("RS"));
			temp.put("BZX",bzxs);
			// 计算占比
			DecimalFormat df = new DecimalFormat("0.00");
			float num= Integer.parseInt(zczx)==0?0:(float)Integer.parseInt(bzxs)/Integer.parseInt(zczx)*100;
			temp.put("ZB", df.format(num));
			
			// 获取消息通知状态
			String xxztSql ="SELECT * FROM TB_YKT_BZX_MAIL_FJLB MAIL WHERE JXZZJGID="+yxid;
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
		backval.put("data", xss);
		backval.put("success", true);
		backval.put("count", xss.size());
		return Struts2Utils.map2json(backval);
	}
	/**
	 * 转换数据。
	 * @param values
	 * @return
	 */
	private Map trans(List<Map> values){
		Map<String,Map<String,String>> xsMap = new HashMap<String,Map<String,String>>();
		for(Map temp : values){
			String key = temp.get("YX").toString();
			String xszt = temp.get("XSZT").toString();
			String count = temp.get("RS").toString();
			if(!xsMap.containsKey(key)){
				Map<String,String> xsztMap = new HashMap<String,String>();
				xsztMap.put(xszt, count);
				xsMap.put(key, xsztMap);
			}else{
				Map<String,String> xsztMap = xsMap.get(key);
				xsztMap.put(xszt, count);
			}
		}
		return xsMap ;
	}
	
	@Override
	public String queryYxgly(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String yxid = json.get("yxid").toString();
		String sql ="SELECT YX.ID AS YXID,JZG.DZXX,JZG.LXDH,YX.MC AS YX,JZG.ZGH,JZG.XM FROM TB_YXGLY GLY LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH "+
							" LEFT JOIN TB_JXZZJG YX ON YX.ID = GLY.YX_ID WHERE GLY.YX_ID ="+yxid;
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
		boolean flag = mailSender.sendMail(yxid);
		return "{success:"+flag+"}";
	}
	
	@Override
	public String queryYxglyJszt(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String yxid = json.get("yxid").toString();
		String sql ="SELECT YX.ID AS YXID,JZG.DZXX,JZG.LXDH,YX.MC AS YX,JZG.ZGH,JZG.XM,FJLB.SFZT AS JSZT,FJLB.DCRQ AS JSRQ FROM TB_YXGLY GLY" +
				" LEFT JOIN TB_JZGXX JZG ON JZG.ZGH = GLY.ZGH" +
				" LEFT JOIN TB_YKT_BZX_MAIL_FJLB FJLB ON FJLB.JXZZJGID = GLY.YX_ID "+
				" LEFT JOIN TB_JXZZJG YX ON YX.ID = GLY.YX_ID WHERE GLY.YX_ID ="+yxid;
		List<Map> results = baseDao.querySqlList(sql);
		Map backval = new HashMap();
		backval.put("count", results.size());
		backval.put("data", results);
		backval.put("success", true);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getBzxMd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		Map paramsMap = Utils4Service.packageParams(params);
		String yxid = json.get("yxid").toString();
		String tcSql = " AND 1=1 ";
		if(json.containsKey("xm")) {
			String name = json.getString("xm");
			tcSql =" AND XJ.XM like '"+name+"%' ";
		}
		String bzxMdSql ="SELECT T.* FROM (SELECT BZX.XH,BZX.XM," +
				"TO_CHAR(CAST(BZX.SCXFSJ AS DATE),'YYYY-MM-DD HH:MM:SS') AS SCXFSJ," +
				"TO_CHAR(CAST(BZX.SCMJSJ AS DATE),'YYYY-MM-DD HH:MM:SS') AS SCMJSJ," +
				" BZX.TJSJ,BZX.YX,BZX.ZY,ZSXX.ZSDD,XJ.LXDH FROM TB_YKT_BZX BZX" +
				" LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = BZX.XH  " +
				" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
				"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = XJ.XH "+
				"WHERE BZX.YX_ID = "+yxid+tcSql+" ORDER BY ZY)T";
		
		Map result = baseDao.queryTableContentBySQL(bzxMdSql,paramsMap);
		
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
	public HSSFWorkbook getBzxMdExport(String params) {
		JSONObject json = JSONObject.fromObject(params);
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
				String bzxMdSql ="SELECT bzx.*,zsxx.zsdd FROM TB_YKT_BZX bzx " +
						" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
						"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = bzx.xh "+
						"WHERE bzx.YX_ID = "+yx_id+" ORDER BY bzx.ZY";
				List<Map> xsList = baseDao.querySqlList(bzxMdSql);
				List<Object> values = new ArrayList<Object>();
				String yx ="";
				for(Map xsMap : xsList){
					String xh = xsMap.get("XH").toString();
					String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
					yx = xsMap.get("YX")==null?"":xsMap.get("YX").toString();
					String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
					String scxfsj = xsMap.get("SCXFSJ")==null?"":xsMap.get("SCXFSJ").toString();
					String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
					String zsdd =  xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
					String tjsj = xsMap.get("TJSJ").toString();
					XsmdExport export = new XsmdExport();
					export.setSccrsj(sccrsj);
					export.setScxfsj(scxfsj);
					export.setXh(xh);
					export.setXm(xm);
					export.setYx(yx);
					export.setZy(zy);
					export.setTbsj(tjsj);
					export.setZsdd(zsdd);
					values.add(export);
				}
				String[] strs = {"学号","姓名","院系","专业","宿舍","上次消费时间","上次门禁出入时间","统计时间"};
				if(!"".equals(yx)){
					HSSFSheet sheet = workbook.createSheet(yx);
					
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
			String bzxMdSql ="SELECT bzx.*,zsxx.zsdd FROM TB_YKT_BZX bzx " +
					" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
					"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = bzx.xh "+
					"WHERE bzx.YX_ID = "+yx_id+" ORDER BY bzx.ZY";
			List<Map> xsList = baseDao.querySqlList(bzxMdSql);
			List<Object> values = new ArrayList<Object>();
			String yx = "";
			for(Map xsMap : xsList){
				String xh = xsMap.get("XH").toString();
				String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
				yx = xsMap.get("YX")==null?"":xsMap.get("YX").toString();
				String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
				String scxfsj = xsMap.get("SCXFSJ")==null?"":xsMap.get("SCXFSJ").toString();
				String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
				String zsdd =  xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
				String tjsj = xsMap.get("TJSJ").toString();
				XsmdExport export = new XsmdExport();
				export.setSccrsj(sccrsj);
				export.setScxfsj(scxfsj);
				export.setXh(xh);
				export.setXm(xm);
				export.setYx(yx);
				export.setZy(zy);
				export.setTbsj(tjsj);
				export.setZsdd(zsdd);
				values.add(export);
			}
			String[] strs = {"学号","姓名","院系","专业","宿舍","上次消费时间","上次门禁出入时间","统计时间"};
			HSSFWorkbook workbook = ExportUtil.getHSSFWorkbook(strs, yx, values);
			return workbook;
		}
	}
	public class XsmdExport{
		private String xh;
		private String xm;
		private String yx;
		private String zy;
		private String zsdd;
		private String scxfsj;// 上次消费时间
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
		public String getScxfsj() {
			return scxfsj;
		}
		public void setScxfsj(String scxfsj) {
			this.scxfsj = scxfsj;
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
}
