<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>欢迎登录</title>
	<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#sub").click(function(){
				var user = $("#user").val();
				var pass = $("#pass").val();
				if(user==""||pass==""){
					alert("帐号密码不能为空");
				}
				else if(pass.length>30){
					alert("密码长度不能超过30");
				}
				else{
					$("#loginForm").submit();
				}
			});
		});
	</script>
</head>
<body>
	<%
		String error = (String)request.getAttribute("error");
		if(error!=null){
			out.println("<script>alert('"+error+"');top.location='login.jsp';</script>");
		}
		else{
			String  realpath  =  request.getServletContext().getRealPath("/");
			System.out.println(realpath);
		}
	%>
	<form method="post" action="login" id="loginForm">
		<input type="text" id="user" name="account" value=""><br>
		<input type="password" id="pass" name="password" value=""><br>
		<input type="submit" id="sub" value="登录">
	</form>
</body>
</html>