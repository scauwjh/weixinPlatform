<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Member</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var json;
		$.get("member?action=get",
			function(data,status){
				//alert(data);
				data = $.trim(data);
				if(data=="error")
					alert("no member");
				else{
					json = eval("("+data+")");
					//$("#test").html(data);
					$("#main_content").append("<p>会员ID："+json.member[0].memberID+"</p>");
					$("#main_content").append("<p>联系电话："+json.member[0].telephone+"</p>");
					$("#main_content").append("<p>创建时间："+json.member[0].createTime+"</p>");
				}
			}
		);
	});
</script>
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
			</div>
		</div>
	</div>
</body>
</html>