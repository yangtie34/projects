package com.jhkj.mosdc.framework.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbTySh;
import com.jhkj.mosdc.framework.po.TbTyShlc;
import com.jhkj.mosdc.framework.po.TbTyShlcHj;
import com.jhkj.mosdc.framework.po.TcXxbzdmjg;
import com.jhkj.mosdc.framework.service.AuditProcessService;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;

public class AuditProcessServiceImpl implements AuditProcessService {
	
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public Map getShztAndCzlxShowStr(Long id,String lsmk) throws Throwable {
		Map resultMap = new HashMap();
		String shzt ="",czl="";
		//最后审核信息
		TbTySh sh = this.getLastExecuteShxx(id);
		TbTyShlcHj dqhj = null;
		String dm = "2";
		if(sh!=null){
		//审核状态标准代码
		TcXxbzdmjg bzdm = (TcXxbzdmjg) this.baseDao
				.queryById(sh.getShztId(), TcXxbzdmjg.class.getName());
		dm = bzdm.getDm();
		//当前环节信息
		dqhj = (TbTyShlcHj)this.baseDao
				.queryById(sh.getHjId(), TbTyShlcHj.class.getName()) ;
		}
		//角色拥有的环节权限
		TbTyShlcHj roleHj = this.getHjByRole(lsmk);
		 if("2".equals(dm)){/*通过*/
	            /*获取下一未审核环节id及信息*/
	        	if(dqhj!=null){
	        		TbTyShlcHj nextHj = this.getNextHj(dqhj);
	        		if(nextHj!=null){
		        		shzt = "待"+nextHj.getMc()+"审核" ;
		        	}else{
		        		shzt = "已通过";
		        	}
	        		/*通过，即只有角色环节是记录办理环节的直接下一环节时有通过、退回操作。*/
		        	if(roleHj!=null){
//		        		czl = (roleHj.getHjsx()-dqhj.getHjsx()==1)? "审核 退回" : "";
		        		czl = (roleHj.getHjsx()-dqhj.getHjsx()==1)? "审核" : "";
		        	}
	        	}else{
	        		TbTyShlcHj firstHj = this.getFirstHj(lsmk);
	        		if(firstHj!=null){
	        			shzt = "待"+firstHj.getMc()+"审核";
	        			if(roleHj!=null){
		        			if(firstHj.getId().longValue()==roleHj.getId().longValue()){
		        				czl="审核";
		        			}
	        			}
	        		}
	        	}
	        	
	            /*拼接字符串*/
	        }else if("9".equals(dm)){/*退回*/
	            /*本环节+退回*/
	        	TbTyShlcHj upHj = this.getUpHj(dqhj);
	        	if(roleHj!=null){
	        		/*退回，即只有角色环节是记录办理环节的直接上一环节时有重新送审操作。*/
	        		czl = (roleHj.getHjsx()-dqhj.getHjsx()==-1)? "重审" : "";
	        	}
	        	 /*该环节是否具有上一环节，如果有则需要添加退回按钮！*/
	        	if(upHj!=null){
	        		shzt = "待"+upHj.getMc()+"审核";
//	        		czl = czl+" 退回";
	        	}
	       
	        }else if("1".equals(dm)){/*未审核*/
	        	shzt = "待"+dqhj.getMc()+"审核";
	        	if(roleHj!=null){
	        		//角色拥有环节和当前环节是同一个环节
	        		if(roleHj.getHjsx()-dqhj.getHjsx()==0){
	        			czl = "审核";
	        		}
	        	}
	        	
	        }else if("0".equals(dm)){/*未送审*/
	        	shzt ="未送审";
	        	czl  = "送审";
	        }else if("5".equals(dm)){/*未通过*/
	        	shzt ="未通过";
	        }
		 resultMap.put("shzt", shzt);
		 resultMap.put("czl", czl);

		
		return resultMap;
	}
	public Map getShztAndCzlxShowStr(Long id,Long lcId) throws Throwable {
		Map resultMap = new HashMap();
		String shzt ="",czl="";
		//最后审核信息
		TbTySh sh = this.getLastExecuteShxx(id);
		TbTyShlcHj dqhj = null;
		String dm = "2";
		if(sh!=null){
		//审核状态标准代码
		TcXxbzdmjg bzdm = (TcXxbzdmjg) this.baseDao
				.queryById(sh.getShztId(), TcXxbzdmjg.class.getName());
		dm = bzdm.getDm();
		//当前环节信息
		dqhj = (TbTyShlcHj)this.baseDao
				.queryById(sh.getHjId(), TbTyShlcHj.class.getName()) ;
		}
		//角色拥有的环节权限
		TbTyShlcHj roleHj = this.getHjByRole(lcId);		
		if("2".equals(dm)){/*通过*/
	            /*获取下一未审核环节id及信息*/
	        	if(dqhj!=null){
	        		TbTyShlcHj nextHj = this.getNextHj(dqhj);
	        		if(nextHj!=null){
		        		shzt = "待"+nextHj.getMc()+"审核" ;
		        	}else{
		        		shzt = "已通过";
		        	}
	        		/*通过，即只有角色环节是记录办理环节的直接下一环节时有通过、退回操作。*/
		        	if(roleHj!=null && null != nextHj){
		        		czl = "";
//		        		czl = (roleHj.getHjsx()-dqhj.getHjsx()==1)? "审核 退回" : "";
//		        		czl = (roleHj.getHjsx()-dqhj.getHjsx()==1)? "审核" : "";
                        Long jsId=nextHj.getJsId();//下个环节角色id
               		    UserInfo ui = UserPermissionUtil.getUserInfo();
         	            String roleids = ui.getRoleIds();
         		        String[] roles = roleids.split(",");//用户拥有的角色权限
         		        for(String roleid : roles){  //如果角色在用户角色范围内    		        	
         		        	if(roleid.equals(jsId.toString())){
         		        		czl = "审核";
         		        	}
         		        }
		        	}
	        	}else{
	        		TbTyShlcHj firstHj = this.getFirstHj(lcId);
	        		if(firstHj!=null){
	        			shzt = "待"+firstHj.getMc()+"审核";
	        			if(roleHj!=null){
		        			if(firstHj.getId().longValue()==roleHj.getId().longValue()){
		        				czl="审核";
		        			}
	        			}
	        		}
	        	}
	        	
	            /*拼接字符串*/
	        }else if("9".equals(dm)){/*退回*/
	            /*本环节+退回*/
	        	TbTyShlcHj upHj = this.getUpHj(dqhj);
	        	if(roleHj!=null){
	        		/*退回，即只有角色环节是记录办理环节的直接上一环节时有重新送审操作。*/
	        		czl = (roleHj.getHjsx()-dqhj.getHjsx()==-1)? "重审" : "";
	        	}
	        	 /*该环节是否具有上一环节，如果有则需要添加退回按钮！*/
	        	if(upHj!=null){
	        		shzt = "待"+upHj.getMc()+"审核";
//	        		czl = czl+" 退回";
	        	}
	       
	        }else if("1".equals(dm)){/*未审核*/
	        	shzt = "待"+dqhj.getMc()+"审核";
	        	if(roleHj!=null){
	        		//角色拥有环节和当前环节是同一个环节
	        		if(roleHj.getHjsx()-dqhj.getHjsx()==0){
	        			czl = "审核";
	        		}
	        	}
	        	
	        }else if("0".equals(dm)){/*未送审*/
	        	shzt ="未送审";
	        	czl  = "送审";
	        }else if("5".equals(dm)){/*未通过*/
	        	shzt ="未通过";
	        }
		 resultMap.put("shzt", shzt);
		 resultMap.put("czl", czl);
		 return resultMap;
	}
	
