<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>unit</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/unit_id.js"></script>
<style type="text/css">
 	#id_content{
 		float:left;
 		margin:5px 10px;
 		height:70px;
 		width:350px;
 	}
	#id_operate{
		float:left;
		margin:25px 30px;
 		width:130px;
	}
	#id_operate a{
		margin-left:6px;
		margin-right:0px;
		width:50px;
		height:30px;
		line-height:30px;
		font-size:15px;
	}
	.div_class{
		margin-bottom:12px;
		font-size:16px;
	}
	.div_class span{
		display:inline-block;
		width:282px;
	}
	#id_warning{
		margin:0 10px;
	}
</style>
</head>
<body>
	<jsp:include page="head.jsp" flush="true" />
	<div id="main">
		<div id="main_container">
			<div class="global_title">
				<span>单位信息</span>
			</div>
			<div id="main_left">
				<ul class="left_menu">
					<li class="left_nav"><a href="unit.jsp">单位介绍</a></li>
					<li class="left_nav_selected"><a href="unit_id.jsp">APPID和Secret</a></li>
					<li class="left_nav"><a href="javascript:;">其他</a></li>
				</ul>
			</div>
			<div id="main_content">
				<div id="id_content">
					<div id="appid" class="div_class"><b>appid：</b><span></span></div>
					<div id="secret" class="div_class"><b>secret：</b><span></span></div>
				</div>
				<div id="id_operate">
					<a class="button_class" title="点击修改" href="javascript:;" id="change_button">修改</a>
					<a class="button_class" title="点击确定" href="javascript:;" id="sure_button">确定</a>
				</div>
				<div class="clear" id="id_warning"><font color="red">注意：修改appid和secret请谨慎，修改前请与微信公众平台核对，否则会导致营销帐号无法使用</font></div>
			</div>
		</div>
	</div>
</body>
</html>