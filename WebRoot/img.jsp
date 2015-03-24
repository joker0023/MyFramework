<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta charset="utf-8">
    <title>IMG</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/imgtools.css">
    <style type="text/css">
    	.btn:FOCUS,.btn:ACTIVE:FOCUS,.btn.active:FOCUS{
			outline: none;
		}
    	.u-btn, .u-input{
    		border-radius: 0;
    	}
    	
    	
    	.g-container {
    		border: 1px solid #ccc;
    		border-width: 0 1px 1px;
    		min-width: 1000px;
    		max-width: 1200px;
    		margin: 0 auto;
    	}
    	
		.g-header {
			height: 100px;
			background-color: #CCC;
			background: linear-gradient(to top, #CCC,#FFF,#CCC);
			filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#cccccc');
		}
		.m-menu {
			margin: 0 auto;
			width: 700px;
			height: 100%;;
			position: relative;
		}
		.m-menu .m-menu-tab {
			position: absolute;
			bottom: 0;
			margin: 0;
			padding: 0;
			display: block;
		}
		.m-menu .m-menu-tab li {
			list-style: none;
			display: inline-block;
			*display: inline;
			*zoom: 1;
			*margin-right: 5px;
		}
		.m-menu .m-menu-tab > li > .u-btn {
			width: 100px;
			opacity: 0.5;
			-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=50)"; /*IE8*/  
     		filter: alpha(opacity=50);  /*IE5、IE5.5、IE6、IE7*/  
		}
		.m-menu .m-menu-tab > li > .u-btn:HOVER{
			opacity: 0.8;
			-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=50)"; /*IE8*/  
     		filter: alpha(opacity=80);  /*IE5、IE5.5、IE6、IE7*/  
		}
		.m-menu .m-menu-tab > li.active > .u-btn {
			opacity: 1;
			-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=100)"; /*IE8*/  
     		filter: alpha(opacity=100);  /*IE5、IE5.5、IE6、IE7*/  
		}
		
		
		.g-body {
		
		}
		
		.g-left {
			min-height: 100px;
			padding: 10px;
		}
		.m-template {
			margin-bottom: 10px;
		}
		
		.m-template img {
			width: 100%;
			cursor: pointer;
		}
		
		.g-center {
			width: 700px;
			min-height: 100px;
			border-left: 1px solid #ccc;
			border-right: 1px solid #ccc;
			padding: 10px;
			display: block;
			margin: 0;
		}
		.g-center > li {
			list-style: none;
		}
		.g-center .m-canvas {
			background-color: lavender;
			height: 200px;
			margin-bottom: 10px;
			display: block;
			padding: 0;
		}
		.g-center .m-canvas > .close {
			font-size: 30px;
			color: red;
			display: none;
		}
		.ui-state-highlight {
			background-color: whitesmoke;
			height: 200px;
			margin-bottom: 20px;
		}
		
		.g-right {
			min-height: 100px;
			padding: 10px;
		}
		
		
		.g-footer {
		
		}
    </style>
  </head>
  <body>
  	<div class="g-container">
  		<div class="g-header">
  			<div class="m-menu">
  				<ul class="m-menu-tab">
  					<li class="active"><a href="#main-tab" data-toggle="tab" class="btn btn-primary u-btn">tab1</a></li>
					<li><a href="#tab2" data-toggle="tab" class="btn btn-primary u-btn">tab2</a></li>
					<li><a href="#tab3" data-toggle="tab" class="btn btn-primary u-btn">tab3</a></li>
				</ul>
	  		</div>
  		</div>
  		<div class="g-body tab-content">
  			<div id="main-tab" class="tab-pane active fade in">
  				<table >
	  				<tr>
	  					<td style="width: 30%;vertical-align: top;">
	  						<div class="g-left">
	  							<div class="m-template">
	  								<img alt="" src="${pageContext.request.contextPath }/assets/img/test/subbg.png">
	  							</div>
	  							<div class="m-template">
	  								<img alt="" src="${pageContext.request.contextPath }/assets/img/test/subbg.png">
	  							</div>
	  						</div>
	  					</td>
	  					<td style="width: 700px;vertical-align: top;">
	  						<ul class="g-center">
	  						
	  						</ul>
	  					</td>
	  					<td style="width: 30%;vertical-align: top;">
	  						<div class="g-right"></div>
	  					</td>
	  				</tr>
	  			</table>
  			</div>
  			
  			<div id="tab2" class="tab-pane fade">
  				<div style="background-color: #EEE; width: 800px; height: 600px; margin: 10px auto;"></div>
  			</div>
  			<div id="tab3" class="tab-pane fade">tab 3 ***</div>
  		</div>
 	</div>
  </body>
  <script type="text/template" id="img-container-template">
		<li class="m-canvas">
			<button type="button" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		</li>
  </script>
  <script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
  <script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.core.js"></script>
  <script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.widget.js"></script>
  <script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.mouse.js"></script>
  <script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.sortable.js"></script>
  <script src="${pageContext.request.contextPath }/assets/js/utils.js" ></script>
  <script src="${pageContext.request.contextPath }/assets/js/canvas-util.js" ></script>
  <script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
  <script type="text/javascript">
  	$(function(){
  		$(".g-center").sortable({
			opacity: 0.6,
			placeholder: 'ui-state-highlight'
		});
  		
  		if($(".g-center").height() < $(".g-left").height()){
  			$(".g-center").height($(".g-left").height());
  		}
  		if($(".g-center").height() < $(".g-right").height()){
  			$(".g-center").height($(".g-right").height());
  		}
  		
  		$(".g-center").on("mouseenter", ".m-canvas", function(){
  			$(this).find(".close").show();
  		});
		$(".g-center").on("mouseleave", ".m-canvas", function(){
			$(this).find(".close").hide();
  		});
		$(".g-center").on("click", ".m-canvas > .close", function(){
			$(this).parent().remove();
  		});
		
		$(".g-left > .m-template > img").click(function(){
			$(".g-center").append($("#img-container-template").html());
		});
  	});
  </script>
</html>
