package cn.gilight.framework.uitl.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.gilight.dmm.business.util.Constant;


/**
 * 数学计算工具类。
 * 
 * @author zhangzg
 * @date 2014-3-18 下午6:06:01
 */
public class MathUtils {
	
	/**
	 * 获取平均值（保留两位小数）
	 * @param values
	 * @return double
	 */
	public static double getAvgValue(List<Double> values){
		return getAvgValue(values, 2);
	}
	/**
	 * 获取平均值
	 * @param values
	 * @param maxDigits 小数位数
	 * @return double
	 */
	public static double getAvgValue(List<Double> values, int maxDigits){
		int size = values.size();
		double value_sum = 0d, // 总和
				value_avg = 0d; // 平均分
		for (Double val : values) {
			if(val != null){
				value_sum += val;
			}
		}
		value_avg = size==0 ? value_avg : value_sum / size;
		return getPoint(value_avg, maxDigits);
	}
	
	/**
	 * 获取平均值（null不参与平均值的计算）（保留两位小数）
	 * @param values
	 * @return double
	 */
	public static double getAvgValueExcludeNull(List<Double> values){
		return getAvgValueExcludeNull(values, 2);
	}
	/**
	 * 获取平均值（null不参与平均值的计算）
	 * @param values
	 * @param maxDigits 小数位数
	 * @return double
	 */
	public static double getAvgValueExcludeNull(List<Double> values, int maxDigits){
		int size_real = 0;
		double value_sum = 0d, // 总和
				value_avg = 0d; // 平均分
		for (Double val : values) {
			if(val != null){
				value_sum += val;
				size_real++;
			}
		}
		value_avg = size_real==0 ? value_avg : value_sum / size_real;
		return getPoint(value_avg, maxDigits);
	}
	
	/**
	 * 传给我一个Double数组，返回给你它们的方差值。 方差描述一个数据集的波动程度,该值越小代表波动越稳定。
	 * 
	 * @Title: getvariance
	 * @param values
	 * @return double
	 */
	public static double getVariance(List<Double> values) {
		int size = values.size();
		double value_mean = 0d, // 平均分
		value_deffer_sum = 0d;// 平方差总和
		value_mean = getAvgValue(values);
		for (Double val : values) {
			value_deffer_sum += (val - value_mean) * (val - value_mean);
		}
		
		double value = size==0 ? value_deffer_sum : value_deffer_sum / size;
		return get2Point(value);
	}

