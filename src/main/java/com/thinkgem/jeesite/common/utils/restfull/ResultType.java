package com.thinkgem.jeesite.common.utils.restfull;

import java.io.Serializable;

/**
 * 返回结果类型
 * 
 * @author 长春叭哥
 * @version 2018-10-12
 */
public class ResultType implements Serializable {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1450317220382328032L;

	/**
	 * 操作失败 0
	 */
	public final static String ERROR = "0";
	/**
	 * 操作成功 1
	 */
	public final static String SUCCESS = "1";
	
	

}
