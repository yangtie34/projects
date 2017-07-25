package com.eyun.framework.util.android;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.eyun.framework.angular.core.Scope;
import com.eyun.framework.util.CallBack;

import static com.eyun.framework.angular.core.Scope.activity;

/**
 * 打开或关闭软键盘
 * 
 * @author zhy
 * 
 */
public class KeyBoardUtils
{
	/**
	 * 打卡软键盘
	 * 
	 * @param mEditText
	 *            输入框

	 */
	public static void openKeybord(EditText mEditText)
	{
		InputMethodManager imm = (InputMethodManager) Scope.activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * 关闭软键盘
	 * 
	 * @param mEditText
	 *            输入框
	 */
	public static void closeKeybord(EditText mEditText)
	{
		InputMethodManager imm = (InputMethodManager) Scope.activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
	/**
	 * 点击EditText文本框之外任何地方隐藏键盘的解决办法
	 * @param ev
	 * @param pubInterface
	 * @return
	 */
	public static boolean dispatchTouchEvent(MotionEvent ev, CallBack pubInterface) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = activity.getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm != null) {
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				}
			}
			return (Boolean) pubInterface.run();
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (activity.getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return activity.onTouchEvent(ev);

	}

	/**
	 * 是否应该隐藏输入法
	 * @param v
	 * @param event
	 * @return
	 */
	public static  boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = {0, 0};
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				ViewUtil.getRootView().setFocusable(true);
				ViewUtil.getRootView().setFocusableInTouchMode(true);
				ViewUtil.getRootView().requestFocus();
				return true;
			}
		}
		return false;
	}
}
