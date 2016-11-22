package com.jhkj.mosdc.sc.service.impl;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.util.ReflectionUtils;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbJxzzjg;
import com.jhkj.mosdc.framework.po.TbXzzzjg;
import com.jhkj.mosdc.framework.scheduling.po.Message;
import com.jhkj.mosdc.framework.util.DateUtils;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.TeachingOrganizationalStructureNode;
import com.jhkj.mosdc.sc.domain.ChartModel;
import com.jhkj.mosdc.sc.service.SchoolBasicSituationService;
import com.jhkj.mosdc.sc.util.BzksUtils;
import com.jhkj.mosdc.xxzy.po.TbXxzyBjxxb;
import com.sun.faces.util.MessageFactory;

public class SchoolBasicSituationServiceImpl implements
		SchoolBasicSituationService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String queryChart(String params) throws Exception {
		JSONObject json = JSONObject.fromObject(params);
		String methodname = json.getString("method");
		Method method = ReflectionUtils.findMethod(this.getClass(), methodname);
		Object obj = ReflectionUtils.invokeMethod(method, this);
		return Struts2Utils.objects2Json(obj);
	}
	/**
	 * 组织结构查询
	 */
	/**
	 * 查询学校教学组织结构
	 */
	public TeachingOrganizationalStructureNode queryJxzzjg() throws Exception{
		TbJxzzjg txj = new TbJxzzjg();
		List<TbJxzzjg> list = baseDao.loadAll(txj);
		TbXxzyBjxxb bj = new TbXxzyBjxxb();
		bj.setSfky(1l);
		List<TbXxzyBjxxb> bjlist = baseDao.loadEqual(bj);
		Map<Long,TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjgAndTbXxzyBjxxb(list, bjlist);
		TeachingOrganizationalStructureNode.generateNodeTree(hash);
		return TeachingOrganizationalStructureNode.getRoot(hash);
	}
	
	/**
	 * 查询学校行政组织结构
	 */
	public TeachingOrganizationalStructureNode queryXzzzjg() throws Exception{
		TbXzzzjg txj = new TbXzzzjg();
		List<TbXzzzjg> list = baseDao.loadAll(txj);
		TbXxzyBjxxb bj = new TbXxzyBjxxb();
		Map hash = TeachingOrganizationalStructureNode.translateNodeHashForTbXzzzjg(list);
//		bj.setSfky(1l);
//		List<TbXxzyBjxxb> bjlist = baseDao.loadEqual(bj);
//		Map<Long,TeachingOrganizationalStructureNode> hash = TeachingOrganizationalStructureNode.translateNodeHashForTbJxzzjgAndTbXxzyBjxxb(list, bjlist);
		TeachingOrganizationalStructureNode.generateNodeTree(hash);
		return TeachingOrganizationalStructureNode.getRoot(hash);
	}

	/************************ 统计功能 *****************************/

	@Override
	public String queryDormBasic() {
		// TODO Auto-generated method stub
		// 宿舍楼几座，共有多少间宿舍，多少床位
		String msg = "<div style={4}>学校共有宿舍楼<span style={5}>{0}</span>座,"
				+ " 楼层<span  style={6}>{1}</span>层,宿舍<span  style={7}>{2}</span>间,床位数<span  style={8}>{3}</span>个</div>";
		// 查询楼宇个数
		String lysql ="SELECT COUNT(1) FROM TB_DORM_CCJG WHERE CCLX='LY'";
		int ly = baseDao.querySqlCount(lysql);
		// 查询楼层个数
		String lcsql ="SELECT COUNT(1) FROM TB_DORM_CCJG WHERE CCLX='LC'";
		int lc =baseDao.querySqlCount(lcsql);
		// 查询宿舍个数
		String fjsql ="SELECT COUNT(1) FROM TB_DORM_CCJG WHERE CCLX='QS'";
		int fj =baseDao.querySqlCount(fjsql);
		// 查询床位数
		String cwsql ="SELECT COUNT(1) FROM TB_DORM_CW";
		int cw =baseDao.querySqlCount(cwsql);
		String divS = "'margin:20px;'";
		String numS = "'color:red;'";

		msg = StringUtils.format(msg, ly, lc, fj, cw, divS, numS, numS, numS,
				numS);

		return msg;
	}

	@Override
	public List queryRzqk() {
		String yrz = "select count(xs_id) from tb_dorm_zy";
		String wrz = "select count(xjxx.id) from tb_xjda_xjxx xjxx left join tb_dorm_zy zy on zy.xs_id = xjxx.id "+
					"where xjxx.xjzt_id = 1000000000000101 and xjxx.xszt_id = 1 and zy.id is null {0}";
		wrz = MessageFormat.format(wrz, BzksUtils.getAndWhereSql());
		List<ChartModel> list = new ArrayList<ChartModel>();
		ChartModel cm = new ChartModel();
		cm.setName("未入住学生");
		cm.setField("");
		cm.setValue(baseDao.querySqlCount(wrz));

		ChartModel cm1 = new ChartModel();
		cm1.setName("已入住学生");
		cm1.setField("");
		cm1.setValue(baseDao.querySqlCount(yrz));

		list.add(cm);
		list.add(cm1);
		return list;
	}

	/******************** 课程概况查询 ********************/
	@Override
	public String queryKcBasic() {
		// TODO Auto-generated method stub
		// 课程分类，开设课程，承担教学任务部门
		String msg = "<div style={3}>学校共开设课程分类<span style={4}>{0}</span>种,"
				+ " 开设课程共<span  style={5}>{1}</span>门,承担教学任务部门共<span  style={6}>{2}</span>个</div>";

		String divS = "'margin:20px;'";
		String numS = "'color:red;'";

		// 查询课程模块数
		String sql1 = "SELECT COUNT(1) FROM TB_KCK_KCMK WHERE SFKY=1";
		
		int mks = baseDao.querySqlCount(sql1);
		// 查询课程数
		String sql2 = "SELECT COUNT(1) FROM TB_KCK_KCXXB WHERE SFKY=1";
		
		int kcs = baseDao.querySqlCount(sql2);
		// 查询承担单位数
		String sql = "select count(distinct dw.cddw_id) from tb_kck_kccddwb dw where dw.sfky = 1";
		int cddws = baseDao.querySqlCount(sql);

		msg = StringUtils.format(msg, mks, kcs, cddws, divS, numS, numS, numS);

		return msg;
	}

	@Override
	public List queryKcfl() {
		// TODO Auto-generated method stub
		String sql = "select mk.mc,count(distinct kccd.kc_id)num from tb_kck_kccddwb kccd"
				+ " inner join tb_kck_kcmk mk on kccd.kcmk_id = mk.id group by mk.mc";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		List<ChartModel> clist = new ArrayList<ChartModel>();
		for (Map m : list) {
			ChartModel cm = new ChartModel();
			cm.setName(m.get("mc").toString());
			cm.setValue(Integer.parseInt(m.get("num").toString()));
			clist.add(cm);
		}
		return clist;
	}

	@Override
	public List queryXbkc() {
		// TODO Auto-generated method stub
		String sql = "select jg.mc,count(distinct kccd.kc_id)num from tb_kck_kccddwb kccd "
				+ " inner join tb_jxzzjg jg on kccd.cddw_id = jg.id group by jg.mc";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		List<ChartModel> clist = new ArrayList<ChartModel>();
		for (Map m : list) {
			ChartModel cm = new ChartModel();
			cm.setName(m.get("mc").toString());
			cm.setValue(Integer.parseInt(m.get("num").toString()));
			clist.add(cm);
		}
		return clist;
	}
	private String wheresql =" AND XJXX.XJZT_ID = 1000000000000101 ";
	/*************************** 学生概况统计 *******************************/
	@Override
	public List<ChartModel> queryStudentBasic() {
		// TODO Auto-generated method stub
		// 课程分类，开设课程，承担教学任务部门
		String sql = "select count(dm.mc)num,dm.mc from tb_xjda_xjxx xjxx "
				+ " inner join tc_xxbzdmjg dm on xjxx.xjzt_id = dm.id  where 1=1 {0}  group by dm.mc";
		sql = MessageFormat.format(sql, BzksUtils.getAndWhereSql());
		return mlistToClist(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public List queryStudentByAgeRange() {
		// TODO Auto-generated method stub
		String sql_10 = "select count(1) from tb_xjda_xjxx xjxx where  xjxx.csrq is not null "
				+ " {0} {1} and substr(xjxx.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(xjxx.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12)>=10";
		sql_10 = MessageFormat.format(sql_10, wheresql,BzksUtils.getAndWhereSql());
		String sql_16 = "select count(1) from tb_xjda_xjxx xjxx where  xjxx.csrq is not null "
				+ " {0} {1} and substr(xjxx.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(xjxx.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12)>16";
		sql_16 = MessageFormat.format(sql_16, wheresql,BzksUtils.getAndWhereSql());
		String sql_19 = "select count(1) from tb_xjda_xjxx xjxx where  xjxx.csrq is not null "
				+ " {0} {1} and substr(xjxx.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(xjxx.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12)>18";
		sql_19 = MessageFormat.format(sql_19, wheresql,BzksUtils.getAndWhereSql());
		String sql_22 = "select count(1) from tb_xjda_xjxx xjxx where  xjxx.csrq is not null "
				+ " {0} {1} and substr(xjxx.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(xjxx.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12)>22";
		sql_22 = MessageFormat.format(sql_22, wheresql,BzksUtils.getAndWhereSql());
		String sql_25 = "select count(1) from tb_xjda_xjxx xjxx where  xjxx.csrq is not null "
				+ " {0} {1} and substr(xjxx.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(xjxx.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12)>25";
		sql_25 = MessageFormat.format(sql_25, wheresql,BzksUtils.getAndWhereSql());
		String sql_28 = "select count(1) from tb_xjda_xjxx xjxx where  xjxx.csrq is not null "
				+ " {0} {1} and substr(xjxx.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(xjxx.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12)>28";
		sql_28 = MessageFormat.format(sql_28, wheresql,BzksUtils.getAndWhereSql());
		
		int num_10 = baseDao.querySqlCount(sql_10);
		int num_16 = baseDao.querySqlCount(sql_16);
		int num_19 = baseDao.querySqlCount(sql_19);
		int num_22 = baseDao.querySqlCount(sql_22);
		int num_25 = baseDao.querySqlCount(sql_25);
		int num_28 = baseDao.querySqlCount(sql_28);

		List<ChartModel> list = new ArrayList<ChartModel>();

		ChartModel cm = new ChartModel();
		cm.setName("10-16岁");
		cm.setValue(num_10 - num_16);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("17-18岁");
		cm.setValue(num_16 - num_19);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("19-22岁");
		cm.setValue(num_19 - num_22);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("23-25岁");
		cm.setValue(num_22 - num_25);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("25-28岁");
		cm.setValue(num_25 - 28);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("大于28岁");
		cm.setValue(num_28);
		list.add(cm);
		return list;
	}

	@Override
	public List queryStudentByMz() {
		// TODO Auto-generated method stub
		String hanzsql = "select tc.mc,count(tc.mc)num from tb_xjda_xjxx xjxx inner join tc_xxbzdmjg tc on xjxx.mz_id = tc.id and tc.dm = ''01'' where 1=1 {0} {1} group by tc.mc";
		hanzsql = MessageFormat.format(hanzsql, BzksUtils.getAndWhereSql(),wheresql);
		String huizsql = "select tc.mc,count(tc.mc)num from tb_xjda_xjxx xjxx inner join tc_xxbzdmjg tc on xjxx.mz_id = tc.id and tc.dm = ''03'' where 1=1 {0} {1} group by tc.mc";
		huizsql = MessageFormat.format(huizsql, BzksUtils.getAndWhereSql(),wheresql);
		String qtzsql = "select ''其他民族'' as mc,count(tc.mc)num from tb_xjda_xjxx xjxx inner join tc_xxbzdmjg tc on xjxx.mz_id = tc.id and tc.dm not in(''01'',''03'') where 1=1 {0} {1} ";
		qtzsql = MessageFormat.format(qtzsql, BzksUtils.getAndWhereSql(),wheresql);
		
		List<Map> list = new ArrayList<Map>();

		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(hanzsql);
		List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(huizsql);
		List<Map> list3 = baseDao.queryListMapInLowerKeyBySql(qtzsql);

		list.addAll(list1);
		list.addAll(list2);
		list.addAll(list3);

		return mlistToClist(list);
	}

	@Override
	public List queryStudentByRxcc() {
		// TODO Auto-generated method stub
		String sql = "select  dm.mc,count(dm.mc)num from dm_zxbz.t_zxbz_xw dm left join tb_xjda_xjxx xjxx on xjxx.jdxw_id = dm.dm where dm.ls is null {0} {1} group by dm.mc";
		sql = MessageFormat.format(sql, BzksUtils.getAndWhereSql(),wheresql);
		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(sql);
		String wrxsql = "select  ''未维护'' as mc,count(xjxx.id)num from tb_xjda_xjxx xjxx where xjxx.jdxw_id is null {0} {1} ";
		wrxsql = MessageFormat.format(wrxsql, BzksUtils.getAndWhereSql(),wheresql);
		List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(wrxsql);
		list1.addAll(list2);
		return mlistToClist(list1);
	}

	@Override
	public List queryStudentByDq() {
		// TODO Auto-generated method stub
		String sql = "select qh.mc,count(qh.mc)num from tb_xjda_xjxx_sydtj ls inner join tc_xzqh qh on ls.sqxm = qh.qxm and ls.qxm like '16%' group by qh.mc";
		List<Map> list = baseDao.queryListMapInLowerKeyBySql(sql);
		String qtsql = "select '其他' as mc,count(ls.xs_id)num from tb_xjda_xjxx_sydtj ls inner join tc_xzqh qh on ls.sqxm = qh.qxm and ls.qxm not like '16%'";
		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(qtsql);
		list.addAll(list1);

		return mlistToClist(list);
	}

	@Override
	public List queryStudentWithLs() {
		// TODO Auto-generated method stub
		String sql = "select  dm.mc,count(dm.mc)num from dm_zxbz.t_zxbz_xb dm left join tb_xjda_xjxx xjxx on xjxx.xb_id = dm.wid where 1=1 {0} {1} group by dm.mc";
		sql = MessageFormat.format(sql, BzksUtils.getAndWhereSql(),wheresql);
		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(sql);
		String wrxsql = "select  ''未维护'' as mc,count(xjxx.id)num from tb_xjda_xjxx xjxx where xjxx.xb_id is null {0} {1}";
		wrxsql = MessageFormat.format(wrxsql, BzksUtils.getAndWhereSql(),wheresql);
		List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(wrxsql);
		list1.addAll(list2);
		
		
		return mlistToClist(list1);
	}

	private List<ChartModel> mlistToClist(List<Map> list) {
		List<ChartModel> clist = new ArrayList<ChartModel>();
		for (Map m : list) {
			ChartModel cm = new ChartModel();
			cm.setName(m.get("mc").toString());
			cm.setValue(Integer.parseInt(m.get("num").toString()));
			clist.add(cm);
		}
		return clist;
	}

	/************************** 教职工基本信息统计 ******************************/

	@Override
	public List queryTeacherBasic() {
		// TODO Auto-generated method stub
		String sql = "select dm.mc,count(dm.mc)num from tb_jzgxx t"
				+ " inner join tc_xxbzdmjg dm on t.jzglb_id = dm.id group by dm.mc  order by count(dm.mc) desc";

		return mlistToClist(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public List queryTeacherBYXb() {
		// TODO Auto-generated method stub
		String sql = "select dm.mc,count(dm.mc)num from tb_jzgxx t"
				+ " inner join tc_xxbzdmjg dm on t.xb_id = dm.id group by dm.mc  order by count(dm.mc) desc";
		return mlistToClist(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public List queryTeacherByAgeRange() {
		// TODO Auto-generated method stub
		String sql_20 = "select count(1) from tb_jzgxx t where  t.csrq is not null "
				+ " and substr(t.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(t.csrq, 'yyyy-mm-dd hh24:mi:ss')) / 12)>=20";
		String sql_30 = "select count(1) from tb_jzgxx t where  t.csrq is not null "
				+ " and substr(t.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(t.csrq, 'yyyy-mm-dd hh24:mi:ss')) / 12)>30";
		String sql_40 = "select count(1) from tb_jzgxx t where  t.csrq is not null "
				+ " and substr(t.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(t.csrq, 'yyyy-mm-dd hh24:mi:ss')) / 12)>40";
		String sql_50 = "select count(1) from tb_jzgxx t where  t.csrq is not null "
				+ " and substr(t.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(t.csrq, 'yyyy-mm-dd hh24:mi:ss')) / 12)>50";
		String sql_60 = "select count(1) from tb_jzgxx t where  t.csrq is not null "
				+ " and substr(t.csrq,0,4)>=1900 and ceil(months_between(sysdate, to_date(t.csrq, 'yyyy-mm-dd hh24:mi:ss')) / 12)>60";
		String sql_qt = "select count(1) from tb_jzgxx t where  t.csrq is null  ";

		int num_20 = baseDao.querySqlCount(sql_20);
		int num_30 = baseDao.querySqlCount(sql_30);
		int num_40 = baseDao.querySqlCount(sql_40);
		int num_50 = baseDao.querySqlCount(sql_50);
		int num_60 = baseDao.querySqlCount(sql_60);
		int num_qt = baseDao.querySqlCount(sql_qt);

		List<ChartModel> list = new ArrayList<ChartModel>();

		ChartModel cm = new ChartModel();
		cm.setName("20-30岁");
		cm.setValue(num_20 - num_30);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("31-40岁");
		cm.setValue(num_30 - num_40);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("41-50岁");
		cm.setValue(num_40 - num_50);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("51-60岁");
		cm.setValue(num_50 - num_60);
		list.add(cm);

		cm = new ChartModel();
		cm.setName("其他");
		cm.setValue(num_qt);
		list.add(cm);

		return list;
	}

	@Override
	public List queryTeacherByMz() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String hanzsql = "select tc.mc,count(tc.mc)num from tb_jzgxx t inner join tc_xxbzdmjg tc on t.mz_id = tc.id and tc.dm = '01' group by tc.mc";
		String huizsql = "select tc.mc,count(tc.mc)num from tb_jzgxx t inner join tc_xxbzdmjg tc on t.mz_id = tc.id and tc.dm = '03' group by tc.mc";
		String qtzsql = "select '其他民族' as mc,count(distinct t.id)num from tb_jzgxx t inner join tc_xxbzdmjg tc on t.mz_id = tc.id and tc.dm not in('01','03') or t.mz_id is null";

		List<Map> list = new ArrayList<Map>();

		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(hanzsql);
		List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(huizsql);
		List<Map> list3 = baseDao.queryListMapInLowerKeyBySql(qtzsql);

		list.addAll(list1);
		list.addAll(list2);
		list.addAll(list3);

		return mlistToClist(list);
	}

	@Override
	public List queryTeacherByYx() {
		// TODO Auto-generated method stub
		String sql = "select zzjg.mc,count(zzjg.mc)num from tb_jzgxx jzg "
				+ " inner join tb_jxzzjg zzjg on jzg.yx_id = zzjg.id group by zzjg.mc";

		return mlistToClist(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public List queryTeacherByXlcc() {
		// TODO Auto-generated method stub
		String sql = "select dm.mc,count(dm.mc)num from tb_jzgxx t"
				+ " inner join dm_zxbz.t_zxbz_whcd dm on t.whc_id = dm.dm group by dm.mc  order by count(dm.mc) desc";
		String qtsql = "select '其他' as mc,count(t.id)num from tb_jzgxx t where t.whc_id is null";
		
		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(sql);
		List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(qtsql);
		list1.addAll(list2);
		
		return mlistToClist(list1);
	}

	@Override
	public List queryTeacherByZc() {
		// TODO Auto-generated method stub
		String sql = "select dm.dm as id,dm.mc,count(dm.mc)num from dm_zxbz.t_zxbz_zyjszw dm left join tb_jzgxx jzg on jzg.zyjszc_id = dm.dm where dm.ls ='010' group by dm.dm,dm.mc";
		String qtsql = "select '未维护' as mc,count(t.id)num from tb_jzgxx t where t.zyjszc_id is null";
		List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(sql);
		List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(qtsql);
		list1.addAll(list2);
		return mlistToClist(list1);
	}

}
