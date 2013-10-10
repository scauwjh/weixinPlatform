function print(id,tmpJson){
	//alert(id);
	var last_id = id+4;
	if(last_id>tmpJson.member.length){
		last_id = tmpJson.member.length;
	}
	$("#main_content").html("");
	for(var i=id*4;i<last_id;i++){
		var str = "";
		str += "<div class='member_container'>";
		str += "<div class='member_list'>";
		str += "<div><label>会员ID：</label>"+tmpJson.member[i].memberID+"</div>";
		str += "<div><label>联系电话：</label><span>"+tmpJson.member[i].telephone+"</span></div>";
		str += "<div><label>创建时间：</label>"+tmpJson.member[i].createTime+"</div><br><br>";
		str += "</div>";
		str += "<div class='operate'>";
		str += "<a href='javascript:;' class='change_button button_class'>修改</a>";
		str += "<a href='javascript:;' class='sure_button button_class'>确定</a>";
		str += "</div>";
		str += "</div>";
		$("#main_content").append(str);
	}
	//alert("123");
}

$(document).ready(function(){
	var json = null;
	var page = 0;
	var allPage = 0;
	$.ajaxSettings.async = false;
	$.get("member?action=get",
		function(data,status){
			data = $.trim(data);
			if(data=="error")
				alert("no member");
			else{
				json = eval("("+data+")");
				print(page,json);
			}
		}
	);
	allPage = parseInt(json.member.length/4);
	var flag = false;
	var tel_val = null;
	var change_val = ["修改","取消"];
	$(".change_button").click(function(){
		if(!flag&&$(this).text()!=change_val[1]){
			flag = !flag;
			var obj = $(this).parent().prev().find("span");
			tel_val = obj.text();
			obj.attr("contenteditable","");
			obj.addClass("edit");
			$(this).text(change_val[1]);
		}
		else if(flag&&$(this).text()!=change_val[0]){
			flag = !flag;
			var obj = $(this).parent().prev().find("span");
			obj.text(tel_val);
			obj.removeAttr("contenteditable","");
			obj.removeClass("edit");
			$(this).text(change_val[0]);
		}
	});
	$(".sure_button").click(function(){
		var tel = $(this).parent().prev().find("span");
		tel_val = tel.text();
		tel.removeAttr("contenteditable","");
		tel.removeClass("edit");
		if(flag&&$(this).prev().text()!=change_val[0]){
			$(this).prev().text(change_val[0]);
			//获取当前div的index
			var id = $(this).parent().parent().index();
			var reqJson = {
				"memberID":json.member[id].memberID,
				"unitID":json.member[id].unitID,
				"coupon":json.member[id].coupon,
				"telephone":tel_val
			};
			var req = JSON.stringify(reqJson);
			//alert(req);
			$.post("member?action=update",
				{data:req},
				function(data,status){
					if($.trim(data)=="ok"){
						alert("更新成功！");
					}
					else{
						alert("error");
					}
				}
			);
			flag = false;
		}
		else{
			alert("没有任何修改");
		}
	});
	$("#next").click(function(){
		if(page<allPage){
			print(++page,json);
		}
	});
	$("#back").click(function(){
		if(page>0){
			print(--page,json);
		}
	});
});