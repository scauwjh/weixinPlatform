<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Coupon</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$.get("coupon?action=get",
			function(data,status){
				//alert(data);
				data = $.trim(data);
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