	/**
	 * 计算标准差（均方差） 标准差反映一个数据集的离散程度。
	 * 
	 * @Title: getStandardDeviation
	 * @return
	 * @param values
	 * @return double
	 */
	public static double getStandardDeviation(List<Double> values) {
		return get2Point(Math.sqrt(getVariance(values)));
	}
	/**
	 * 获取极差
	 * @Title: getRange 
	 * @param values
	 * @return double
	 */
	public static double getRange(List<Double> values){
		if(values.size()==0 || values.size()==1 || getMaxValue(values)==0.0){
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
		DecimalFormat df = getDecimalForamt(2);
		return Double.parseDouble(df.format(dd));
	}
	/**
	 * 保留 * 位小数
	 * @param dd
	 * @param maxDigits *位小数
	 * @return double
	 */
	public static double getPoint(double dd, int maxDigits){
		DecimalFormat df = getDecimalForamt(maxDigits);
		return Double.parseDouble(df.format(dd)); //返回的是String类型
	}
	
	/**
	 * 获取格式化类
	 * @param maxDigits
	 * @return DecimalFormat
	 */
	private static DecimalFormat getDecimalForamt(int maxDigits){
		String digits = "0";
		for(int i=0; i<maxDigits; i++){
			digits = i==0 ? digits+"." : digits;
			digits += "0";
		}
		// 设置精确到小数点后 几 位  
		return new DecimalFormat(digits);//格式化小数  
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
     * 获取整型数组中的偏态值
     * @param ds 
     * @return
     */
    public static Double getSkewnessValue(List<Double> ds){
    	 if (ds == null || ds.isEmpty()){
    		 return null;
    	 }
    	 Double avg = getAvgValue(ds),
    			mid = getMiddleValue(ds),
    			stddev = getStandardDeviation(ds);
    	 Double value = getDivisionResult((avg-mid)*3, stddev, 2);
    	 return get2Point(value);
    }
    /**
     * 获取整型数组中的区分度
     * @param ds 成绩集合 
     * @param denominator 分母; eg：100  试卷满分   
     * @return
     */
    public static Double getDistinctionDegree(List<Double> ds,Double denominator){
	    if (ds == null || ds.isEmpty()){
		    return null;
	    }
    	Double size = getPoint(ds.size()*Constant.Math_Distinction_Scale, 0);
    	Collections.sort(ds);
    	List<Double> a = ds.subList(0, size.intValue()),
    	             b = ds.subList((ds.size()-size.intValue()), ds.size());
    	Double aAvg = getAvgValue(a),bAvg = getAvgValue(b);
    	Double value = aAvg > bAvg ? getDivisionResult(aAvg-bAvg, denominator, 2):
    		getDivisionResult(bAvg-aAvg, denominator, 2);
    	return get2Point(value);
    }
    /**
     * 获取整型数组中的效度
     * @param ds 
     * @return
     */
    public static Double getValidityValue(List<Double> ds,Double denominator){
    	 if (ds == null || ds.isEmpty()){
 		    return null;
 	    }
     	Double size = getPoint(ds.size()*Constant.Math_Distinction_Scale, 0);
     	Collections.sort(ds);
     	List<Double> a = ds.subList(0, size.intValue()),
     	             b = ds.subList((ds.size()-size.intValue()), ds.size());
     	Double aAvg = getAvgValue(a),bAvg = getAvgValue(b);
     	Double value = aAvg > bAvg ? getDivisionResult(aAvg-bAvg, denominator, 2):
     		getDivisionResult(bAvg-aAvg, denominator, 2);
     	return get2Point(value);
    }
    /**
     * 获取中位数【长度为奇数，则中位数是中间的那个，长度为偶数，则去中间2位数的平均数】
     * @param ds
     * @return
     */
    public static double getMiddleValue(List<Double> ds){
    	double mv = -0d;
    	int size = ds.size();
    	if( ds == null || ds.size()==0 ){
    		mv = 0d;
    	}else{
	    	// 先排序
	    	Collections.sort(ds);
	    	// 如果数组长度为奇数，则中位数是中间的那个
	    	// 如果数组长度为偶数，则去中间2位数的平均数
	    	
	    	if(ds.size()%2 == 1){
	    		mv = ds.get(size/2);
	    	}else{
	    		mv = (ds.get(size/2-1)+ ds.get(size/2))/2;
	    	}
    	}
    	
    	return get2Point(mv);
    }
    
    /**
     * 取众数，出现次数最多的不止一个时，取它们的中位数
     * @param list
     * @return double
     */
    public static double getModeValue(List<Double> list){
    	double val = 0D;
    	int maxCount = 1; // 最大值出现次数
    	List<Double> maxList = new ArrayList<>(); // 出现次数最多的值集合
    	Map<Double, Integer> map = new HashMap<>();
    	for(Double d : list){
    		if(d != null){
	    		if(!map.containsKey(d)){
	    			map.put(d, 1);
	    		}else{
	    			int count = map.get(d).intValue();
	    			if(count >= maxCount){
	    				if(count > maxCount){
	    					val = d;
	    					maxList.clear();
	    				}else{
	    					maxList.add(d);
	    				}
	    				maxCount = count;
	    			}
	    		}
    		}
    	}
    	// 出现次数最多的不只一个，取中间值
    	if(!maxList.isEmpty()){
    		val = getMiddleValue(maxList);
    	}
    	return val;
    }
    
    /**
     * 获取百分比（保留两位小数）
     * @param num1 被除数
     * @param num2 除数
     * @return 12.35
     */
    public static Double getPercentNum(int num1,int num2){
    	return getPercentNum(new Double(num1),new Double(num2));
    }
    /**
     * 获取百分比（保留两位小数）
     * @param dividend 被除数
     * @param divisor 除数
     * @return 12.35
     */
    public static Double getPercentNum(Double dividend, Double divisor){
    	return getPercentNum(dividend, divisor, 2);  // 设置精确到小数点后2位  
    }
    /**
     * 获取百分比
     * @param dividend 被除数
     * @param divisor 除数
     * @param maxDigits 保留小数位数
     * @return 12.35
     */
    public static Double getPercentNum(Double dividend, Double divisor, int maxDigits){
    	if(divisor == null || divisor == 0) return 0D;
    	DecimalFormat df = getDecimalForamt(maxDigits);
    	return Double.valueOf(df.format(dividend / divisor * 100)); //format返回String类型
    }
    
    /**
     * 获取百分比（带%）（保留两位小数）
     * @param num1
     * @param num2
     * @return 12.35%
     */
    public static String getPercent(int num1,int num2){
    	Double val = getPercentNum(num1, num2);
    	return val==0 ? val+"" : val+"%";
    }
    /**
     * 得到除法结果
     * @param dividend 被除数
     * @param divisor 除数
     * @param maxDigits 保留小数位数
     * @return
     */
    public static Double getDivisionResult(int dividend, int divisor, int maxDigits){
    	return getDivisionResult(new Double(dividend), new Double(divisor), maxDigits);
    }
    /**
     * 得到除法结果
     * @param dividend 被除数
     * @param divisor 除数
     * @param maxDigits 保留小数位数
     * @return
     */
    public static Double getDivisionResult(Double dividend, Double divisor, int maxDigits){
    	if(divisor==null || divisor == 0) return 0D;
    	DecimalFormat df = getDecimalForamt(maxDigits); 
    	return Double.valueOf(df.format(dividend / divisor)); //返回的是String类型
    }
   
    /**
     * 大于等于指定数值的数据占比
     * @param ds 所有数据
     * @param dou 需要超过的数
     * @return String <br>
     * 20.23%
     */
    public static String getScale(List<Double> ds, Double dou){
		return (getScaleNum(ds, dou))+"%";
    }

    /**
     * 大于等于指定数值的数据占比
     * @param ds 所有数据
     * @param dou 需要超过的数
     * @return Double <br>
     * 12 (占比12%)
     */
    public static Double getScaleNum(List<Double> ds, Double dou){
    	Collections.sort(ds);
    	Collections.reverse(ds);
    	int size = ds.size();
    	double count = 0, value = 0d; // 比例
    	for (Double val : ds) {
    		if(val >= dou){
    			count++;
    		}else break;
    	}
    	value = size==0 ? 0 : count / size * 100;
    	return (get2Point(value));
    }
    
    /**
     * 获取增幅
     * @param comparData 比较数据
     * @param compartoData 被比较数据
     * @param maxDigits 保留小数位数
     * @return Double
     */
    public static Double getAmplificationNum(Double comparData, Double compartoData, int maxDigits){
    	return getPercentNum(comparData-compartoData, compartoData, 2);
    }
    /**
     * 获取增幅
     * @param comparData 比较数据
     * @param compartoData 被比较数据
     * @param maxDigits 保留小数位数
     * @return Double
     */
    public static Double getAmplificationNum(int comparData, int compartoData, int maxDigits){
    	return getPercentNum(new Double(comparData-compartoData), new Double(compartoData), 2);
    }
    /**
     * 获取增幅（带%）
     * @param comparData 比较数据
     * @param compartoData 被比较数据
     * @param maxDigits 保留小数位数
     * @return String
     */
    public static String getAmplification(Double comparData, Double compartoData, int maxDigits){
    	Double val = getAmplificationNum(comparData-compartoData, compartoData, 2);
    	return val==0 ? val+"" : val+"%";
    }
	/**
	 * 重抽样函数
	 * @param list 集合
	 * @param length 需要抽样出多少样本
	 * @return List<T> 返回的是抽样的总集合
	 */
	public static <T> List<T> getMultipleSampling(List<T> list,int length){
		Random ran = new Random();
		int index = list.size()-1;
		List<T> result = new ArrayList<>();
		for(int i=0;i<length;i++){
			result.add(list.get(ran.nextInt(index)));
		}
		return result;
	}
    
    public static void main(String[] args) {
//    	int dividend = 400, divisor = 200, maxDigits = 3;
    	Double dividend2 = 400D, divisor2 = null;
//    	System.out.println(getDivisionResult(dividend, divisor, maxDigits));
    	System.out.println(getPercentNum(dividend2, divisor2));
//    	System.out.println(getAmplificationNum(dividend, divisor, maxDigits));
	}
}
