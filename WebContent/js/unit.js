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