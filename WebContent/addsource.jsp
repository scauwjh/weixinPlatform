<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
Integer rand = (int)(Math.random()*100);
%>
<!DOCTYPE HTML>
<html>
<head>
<title>Manager</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json.js"></script>
<script type="text/javascript">
	var id = 0;
	var select = "way1";
	var tmpName = ".img1";
	var rand = "<%=rand%>";
	var type = 1;
	var picNum = 1;
	$(document).ready(function(){
		$("#getDetails").click(function(){
			id= !id;
			if(id==1){
				$("#wayDiv").css("width","540px");
				$("#details").fadeIn();
			}
			else{
				$("#wayDiv").css("width","340px");
				$("#details").css("display","none");
			}
		});
		$("[name='way']").change(function(){
			$("[name='way']").each(function(){
			    if($(this).attr("checked")=="checked"){
			    	select=$(this).val();
			    	tmpName = ".img1";
			    	type = 1;
			    	picNum = 1;
					if(select=="way2"){
						tmpName = ".img2";
						type = 2;
						picNum = 4;
					}
				}
			});
		});
		$("#submit").click(function(){
			var picUrl = new Array();
			var picDesc = new Array();
			var picTitle = new Array();
			var jumpUrl = new Array();
			for(var i=0;i<picNum;i++){
				picTitle[i] = $.trim($("#title"+type+"-"+(i+1)).html());
				picDesc[i] = $.trim($("#desc"+type+"-"+(i+1)).html());
				picUrl[i] = "http://61.28.112.242/weixin/images/picmsg/"+rand+(i+1)+".jpg";
				jumpUrl[i] = "http://www.baidu.com/";
			}
			var picMsg = {
					"type":type,
					"picNum":picNum,
					"picUrl":picUrl,
					"picTitle":picTitle,
					"picDesc":picDesc,
					"jumpUrl":jumpUrl
			};
			var msg = JSON.stringify(picMsg);
 			$("#picMsgid").val(msg);
			//alert(msg);
 			$("#messageForm").submit();
		});
		$(".img").click(function(){
			$(this).parent().nextAll("form").children("input").click();
		});
		$("#iframe1").load(function(){
			var iframe = window.frames[0];
			var ret = $(iframe.document.body).text();
			if($.trim(ret)=="ok"){
				for(var i=0,len=$(tmpName).length;i<len;i++){
					$(tmpName)[i].src = "images/picmsg/<%=rand%>"+(i+1)+".jpg?t="+ Math.random();
				}
			}
		});
	});
	
</script>
<style type="text/css">
	.margin{
		margin-top:10px;
		margin-left:13px;
		width:230px;
		color:#333333;
	}
	.box{
		border-radius:10px;
		border:1px solid #CCC;
		width:250px;
		height:270px;
	}
	.line{
		margin-top:5px;
		width:250px;
		height:1px;
		background:#CCC;
	}
	.shade{
		width:220px;
		height:20px;
		background:rgba(0,0,0,0.5);
		margin-top:-22px;
		color:white;
		font-size:12px;
		line-height:20px;
		padding-left:5px;
	}
	#main_right img{
		position:relative;
		z-index:-1;
	}
	#main{
		width:900px;
		height:800px;
		margin-top:20px;
	}
	#main_left{
		float:left;
		width:200px;
		height:300px;
		border:1px solid #CCC;
		margin-left:250px;
		margin-top:20px;
	}
	#main_right{
		float:left;
		width:600px;
		margin-left:20px;
		margin-top:20px;
	}
