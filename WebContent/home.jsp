<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%
	//code by wjh
	String error = (String) request.getAttribute("error");
	if (error != null) {
		out.println("<script>alert('" + error + "');top.location='login.jsp';</script>");
	}

	//code by galin
	if (session.getAttribute("authorize.user.name") == null)
		out.println("<script>alert('" + error + "');top.location='login.jsp';</script>");
		//response.sendRedirect("/login.jsp");
	else {
		if (session.getAttribute("authorize.user.home") != null)
			response.sendRedirect((String) session
					.getAttribute("authorize.user.home"));
	}
%>
