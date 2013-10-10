<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Member</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/member.js"></script>
<link rel="stylesheet" type="text/css" href="css/member.css">
</head>
<body>
	<jsp:include page="head.jsp" flush="true" />
	<div id="main">
		<div id="main_container">
			<div class="global_title">
				<span>会员管理</span>
			</div>
			<div id="main_left">
				<ul class="left_menu">
					<li class="left_nav_selected"><a href="javascript:;">会员管理</a></li>
				</ul>
			</div>
			<div id="main_content">
			</div>
			<div class="page">
				<a href='javascript:;' id='back'>上一页</a>
				<a href='javascript:;' id='next'>下一页</a>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp" flush="true" />
</body>
</html>