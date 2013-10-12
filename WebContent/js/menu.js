var json;
$(document).ready(function() {
	$.ajaxSettings.async = false; //设置同步请求
	$.get("menu?action=get",
		function(data, status) {
			if(data=="error"){
				json = {"button":[]};
			}
			else{
				json = eval("(" + data + ")");
			}
		}
	);
	$.ajaxSettings.async = true; //设置异步请求
	init();
	$(".i_icon1").hover(function() {
		$(this).toggleClass("i_icon1_hover");
	});
	$(".i_icon2").hover(function() {
		$(this).toggleClass("i_icon2_hover");
	});
	$(".btn").hover(function() {
		$(this).toggleClass("btn_hover");
	});
	$("#dialog-add").dialog({ //添加菜单节点
		autoOpen: false,
		height: 300,
		width: 500,
		modal: true,
		draggable: true,
		buttons: {提交: function() {
				var temp_id = new_id();
				var temp_name = $("#dialog-add").find("input").val();
				var place = $("#dialog-add").children("div").eq(0).html();
				for (var i = 0; i < json.button.length; i++) {
					if (json.button[i].id == place) {
						if (!json.button[i].sub_button) {
							json.button[i].sub_button = [];
							delete json.button[i].type;
							if (json.button[i].message)
								delete json.button[i].message;
							if (json.button[i].url)
								delete json.button[i].url;
						}
						json.button[i].sub_button.push({
							"id": temp_id,
							"type": "view",
							"name": temp_name,
							"url": "#"
						});
					}
				}
				init();
				//var req = JSON.stringify(json);
				//alert(req);
				$("#dialog-add").find("input").val("").end().dialog("close");
			},
			返回: function() {
				$("#dialog-add").dialog("close");
			}
		}
	});
	$("#dialog-del").dialog({ //删除节点
		autoOpen: false,
		height: 220,
		width: 300,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				var temp_name = $("#dialog-del").find("input").val();
				var temp_id = $("#dialog-del").children("div").eq(0).html();
				//alert(temp_id);
				for (var i = 0; i < json.button.length; i++) {
					if (json.button[i].id == temp_id) {
						json.button.splice(i, 1);
						break;
					}
					if (json.button[i].sub_button) {
						for (var j = 0; j < json.button[i].sub_button.length; j++) {
							if (json.button[i].sub_button[j].id == temp_id) {
								json.button[i].sub_button.splice(j, 1);
								break;
							}
						}
					}
					if (json.button[i].sub_button && json.button[i].sub_button == "") {
						delete json.button[i].sub_button;
						json.button[i].type = "view";
						json.button[i].url = "#";
					}
				}
				init();
				//var req = JSON.stringify(json);
				//alert(req);
				$("#dialog-del").find("input").val("").end().dialog("close");
			},
			返回: function() {
				$("#dialog-del").dialog("close");
			}
		}
	});
	$("#dialog-mod").dialog({ //修改菜单名称
		autoOpen: false,
		height: 300,
		width: 500,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				var temp_name = $("#dialog-mod").find("input").val();
				var temp_id = $("#dialog-mod").children("div").eq(0).html();
				//alert(temp_id);
				for (var i = 0; i < json.button.length; i++) {
					if (json.button[i].id == temp_id) {
						json.button[i].name = temp_name;
						break;
					}
					if (json.button[i].sub_button) {
						for (var j = 0; j < json.button[i].sub_button.length; j++) {
							if (json.button[i].sub_button[j].id == temp_id) {
								json.button[i].sub_button[j].name = temp_name;
								break;
							}
						}
					}
				}
				init();
				//var req = JSON.stringify(json);
				//alert(req);
				$("#dialog-mod").find("input").val("").end().dialog("close");
			},
			返回: function() {
				$("#dialog-mod").dialog("close");
			}
		}
	});
	$("#dialog-form").dialog({
		autoOpen: false,
		height: 300,
		width: 500,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				var temp_name = $("#dialog-form").find("input").val();
				var temp_id = new_id();
				json.button.push({
					"id": temp_id,
					"type": "view",
					"name": temp_name,
					"url": "#"
				});
				init();
				//var req = JSON.stringify(json);
				//alert(req);
				$("#dialog-form").find("input").val("").end().dialog("close");
			},
			返回: function() {
				$("#dialog-form").dialog("close");
			}
		}
	});
	$("#dialog-lang").dialog({
		autoOpen: false,
		height: 500,
		width: 700,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				alert("提交什么什么的");
			},
			返回: function() {
				$("#dialog-lang").dialog("close");
			}
		}
	});
	$("#dialog-pic").dialog({
		autoOpen: false,
		height: 500,
		width: 700,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				alert("提交什么什么的");
			},
			返回: function() {
				$("#dialog-pic").dialog("close");
			}
		}
	});
	$("#dialog-video").dialog({
		autoOpen: false,
		height: 500,
		width: 700,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				alert("提交什么什么的");
			},
			返回: function() {
				$("#dialog-video").dialog("close");
			}
		}
	});
	$("#dialog-pictxt").dialog({
		autoOpen: false,
		height: 500,
		width: 700,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				alert("提交什么什么的");
			},
			返回: function() {
				$("#dialog-pictxt").dialog("close");
			}
		}
	});
	$("#dialog-confirm").dialog({
		autoOpen: false,
		height: 300,
		width: 500,
		modal: true,
		draggable: false,
		buttons: {提交: function() {
				var req = JSON.stringify(json);
				alert(req);
				$.post("menu?action=post", {
						data: req
					},
					function(data, status) {
						alert(data);
					});
				$("#dialog-confirm").dialog("close");
			},
			返回: function() {
				$("#dialog-confirm").dialog("close");
			}
		}
	});
});
var temp, temp_id;

