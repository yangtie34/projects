package com.jhkj.mosdc.sc.job.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.statics.StorePath;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.newoutput.util.DateUtils;
import com.jhkj.mosdc.sc.job.ExportBzxXsService;
import com.jhkj.mosdc.sc.job.JobStatusState;

public class ExportBzxXsServiceImpl extends BaseServiceImpl implements
		ExportBzxXsService {
	private static final String SHEETNAME ="疑似不再校学生名单(3天内无消费，无门禁数据)";
	private static final String SHEETNAME4WG = "晚寝晚归学生名单";
	private static final String SHEETNAME4WZS = "昨天疑似未住宿学生名单";
	@Override
	public void export4Bj() {
		List<Map> results = new ArrayList<Map>();
		
	} 
	@Override
	public void export4Yx() {
		JobStatusState.EXPORT_OVER_YX=0;// 设置正在导出
		String today = DateUtils.getCurrentDate_DayString();
		if(JobStatusState.THREE_DAY_NOT_IN_SCHOOL==1){
			String yxSql ="SELECT * FROM TB_JXZZJG T WHERE T.CCLX='YX'";
			List<Map> yxList = baseDao.querySqlList(yxSql);
			String delete = "DELETE FROM TB_YKT_BZX_MAIL_FJLB ";
			baseDao.deleteBySql(delete);
			Map<String,HSSFWorkbook> wbs = new HashMap<String,HSSFWorkbook>();
			for(Map temp : yxList){
				String name = temp.get("MC").toString();
				String yx_id = temp.get("ID").toString();
				String bzxMdSql ="SELECT bzx.*,zsxx.zsdd FROM TB_YKT_BZX bzx " +
						" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
						"WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = bzx.xh "+
						"WHERE bzx.YX_ID = "+yx_id+" ORDER BY bzx.ZY";
				List<Map> xsList = baseDao.querySqlList(bzxMdSql);
				List<Object> values = new ArrayList<Object>();
				for(Map xsMap : xsList){
					String xh = xsMap.get("XH").toString();
					String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
					String yx = xsMap.get("YX")==null?"":xsMap.get("YX").toString();
					String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
					String scxfsj = xsMap.get("SCXFSJ")==null?"":xsMap.get("SCXFSJ").toString();
					String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
					String zsdd = xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
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
				HSSFWorkbook workbook = getHSSFWorkbook(strs, SHEETNAME, values);
				wbs.put(yx_id+"_"+name, workbook);
			}
			Iterator<String> it = wbs.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String yxid = key.split("_")[0],yx = key.split("_")[1];
				HSSFWorkbook workbook = wbs.get(key);
				String filePath =StorePath.getBasePath()+yx+"-"+SHEETNAME+today+".xls";
				saveFile(workbook,filePath );
				
				String insert = "INSERT INTO TB_YKT_BZX_MAIL_FJLB(JXZZJGID,FJML,SFZT,DCRQ) VALUES('"+yxid+"','"+filePath+"',0,'"+today+"')";
				
				baseDao.insert(insert);
			}
			JobStatusState.EXPORT_OVER_YX=1;// 设置导出成功
		}
	}
	@Override
	public void export4Zy() {
		
	}
	
	@Override
	public void saveExport4Wg() {
		JobStatusState.EXPORT_YESTERDAY_WG=0;// 设置正在导出
		// 获取昨天的日期
		Date date = DateUtils.getNextDay(new Date(),-1);
		String yesterday = DateUtils.getDateFormat(date, "yyyy-MM-dd");
//		yesterday ="2014-12-18";
		if(JobStatusState.YESTERDAY_WG==1){
			String yxSql ="SELECT * FROM TB_JXZZJG T WHERE T.CCLX='YX'";
			List<Map> yxList = baseDao.querySqlList(yxSql);
			
			Map<String,HSSFWorkbook> wbs = new HashMap<String,HSSFWorkbook>();
			for(Map temp : yxList){
				
				String name = temp.get("MC").toString();
				String yx_id = temp.get("ID").toString();
				String delete = "DELETE FROM TB_YKT_TEMP_WG_MAIL where jxzzjgid="+yx_id+" and dyrq='"+yesterday+"'";
				baseDao.deleteBySql(delete);
				String wgMdSql ="SELECT T.* FROM (SELECT WGMD.XH,ZZJG.MC YX,ZZJG.ID YX_ID,XJ.LXDH,ZY.MC AS ZY,ZSXX.ZSDD,XJ.XM,WGMD.SKSJ SCMJSJ,WGMD.TJSJ "+ 
						" FROM TB_YKT_TEMP_WG WGMD LEFT JOIN TB_JXZZJG ZZJG ON TO_CHAR(ZZJG.ID) = WGMD.YX_ID  "+
						" LEFT JOIN TB_JXZZJG ZY ON ZY.ID = WGMD.ZY_ID AND ZY.CCLX='ZY' "+
						" LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = WGMD.XH  "+
						" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
						" WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = XJ.XH "+
						" WHERE ZZJG.MC IS NOT NULL AND WGMD.SKSJ LIKE '"+yesterday+"%' AND ZZJG.ID = "+yx_id+" ORDER BY YX,ZY,XH DESC)T";
				List<Map> xsList = baseDao.querySqlList(wgMdSql);
				List<Object> values = new ArrayList<Object>();
				for(Map xsMap : xsList){
					String xh = xsMap.get("XH").toString();
					String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
					String yx = xsMap.get("YX")==null?"":xsMap.get("YX").toString();
					String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
					String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
					String zsdd =   xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
					String tjsj = xsMap.get("TJSJ").toString();
					WgXsmd export = new WgXsmd();
					export.setSccrsj(sccrsj);
					export.setXh(xh);
					export.setXm(xm);
					export.setYx(yx);
					export.setZy(zy);
					export.setTbsj(tjsj);
					export.setZsdd(zsdd);
					values.add(export);
				}
				String[] strs = {"学号","姓名","院系","专业","宿舍","最近门禁出入时间","统计时间"};
				HSSFWorkbook workbook = getHSSFWorkbook(strs, SHEETNAME4WG, values);
				wbs.put(yx_id+"_"+name, workbook);
			}
			
			Iterator<String> it = wbs.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String yxid = key.split("_")[0],yx = key.split("_")[1];
				HSSFWorkbook workbook = wbs.get(key);
				String filePath =StorePath.getBasePath()+yx+"-"+SHEETNAME4WG+yesterday+".xls";
				saveFile(workbook,filePath );
				String insert = "INSERT INTO TB_YKT_TEMP_WG_MAIL(JXZZJGID,FJML,SFZT,DYRQ) VALUES('"+yxid+"','"+filePath+"',0,'"+yesterday+"')";
				baseDao.insert(insert);
			}
			JobStatusState.EXPORT_YESTERDAY_WG=1;// 设置导出成功
		}
		
	}
	@Override
	public void saveExport4Wzs() {
		JobStatusState.EXPORT_YESTERDAY_WZS=0;// 设置正在导出
		// 获取昨天的日期
		Date date = DateUtils.getNextDay(new Date(),-1);
		String yesterday = DateUtils.getDateFormat(date, "yyyy-MM-dd");
//		yesterday ="2014-12-18";
		System.out.println("开始导出 未住宿 名单");
		if(JobStatusState.YESTERDAY_WZS==1){
			String yxSql ="SELECT * FROM TB_JXZZJG T WHERE T.CCLX='YX'";
			List<Map> yxList = baseDao.querySqlList(yxSql);
			
			Map<String,HSSFWorkbook> wbs = new HashMap<String,HSSFWorkbook>();
			for(Map temp : yxList){
				
				String name = temp.get("MC").toString();
				String yx_id = temp.get("ID").toString();
				String delete = "DELETE FROM TB_YKT_TEMP_WZS_MAIL where jxzzjgid="+yx_id+" and dyrq='"+yesterday+"'";
				baseDao.deleteBySql(delete);
				
				String wgMdSql ="SELECT T.* FROM (SELECT WGMD.XH,ZZJG.MC YX,ZZJG.ID YX_ID,XJ.LXDH,ZY.MC AS ZY,ZSXX.ZSDD,XJ.XM,WGMD.XFSJ SCXFSJ,WGMD.SKSJ SCMJSJ,WGMD.TJSJ "+ 
						" FROM TB_YKT_TEMP_Wzs WGMD LEFT JOIN TB_JXZZJG ZZJG ON TO_CHAR(ZZJG.ID) = WGMD.YX_ID  "+
						" LEFT JOIN TB_JXZZJG ZY ON ZY.ID = WGMD.ZY_ID AND ZY.CCLX='ZY' "+
						" LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = WGMD.XH  "+
						" LEFT JOIN (SELECT ZY.XS_ID,LY2.MC||'/'||LY.MC||'/'||FJ.MC||'/'||CW.MC||'号床' ZSDD FROM TB_DORM_ZY ZY ,TB_DORM_CW CW ,TB_DORM_CCJG FJ,TB_DORM_CCJG LY,TB_DORM_CCJG LY2 "+
						" WHERE ZY.CW_ID = CW.ID AND CW.FJ_ID = FJ.ID AND FJ.FJD_ID = LY.ID AND LY.FJD_ID = LY2.ID) ZSXX  ON ZSXX.XS_ID = XJ.XH "+
						" WHERE ZZJG.MC IS NOT NULL AND WGMD.XFSJ LIKE '"+yesterday+"%' AND ZZJG.ID = "+yx_id+" ORDER BY YX,ZY,XH DESC)T";
				
				List<Map> xsList = baseDao.querySqlList(wgMdSql);
				List<Object> values = new ArrayList<Object>();
				for(Map xsMap : xsList){
					String xh = xsMap.get("XH").toString();
					String xm = xsMap.get("XM")==null?"":xsMap.get("XM").toString();
					String yx = xsMap.get("YX")==null?"":xsMap.get("YX").toString();
					String zy = xsMap.get("ZY")==null?"":xsMap.get("ZY").toString();
					String scxfsj = MapUtils.getString(xsMap, "SCXFSJ");
					String sccrsj =  xsMap.get("SCMJSJ")==null?"":xsMap.get("SCMJSJ").toString();
					String zsdd =  xsMap.get("ZSDD")==null?"":xsMap.get("ZSDD").toString();
					String tjsj = xsMap.get("TJSJ").toString();
					XsmdExport export = new XsmdExport();
					export.setSccrsj(sccrsj);
					export.setXh(xh);
					export.setScxfsj(scxfsj);
					export.setXm(xm);
					export.setYx(yx);
					export.setZy(zy);
					export.setTbsj(tjsj);
					export.setZsdd(zsdd);
					values.add(export);
				}
				String[] strs = {"学号","姓名","院系","专业","宿舍","当日最后一次消费","之前最后一次出入","统计时间"};
				HSSFWorkbook workbook = getHSSFWorkbook(strs, SHEETNAME4WZS, values);
				wbs.put(yx_id+"_"+name, workbook);
			}
			
			Iterator<String> it = wbs.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String yxid = key.split("_")[0],yx = key.split("_")[1];
				HSSFWorkbook workbook = wbs.get(key);
				String filePath =StorePath.getBasePath()+yx+"-"+SHEETNAME4WZS+yesterday+".xls";
				saveFile(workbook,filePath );
				String insert = "INSERT INTO TB_YKT_TEMP_WZS_MAIL(JXZZJGID,FJML,SFZT,DYRQ) VALUES('"+yxid+"','"+filePath+"',0,'"+yesterday+"')";
				baseDao.insert(insert);
			}
			JobStatusState.EXPORT_YESTERDAY_WZS=1;// 设置导出成功
		}
	}
	/**
	 * 将导出的文件对象保存至文件系统中。
	 * @param workbook
	 * @param filePath
	 */
	private void saveFile(HSSFWorkbook workbook,String filePath){
		// 保存到服务器上
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			workbook.write(out);
		} catch (IOException e) {
			System.out.println(e.toString());
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				System.out.println("---导出文件报错---");
			}
		}
	}
	/**
	 * 取得工作簿对象。
	 * @param strs
	 * @param sheetName
	 * @param values
	 * @return
	 */
	private HSSFWorkbook getHSSFWorkbook(String[] strs,String sheetName,List<Object> values){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle headerStyle = ExportUtil.createContentStyle(workbook);
		HSSFCellStyle contentStyle = ExportUtil.createContentStyle(workbook);
		contentStyle.setWrapText(true);// 自动换行
		HSSFSheet sheet = workbook.createSheet(sheetName);
	
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
    	return workbook;
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
	public class WgXsmd{
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
}
