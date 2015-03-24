<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="utf-8">
		<title>STAR</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
		<style type="text/css">
			body {
	    		background: linear-gradient(to right, #000000,#000011,#000000);
	    	}
	    	#cnzz_stat_icon_1253952620 {
	    		display: none;
	    	}
	    	.container {
	    		text-align: center;
	    		font-weight: bold;
	    		font-size: 50px;
	    		text-shadow: 15px 15px 4px #3399FF;
	    		color: red;
	    		height: 600px;
	    		width: 100%;
	    		padding: 0;
	    	}
	    	#mycanvas {
	    	
	    	}
	    	.round {
	    		display: none;
	    	}
	    	#star {
	    		display: none;
	    	}
	    	#center {
	    		display: none;
	    	}
		</style>
	</head>
	<body>
		<%
			//response.sendRedirect(request.getContextPath() + "/test1.jsp");
		%>
		<div class="container">
  			<canvas id="mycanvas" width="1400" height="700">
  				那么烂的浏览器快点换了吧!!
  			</canvas>
  			<canvas id="star" width="1400" height="700"></canvas>
  			<canvas id="center" width="200" height="200"></canvas>
	 	</div>
		 
		
		<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
		<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
		<script type="text/javascript">
			var centerX = 600;
			var centerY = 350;
			
			$(function(){
				var clientHeight = document.documentElement.clientHeight;
				var clientwidth  = document.documentElement.clientWidth;
				
				init();
				
				var canvas = $("#mycanvas")[0];
				var context = canvas.getContext("2d");
				context.strokeStyle = "#333";
				
				var $rounds = $(".round");
				setInterval(function(){
					context.clearRect(0, 0, canvas.width, canvas.height);
					
					drawStar(context);
					
					drawCenter(context);
					
					for(var i = 0; i < $rounds.length; i++){
						var round = $rounds[i];
						
						round.radius += round.step;
						if(round.radius > Math.PI*2){
							round.radius = 0;
						}
						drawRound(context, round.x, round.y, round.a, round.b, round.radius, round);
					}
				}, 30);
			});
			
			function drawStar(context){
				context.drawImage($("#star")[0], 0 , 0);
			}
			
			function drawCenter(context){
				var center = $("#center")[0];
				var width = centerX - center.width/2;
				var height = centerY - center.height/2;
				context.drawImage(center, width , height);
			}
			
			function drawRound(context, x, y, a, b, radius, canvas){
				drawEllipse(context, x, y, a, b);
				context.save();
				context.globalAlpha = 0.9;
				var xx = x + a*Math.cos(radius);
			   	var yy = y + b*Math.sin(radius);
			   
			   	var w = canvas.width/6 + (20 * (yy/(y)));
			   
			   	context.drawImage(canvas, xx-w/2 , yy-w/2, w, w);
			   	context.restore();
			};
			
			function drawEllipse(context, x, y, a, b){
				   var radius = 0;
				   context.beginPath();
				   context.moveTo(x + a, y);
				   var xx,yy;
				   while(radius < Math.PI*2){
					   xx = x + a * Math.cos(radius);
					   yy = y + b * Math.sin(radius);
					   context.lineTo(xx, yy);
					   radius += 0.05;
				   }
				   context.closePath();
				   context.stroke();
				};
			
			function init(){
				initRound();
				
				initCenter();
				
				initStart();
			}
			
			function initRound(){
				for(var i = 0; i < 7; i++){
					var $round = $('<canvas class="round"></canvas>');
					var add = parseInt(Math.random() * 40);
					if(add % 2 != 0){
						add ++;
					}
					var width = 50 + add;
					var height = 50 + add;
					$round.attr("width", width);
					$round.attr("height", height);
					$(".container").append($round);
					
					var round = $round[0];
					var context = round.getContext("2d");
					var r = parseInt(round.width/2);
					var grd = context.createRadialGradient(r-10, parseInt(r/2), 0, r, r, r);
					grd.addColorStop(0, "#336699");
					grd.addColorStop(1, "#000066");
					
					context.fillStyle = grd;
					context.beginPath();
					context.arc(r, r, r, 0, Math.PI*2, true);
					context.fill();
					
					round.radius = Math.random() * Math.PI*2;
					round.x = centerX + parseInt(Math.random() * 20);
					round.y = centerY + parseInt(Math.random() * 20);
					round.a = 300 + i*30;
					round.b = 100 + i*10;
					round.step = 0.002 + Math.random() * 0.01;
				}
			}
			
			function initCenter(){
				var canvas = $("#center")[0];
				var context = canvas.getContext("2d");
				var r = 50;
				var x = canvas.width/2;
				var y = canvas.height/2;
				
				context.shadowOffsetX = 0;
				context.shadowOffsetY = 0;
				context.shadowBlur = 50;
				context.shadowColor = "#FFFF00";
				
				var grd = context.createRadialGradient(x-20, y-20, 0, x, y, r);
				grd.addColorStop(0, "#FF0000");
				grd.addColorStop(0.4, "#FF3333");
				grd.addColorStop(1, "#FFCC33");
				
				context.fillStyle = grd;
				context.beginPath();
				context.arc(x, y, r, 0, Math.PI*2, true);
				context.fill();
			}
			
			function initStart(){
				var canvas = $("#star")[0];
				var context = canvas.getContext("2d");
				context.shadowOffsetX = 0;
				context.shadowOffsetY = 0;
				context.shadowBlur = 20;
				context.shadowColor = "#FFFF00";
				context.fillStyle = "#FFF";
				for(var i = 0; i < 60; i++){
					var x = parseInt(Math.random() * canvas.width);
					var y = parseInt(Math.random() * canvas.height);
					context.beginPath();
					context.arc(x, y, 1, 0, Math.PI*2, true);
					context.fill();
				}
				
				setInterval(function(){
					var a = parseInt(Math.random() * canvas.width);
					var b = parseInt(Math.random() * canvas.height);
					context.clearRect(a, b, a + 50, b + 50);
					for(var i = 0; i < 5; i++){
						var x = parseInt(Math.random() * canvas.width);
						var y = parseInt(Math.random() * canvas.height);
						context.beginPath();
						context.arc(x, y, 1, 0, Math.PI*2, true);
						context.fill();
					}
				}, 2000);
			}
		</script>
	</body>
</html>
