<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>unit</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var memo_html = $("#memo_content").html();
		$.get("unit?action=get",
			function(data,status){
				//alert(data);
				data = $.trim(data);
				if(data=="error")
					alert("error");
				else{
					json = eval("("+data+")");
					$("#memo_content").text(json.unitMemo);
					//先保存未修改的内容
					memo_html = $("#memo_content").html();
				}
			}
		);
		var flag = true;
		$("#memo_content").hover(function(){
			if(flag)
				$("#unit_operate").css("display","block");
		});
		$("#memo").hover(function(){
			if(flag)
				$("#unit_operate").css("display","none");
		});
		var change_val = ["修改","取消"];
		var sure_flag = false;
		$("#change_button").click(function(){
			flag = !flag;
			if(!flag){
				sure_flag = true;
				$("#memo_content").attr("contenteditable","");
				$("#memo_content").css("border","1px solid #6bae2b");
				$("#change_button").text(change_val[1]);
			}
			else{
				sure_flag = false;
				//alert(memo_html);
				$("#memo_content").html(memo_html);
				$("#memo_content").removeAttr("contenteditable");
				$("#memo_content").css("border","0px");
				$("#change_button").text(change_val[0]);
			}
		});
		$("#sure_button").click(function(){
			flag = true;
			$("#memo_content").removeAttr("contenteditable");
			$("#memo_content").css("border","0px");
			$("#change_button").text(change_val[0]);
			json.unitMemo = $("#memo_content").html();
			var req = JSON.stringify(json);
			//alert(req);
			if(sure_flag){
				sure_flag = false;
				$.post("unit?action=update",
					{data:req},
					function(data,status){
						if($.trim(data)=="ok"){
							memo_html = $("#memo_content").html();
							alert("更新成功！");
						}
					}
				);
			}
			else{
				alert("没有任何修改");
			}
		});
	});
</script>
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
</body>
</html>