<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>unit_welcome</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript">

function printPicMsg(div_id,json){
	str 
}
$(document).ready(function(){
	var message = null;
	var welcomeID = null;
	var welcomeMsg = null;//单位确认的欢迎首页
	var showMsg = null;//选择框预览的图文信息
	var picMsgJson = null;//图文素材json
	var picMsgNum = null;//图文素材数量
	var flag = false;
	$.ajaxSettings.async = false;
	$.get("sources?action=get",
		function(data){
			if(data=="error"){
				flag = true;
			}else{
				picMsgJson = eval("("+data+")");
				picMsgNum = picMsgJson.messageNum;
				var str = "<div id='select_box_container'><div style='float:left'><select id='selected_msg'>";
				for(var i=0;i<picMsgNum;i++){
					str += "<span>图文消息ID：</span><option value='"+(i+1)+"'>"+(i+1)+"</option>";
				}
				str += "</select></div>";
				str += "<div id='select_a'><a href='javascript:;'>选择</a></div></div>"
				$("#select_box").append(str);
			}
		}
	);
	$.get("message?action=get",
		function(data){
			if(data=="error"){
				flag = true;
			}else{
				message = eval("("+data+")");
				welcomeID = message.welcomeMessage;
				welcomeMsg = picMsgJson.message[welcomeID-1];
				showMsg = picMsgJson.message[0];
				//初始化
				$("#present_msg_box").append("<p>"+JSON.stringify(welcomeMsg)+"</p>");
				$("#select_msg_box").append("<p>"+JSON.stringify(showMsg)+"</p>");
				
				$("#present_id").append("<div id='welcome_id'><b>"+welcomeID+"</b></div>");
				$("#select_hint").append("<div id='welcome_id'><b>请选择首页</b></div>");
			}
		}
	);
	if(flag){
		alert("error");
	}
	$("#selected_msg").change(function(){
		var value = $("#selected_msg").val();
		showMsg = picMsgJson.message[value-1];
		alert(JSON.stringify(showMsg));
		//触发右边的预览改变
		$("#select_msg_box").text(JSON.stringify(welcomeMsg));
	});
	$("#select_a").click(function(){
		var value = $("#selected_msg").val();
		$("#welcome_id").children().text(value);
		welcomeMsg = picMsgJson.message[value-1];
		alert(JSON.stringify(welcomeMsg));
		//触发左边的预览改变
		$("#present_msg_box").text(JSON.stringify(welcomeMsg));
	});
});
</script>
<style type="text/css">
	#main{
		height:500px;
	}
	#present_msg{
		float:left;
		width:270px;
		height:370px;
		//border:1px solid #ccc;
	}
	#select_msg{
		float:left;
		width:270px;
		height:370px;
		margin-left:26px;
		//border:1px solid #ccc;
	}
	#select_box_container{
		width:130px;
		margin-left:auto;
		margin-right:auto;
	}
	.align_center{
		margin-top:10px;
		text-align:center;
	}
	.msg_box{
		width:250px;
		height:280px;
		margin-left:auto;
		margin-right:auto;
		margin-top:15px;
		//border:1px solid #ccc;
		text-align:center;
	}
	#selected_msg{
		width:70px;
	}
	#select_hint{
		text-align:center;
		line-hight:80px;
	}
	#select_hint span{
		line-hight:50px;
	}
	#submit_button{
		margin-left:auto;
		margin-right:auto;
		margin-top:10px;
		text-align:center;
		width:45px;
		height:25px;
		background:#6bae2b;
		border-radius:3px;
	}
	#submit_button a{
		color:white;
		line-height:25px;
		font-weight:800;
	}
	#select_a{
		float:left;
		width:45px;
		height:25px;
		border-radius:3px;
		margin-left:10px;
		background:#6bae2b;
		color:white;
	}
	#select_a a{
		color:white;
		line-height:25px;
		font-weight:800;
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
					<li class="left_nav"><a href="unit_id.jsp">APPID和Secret</a></li>
					<li class="left_nav_selected"><a href="javascript:;">微信首页设置</a></li>
				</ul>
			</div>
			<div id="main_content">
				<!-- 已选择的首页 -->
				<div id="present_msg">
					<div id="present_id" class="align_center"></div>
					<!-- 这里就是放图文信息的框 -->
					<div class="msg_box" id="present_msg_box">
						
					</div>
					<div id="submit_button"><a href="javascript:;">确认</a></div>
				</div>
				<!-- 供选择的首页 -->
				<div id="select_msg">
					<div id="select_hint" class="align_center"></div>
					<!-- 这里就是放图文信息的框 -->
					<div class="msg_box" id="select_msg_box">
						
					</div>
					
					<div id="select_box" class="align_center"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp" flush="true" />
</body>
</html>