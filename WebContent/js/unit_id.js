$(document).ready(function(){
	var appid_html = $("#appid").children("span").html();
	var secret_html = $("#secret").children("span").html();
	$.get("unit?action=get",
		function(data,status){
			//alert(data);
			data = $.trim(data);
			if(data=="error")
				alert("error");
			else{
				json = eval("("+data+")");
				$("#appid").children("span").text(json.appid);
				$("#secret").children("span").text(json.secret);
				//先保存未修改的内容
				appid_html = $("#appid").children("span").html();
				secret_html = $("#secret").children("span").html();
			}
		}
	);
	var flag = true;
	var change_val = ["修改","取消"];
	var sure_flag = false;
	$("#change_button").click(function(){
		flag = !flag;
		if(!flag){
			sure_flag = true;
			$("#appid").children("span").attr("contenteditable","");
			$("#appid").children("span").css("border","1px solid #6bae2b");
			$("#secret").children("span").attr("contenteditable","");
			$("#secret").children("span").css("border","1px solid #6bae2b");
			$("#change_button").text(change_val[1]);
		}
		else{
			sure_flag = false;
			$("#appid").children("span").html(appid_html);
			$("#secret").children("span").html(secret_html);
			$("#appid").children("span").removeAttr("contenteditable");
			$("#appid").children("span").css("border","0px");
			$("#secret").children("span").removeAttr("contenteditable");
			$("#secret").children("span").css("border","0px");
			$("#change_button").text(change_val[0]);
		}
	});
	$("#sure_button").click(function(){
		flag = true;
		$("#appid").children("span").removeAttr("contenteditable");
		$("#appid").children("span").css("border","0px");
		$("#secret").children("span").removeAttr("contenteditable");
		$("#secret").children("span").css("border","0px");
		$("#change_button").text(change_val[0]);
		json.appid = $("#appid").children("span").text();
		json.secret = $("#secret").children("span").text();
		var req = JSON.stringify(json);
		//alert(req);
		if(sure_flag){
			sure_flag = false;
			//alert(req);
			$.post("unit?action=update",
				{data:req},
				function(data,status){
					if($.trim(data)=="ok"){
						//先保存未修改的内容
						appid_html = $("#appid").children("span").html();
						secret_html = $("#secret").children("span").html();
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