function open_dialog(temp, temp_id) {
	$("#dialog-" + temp).children("div").eq(0).html(temp_id);
	$("#dialog-" + temp).dialog("open");
	$('.ui-dialog-titlebar').css({
		'background-color': 'rgb(200,200,200)',
		'border': 'none'
	});
}

function left_turn() { //控制Left_list
	$(".list_item").click(function() {
		$(".list_item").removeClass("item_click");
		$(".list_item").removeClass("item_hover");
		$(".item_ul").removeClass("item_ul_hover");
		$(".right_mask").hide();
		$(this).toggleClass("item_click").children(".item_ul").addClass("item_ul_hover");
	});
	$(".list_item").hover(function() {
		if (!$(this).hasClass("item_click")) {
			$(this).addClass("item_hover").children(".item_ul").addClass("item_ul_hover");
		}
	}, function() {
		if (!$(this).hasClass("item_click")) {
			$(this).removeClass("item_hover").children(".item_ul").removeClass("item_ul_hover");
		}
	});
	$(".list_item[name='parents']").click(function() {
		$(".right_mask").show();
	});
}

function turn(temp, temp_id) { //控制Right隐藏div
	$(".right_nav,.right_url,.right_message").hide();
	if (temp == "nav") {
		$(".right_nav").show();
		$("#nav_id").val(temp_id);
	} else if (temp == "back") {
		$(".right_nav").show();
	} else if (temp == "url") {
		$(".right_url").show();
	} else if (temp == "message") {
		$(".right_message").show();
	}
}

function init() {
	var $content, temp_id;
	$(".left_list").empty();
	if (!json.button) {
		return;
	}
	for (var i = 0; i < json.button.length; i++) {
		$content = $("#clone").clone();
		$content.css("display", "block").attr("id", json.button[i].id);
		$content.attr("onclick", "turn('nav','" + json.button[i].id + "')").children("a").html(json.button[i].name);
		$content.find("a").eq(1).attr("onclick", "open_dialog('add','" + json.button[i].id + "')").end().eq(2).attr("onclick", "open_dialog('del','" + json.button[i].id + "')").end().eq(3).attr("onclick", "open_dialog('mod','" + json.button[i].id + "')");
		$(".left_list").append($content);
		if (json.button[i].sub_button) {
			$("#" + json.button[i].id).attr("name", "parents");
			for (var j = 0; j < json.button[i].sub_button.length; j++) {
				$content = $("#clone").clone();
				$content.css("display", "block").attr("id", json.button[i].sub_button[j].id);
				$content.attr("onclick", "turn('nav','" + json.button[i].sub_button[j].id + "')").children("a").html(json.button[i].sub_button[j].name);
				$content.find("a").eq(0).prepend("&bull;&nbsp;").end().eq(2).attr("onclick", "open_dialog('del','" + json.button[i].sub_button[j].id + "')").end().eq(3).attr("onclick", "open_dialog('mod','" + json.button[i].sub_button[j].id + "')");
				$content.find(".item_add").css("background", "none");
				$(".left_list").append($content);
			}
		}
		left_turn();
	}
}

