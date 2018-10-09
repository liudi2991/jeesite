/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import org.junit.Test;

import com.thinkgem.jeesite.common.utils.pinyin.ChineseHelper;
import com.thinkgem.jeesite.common.utils.pinyin.PinyinException;
import com.thinkgem.jeesite.common.utils.pinyin.PinyinFormat;
import com.thinkgem.jeesite.common.utils.pinyin.PinyinHelper;

/**
 * 
 * @author yaohailu
 * @version 2018年10月9日
 */
public class PinyinUtils {

	/**
	 * ChineseHelper.<br/>
	 * java You can use ChineseHelper to operate chinese charator.
	 * 
	 * @author 长春叭哥
	 */
	@Test
	public void chineseHelperTest() {
		System.out.println(ChineseHelper.convertToTraditionalChinese("大家好，我是长春叭哥"));
	}

	/**
	 * PinyinHelper.<br/>
	 * 
	 * @throws PinyinException
	 * @author yaohailu
	 */
	@Test
	public void pinyinHelperTest() throws PinyinException {
		System.out.println(PinyinHelper.convertToPinyinString("大家好，我是长春叭哥", "_", PinyinFormat.WITH_TONE_MARK));
	}
}
