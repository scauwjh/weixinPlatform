<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<script type="text/javascript" src="js/json2.js"></script>
<link rel="stylesheet" type="text/css" href="css/menu.css" />
<link rel="stylesheet" type="text/css" href="css/global.css" />
<link rel="stylesheet" type="text/css" href="css/jquery-ui-1.8.17.custom.css" />
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
<script type="text/javascript" src="js/menu.js"></script>
</head>
<body>
	<jsp:include page="head.jsp" flush="true" />
	<div id="main">
		<div id="main_container">
			<div class="box">
				<div class="global_title">
					<span>编辑</span>
				</div>
				<div class="main">
					<div class="left">
						<div class="left_title"><span>&nbsp;菜单管理</span><a onclick="open_dialog('form')">添加菜单</a>
						</div>
						<div class="list_item" id="clone" style="display:none;"><a></a>		
							<ul class="item_ul">
								<li class="item_li"><a class="item_add"></a></li>
								<li class="item_li"><a class="item_del"></a></li>
								<li class="item_li"><a class="item_mod"></a></li>
							</ul>
						</div>
						<div class="left_list">
						</div>
					</div>
					<div class="right">
						<div class="right_title"><span>&nbsp;设置</span><a onclick="open_dialog('confirm')">保存修改</a></div>
						<div class="right_mask"><p>&nbsp;&nbsp;请选择子菜单进行操作</p></div>
						<div class="right_nav">
							<input id="nav_id" style="display:none;"	/>
							<p style="font-size:14px;">&nbsp;&nbsp;请选择订阅者点击菜单后，公众号做出的相应动作</p>
							<div class="action_icon">
								<a>
									<i class="i_icon1" onclick="turn('message');"></i>
									<span>发送消息</span>
								</a>
							</div>
							<div class="action_icon" onclick="turn('url');">
								<a>
									<i class="i_icon2"></i>
									<span>跳转页面</span>
								</a>
							</div>
						</div>
						<div class="right_info">
							<p id="info_msg">订阅者点击该子菜单会跳转至以下链接:</p>
							<div id="info_div"></div>
							<button onclick="turn('nav')" class="btn" style="margin-left:180px;">编辑</button>
						</div>
						<div class="right_url">	
							<p>订阅者点击该子菜单会跳到以下链接</p>
							<div class="input_div">
								<input type="text" class="input_url" id="input_url" placeholder="输入url"/>
							</div>
							<button onclick="post('url')" class="btn">提交</button>
							<button onclick="turn('back')" class="btn">返回</button>
						</div>
						<div class="right_message">
							<div class="message_box">
								<div class="box_head">
									<ul class="box_ul">
										<li><a id="msg">文字</a></li>
										<li><a onclick="open_dialog('lang')">语言</a></li>
										<li><a onclick="open_dialog('pic')">图片</a></li>
										<li><a onclick="open_dialog('video')">视频</a></li>
										<li><a onclick="open_dialog('pictxt')">图文</a></li>
									</ul>
								</div>
								<div class="box_area">
									<textarea class="box_txt" id="box_msg"></textarea>
									<button onclick="post('msg')" class="btn" style="margin-left:150px">提交</button>
									<button onclick="turn('back')" class="btn" style="margin-left:100px">返回</button>
								</div>
							</div>
						</div>
						<div id="dialog-add" title="添加子菜单" style="display:none;"><div class="send_msg" id="send_msg"></div>
								<div class="input_div">
									<input type="text" class="input_url" id="input_name" placeholder="输入名称"/>
								</div>
						</div>
						<div id="dialog-del" title="删除菜单" style="display:none;"><div class="send_msg" id="send_msg"></div><center>确定删除？</center></div>
						<div id="dialog-mod" title="修改菜单" style="display:none;"><div class="send_msg" id="send_msg"></div>
								<div class="input_div">
									<input type="text" class="input_url" id="input_name" placeholder="输入名称"/>
								</div>
						</div>
						<div id="dialog-form" title="创建菜单" style="display:none;"><div class="send_msg" id="send_msg"></div>
								<div class="input_div">
									<input type="text" class="input_url" id="input_name" placeholder="输入名称"/>
								</div>
						</div>
						<div id="dialog-lang" title="语言" style="display:none;">lang<br>sadasdasd</div>
						<div id="dialog-pic" title="图片" style="display:none;">pic</div>
						<div id="dialog-video" title="视频" style="display:none;">video</div>
						<div id="dialog-pictxt" title="图文" style="display:none;">pictxt</div>
						<div id="dialog-confirm" title="保存修改" style="display:none;"><div class="send_msg" id="send_msg"></div><center>确定保存修改？</center></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>