package cn.gilight.framework.uitl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;

public class RListUtil {
	public static List<Map<String, Object>> toList(RList rlist,String sn) {
        String [] names = rlist.keys();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<String[]> cloumns = new ArrayList<String[]>();
		for (int i=0;i<rlist.size();i++) {
			try {
				cloumns.add(rlist.at(i).asStrings());
			} catch (REXPMismatchException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i < cloumns.get(0).length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int j = 0; j < names.length; j++) {
				if(sn!=null && sn.indexOf(","+names[j]+",")>=0 ){
					BigDecimal bd = new BigDecimal(cloumns.get(j)[i]);
			//		double snNum=bd.setScale(4,BigDecimal.ROUND_HALF_UP).doubleValue()*100;
			//		snNum=MathUtils.get2Point(snNum);
					map.put(names[j], bd);
				}else{
					map.put(names[j], cloumns.get(j)[i]);
				}
			}
			list.add(map);
		}
		return list;
	}
}
