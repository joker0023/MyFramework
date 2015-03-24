<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="utf-8">
  	<title>Log Info</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
  	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
  	<style type="text/css">
    	ul {
    		display: block;
    		margin: 10px 0;
    		padding: 0;
    		list-style: none;
    		background-color: #daeaf2;
		    border-color: #e9f2f7 #e9f2f7 #cfdee6;
		    border-style: solid;
		    border-width: 1px;
		    height: 40px;
    	}
    	li {
    		display: inline-block;
    		list-style: none;
    		margin-left: 10px;
    	}
    	li a {
			color: #294766;
		    display: block;
		    font: bolder 14px/40px 'Microsoft YaHei','宋体',微软雅黑,Arial,Tahoma;
		    height: 40px;
		    letter-spacing: 2px;
		    width: 100px;
		    text-align: center;
		    text-decoration: none;
		}
		ul li a.z-crt, ul li a:HOVER {
		    background-color: #f2fbff;
		    border-left: 1px solid #cfdae6;
		    border-right: 1px solid #cfdae6;
		    color: #294766;
		    text-decoration: none;
		}
		pre {
			white-space: pre-wrap;
		}
  	</style>
  </head>
  <body>
	<div class="container">
		<ul>
			<li><a href="${pageContext.request.contextPath }/console/log/info.do?type=error&tomcat=${requestScope.data.tomcat}" class="${requestScope.data.type == 'error'?'z-crt':''}">ERROR</a></li>
			<li><a href="${pageContext.request.contextPath }/console/log/info.do?type=info&tomcat=${requestScope.data.tomcat}" class="${requestScope.data.type == 'info'?'z-crt':''}">INFO</a></li>
		</ul>
		<div class="logContainer">
			<c:if test="${null != requestScope.data.log}">
				<a href="${pageContext.request.contextPath }/console/log/download.do?type=${requestScope.data.type}&tomcat=${requestScope.data.tomcat}" target="_blank">下载</a>
				<pre>${requestScope.data.log}</pre>
			</c:if>
		</div>
	</div>
  	<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
  	<script type="text/javascript">
  		$(function(){
  			$("ul li a").click(function(){
  				$("ul li a").removeClass("z-crt");
  				$(this).addClass("z-crt");
  			});
  		});
  	</script>
  </body>
</html>
