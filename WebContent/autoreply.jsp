<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var json = null;
	$.get("unit?action=get",
		function(data){
			json = eval("("+data+")");
			$("#main_container").text(JSON.stringify(json.autoReply));
		}
	);
});
</script>
</head>
<body>
	<jsp:include page="head.jsp" flush="true" />
	<div id="main">
		<div id="main_container">
		
		</div>
	</div>
	<jsp:include page="foot.jsp" flush="true" />
</body>
</html>