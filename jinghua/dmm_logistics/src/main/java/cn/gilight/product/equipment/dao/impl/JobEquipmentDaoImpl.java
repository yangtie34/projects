package cn.gilight.product.equipment.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.equipment.dao.JobEquipmentDao;

@Repository("jobEquipmentDao")
public class JobEquipmentDaoImpl implements JobEquipmentDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public void updateEquipmentYear(int year){
		
		String delSql="DELETE TL_EQUIPMENT_YEAR WHERE YEAR_=? ";
		baseDao.getJdbcTemplate().update(delSql,new Object[]{year});
		
		String addSql="INSERT INTO TL_EQUIPMENT_YEAR "+
						"SELECT T.YEAR_,T.NUMS,NVL((T.NUMS - NVL(A.NUMS, 0)),0) UPNUMS, "+
						"NVL(T.MONEYS, 0) MONEYS, NVL(T.MONEYS - NVL(A.MONEYS, 0), 0) UPMONEYS "+
						"FROM (SELECT ? YEAR_,NVL(SUM(B.COUNT_), 0) NUMS, "+
						"ROUND(NVL(SUM(B.PRICE * B.COUNT_), 0), 2) MONEYS "+
						"FROM T_EQUIPMENT B WHERE B.BUY_DATE < ?) T "+
						"FULL JOIN (SELECT * FROM TL_EQUIPMENT_YEAR WHERE YEAR_ = ?) A ON 1 = 1 ";
		
		baseDao.getJdbcTemplate().update(addSql,new Object[]{year,year+1+"",year-1});
		
	}
}
