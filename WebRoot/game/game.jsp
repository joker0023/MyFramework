<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  	<head>
  		<meta charset="utf-8">
    	<title>GAME</title>
  	</head>
	<body>
    
		<div id="playground" style="width: 700px; height: 250px; background: black;">
		    <div id="welcomeScreen" style="width: 700px; height: 250px; position: absolute; z-index: 100;">
		        <div style="position: absolute; top: 120px; width: 700px; color: white;">
		            <div style="text-align: center;"><a style="cursor: pointer;" id="startbutton">click here to show the demo</a></div>
		        </div>
		    </div>
		</div>
    
		<script type="text/javascript" src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js" charset="UTF-8"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/game/gamequery-0.7.js" charset="UTF-8"></script>
		<script type="text/javascript">
	     	$(function(){
	     		var PLAYGROUND_HEIGHT = 250;
	            var PLAYGROUND_WIDTH = 700;
	            var REFRESH_RATE = 30;
	            
	            var smallStarSpeed = 1;
	            
	            var mediumStarSpeed = 3;
	                
	            var bigStarSpeed = 5;
	            
	            var background1 = new $.gameQuery.Animation({imageURL: "background1.png"});
	            var background2 = new $.gameQuery.Animation({imageURL: "background2.png"});
	            var background3 = new $.gameQuery.Animation({imageURL: "background3.png"});
	            var background4 = new $.gameQuery.Animation({imageURL: "background4.png"});
	            var background5 = new $.gameQuery.Animation({imageURL: "background5.png"});
	            var background6 = new $.gameQuery.Animation({imageURL: "background6.png"});

	            $("#playground").playground({
	                height: PLAYGROUND_HEIGHT, 
	                width: PLAYGROUND_WIDTH, 
	                keyTracker: true});
	            
	            $.playground()
	            .addGroup("background", {width: PLAYGROUND_WIDTH, 
	                                     height: PLAYGROUND_HEIGHT})
	            .addSprite("background1", {animation: background1, 
	                                       width: PLAYGROUND_WIDTH, 
	                                       height: PLAYGROUND_HEIGHT})
	            .addSprite("background2", {animation: background2,
	                                       width: PLAYGROUND_WIDTH, 
	                                       height: PLAYGROUND_HEIGHT, 
	                                       posx: PLAYGROUND_WIDTH})
	            .addSprite("background3", {animation: background3, 
	                                       width: PLAYGROUND_WIDTH, 
	                                       height: PLAYGROUND_HEIGHT})
	            .addSprite("background4", {animation: background4, 
	                                       width: PLAYGROUND_WIDTH, 
	                                       height: PLAYGROUND_HEIGHT, 
	                                       posx: PLAYGROUND_WIDTH})
	            .addSprite("background5", {animation: background5, 
	                                       width: PLAYGROUND_WIDTH, 
	                                       height: PLAYGROUND_HEIGHT})
	            .addSprite("background6", {animation: background6, 
	                                       width: PLAYGROUND_WIDTH, 
	                                       height: PLAYGROUND_HEIGHT, 
	                                       posx: 300});
	            
	            $.playground().registerCallback(move, REFRESH_RATE);
	            
	            function move(){
	           		//var newPos = parseInt($("#background1").css("left")) - smallStarSpeed;
	            	//if(newPos < -PLAYGROUND_WIDTH){
	            		//newPos = newPos + 2 * PLAYGROUND_WIDTH;
	            	//}
	               	var newPos = (parseInt($("#background1").css("left")) - smallStarSpeed - PLAYGROUND_WIDTH)
	                                       % (-2 * PLAYGROUND_WIDTH) + PLAYGROUND_WIDTH;
	               	$("#background1").css("left", newPos);

	               	newPos = (parseInt($("#background2").css("left")) - smallStarSpeed - PLAYGROUND_WIDTH)
	                                  % (-2 * PLAYGROUND_WIDTH) + PLAYGROUND_WIDTH;
	              	$("#background2").css("left", newPos);

	               	newPos = (parseInt($("#background3").css("left")) - mediumStarSpeed - PLAYGROUND_WIDTH)
	                                  % (-2 * PLAYGROUND_WIDTH) + PLAYGROUND_WIDTH;
	               	$("#background3").css("left", newPos);

	              	 newPos = (parseInt($("#background4").css("left")) - mediumStarSpeed - PLAYGROUND_WIDTH)
	                                  % (-2 * PLAYGROUND_WIDTH) + PLAYGROUND_WIDTH;
	               	$("#background4").css("left", newPos);

	              	 newPos = (parseInt($("#background5").css("left")) - bigStarSpeed - PLAYGROUND_WIDTH)
	                                  % (-2 * PLAYGROUND_WIDTH) + PLAYGROUND_WIDTH;
	               	$("#background5").css("left", newPos);

	               	newPos = (parseInt($("#background6").css("left")) - bigStarSpeed - PLAYGROUND_WIDTH)
	                                  % (-2 * PLAYGROUND_WIDTH) + PLAYGROUND_WIDTH;
	            	$("#background6").css("left", newPos);
	            }
	            
	            
	            
	            $("#startbutton").click(function(){
	                $.playground().startGame(function(){
	                    $("#welcomeScreen").remove();
	                });
	            });
	     	});
		</script>
  	</body>
</html>
