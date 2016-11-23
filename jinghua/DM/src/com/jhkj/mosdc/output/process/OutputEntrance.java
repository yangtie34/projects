package com.jhkj.mosdc.output.process;
/**
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-5
 * @TIME: 下午05:29:38
 */
public interface OutputEntrance {
	/**
	 * 输出页面统一处理接口。所有输出页面的初始化数据都由该接口提供。
	 * 
	 * @param params
	 * output={
	 * 	'pageId':页面id,
	 * 	'templateId':模板id,
	 * 	'requestType':'linkage',//联动请求的参数，应带上次参数且值为linkage/godown(下钻)
	 * 	globalParams:{// 联动请求时的公共参数。
	 * 		date:{
	 * 			beginDate:'2012-01-01',
	 * 			endDate:'2012-02-02'
	 * 		}
	 * 	}
	 * 	'components':[
	 * 		{
	 * 			componentId:'300001',
	 * 			compoParams:{
	 * 				wd:'',
	 * 				zb:'',
	 * 				fw:''
	 * 			}
	 * 		}
	 * 	]
	 * }
	 * @return json格式的字符串。
	 */
	public String queryData(String params);

}
