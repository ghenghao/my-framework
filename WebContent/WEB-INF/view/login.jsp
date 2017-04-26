<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
</head>
<body>
	<shiro:guest>
		<form action="<%=request.getContextPath() %>/login" method="post">
			<table>
				<tr>
					<td>用户名:</td>
					<td><input type="text" name="username"></td>
				</tr>
				<tr>
					<td>密码:</td>
					<td><input type="password" name="password"></td>
				</tr>
				<tr><td>
					<button type="submit">登录</button>
				</td></tr>
			</table>
		</form>
	</shiro:guest>
	
	<shiro:user>
		<p>身份：<shiro:principal/></p>
	</shiro:user>
</body>
</html>