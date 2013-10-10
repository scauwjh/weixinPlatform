<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet" type="text/css" href="css/global.css">
<script type="text/javascript">
$(document).ready(function(){
	var flag = 0;
	$("#nav_ul").children("li").children("a").hover(function(){
		flag = 0;
		if($(this).hasClass("li_selected"))
			flag = 1;
		if(flag == 0)
			$(this).addClass("li_selected");
	},function(){
		if(flag == 0)
			$(this).removeClass("li_selected");
	});
});
</script>
<div id="header">
	<div id="header_container">
		<div id="logo">
			<img src="images/logo.png">
		</div>
		<div id="navigate">
			<ul id="nav_ul">
				<li class="nav_li li_selected"><a href="unit.jsp" class="nav_a">单位信息</a></li><!-- 单位信息 -->
				<li class="nav_li"><a href="member.jsp" class="nav_a">会员管理</a></li>
				<li class="nav_li"><a href="sources.jsp" class="nav_a">素材管理</a></li>
				<li class="nav_li"><a href="coupon.jsp" class="nav_a">优惠信息</a></li>
				<li class="nav_li"><a href="autoreply.jsp" class="nav_a">自动回复</a></li>
				<li class="nav_li"><a href="menu.jsp" class="nav_a">自定义菜单</a></li>
			</ul>
		</div>
	</div>
</div>
	<!--
	<a href="addmenu.jsp">添加菜单（测试数据用）</a><br>
	<a href="addsource.jsp">添加素材（测试数据用）</a><br>
	<a href="addcoupon.jsp">添加优惠券（测试数据用）</a><br>
	<a href="addmember.jsp">添加粉丝（测试数据用）</a><br>
	-->