<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="js/json.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var json;
		json = {
			"button":[{
				"id":1,
				"type":"click",
				"key":"key1",
				"name":"会员卡",
				"message":{"type":"picMsg","picMsgID":1}
			},{
				"id":2,
				"type":"view",
				"name":"优惠信息",
				"url":"http://www.baidu.com"
			},{
				"id":3,
				"name":"其他菜单",
				"sub_button":[{
					"id":4,
					"type":"click",
					"key":"keyEx1",
					"name":"会员卡A",
					"message":{"type":"msg","msg":"这就是传说中的会员卡A啦，唔系你仲想点啊？？"}
				},{
					"id":5,
					"type":"view",
					"name":"会员卡B",
					"url":"http://http://www.google.com"
				}]
			}]
		};
		//alert(JSON.stringify(json));
		var req = JSON.stringify(json);
		$.post("createmenu?action=post",
		{
			data:req
		},
		function(data,status){
			alert("data:"+data+" status:"+status);
		});
	});
</script>
</head>
<body>

</body>
</html>