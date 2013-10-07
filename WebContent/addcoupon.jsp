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
		"couponID":"123456",
		"couponName":"10元优惠券",
		"memo":"凭此电子券到现场可抵消10元的购物优惠。\n本活动解释权属于wjh",
		"startedDate":"2013-09-21 16:06:00",
		"expiredDays":30
	};
	var req = JSON.stringify(json);
	$(document).ready(function(){
		//alert(req);
		$.post("coupon?action=add",
		{data:req},
		function(data,status){
			alert(data);
		});
	});
</script>
</head>
<body>
	<div class="main_right_page">
		<div class="main_header">
			<span>推送信息/优惠券管理</span>
		</div>
		<div class="main_inner">
			
		</div>
	</div>
</body>
</html>