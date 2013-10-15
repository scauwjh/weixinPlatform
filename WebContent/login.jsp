<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	session="true"%>
<%@ page import="cn.edu.scau.tvprotal.core.authorize.CryptoUtil"%>
<%@ page import="java.security.KeyPair"%>
<%@ page import="java.security.interfaces.RSAPublicKey"%>
<%
	//code by wjh
	String realpath = request.getServletContext().getRealPath("/");
	System.out.println(realpath);

	// RSA の鍵生成から
	KeyPair kp = CryptoUtil.generateRSAKey();
	session.setAttribute("authorize.key", kp);

	RSAPublicKey pubkey = (RSAPublicKey) kp.getPublic();
%>
<html>
<head>
<title></title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="js/login/jquery-1.10.2.js"></script>
<!-- RSA encryption implemention by "Tom Wu"<http://www-cs-students.stanford.edu/~tjw/jsbn/> -->
<script src="js/login/crypto/jsbn.js"></script>
<script src="js/login/crypto/prng4.js"></script>
<script src="js/login/crypto/rng.js"></script>
<script src="js/login/crypto/rsa.js"></script>
<script>
$(document).ready(function(){
	$("#login").click(function() {
		var cipher = new RSAKey();
		cipher.setPublic("<%=pubkey.getModulus().toString(16)%>", "<%=pubkey.getPublicExponent().toString(16)%>");
		var encrypted = cipher.encrypt($("#pass").val());
		console.log(encrypted);
		$.post("cgi/authorize/adminLogin", 
		{
			"name" : $("#name").val(),
			"pass" : encrypted
		}).done(function(resp) {
 			location.href = "home.jsp";
 		}).fail(function(){
 			alert("帐号或者密码不正确");
 		});
	});
});
</script>

</head>
<body>
	<form action="javascript:;">
		<input id="name" /><br />
		<input type="password" id="pass" /><br />
		<input type="submit" id="login" value="登录" />
	</form>
</body>
</html>
