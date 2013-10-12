<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>unit_welcome</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	var message = null;
	var welcomeID = null;
	var welcomeMsg = null;//单位确认的欢迎首页
	//var showMsg = null;//选择框预览的图文信息
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
				str += "<div id='select_a'><a href='javascript:;'>选择</a></div></div>";
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
				init(welcomeID,"left");
				init(1,"right");
				/* welcomeMsg = picMsgJson.message[welcomeID-1];
				
				showMsg = picMsgJson.message[welcomeID-1].message.picTitle[0]; */
				//alert(JSON.stringify(showMsg));
				
				//初始化
				/*$("#present_msg_box").append("<p>"+JSON.stringify(welcomeMsg)+"</p>");
				$("#select_msg_box").append("<p>"+JSON.stringify(showMsg)+"</p>");
				
				$("#present_id").append("<div id='welcome_id'><b>"+welcomeID+"</b></div>");
				$("#select_hint").append("<div id='welcome_id'><b>请选择首页</b></div>");*/
				
			}
		}
	);
	if(flag){
		alert("error");
	}
	$("#selected_msg").change(function(){
		var value = $("#selected_msg").val();
		//showMsg = picMsgJson.message[value-1];
		/*alert(JSON.stringify(showMsg));*/
		//触发右边的预览改变
		init(value,"right");
		//$("#select_msg_box").text(JSON.stringify(showMsg));
	});
	var select_flag = false;
	$("#select_a").click(function(){
		select_flag = true;
		var value = $("#selected_msg").val();
		$("#welcome_id").children().text(value);
		welcomeMsg = picMsgJson.message[value-1];
		//alert(JSON.stringify(welcomeMsg));
		//触发左边的预览改变
		init(value,"left");
		//$("#present_msg_box").text(JSON.stringify(welcomeMsg));
	});
	
	$("#submit_button").click(function(){
		//alert(JSON.stringify(welcomeMsg));
		if(select_flag==false){
			alert("没有更改");
		}
		else{
			var tmpID = welcomeMsg.ID;
			//alert(tmpID);
			message.welcomeMessage = tmpID;
			var req = JSON.stringify(message);
			//alert(req);
			$.post("message?action=updateUM",
				{data:req},
				function(data){
					alert(data);
				}
			);
		}
		select_flag = false;
	});

	function init(temp_id, temp){
		var target = jsonSearch(temp_id);
		//alert(JSON.stringify(target))
		var temp_title = target.message.picTitle[0];
		var temp_date = target.updateTime;
		var temp_msg = target.message.picDesc[0];
		var temp_url = target.message.picUrl[0];
		temp_msg = temp_msg.replace(/\s\n/g,"<br/>");
		if(temp == "right"){
			$("#temp_title").children("span").html(temp_title);
			$("#temp_date").children("span").html(temp_date);
			$("#temp_img").attr("src",temp_url);
			$("#temp_msg").css({"line-height":"30px"}).html(temp_msg);
		}else if(temp == "left"){
			$("#cur_title").children("span").html(temp_title);
			$("#cur_date").children("span").html(temp_date);
			$("#cur_img").attr("src",temp_url);
			$("#cur_msg").css({"line-height":"30px"}).html(temp_msg);
		}else
			alert("error");
	}

	function jsonSearch(temp_id){
		if(!picMsgJson.message){
			alert("json error");
			return;
		}
		for(var i=0; i<picMsgJson.message.length; i++){
			if(picMsgJson.message[i].ID == temp_id){
				return picMsgJson.message[i];
			}
		}
		if(i==0)
			alert("json error");
		return;
	}
});
	
</script>
<style type="text/css">
	#main{
		height:600px;
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
		height:410px;
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
	.temp_div{
		border: 1px solid #eee;
		display: block;
		-webkit-border-radius:5px;
		border-radius:5px;
		-webkit-box-shadow:0 0 5px rgba(0,0,0,0.8);
		-moz-box-shadow:0 0 5px rgba(0,0,0,0.8);
	}
	.template{
		width: 250px;
		color: #333;
	}
	.template tr{
		height: 25px;
	}
	.temp_fields{
		width: 70px;
		font-size: 14px;
		text-align: left;
		padding: 5px 14px;
	}
	.temp_fields span{
		line-height: 14px;
	}
	#temp_title,#cur_title{
		text-align: left;
		padding: 5px 10px;
	}
	#temp_date,#cur_date{
		text-align: left;
		padding: 2px 14px;
	}
	#temp_img,#cur_img{
		height: 113px;
		width: 230px;
	}
	#temp_date span,#cur_date span{
		font-size: 10px;
		color: #aaa;
	}
	#temp_title span,#cur_title span{
		font-size: 16px;
		word-spacing: 1px;
		color: #000;
	}
 	table {
   		border-collapse: collapse;
 	}
 	th, td {
 		padding: 0 0;
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
						<div class="temp_div">
							<table class="template">
								<tr>
									<th id="cur_title">
										<span></span>
									</th>
								</tr>
								<tr>
									<td id="cur_date">
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										<img src="" alt="pic" id="cur_img">
									</td>
								</tr>
								<tr>
									<td id="cur_msg" class="temp_fields">
										<span></span>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div id="submit_button"><a href="javascript:;">确认</a></div>
				</div>
				<!-- 供选择的首页 -->
				<div id="select_msg">
					<div id="select_hint" class="align_center"></div>
					<!-- 这里就是放图文信息的框 -->
					<div class="msg_box" id="select_msg_box">
						<div class="temp_div">
							<table class="template">
								<tr>
									<th id="temp_title">
										<span></span>
									</th>
								</tr>
								<tr>
									<td id="temp_date">
										<span></span>
									</td>
								</tr>
								<tr>
									<td>
										<img src="" alt="pic" id="temp_img">
									</td>
								</tr>
								<tr>
									<td id="temp_msg" class="temp_fields">
										<span></span>
									</td>
								</tr>
							</table>
						</div>
					</div>
					
					<div id="select_box" class="align_center"></div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="foot.jsp" flush="true" />
</body>
</html>