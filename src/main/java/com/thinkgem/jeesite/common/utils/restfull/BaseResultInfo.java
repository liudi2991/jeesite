package com.thinkgem.jeesite.common.utils.restfull;

import java.io.Serializable;
import java.util.HashMap;

import java.util.Map;

/**
 * Restfull API Common ResultInfo class
 * 
 * @param <T> 返回数据
 * @author yaohailu
 * @version 2018年10月11日
 */
public class BaseResultInfo<T> implements Serializable {

	protected static final long serialVersionUID = -2368446516546812379L;

	/**
	 * Result Data
	 */
	protected T data;
	protected String code = ResultType.ERROR;
	protected String msg;

	/**
	 * 获取默认结果集实例
	 * 
	 * @return
	 * @author yaohailu
	 */
	public static Map<String, Object> getDefaultDataInstance() {
		return new HashMap<String, Object>();
	}

	/**
	 * 返回默认结果集
	 * 
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 * @author yaohailu
	 */
	public static BaseResultInfo<Map<String, Object>> getDefaultMapResultInfoInstance(String code, String msg, Map<String, Object> data) {
		return new BaseResultInfo<Map<String, Object>>(code, msg, data);
	}

	/**
	 * 创建一个新的BaseResultInfo 实例对象
	 * @param code
	 * @param msg
	 * @param data
	 * @return
	 * @author yaohailu
	 */
	public BaseResultInfo<T> getDefaultResultInfoIn(String code, String msg, T data) {
		return new BaseResultInfo<T>(code, msg, data);
	}

	public BaseResultInfo() {

	}

	public BaseResultInfo(T t) {
		setData(t);
	}

	public BaseResultInfo(String code, String msg) {
		setCode(code);
		setMsg(msg);
	}

	public BaseResultInfo(String code, String msg, T t) {
		this(code, msg);
		setData(t);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}