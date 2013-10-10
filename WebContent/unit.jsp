<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>unit</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/unit.js"></script>
<style type="text/css">
	#memo{
	 	float:left;
	 	width:355px;
	 	height:200px;
 	}
 	#memo_content{
 		margin:10px 10px;
 		height:150px;
 		width:352px;
 	}
	#qrcode{
		float:left;
		width:145px;
		height:140px;
		border:1px solid #ccc;
		margin:10px 20px;
	}
	.button_class{
		width:30px;
		height:20px;
		display: inline-block;
		background-color:#5ba10e;
		background: -webkit-gradient(linear,left top,left bottom,from(#6fb420),to(#51930d));
		color:#ffffff;
		border:1px solid #638467;
		-webkit-border-radius: 5px;
		text-align: center;
		line-height:20px;
		font-size:12px;
		text-decoration:none;
	}
	#unit_operate{
		height:150px;
 		width:355px;
		margin:-8px 280px;
		display:none;
	}
	#unit_operate a{
		margin-left:6px;
		margin-right:0px;
		width:30px;
		height:20px;
		line-height:20px;
		font-size:12px;
	}
	#main{
		height:350px;
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
					<li class="left_nav_selected"><a href="unit.jsp">单位介绍</a></li>
					<li class="left_nav"><a href="unit_id.jsp">APPID和Secret</a></li>
					<li class="left_nav"><a href="javascript:;">其他</a></li>
				</ul>
			</div>
			<div id="main_content">
				<div id="memo">
					<div id="memo_content">
						广州纪梵酒店于198x年开业， 200X年装修，拥有900多间房，位于广州市繁盛商业区
						之心脏地带，交通极为便利，是中国首批三家白金酒店之一。广州纪梵酒店装饰富丽堂皇，拥有
						客、套房和数百套寓及写字楼，设备豪华，环境典雅舒适。别具特色的中西餐厅及酒吧，荟萃中、
						法、日等多国风味美食，配合细致殷勤。
					</div>
					<div id="unit_operate">
						<a class="button_class" title="点击修改" href="javascript:;" id="change_button">修改</a>
						<a class="button_class" title="点击确定" href="javascript:;" id="sure_button">确定</a>
					</div>
				</div>
				<div id="qrcode">
					<div><img src="images/qrcode.jpg" width="143px" height="138px"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp" flush="true" />
</body>
</html>