function new_id() {
	var tag = new Array("1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
	for (var i = 0; i < json.button.length; i++) {
		tag[json.button[i].id] = 1;
		if (json.button[i].sub_button) {
			for (var j = 0; j < json.button[i].sub_button.length; j++)
				tag[json.button[i].sub_button[j].id] = 1;
		}
	}
	for (var i = 1; i < tag.length; i++) {
		if (tag[i] == 0) {
			return i;
		}
	}
}

function post(temp) {
	var tag_id = $("#nav_id").val();
	var tag_url = $("#input_url").val();
	var tag_msg = $("#box_msg").val();
	if (temp == "url") {
		if (tag_url == "") {
			alert("请输入内容");
			return;
		}
		for (var i = 0; i < json.button.length; i++) {
			if (json.button[i].id == tag_id) {
				if (json.button[i].type == "click") {
					json.button[i].type = "view";
					delete json.button[i].message;
					delete json.button[i].key;
					json.button[i].url = tag_url;
				} else if (json.button[i].type == "view") {
					json.button[i].url = tag_url;
				}
			}
			if (json.button[i].sub_button) {
				for (var j = 0; j < json.button[i].sub_button.length; j++) {
					if (json.button[i].sub_button[j].id == tag_id) {
						if (json.button[i].sub_button[j].type == "click") {
							json.button[i].sub_button[j].type = "view";
							delete json.button[i].sub_button[j].message;
							delete json.button[i].sub_button[j].key;
							json.button[i].sub_button[j].url = tag_url;
						} else if (json.button[i].sub_button[j].type == "view") {
							json.button[i].sub_button[j].url = tag_url;
						}
					}
				}
			}
		}
	} else if (temp == "msg") {
		if (tag_msg == "") {
			alert("请输入内容");
			return;
		}
		for (var i = 0; i < json.button.length; i++) {
			if (json.button[i].id == tag_id) {
				if (json.button[i].type == "click") {
					json.button[i].message.msg = tag_msg;
				} else if (json.button[i].type == "view") {
					json.button[i].type = "click";
					delete json.button[i].url;
					json.button[i].message = {
						"type": "msg",
						"msg": tag_msg
					};
					json.button[i].key = "key" + tag_id;
				}
			}
			if (json.button[i].sub_button) {
				for (var j = 0; j < json.button[i].sub_button.length; j++) {
					if (json.button[i].sub_button[j].id == tag_id) {
						if (json.button[i].sub_button[j].type == "click") {
							json.button[i].sub_button[j].message.msg = tag_msg;
						} else if (json.button[i].sub_button[j].type == "view") {
							json.button[i].sub_button[j].type = "click";
							delete json.button[i].sub_button[j].url;
							json.button[i].sub_button[j].message = {
								"type": "msg",
								"msg": tag_msg
							};
							json.button[i].sub_button[j].key = "keyEx" + tag_id;
						}
					}
				}
			}
		}
	}
	$("#box_msg").val("");
	$("#input_url").val("");
	//alert(JSON.stringify(json));
	//alert($("#nav_id").val());
	/*
	req = JSON.stringify(json);
	alert(req);
	//var req = JSON.stringify(json);
	$.post("menu?action=post",
	{data:req},
	function(data,status){
		alert(data);
	});
	*/
}