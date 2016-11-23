package com.jhkj.mosdc.sc.util;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

/**
 * 数学计算工具类。
 * 
 * @Description: TODO
 * @author zhangzg
 * @date 2014-3-18 下午6:06:01
 */
public class MathUtils {
	/**
	 * 获取平均值
	* @Title: getAvgvalue 
	* @Description: TODO
	* @param values
	* @return
	* @return double
	* @throws
	 */
	public static double getAvgvalue(List<Double> values){
		int size = values.size();
		double value_sum = 0d, // 总和
		value_mean = 0d; // 平均分
		for (Double val : values) {
			value_sum += val;
		}
		value_mean = size==0?value_sum:value_sum / size;
		return get2Point(value_mean);
	}
	/**
	 * 传给我一个Double数组，返回给你它们的方差值。 方差描述一个数据集的波动程度,该值越小代表波动越稳定。
	 * 
	 * @Title: getvariance
	 * @Description: TODO
	 * @param values
	 * @return
	 * @return double
	 * @throws
	 */
	public static double getVariance(List<Double> values) {
		int size = values.size();
		double value_mean = 0d, // 平均分
		value_deffer_sum = 0d;// 平方差总和
		value_mean = getAvgvalue(values);
		for (Double val : values) {
			value_deffer_sum += (val - value_mean) * (val - value_mean);
		}
		
		double value = size==0 ? value_deffer_sum : Math.sqrt(value_deffer_sum / size);
		return get2Point(value);
	}

	/**
	 * 计算标准差（均方差） 标准差反映一个数据集的离散程度。
	 * 
	 * @Title: getStandardDeviation
	 * @Description: TODO
	 * @return
	 * @param values
	 * @return double
	 * @throws
	 */
	public static double getStandardDeviation(List<Double> values) {
		return get2Point(Math.sqrt(getVariance(values)));
	}
	/**
	 * 获取极差
	* @Title: getRange 
	* @Description: TODO
	* @param values
	* @return
	* @return double
	* @throws
	 */
	public static double getRange(List<Double> values){
		if(values.size()==0){
			return 0d;
		}else{
			return get2Point(getMaxValue(values)-getMinValue(values));
		}
	}
	/**
	 * 保留2位小数
	 * @param dd
	 * @return
	 */
	public static double get2Point(double dd){
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.parseDouble(df.format(dd));
	}
	/**
	 * 获取整型数组中的最大值
	 * @param arr
	 * @return
	 */
    public static double getMaxValue(List<Double> arr) {  
        double max = Double.MIN_VALUE;  
          
        for(int i = 0; i < arr.size(); i++) {  
            if(arr.get(i) > max)  
                max = arr.get(i);  
        }  
          
        return get2Point(max);  
    }  
    /**
     * 获取整型数组中的最小值  
     * @param arr
     * @return
     */
    public static double getMinValue(List<Double> arr) {  
        double min = Double.MAX_VALUE;  
          
        for(int i = 0; i < arr.size(); i++) {  
            if(arr.get(i)!=0 && arr.get(i) < min)  
                min = arr.get(i);  
        }  
          
        return get2Point(min);
    }
    /**
     * 获取中位数
     * @param ds
     * @return
     */
    public static double getMiddleValue(List<Double> ds){
    	double mv = -0d;
    	int size = ds.size();
    	// 先排序
    	Collections.sort(ds);
    	// 如果数组长度为奇数，则中位数是中间的那个
    	// 如果数组长度为偶数，则去中间2位数的平均数
    	if(ds.size()==0){
    		mv = 0d;
    	}else if(ds.size()%2 == 1){
    		mv = ds.get(size/2);
    	}else{
    		mv = (ds.get(size/2-1)+ ds.get(size/2))/2;
    	}
    	
    	return get2Point(mv);
    }
   
    public static String getScale(List<Double> ds,Double dou){
    	int size = ds.size();
		double bl=0;
		double value_mean = 0d; // 平均分
		for (Double val : ds) {
			if(val>=dou){
				bl++;
			}
		}
		value_mean = size==0?0:bl / size *100;
		return (get2Point(value_mean))+"%";
    }
    
}
