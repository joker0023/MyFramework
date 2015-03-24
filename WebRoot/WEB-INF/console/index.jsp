<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>XXX后台</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/console.css">
		<style type="text/css">
		
		</style>
		<script type="text/javascript">
			if(top != self) {
				top.location.href = self.location.href;
			}
		</script>
	</head>
	<body>
		<div class="g-doc">
			<div class="g-left">
				<div class="m-left-nav">
		    		<div class="nav-head">
			    		<a href="${pageContext.request.contextPath }/console/console_home.do" target="mainIframe" class="home_page_btn">
			    			<img alt="" class="img-circle" width="50" style="vertical-align: bottom;"
			    			 src="${pageContext.request.contextPath }${sessionScope.console_user.avatar }">
			    			${sessionScope.console_user.nick }
			    		</a>
			    	</div>
			    	<ul>
			    		<li class="par-nav">
			    			<a href="javascript:void(0);">人员管理</a>
			    			<ul class="sub-nav">
			    				<li>
					    			<a href="${pageContext.request.contextPath }/console/consoleuser/list.do" target="mainIframe">后台人员</a>
			    				</li>
			    				<li>
					    			<a href="${pageContext.request.contextPath }/console/webuser/list.do" target="mainIframe">前台人员</a>
			    				</li>
			    			</ul>
			    		</li>
			    		<li>
			    			<a href="${pageContext.request.contextPath }/console/actionrecord/list.do" target="mainIframe">操作记录</a>
			    		</li>
			    		<li>
			    			<a href="${pageContext.request.contextPath }/console/permission/list.do" target="mainIframe">角色管理</a>
			    		</li>
			    		<li class="par-nav">
			    			<a href="javascript:void(0);">日记</a>
			    			<ul class="sub-nav">
			    				<li>
					    			<a href="${pageContext.request.contextPath }/console/log/info.do?type=error&tomcat=1" target="mainIframe">Tomcat1</a>
			    				</li>
			    			</ul>
			    		</li>
			    		<li>
			    			<a href="${pageContext.request.contextPath }/console/console_logout.do">退出</a>
			    		</li>
			    	</ul>
			    </div>
			</div>
			<div class="g-right">
				<iframe id="mainIframe" name="mainIframe" style="overflow-x: hidden;border: 0" width="90%" src="${pageContext.request.contextPath }/console/console_home.do" onload="loadComplete();">
					
				</iframe>
			</div>
		</div>
		
		<!-- GOTOP START -->
		<div id="goTop"><img src="${pageContext.request.contextPath }/assets/img/gotop.png"></div>
		<!-- GOTOP END -->
	</body>
	
	<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".home_page_btn").click(function(){
				$(".m-left-nav ul li a").removeClass("active");
				$(".sub-nav").slideUp(300);
			});
			
			//菜单点击事件
			$(".m-left-nav>ul>li>a").click(function(){
				var $this = $(this);
				if($this.hasClass("active")){
					$(".m-left-nav ul li a").removeClass("active");
					$this.addClass("active");
					if($this.parent().hasClass("par-nav")){
						$this.parent().find(".sub-nav").slideToggle(300);
					}else{
						$(".sub-nav").slideUp(300);
					}
				}else{
					$(".m-left-nav ul li a").removeClass("active");
					$this.addClass("active");
					$(".sub-nav").slideUp(300);
					if($this.parent().hasClass("par-nav")){
						$this.parent().find(".sub-nav").slideDown(300);
					}
				}
			});
			
			$(".m-left-nav ul.sub-nav li a").click(function(){
				var $this = $(this);
				$(".m-left-nav ul li a").removeClass("active");
				$this.parent().parent().prev("a").addClass("active");
				$this.addClass("active");
			});
			
			//回到顶部
			$("#goTop").hide();
	        $("#goTop").click(function(){ //当点击标签的时候,使用animate在200毫秒的时间内,滚到顶部
	        	$("#goTop").animate({bottom:"10px"},150,function(){
	        		$("#goTop").animate({bottom:"40px"},150);
	        	});
	        	$("#mainIframe").contents().find("html,body").animate({scrollTop:"0px"},400);
	        });
		});
		
		function loadComplete(){
			$(window.frames["mainIframe"]).scroll(function(){
				var scrollt = this.document.documentElement.scrollTop + this.document.body.scrollTop;
				if( scrollt > 200 ){  //判断滚动后高度超过200px,就显示  
					console.log("show");
	              $("#goTop").css("display","block");
	            }else{      
	              $("#goTop").css("display","none");
	            }
			});
			
			autoIframeHeight();
		}
		
		function autoIframeHeight() {
			var clientHeight = document.documentElement.clientHeight;
	    	
	    	//初始化左侧菜单栏的高度
	    	$(".g-left").height(clientHeight);
	    	
	    	//初始化iframe的高度
	    	$("#mainIframe").height(clientHeight - 5);
	    	
			
	    	//初始化iframe的宽度
			var clientWidth = document.documentElement.clientWidth;
			$("#mainIframe").attr("width",clientWidth - 200);
		}
	</script>
</html>