<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<meta content="text/html; charset=gb2312" http-equiv="Content-Type"/>
		<title>MPWX</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
		<style type="text/css">
			
		</style>
	</head>
	<body>
		
		
		
		<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
			$(function(){
				$.ajax({
					type: "post",
					url: "${pageContext.request.contextPath }/mobile/mpwx/sign",
					data: {
						
					},
					dataType: "json",
					success: function(data){
						
					},
					error: function(data, status, e){
						alert(e);
					}
				});
			});
		</script>
	</body>
</html>
