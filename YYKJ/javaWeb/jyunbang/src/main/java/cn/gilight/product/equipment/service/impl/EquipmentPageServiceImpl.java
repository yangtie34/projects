package cn.gilight.product.equipment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.product.equipment.dao.EquipmentPageDao;
import cn.gilight.product.equipment.service.EquipmentPageService;

@Service("equipmentPageService")
public class EquipmentPageServiceImpl implements EquipmentPageService  {
	
	@Autowired
	private EquipmentPageDao equipmentPageDao;

	@Override
	public Page getEmDetil(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, boolean isOT,String emType) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetil(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType);
	}

	@Override
	public Page getEmDetilByType(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, boolean isOT,
			String emType, String type, String typeValue) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetilByType(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, type, typeValue);
	}

	@Override
	public Page getEmDetilByDeptGroup(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, String deptGroup) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetilByDeptGroup(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, deptGroup);
	}

	@Override
	public Page getEmDetilByDeptId(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, String deptGroup, String deptId) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetilByDeptId(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, deptGroup, deptId);
	}

	@Override
	public Page getEmDetilByManagerId(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, String managerId) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetilByManagerId(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, managerId);
	}

	@Override
	public Page getManager(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, boolean isOT,
			String emType,String deptGroup) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_RYXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getManager(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType,deptGroup);
	}

	@Override
	public Page getManagerByType(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, boolean isOT,
			String emType,String deptGroup, String type, String typeValue) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_RYXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getManagerByType(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType,deptGroup, type, typeValue);
	}

	@Override
	public Page getManagerByDeptGroup(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, String deptGroup) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_RYXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getManagerByDeptGroup(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, deptGroup);
	}

	@Override
	public Page getManagerByDeptId(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, String deptGroup, String deptId) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_RYXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getManagerByDeptId(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, deptGroup, deptId);
	}

	@Override
	public Page getEmDetilByYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, boolean isOT,
			String emType, boolean isUp, int year) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetilByYear(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, isUp, year);
	}

	@Override
	public Page getEmDetilByLastYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, int year) {
		if(!GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), ShiroTagEnum.YQSB_SBXZ.getCode())){
			return new Page(false);
		}
		return equipmentPageDao.getEmDetilByLastYear(currentPage,numPerPage,totalRow,sort,isAsc, isOT, emType, year);
	}
	
}
