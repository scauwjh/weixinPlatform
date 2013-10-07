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
					for(var i=0;i<json.member.length;i++){
						var str = "";
						str += "<div class='member_container'>";
						str += "<div class='member_list'>";
						str += "<p>会员ID："+json.member[0].memberID+"</p>";
						str += "<p>联系电话："+json.member[0].telephone+"</p>";
						str += "<p>创建时间："+json.member[0].createTime+"</p><br><br>";
						str += "</div>";
						str += "<div class='operate'><button>修改</button></div>";
						str += "</div>";
						$("#main_content").append(str);
					}
				}
			}
		);
	});
</script>
<style type="text/css">
	.member_list{
		float:left;
		width:450px;
	}
	.operate{
		float:left;
		width:100px;
		margin:30px 0;
	}
	.member_container{
		width:550px;
		height:100px;
	}
</style>
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
					<li class="left_nav_selected"><a href="#">会员管理</a></li>
				</ul>
			</div>
			<div id="main_content">
			</div>
		</div>
	</div>
</body>
</html>