</style>
</head>
<body>	
	<jsp:include page="head.jsp" flush="true" />

	<div class="main">
		<div id="main_head">
			
		</div>
		
		<div id="main_left">
			这个是选择菜单列表
		</div>
		<div id="main_right">
			<div>
				<span>点击图文进行编辑</span>
				<button id="submit">提交</button>
			</div>
			
			<form method="post" action="weixinmessage?action=add" id="messageForm">
				<input type="hidden" name="picMsg" id="picMsgid">
			</form>
			
			<div id="message" style="width:900px;">
				<div style="float:left;width:340px;margin-top:30px;" id="wayDiv">
					<span>&nbsp;方案一</span>
					<input type="radio" name="way" value="way1" checked="checked"/>
					<br><br>
					<div class="box" style="float:left">
						<div class="margin">
							<div contenteditable="" style="width:225px;" id="title1-1">
								双椒炒腊肠
							</div>
						</div>
						
						<!-- 方案一 图片1 -->
						<div class="margin">
							<div class="img"><img src="images/picmsg/meishi.png" style="width:225px;height:115px;" class="img1"/></div>
							<div class="shade" contenteditable="" id="title1-2">
								炎炎夏日，清凉一夏
							</div>
						</div>
						<form method="post" action="WeixinManager?action=uploadFoodPic" target="form1" enctype="multipart/form-data">
							<input type="hidden" name="path" value="food">
							<input type="file" name="<%=rand%>1.jpg" value="" style="display:none;" onchange="this.parentNode.submit()" />
						</form>
						<!-- end -->
						
						<div class="margin">
							<div contenteditable="" style="width:225px;height:70px;font-size:13px;" id="desc1-1">
								 广州的夏天虽说已经快过去了，但是依然热得让人烦躁，这些天感觉特别的闷热，那么现在有什么消暑的美食呢？
							</div>
						</div>
						<div style="margin-left:10px;margin-top:5px;width:230px;height:1px;background:#ccc;"></div>
						<div style="color:#333;font-size:14px;margin-left:12px;line-height:25px;" id="getDetails">
							查看全文(点击编辑详细说明)&nbsp;&nbsp;&nbsp;>
						</div>
					</div>
					
					<div style="float:left;margin-left:20px;display:none;" id="details">
						<div class="box">
							<div style="text-align:center;">
								<span style="line-height:50px;color:#333;">详细介绍</span>
							</div>
							<div id="desc1-2" style="border:1px solid #CCC;color:#333;width:228px;height:208px;margin-left:10px;" contenteditable="">
								
							</div>
						</div>
					</div>
				</div>
				
				
				<div style="float:left;width:340px;margin-top:30px;">
					<span>&nbsp;方案二</span>
					<input type="radio" name="way" value="way2" />
					<br><br>
					<div class="box">
						<!-- 方案二 图片1 -->
						<div class="margin">
							<div class="img"><img src="images/picmsg/meishi.png" style="width:225px;height:120px;" class="img2" /></div>
							<div class="shade" contenteditable="" id="title2-1">
								炎炎夏日，清凉一夏
							</div>
						</div>
						<form method="post" action="WeixinManager?action=uploadFoodPic" target="form1" enctype="multipart/form-data">
							<input type="hidden" name="path" value="food">
							<input type="file" name="<%=rand%>1.jpg" value="" style="display:none;" onchange="this.parentNode.submit()" />
						</form>
						<!-- end -->
						
						<%for(int i=0;i<3;i++){%>
						<div class="line" style="<%=(i==0)?"margin-top:10px;":""%>"></div>
						<div class="margin" style="height:26px;">
							<div id="title2-<%=(i+2)%>" contenteditable="" style="width:180px;float:left;font-size:13px;line-height:25px;">
								响油芦笋
							</div>
							<!-- 方案二 图片2 -->
							<div style="float:left" class="picDiv">
								<div class="img"><img src="images/picmsg/meishi.png" style="width:40px;height:25px;" class="img2"></div>
							</div>
							<form method="post" action="WeixinManager?action=uploadFoodPic" target="form1" enctype="multipart/form-data">
								<input type="hidden" name="path" value="food">
								<input type="file" name="<%=rand%><%=(i+2)%>.jpg"value="" style="display:none;" onchange="this.parentNode.submit()" />
							</form>
							<!-- end -->
						</div>
						<%}%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 隐藏的iframe -->
	<iframe name="form1" style="display:none;" id="iframe1"></iframe>
<!--end main-->	
</body>
</html>