<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>unit</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.get("unit?action=get",
			function(data,status){
				//alert(data);
				data = $.trim(data);
				if(data=="error")
					alert("error");
				else{
					json = eval("("+data+")");
					$("#memo").text(json.unitMemo);
				}
			}
		);
	});
</script>
<style type="text/css">
	#memo{
	 	float:left;
	 	width:355px;
	 	height:260px;
	 	margin:0px 15px;
 	}
	#qrcode{
		float:left;
		width:145px;
		height:140px;
		border:1px solid #ccc;
		margin:0px 5px;
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
					<li class="left_nav_selected"><a href="#">单位介绍</a></li>
					<li class="left_nav"><a href="#">APPID和Secret</a></li>
					<li class="left_nav"><a href="#">其他</a></li>
				</ul>
			</div>
			<div id="main_content">
				<div id="memo">
					广州纪梵酒店于198x年开业， 200X年装修，拥有900多间房，位于广州市繁盛商业区
					之心脏地带，交通极为便利，是中国首批三家白金酒店之一。广州纪梵酒店装饰富丽堂皇，拥有
					客、套房和数百套寓及写字楼，设备豪华，环境典雅舒适。别具特色的中西餐厅及酒吧，荟萃中、
					法、日等多国风味美食，配合细致殷勤。<br><br>电话020-XXXXXX<br>传真020-XXXXX
				</div>
				<div id="qrcode">
					<img src="images/qrcode.jpg" width="143px" height="138px">
				</div>
			</div>
		</div>
	</div>
</body>
</html>