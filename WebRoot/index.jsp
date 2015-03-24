<%@page import="com.joker.myfw.helper.HttpHelper"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<title>Index</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
		<style type="text/css">
			.link {
				font-size: 25px;
				list-style: none;
			}
		</style>
	</head>
	<body>
		<%
			//response.sendRedirect(request.getContextPath() + "/mpwx.jsp");
			String ip = HttpHelper.getRealIpAddress(request);
			out.println("ip: " + ip);
		%>
		
		<div class="container">
			<ul class="link">
				<li>
					<a href="${pageContext.request.contextPath }/validation/web_login.do">(1)登录</a>
				</li>
			</ul>
		</div>
		
		<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
		<!--  
		<script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1253952620'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1253952620' type='text/javascript'%3E%3C/script%3E"));</script>
		-->
		<script type="text/javascript">
			$(function(){
				
			});
		</script>
	</body>
</html>
