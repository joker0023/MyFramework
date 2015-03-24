<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta charset="utf-8">
    <title>Test1</title>
    <link rel="stylesheet" href="./assets/css/bootstrap.css">
    <style type="text/css">
    	.m-menu {
			margin: 0 auto;
			width: 700px;
			height: 100px;;
			position: relative;
			background-color: #ccc;
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
			display: inline;
			zoom: 1;
			margin-right: 5px \9;
		}
		.m-menu .m-menu-tab > li > .u-btn {
			width: 100px;
			opacity: 0.5;
			-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=50)"; /*IE8*/  
     		filter: alpha(opacity=50);  /*IE5、IE5.5、IE6、IE7*/  
		}
		
    </style>
  </head>
  <body>
  	<div class="container">
  		<div class="m-menu">
			<ul class="m-menu-tab">
				<li class="active"><a href="#main-tab" data-toggle="tab" class="btn btn-primary u-btn">tab1</a></li>
				<li><a href="#tab2" data-toggle="tab" class="btn btn-primary u-btn">tab2</a></li>
				<li><a href="#tab3" data-toggle="tab" class="btn btn-primary u-btn">tab3</a></li>
			</ul>
  		</div>
 	</div>
  </body>
  <script src="./assets/js/jquery-1.10.2.min.js"></script>
  <script src="./assets/js/bootstrap.js"></script>
  <script type="text/javascript">
  	$(function(){
  		var clientHeight = document.documentElement.clientHeight;
  		
  		$.ajax({
  			type: "post",
  			url: "http://localhost/qianniupc/printtrade/tpl_getDef",
  			data: {
  					deliverName: "顺丰快递-1",
  					pwd: "tdz123"
  				  },
  			dataType: "json",
  			success: function(data){
  				console.log(data);
  			},
  			error: function(data, status, e){
  				alert(e);
  			}
  		});
  		
  	});
  </script>
</html>
