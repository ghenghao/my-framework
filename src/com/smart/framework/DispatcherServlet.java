package com.smart.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.smart.bean.Data;
import com.smart.bean.Handler;
import com.smart.bean.Param;
import com.smart.bean.View;
import com.smart.constant.FrameGlobalConfig;
import com.smart.helper.BeanHelper;
import com.smart.helper.ConfigHelper;
import com.smart.helper.ControllerHelper;
import com.smart.helper.HelperLoader;
import com.smart.helper.RequestHelper;
import com.smart.helper.UploadHelper;
import com.smart.utils.ArrayUtil;
import com.smart.utils.CodecUtil;
import com.smart.utils.JsonUtil;
import com.smart.utils.ReflectionUtil;
import com.smart.utils.StreamUtil;
import com.smart.utils.StringUtil;


/** 
 * Filename:     DispatcherServlet.java 
 * @Copyright:   Copyright (c)2015 
 * @Company:     chixun 
 * @author:     Cooley
 * @function:
 * @version:     1.0 
 * @Create at:   2015年12月8日 下午4:11:42 
 */
@WebServlet(urlPatterns = "/*", loadOnStartup=0)
public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6290192765354116272L;

	
	@Override
	public void init(ServletConfig config) throws ServletException {
		//初始化相关Helper类
		HelperLoader.init();
		//获取ServletContext对象(用于注册Servlet)
		ServletContext servletContext = config.getServletContext();
		//注册处理JSP的Servlet
		ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
		jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
		//注册处理静态资源的默认Servlet
		ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
		defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
		
		UploadHelper.init(servletContext);
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//获取请求方法与请求路径
			String requestMethod = request.getMethod().toLowerCase();
			String requestPath = request.getPathInfo();
			
			if("/favicon.ico".equals(requestPath)){
				return;
			}
			
			
			//获取Action处理器
			Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
			if(handler != null){
				//获取Controller类以及其bean实例
				Class<?> controllerClass= handler.getControllerClass();
				Object controllerBean = BeanHelper.getBean(controllerClass);
				
				Param param;
				
				if(UploadHelper.isMultipart(request)){
					param = UploadHelper.createParam(request);
				}else{
					param = RequestHelper.createParam(request);
				}
				
				//调用Action方法
				Method actionMethod = handler.getActionMethod();
				Object result ;
				if(param.isEmpty()){
					result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
				}else{
					result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
				}
				//处理Action方法返回值
				if(result instanceof View){
					//返回JSP页面
					View view = (View) result;
					handleViewResult(request, response, view);
				}else if(result instanceof Data){
					//返回JSON数据
					Data data = (Data) result;
					handleDataResult(response, data);
				}
			}
		} catch (Exception e) {
			Class<? extends Throwable> exceptionClass = e.getClass();
			//获取异常的来源,因为方法被代理后,异常全部是invoke异常
			Throwable cause = e.getCause();
			if(cause != null && cause instanceof java.lang.reflect.InvocationTargetException){
				Throwable targetException = ((java.lang.reflect.InvocationTargetException)cause).getTargetException();
				if(targetException != null){
					exceptionClass = targetException.getClass();
				}
			}
			
			if(FrameGlobalConfig.GlobalExceptionMap.containsKey(exceptionClass)){
				//处理定义的全局异常
				String errMsg = FrameGlobalConfig.GlobalExceptionMap.get(exceptionClass);
				if(errMsg.endsWith(".jsp")){
					handleViewResult(request, response, new View(errMsg));
				}else{
					handleDataResult(response, new Data(errMsg));
				}
			}else{
				//处理其他异常
				handleDataResult(response, new Data("服务器内部错误，请联系管理员！"));
			}
			e.printStackTrace();
		}
	}

	private void handleDataResult(HttpServletResponse response, Data data)
			throws IOException {
		Object model = data.getModel();
		if(model != null){
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter writer = response.getWriter();
			String json = JsonUtil.toJson(model);
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}

	private void handleViewResult(HttpServletRequest request,
			HttpServletResponse response, View view) throws IOException,
			ServletException {
		String path = view.getPath();
		if(StringUtil.isNotEmpty(path)){
			if(path.startsWith("/")){
				response.sendRedirect(request.getContextPath() + path);
			}else{
				//为JSP页面的参数赋值
				Map<String, Object> model = view.getModel();
				for(Map.Entry<String, Object> entry : model.entrySet()){
					request.setAttribute(entry.getKey(), entry.getValue());
				}
				request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path)
					.forward(request, response);
			}
		}
	}
	
	@Deprecated
	protected void service_old(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取请求方法与请求路径
		String requestMethod = request.getMethod().toLowerCase();
		String requestPath = request.getPathInfo();
		
		if("/favicon.ico".equals(requestPath)){
			return;
		}
		
		
		//获取Action处理器
		Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
		if(handler != null){
			//获取Controller类以及其bean实例
			Class<?> controllerClass= handler.getControllerClass();
			Object controllerBean = BeanHelper.getBean(controllerClass);
			//创建请求参数对象
			Map<String, Object> paramMap = new HashMap<String, Object>();
			///获取get中请求参数
			Enumeration<String> paramNames = request.getParameterNames();
			while(paramNames.hasMoreElements()){
				String paramName = paramNames.nextElement();
				String paramValue = request.getParameter(paramName);
				paramMap.put(paramName, paramValue);
			}
			///获取post中请求参数
			String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
			if(StringUtil.isNotEmpty(body)){
				String[] params = StringUtil.splitString(body, "&");
				if(ArrayUtil.isNotEmpty(params)){
					for(String param : params){
						String[] array = StringUtil.splitString(param, "=");
						if(ArrayUtil.isNotEmpty(array)){
							String paramName = array[0];
							String paramValue = array[1];
							paramMap.put(paramName, paramValue);
						}
					}
				}
			}
			
//			Param param = new Param(paramMap);
			Param param = new Param(null);
			//调用Action方法
			Method actionMethod = handler.getActionMethod();
			Object result ;
			if(param.isEmpty()){
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
			}else{
				result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
			}
			//处理Action方法返回值
			if(result instanceof View){
				//返回JSP页面
				View view = (View) result;
				handleViewResult(request, response, view);
			}else if(result instanceof Data){
				//返回JSON数据
				Data data = (Data) result;
				handleDataResult(response, data);
			}
		}
	}
}

