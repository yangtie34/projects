package com.jhnu.syspermiss;

import java.util.List;

public class T {

	public static void main(String[] args) {
//		byte[] p=UserUtil.getPhotoByUserName("admin");
//		System.out.println();
//		GetCachePermiss.init("1986003","http://localhost:9099/dmm_logistics");
//		boolean ddd=GetCachePermiss.hasPermssion("1986003", "xg:worker:*");
//		System.out.println(ddd);
//		System.out.println(new Date());
		//Object a=new DeptPermissionServiceImpl().getDeptTeachByUserNameAndResource();	
//		System.out.println(new Date());
	/*	GetCachePermiss.init("1986003","http://localhost:80/dmm_xg");

		GetCachePermiss.init("19901023","http://localhost:9099/dmm_logistics");

		

		boolean ddd=GetCachePermiss.hasPermssion("1986003", "xg:worker:*");
		System.out.println(ddd);

	//	boolean ddd=GetCachePermiss.hasPermssion("admin", "hq:book:view");
		
		
		
//		boolean ddd=GetCachePermiss.hasPermssion("19901023", "hq:book:overdueRank:view");
		System.out.println(a);
		
		List<String> b=GetCachePermiss.getDataByDeptAndData("deptTeach","303",a);
		
		System.out.println(b);*/

		
	/*	boolean d=GetCachePermiss.hasPermssion("19901023", "ht:qx:js:*");
		System.out.println(d);
		d=GetCachePermiss.hasPermssion("19901023", "ht:qx:js:view");
		System.out.println(d);
		d=GetCachePermiss.hasPermssion("19901023", "ht:qx:yh:*");
		System.out.println(d);
		d=GetCachePermiss.hasPermssion("19901023", "ht:qx:yh:view");
		System.out.println(d);
		d=GetCachePermiss.hasPermssion("19901023", "ht:qx:*");
		System.out.println(d);
		d=GetCachePermiss.hasPermssion("19901023", "ht:qx:view");
		System.out.println(d);
		List<Resources> dd=GetCachePermiss.getMenusByUserNameShiroTag("19901023", "ht:qx:*");
		System.out.println(dd);*/
//		UserServiceImpl user=new UserServiceImpl();
//		boolean d=user.checkPassword(1l, "ssss");
//		System.out.println(d);
		
//		for(int i=0;i<30;i++){
//			System.out.println("main"+i);
//			List<String> a=GetPermiss.getDataServeSqlbyUserIdShrio("19901023","kdperson:*");
//    		System.out.println(i+"-"+a.size());
//		}
	/*	System.out.println(1);
		List<String> list=new ArrayList();
		//System.out.println(BeanMap.toMap(GetPermiss.getMainPageByUser(GetPermiss.findByUsername("admin"))));
		List<String> a=GetPermiss.getDataServeSqlbyUserIdShrio("admin","xg:worker");
		System.out.println(a);
		System.out.println(GetCachePermiss.hasPermssion("admin","xg:worker", "309"));*/
		/*GetCachePermiss.initEvery("admin");
		System.out.println(GetCachePermiss.findRolesList("admin"));;*/
		
		
		//System.out.println(GetCachePermiss.findRolesList("admin"));
	/*	System.out.println(GetCachePermiss.hasPermssion("admin","xg:view"));*/
	}
	
}
class Demo extends Thread{
    public void run(){
        for(int i=0;i<60;i++){
        	System.out.println("Thread:"+Thread.currentThread().getName());
        	List<String> a=GetPermiss.getDataServeSqlbyUserIdShrio("19901023","kdperson:*");
    		System.out.println(Thread.currentThread().getName()+"------"+a.size());
        }
    }
}