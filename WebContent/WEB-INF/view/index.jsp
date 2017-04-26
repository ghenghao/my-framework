<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<shiro:guest>
		<p>身份：游客</p>
		<a href="<%=request.getContextPath() %>/login">登录</a>
	</shiro:guest>
	
	<shiro:user>
		<p>身份：<shiro:principal/></p>
	</shiro:user>
</body>
</html>