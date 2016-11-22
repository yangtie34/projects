package com.jhkj.mosdc.framework.message.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;

import com.jhkj.mosdc.framework.dao.impl.BaseDaoImpl;
import com.jhkj.mosdc.framework.message.dao.StationLettersDao;
import com.jhkj.mosdc.framework.message.po.TsLetter;
import com.jhkj.mosdc.framework.message.po.TsLetterAddressee;
import com.jhkj.mosdc.framework.message.po.TsLetterAttachment;

public class StationLettersDaoImpl extends BaseDaoImpl implements StationLettersDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TsLetter> queryReciveLetters(String zgId, Integer start, Integer end) {
		// TODO Auto-generated method stub
		String letterHql = " select tl from TsLetter tl ,TsLetterAddressee la where tl.id = la.letterId and la.jzgId ="+zgId+
				" and la.sfky = true  order by tl.sendTime";
		List<TsLetter> letterList = this.getCurrentSession().createQuery(letterHql).setFirstResult(start).setMaxResults(end).list();
		return letterList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int queryReciveLettersCount(String zgId, Integer start, Integer end) {
		// TODO Auto-generated method stub
		String letterHql = " select count(*) from TsLetter tl ,TsLetterAddressee la where tl.id = la.letterId and la.jzgId ="+zgId+
				" and la.sfky = true  order by tl.sendTime";
		Number num = (Number) this.getCurrentSession().createQuery(letterHql).iterate().next();
		return num.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsLetter> querySendLetters(String zgId, Integer start, Integer end) {
		String letterHql = " from TsLetter tl where tl.sendTime is not null and tl.jzgId ="+zgId+
				" and tl.sfky = true  order by tl.sendTime";
		List<TsLetter> letterList = this.getCurrentSession().createQuery(letterHql).setFirstResult(start).setMaxResults(end).list();
		return letterList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int querySendLettersCount(String zgId, Integer start, Integer end) {
		String letterHql = " select count(*) from TsLetter tl where tl.sendTime is not null and tl.jzgId ="+zgId+
				" and tl.sfky = true  order by tl.sendTime";
		Number num = (Number) this.getCurrentSession().createQuery(letterHql).iterate().next();
		return num.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsLetter> queryDraftLetters(String zgId, Integer start, Integer end) {
		String letterHql = " from TsLetter tl where  tl.sendTime is null and  tl.jzgId ="+zgId+
				" and tl.sfky = true  order by tl.sendTime";
		List<TsLetter> letterList = this.getCurrentSession().createQuery(letterHql).setFirstResult(start).setMaxResults(end).list();
		return letterList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TsLetter> queryGarbageLetters(String zgId, Integer start, Integer end) {
		String letterHql = " select tl from TsLetter tl ,TsLetterAddressee la where tl.id = la.letterId and la.jzgId ="+zgId+
				" and la.sfky = false  order by tl.sendTime";
		List<TsLetter> letterList = this.getCurrentSession().createQuery(letterHql).setFirstResult(start).setMaxResults(end).list();
		return letterList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public int queryGarbageLettersCount(String zgId, Integer start, Integer end) {
		String letterHql = " select count(*) from TsLetter tl ,TsLetterAddressee la where tl.id = la.letterId and la.jzgId ="+zgId+
				" and la.sfky = false  order by tl.sendTime";
		Number num = (Number) this.getCurrentSession().createQuery(letterHql).iterate().next();
		return num.intValue();
	}

	@Override
	public Boolean saveLetter(TsLetter letter,
			List<TsLetterAddressee> addressList,
			List<TsLetterAttachment> attachList) throws Exception {
		// TODO Auto-generated method stub
		Long letterId = getId();
		letter.setId(letterId);
		this.save(letter);
		
		for(TsLetterAddressee ta : addressList){
			ta.setLetterId(letterId);
			this.save(ta);
		}
		for(TsLetterAttachment tat : attachList){
			
		}
		return true;
	}

	@Override
	public Boolean updateLetter(TsLetter letter,
			List<TsLetterAddressee> addressList,
			List<TsLetterAttachment> attachList) {
		// TODO Auto-generated method stub
		this.update(letter);
		
		return true;
	}

	@Override
	public Boolean deleteLetterLogic(List ids) {
		// TODO Auto-generated method stub
		String hql = "from TsLetterAddressee ta where ta.letterId in(:alist)";
		TsLetterAddressee ta = (TsLetterAddressee) this.getCurrentSession().createQuery(hql).setParameterList("alist", ids).list().get(0);
		ta.setSfky(false);
		this.update(ta);
		return true;
	}
	/**
	 * 物理删除信件
	 */
	public Boolean deleteLetterPhysical(List ids){
		String hql = "from TsLetterAddressee ta where ta.letterId in(:alist)";
		TsLetterAddressee ta = (TsLetterAddressee) this.getCurrentSession().createQuery(hql).setParameterList("alist", ids).list().get(0);
		this.delete(ta);
		return true;
	}
	@Override
	public List<Map> queryJzg() {
		// TODO Auto-generated method stub
		String sql = "select zg.id,zg.xm,xz.mc from tb_jzgxx zg inner join tb_xzzzjg xz on zg.ks_id = xz.id  and zg.sfky = 1";
		return this.queryListMapInLowerKeyBySql(sql);
	}
	
	public Session getCurrentSession(){
		return this.getHibernateTemplate().getSessionFactory().getCurrentSession();
	}

}
