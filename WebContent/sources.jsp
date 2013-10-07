<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Sources</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	var json;
	$(document).ready(function(){
		$.get("weixinmessage?action=get",
			function(data,status){
				data = $.trim(data);
				//alert(data);
				if(data=="error")
					alert("no coupon");
				else{
					$("#main").html(data);
				}
			}
		);
	});
</script>
</head>
<body>
	<jsp:include page="head.jsp" flush="true" />
	<div id="main">
	</div>
</body>
</html>