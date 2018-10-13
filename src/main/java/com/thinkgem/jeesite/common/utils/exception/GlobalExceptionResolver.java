/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.thinkgem.jeesite.common.utils.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.thinkgem.jeesite.common.mapper.JsonMapper;

/**
 * 
 * @author yaohailu
 * @version 2018年10月13日
 */
@ControllerAdvice
public class GlobalExceptionResolver extends DefaultHandlerExceptionResolver {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception {
		String url = request.getServletPath();
		System.out.println("请求方式错误:" + ex.toString());
		if (url.contains("/api")) {// api返回异常拦截

			if (ex instanceof HttpRequestMethodNotSupportedException) {
				setResponseParam(response, 405, "请求方式错误！");
				return null;
			}

			if (ex instanceof MissingServletRequestParameterException) {
				setResponseParam(response, 400, "错误请求！");
				return null;
			}

			if (ex instanceof NoHandlerFoundException) {
				// 可以进行其他方法处理，LOG或者什么详细记录，我这里直接返回JSON
				setResponseParam(response, 404, "请求路径错误！");
				return null;
			}

			setResponseParam(response, 500, "服务器内部错误！服务暂时不可用！");
			return null;
		}

		// 这里调用父类的异常处理方法，实现其他不需要的异常交给SpringMVC处理
		return super.doResolveException(request, response, handler, ex);

	}

	private void setResponseParam(HttpServletResponse response, int code, String msg) throws IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(JsonMapper.toJsonString(code + ":" + msg));
	}
}