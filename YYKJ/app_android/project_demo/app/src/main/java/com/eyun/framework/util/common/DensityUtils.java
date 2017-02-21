package com.eyun.framework.util.common;

import android.content.Context;
import android.util.TypedValue;

import com.eyun.framework.angular.core.Scope;

/**
 * 常用单位转换的辅助类
 * 
 * @author zhy
 * 
 */
public class DensityUtils
{
	private DensityUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * dp转px
	 * 
	 * @param dpVal
	 * @return
	 */
	public static int dp2px(float dpVal)
	{Context context= Scope.activity;
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * sp转px
	 * 
	 * @param spVal
	 * @return
	 */
	public static int sp2px(float spVal)
	{Context context= Scope.activity;
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				spVal, context.getResources().getDisplayMetrics());
	}

	/**
	 * px转dp
	 * 
	 * @param pxVal
	 * @return
	 */
	public static float px2dp(float pxVal)
	{Context context= Scope.activity;
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxVal / scale);
	}

	/**
	 * px转sp
	 * 
	 * @param pxVal
	 * @return
	 */
	public static float px2sp(float pxVal)
	{Context context= Scope.activity;
		return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
	}

}
