<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<title>AddCoupon</title>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json.js"></script>
<script type="text/javascript">
	var json;
	json = {
		"memberID":"1234568",
		"unitID":1,
		"coupon":[{
			"couponID":"123456",
			"getTime":"2013-09-22 17:55:00"
		},{
			"couponID":"654321",
			"getTime":"2013-09-22 17:55:00"
		}],//获得了多少张优惠券就多少个json
		"telephone":"13042072387",
	};
	var req = JSON.stringify(json);
	$(document).ready(function(){
		//alert(req);
		$.post("member?action=update",
		{data:req},
		function(data,status){
			alert(data);
			$("#main").html(data);
		});
	});
</script>
</head>
<body>
	<div id="main">
	</div>
</body>
</html>