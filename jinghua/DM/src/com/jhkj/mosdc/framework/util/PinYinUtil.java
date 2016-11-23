package com.jhkj.mosdc.framework.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil {
	
	private static HanyuPinyinOutputFormat pinyinFormat = new HanyuPinyinOutputFormat();
	static{
		pinyinFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		pinyinFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyinFormat.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);
	}
	
	public static String[] getPinYin(char c) throws Throwable{
		return PinyinHelper.toHanyuPinyinStringArray(c, pinyinFormat);
	}
	
}
