package com.jhkj.mosdc.framework.message.dao.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.message.dao.AnnouncementDao;
import com.jhkj.mosdc.framework.message.po.TsAnnouncement;
import com.jhkj.mosdc.framework.po.TbXzzzjg;
import com.jhkj.mosdc.permiss.util.UserPermiss;

public class AnnouncementDaoImpl extends BaseDaoImpl implements AnnouncementDao {

	@Override
	public List<TsAnnouncement> queryRencentAnnouncement(String zzjgId) {
		String hql = "select distinct ta from TsAnnouncement ta,TbXzzzjg xzjg,TbXzzzjg xzjg1 where ta.zzjgId = xzjg.id and xzjg1.qxm like :qt xzjg.qxm :qt1"+
				     " and xzjg1.id="+zzjgId;
		
		String qxmSql = "select t.* from tb_xzzzjg t where t.id="+zzjgId;
		TbXzzzjg zzjg = (TbXzzzjg) this.getCurrentSession().createSQLQuery(qxmSql).addEntity(TbXzzzjg.class).uniqueResult();
		String qxm = zzjg.getQxm();
		
		String sql = " select * from ( (select distinct ta.* from ts_announcement ta inner join tb_xzzzjg xzjg on ta.zzjg_id = xzjg.id  "+
                	 " inner join tb_xzzzjg xzjg1 on xzjg1.qxm like xzjg.qxm||'%' and xzjg1.qxm="+qxm+" ) " +
                	 " union all" +
                	 " (select distinct ta.* from ts_announcement ta where ta.zzjg_id in (select id from tb_xzzzjg jg where jg.cclx = 'BM' or jg.cclx = 'KS'))"
                	 + "union all (select distinct ta.* from ts_announcement ta where ta.jzg_id is null) ) order by send_time desc";
		SQLQuery query = this.getHibernateSession().getSessionFactory().getCurrentSession().createSQLQuery(sql);
		query.setFirstResult(0).setMaxResults(4);
		query.addEntity(TsAnnouncement.class);
		return query.list();
	}
	/**
	 * 查询我发出的公告
	 * @param zgId
	 * @return
	 */
	public List<Map> queryMineAnnouncement(Long zgId,String start,String end){
		String zgContidion = zgId != null ?zgId.toString() : " is null ";
		String sql = "select * from (select at.*,rownum rn from ts_Announcement at where jzg_id {0}  order by at.SEND_TIME)T where T.rn>{1} and T.rn<={2} ";
		String formatSql = MessageFormat.format(sql, zgContidion,start,end);
		return this.queryListMapInLowerKeyBySql(formatSql);
	}
	@Override
	public boolean addAnnouncement(TsAnnouncement anment) throws Exception {
		// TODO Auto-generated method stub
		anment.setId(getId());
		if(UserPermiss.getUser().getCurrentLoginname().equals("admin"))anment.setJzgXm("管理员");
		this.save(anment);
		return true;
	}
	@Override
	public boolean removeAnnouncement(String id){
		this.deleteBySql("delete from ts_Announcement where id="+id);
		return true;
	}
	@Override
	public boolean updateAnnouncement(TsAnnouncement anment) throws Exception {
		// TODO Auto-generated method stub
		TsAnnouncement oldAnt = (TsAnnouncement) this.get(TsAnnouncement.class,anment.getId());
		oldAnt.setContent(anment.getContent());
		oldAnt.setSendTime(anment.getSendTime());
		oldAnt.setTitle(anment.getTitle());
		this.update(oldAnt);
		return false;
	}
	public Session getCurrentSession(){
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

}