	@Override
	public void saveShInfo(List<Map> idlist, String lsmk, Long hjId,String shyj,Long shztId) {
		UserInfo userInfo = UserPermissionUtil.getUserInfo();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		List l =new ArrayList();
		//如果审核环节为空,则取第一个环节
		if(hjId==null){
			hjId = this.getFirstHjId(lsmk);
		}
		for(Map m : idlist){
			TbTySh sh = new TbTySh();
			Long id = Long.parseLong(m.get("id").toString());
			//获取审核信息
			TbTySh lastSh = this.getLastExecuteShxx(id);
			int shsh = 1;
			if(lastSh != null){
				shsh = Integer.parseInt(lastSh.getShsx()+"");
				shsh = shsh+1;
			}
			sh.setShxxId(id);
			sh.setLsmk(lsmk);
			sh.setHjId(hjId);
			sh.setShr(userInfo.getUsername());
			sh.setShsj(df.format(new Date()));
			sh.setShyj(shyj);
			sh.setShztId(shztId);
			sh.setShsx(Long.parseLong(shsh+""));
			l.add(sh);
		}
		try {
			this.baseDao.save(l);
		} catch (Exception e) {
			
			e.printStackTrace();
		}		
	}
	
	@Override
	public boolean isOk(Long id,String lsmk) {
		Long tgId = this.baseDao.queryIdByBm("2", "XXDM-SHZT");//已通过
		TbTySh sh = new TbTySh();
		sh.setShxxId(id);
		sh.setLsmk(lsmk);
		List<TbTySh> l = this.baseDao.loadEqual(sh);
		if(l.size()>0){
			sh = this.getMaxInList(l);
			TbTyShlcHj lastHj = this.getLastHj(lsmk);
			if(sh.getShztId().longValue()==tgId.longValue() 
					&& sh.getHjId().longValue()==lastHj.getId().longValue()){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isOk(Long id, Long lcId) throws Throwable {
		Long tgId = this.baseDao.queryIdByBm("2", "XXDM-SHZT");//已通过
		TbTySh sh = new TbTySh();
		sh.setShxxId(id);
		//sh.setLsmk(lsmk);
		List<TbTySh> l = this.baseDao.loadEqual(sh);
		sh = this.getMaxInList(l);
		TbTyShlcHj lastHj = this.getLastHj(lcId);
		if(sh.getShztId().longValue()==tgId.longValue() 
				&& sh.getHjId().longValue()==lastHj.getId().longValue()){
			return true;
		}		
		return false;
	}
	
	
	//获取最后环节的审核信息
	public TbTySh getLastExecuteShxx(Long id) {
    	TbTySh sh = new TbTySh();
    	sh.setShxxId(id);
    	List<TbTySh>  list = this.baseDao.loadEqual(sh);
    	if(list.size()==0){
        	return null;
        }
    	sh = this.getMaxInList(list);
        return sh;
    }
	
	/**
     * 从list中找出最大审核序号对应的审核信息。
     * @param list
     * @param key
     * @return
     */
    private TbTySh getMaxInList(List<TbTySh> list){
        TbTySh maxSh = list.get(0);
        int maxShsx = Integer.parseInt(maxSh.getShsx()+"");
        for (int i=1;i<list.size();i++){
        	TbTySh  sh = list.get(i);
            int shsx = Integer.parseInt(sh.getShsx()+"");
            if(maxShsx<shsx){
                maxShsx = shsx;
                maxSh = list.get(i);
            }
        }
        return maxSh;
    }
    @Override
    public TbTyShlcHj getNowHj(Long id) throws Throwable{
    	//最后审核信息
    	TbTyShlcHj dqhj = new TbTyShlcHj();
		TbTySh sh = this.getLastExecuteShxx(id);
		if(sh!=null){
			//审核状态标准代码
			TcXxbzdmjg bzdm = (TcXxbzdmjg) this.baseDao
					.queryById(sh.getShztId(), TcXxbzdmjg.class.getName());
			//当前环节信息
			dqhj = (TbTyShlcHj)this.baseDao
	    			.queryById(sh.getHjId(), TbTyShlcHj.class.getName()) ;
		
		}
		return dqhj;
    }
    
    /**
     * 获取当前环节的下个未审核环节
     * @param dqhj
     * @return
     * @throws Throwable 
     */
    @Override
    public TbTyShlcHj getNextHj(TbTyShlcHj dqhj) throws Throwable{
        //获取可用环节列表
        TbTyShlcHj hj = new TbTyShlcHj();
        hj.setSslcId(dqhj.getSslcId());
        hj.setSfky(1l);
        List<TbTyShlcHj> hjlist = baseDao.loadEqual(hj);
       
        //清除环节序号小于等于当前环节的环节序号
        for(int i=hjlist.size()-1;i>=0;i--){
			hj = (TbTyShlcHj)hjlist.get(i);
			if(dqhj.getHjsx().longValue()>=hj.getHjsx().longValue()){
				hjlist.remove(i);
			}
        }
		if(hjlist.size()!=0){
			 //获取第一个环节
	        TbTyShlcHj nextHj = hjlist.get(0);
			for(int a=1;a<hjlist.size();a++){
				TbTyShlcHj nhj = hjlist.get(a);
				if(nhj.getHjsx().longValue()<nextHj.getHjsx().longValue()){
					//获取最小序号
					nextHj = nhj;
				}
			}
			return nextHj;
		}else {
			return null;
		}
		
    }
    
    /**
     * 获取当前环节的上个环节
     * @param dqhj
     * @return
     */
    @Override
    public TbTyShlcHj getUpHj(TbTyShlcHj dqhj) {
        //获取可用环节列表
    	TbTyShlcHj hj = new TbTyShlcHj();
        hj.setSslcId(dqhj.getSslcId());
        hj.setSfky(1l);
        List<TbTyShlcHj> hjlist = baseDao.loadEqual(hj);
        //清除环节序号大于等于当前环节的环节序号
        for (int i = hjlist.size() - 1; i >= 0; i--) {
            hj = hjlist.get(i);
            if (dqhj.getHjsx().longValue() <= hj.getHjsx().longValue()) {
                hjlist.remove(i);
            }
        }
        if (hjlist.size() != 0) {
            //获取第一个环节序号
        	TbTyShlcHj upHj = hjlist.get(0);
            for (int a = 1; a < hjlist.size(); a++) {
            	TbTyShlcHj nhj = hjlist.get(a);
                if (nhj.getHjsx().longValue() > upHj.getHjsx().longValue()) {
                    //获取最大序号
                	upHj = nhj;
                }
            }
            return upHj;
        } else{
        	return null;
        }
       
    }
	@Override//获取第一个环节
	public TbTyShlcHj getFirstHj(String lsmk) {
		TbTyShlc lc = new TbTyShlc();
    	lc.setLsmk(lsmk);
    	lc = (TbTyShlc)this.baseDao.loadFirstEqual(lc);
    	if(null != lc){
    		//获取可用环节列表
        	TbTyShlcHj hj = new TbTyShlcHj();
            hj.setSslcId(lc.getId());
            hj.setSfky(1l);
            List<TbTyShlcHj> hjlist = baseDao.loadEqual(hj);
            if (hjlist.size() != 0) {
                //获取第一个环节序号
            	TbTyShlcHj firstHj = hjlist.get(0);
                for (int a = 1; a < hjlist.size(); a++) {
                	TbTyShlcHj nhj = hjlist.get(a);
                    if (nhj.getHjsx().longValue() < firstHj.getHjsx().longValue()) {
                        //获取最小序号
                    	firstHj = nhj;
                    }
                }
                return firstHj;
            } else{
            	return null;
            }
    	}
        return null;
	}
	
	@Override
	public TbTyShlcHj getFirstHj(Long lcId) throws Throwable {
		TbTyShlc lc = (TbTyShlc)this.baseDao
				.queryById(lcId, TbTyShlc.class.getName());
        //获取可用环节列表
    	TbTyShlcHj hj = new TbTyShlcHj();
        hj.setSslcId(lc.getId());
        hj.setSfky(1l);
        List<TbTyShlcHj> hjlist = baseDao.loadEqual(hj);
        if (hjlist.size() != 0) {
            //获取第一个环节序号
        	TbTyShlcHj firstHj = hjlist.get(0);
            for (int a = 1; a < hjlist.size(); a++) {
            	TbTyShlcHj nhj = hjlist.get(a);
                if (nhj.getHjsx().longValue() < firstHj.getHjsx().longValue()) {
                    //获取最小序号
                	firstHj = nhj;
                }
            }
            return firstHj;
        } else{
        	return null;
        }
	}
    /**
     * 获取最后一个环节
     * @param dqhj
     * @return
     */
    
    public TbTyShlcHj getLastHj(String lsmk) {
    	TbTyShlc lc = new TbTyShlc();
    	lc.setLsmk(lsmk);
    	lc = (TbTyShlc)this.baseDao.loadFirstEqual(lc);
        //获取可用环节列表
    	TbTyShlcHj hj = new TbTyShlcHj();
        hj.setSslcId(lc.getId());
        hj.setSfky(1l);
        List<TbTyShlcHj> hjlist = baseDao.loadEqual(hj);
        if (hjlist.size() != 0) {
            //获取第一个环节序号
        	TbTyShlcHj lastHj = hjlist.get(0);
            for (int a = 1; a < hjlist.size(); a++) {
            	TbTyShlcHj nhj = hjlist.get(a);
                if (nhj.getHjsx().longValue() > lastHj.getHjsx().longValue()) {
                    //获取最大序号
                	lastHj = nhj;
                }
            }
            return lastHj;
        } else{
        	return null;
        }
    }
    public TbTyShlcHj getLastHj(Long lcId) throws Throwable {
    	TbTyShlc lc = (TbTyShlc)this.baseDao
    			.queryById(lcId, TbTyShlc.class.getName());
        //获取可用环节列表
    	TbTyShlcHj hj = new TbTyShlcHj();
        hj.setSslcId(lc.getId());
        hj.setSfky(1l);
        List<TbTyShlcHj> hjlist = baseDao.loadEqual(hj);
        if (hjlist.size() != 0) {
            //获取第一个环节序号
        	TbTyShlcHj lastHj = hjlist.get(0);
            for (int a = 1; a < hjlist.size(); a++) {
            	TbTyShlcHj nhj = hjlist.get(a);
                if (nhj.getHjsx().longValue() > lastHj.getHjsx().longValue()) {
                    //获取最大序号
                	lastHj = nhj;
                }
            }
            return lastHj;
        } else{
        	return null;
        }
    }
    /**
     * 
    * @Title: getHjByRole 
    * @Description: 获取角色拥有权限环节信息 
    * @param @param mkm
    * @param @return    设定文件 
    * @return TbTyShlcHj    返回类型 
    * @throws
     */
    @Override
    public TbTyShlcHj getHjByRole(String mkm) {
        UserInfo ui = UserPermissionUtil.getUserInfo();
        String roleids = ui.getRoleIds();
        String[] roles = roleids.split(",");
        List<Map> list = new ArrayList<Map>();
        for(String roleid : roles){
        	TbTyShlc lc = new TbTyShlc();
        	lc.setLsmk(mkm);
        	lc = (TbTyShlc)this.baseDao.loadFirstEqual(lc);
        	if(null != lc){
        		TbTyShlcHj hj = new TbTyShlcHj();
            	hj.setJsId(Long.parseLong(roleid));
            	hj.setSslcId(lc.getId());
                hj = (TbTyShlcHj)this.baseDao.loadFirstEqual(hj);
                if(hj!=null){
                   return hj;
                }
        	}
        }
       return null;
    }

	@Override
	public TbTyShlcHj getHjByRole(Long lcId) throws Throwable {
		 	User ui = UserPermiss.getUser();
	        String roleids = ui.getCurrentRoleLxIds();
	        String hql = "from TbTyShlcHj hj where hj.sslcId="+lcId+" and hj.jsId in("+roleids+") order  by hjsx desc";
	        List<TbTyShlcHj> list = this.baseDao.queryHql(hql);
	        if(list.size()!=0){
	        	return list.get(0);
	        }
	       return null;
	}


	@Override
	public Long getFirstHjId(String lsmk) {
		TbTyShlcHj hj = this.getFirstHj(lsmk);
		if(hj!=null){
			return hj.getId();	
		}
		return null;
		
	}
	
	@Override
	public Long getFirstHjId(Long lcId) throws Throwable {
		TbTyShlcHj hj = this.getFirstHj(lcId);
		if(hj!=null){
			return hj.getId();
		}
		return null;
	}
	
	@Override
	public Long getLastHjId(String lsmk) {
		TbTyShlcHj hj = this.getLastHj(lsmk);
		if(hj!=null){
			return hj.getId();
		}
		return null;
	}

	@Override
	public Long getLastHjId(Long lcId) throws Throwable {
		TbTyShlcHj hj = this.getLastHj(lcId);
		if(hj!=null){
			return hj.getId();
		}
		return null;
	}
	

	@Override
	public Long getHjIdByRole(String lsmk) {
		TbTyShlcHj hj = this.getHjByRole(lsmk);
		if(hj!=null){
		return hj.getId();
		}else{
			return null;
		}
	}

	@Override
	public Long getHjIdByRole(Long lcId) throws Throwable {
		TbTyShlcHj hj = this.getHjByRole(lcId);
		if(hj!=null){
		return hj.getId();
		}else{
			return null;
		}
	}

	@Override
	public Long getNextHjId(Long id) throws Throwable {
		TbTyShlcHj dqhj = this.getNowHj(id);
		TbTyShlcHj nextHj = this.getNextHj(dqhj);
		if(nextHj!=null){
		return nextHj.getId();
		}else{
			return null;
		}
	}

	@Override
	public Long getUpHjId(Long id) throws Throwable  {
		TbTyShlcHj dqhj = this.getNowHj(id);
		TbTyShlcHj upHj = this.getUpHj(dqhj);
		if(upHj!=null){
			return upHj.getId();
		}else{
			return null;
		}
		
	}

	@Override
	public List getHistoryInfo(Long id) {
		String sql = "select hj.mc as hjmc,sh.shzt_id,dm.mc as shztmc,sh.shyj,sh.shsj"
					+ " from tb_ty_sh sh,tb_ty_shlc_hj hj,tc_xxbzdmjg dm"
					+ " where sh.hj_id=hj.id and sh.shzt_id=dm.id and sh.shxx_id="+id+" order by sh.shsj desc";
		List l = this.baseDao.querySqlList(sql);
		return l;
	}

	@Override
	public String getLsmkById(Long lcid,Long id) throws Throwable {
		TbTyShlc lc = (TbTyShlc) this.baseDao
				.queryById(lcid, TbTyShlc.class.getName());
		return lc.getLsmk();
	}

	@Override
	public List<Map> getLcInfoByLsmk(String lsmk) {
		List list = new ArrayList();
		TbTyShlc lc = new TbTyShlc();
		lc.setLsmk(lsmk);
		lc.setSfky(1l);
		List<TbTyShlc> lclist = this.baseDao.loadEqual(lc);
		for(TbTyShlc shlc :lclist){
			Map map = new HashMap();
			map.put("lcId", shlc.getId());
			map.put("lcMc", shlc.getMc());
			TbTyShlcHj hj = new TbTyShlcHj();
			hj.setSslcId(shlc.getId());
			hj.setSfky(1l);
			List<TbTyShlcHj> hjlist = this.baseDao.loadEqual(hj);
			StringBuffer hjJsId = new StringBuffer();
			for(TbTyShlcHj lchj : hjlist){
				hjJsId.append(lchj.getJsId()+",");
			}
			map.put("jsIds", hjJsId.toString().substring(0, hjJsId.length()-1));
			list.add(map);
		}
		return list;
	}

	@Override
	public Long getRoleIdByHjId(Long hjId) throws Throwable {
		TbTyShlcHj  hj =  (TbTyShlcHj)this.baseDao
				.queryById(hjId, TbTyShlcHj.class.getName());
		return hj.getJsId();
	}

	@Override
	public List<Map> getLcInfoByLcIds(String ids) {
		List list = new ArrayList();
		String hql = "";
		if(ids == null){
			hql = " from TbTyShlc where sfky=1";
		}else{
			hql = " from TbTyShlc where sfky=1 and id in("+ids+")";
		}
		List<TbTyShlc> lclist = this.baseDao.queryHql(hql);
		for(TbTyShlc shlc :lclist){
			Map map = new HashMap();
			map.put("lcId", shlc.getId());
			map.put("lcMc", shlc.getMc());
			TbTyShlcHj hj = new TbTyShlcHj();
			hj.setSslcId(shlc.getId());
			hj.setSfky(1l);
			List<TbTyShlcHj> hjlist = this.baseDao.loadEqual(hj);
			StringBuffer hjJsId = new StringBuffer();
			for(TbTyShlcHj lchj : hjlist){
				hjJsId.append(lchj.getJsId()+",");
			}
			String hjjsIds = hjJsId.toString();
			if(hjjsIds.length() == 0)
				continue;
			map.put("jsIds", hjJsId.toString().substring(0, hjJsId.length()-1));
			list.add(map);
		}
		return list;
	}


}
