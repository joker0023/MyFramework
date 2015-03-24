<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="modal fade" id="imgUploadModal">
	<div class="modal-dialog" style="width: 435px;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span>&times;</span>
				</button>
				<h4 class="modal-title">上传图片</h4>
			</div>
			<div class="modal-body">
				<div style="width: 400px; height: 300px; margin: 0px auto; border: 1px solid #ccc;">
					<div style="position: relative;border-right: 1px solid #ccc;width: 300px; height: 300px;float: left;">
						<canvas width="300" height="300" id="imgUploadModal_bgCanvas"></canvas>
						<canvas width="200" height="200" id="imgUploadModal_canvas" style="position: absolute;top:50px;left: 50px;"></canvas>
					</div>
					<div id="imgUploadModal_control" style="width: 90px;float: right; height: 300px;position: relative;">
						<label style="margin-top: 10px;">原图: </label>
						<div style="width: 80px;height: 120px;overflow: hidden;margin-top: 10px;">
							<img width="80" alt="" src="">
						</div>
						<a href="javascript:void(0);" class="btn btn-info btn-xs" style="position: absolute;top: 160px;left: 10px;">上传图片</a>
						<input type="file" style="position: absolute;left: -2000px;">
					</div>
				</div>
			</div>
			<div class="modal-footer">
	        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        	<button type="button" class="btn btn-primary" id="imgUploadModal_viewBtn">预览</button>
	        	<button type="button" class="btn btn-primary" id="imgUploadModal_saveBtn">保存</button>
	      	</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		var imgModal = new ImgUploadModal({circle:true});
		imgModal.loadImg("${pageContext.request.contextPath }/assets/img/avatar-def.jpg");
		
		$("#imgUploadModal_control > .btn").click(function(){
			$(this).next("input[type='file']").click();
		});
		
		$("#imgUploadModal_control > input[type='file']").change(function(){
			if(this.files){
           		var file = this.files[0];
           		if(/image/.test(file.type)){
           			var url = FileUtil.getImgURL(file);
           			imgModal.loadImg(url);
 				}else{
					alert("请选择图片文件");
				}
			}
		});
		
		$("#imgUploadModal_viewBtn").click(function(){
			var url = imgModal.exportImg();
			var win = window.open();
  			win.location.href = url;
		});
		
		$("#imgUploadModal_saveBtn").click(function(){
			
		});
		
	});
	
	function ImgUploadModal(option){
		this.canvas = $("#imgUploadModal_canvas")[0];
		this.bgCanvas = $("#imgUploadModal_bgCanvas")[0];
		this.context = this.canvas.getContext("2d");
		this.bgContext = this.bgCanvas.getContext("2d");
		this.active = false;
		this.width = this.canvas.width;
		this.height = this.canvas.height;
		this.bgWidth = this.bgCanvas.width;
		this.bgHeight = this.bgCanvas.height;
		this.left = parseInt(this.canvas.style.left);
		this.top = parseInt(this.canvas.style.top);
		this.lastPageX = null;
		this.lastPageY = null;
		this.customImg = null;	//不能直接赋值,要调用loadImg
		if(null != option){
			this.action = option.action;
			this.circle = option.circle;
		}
		var _this = this;
		
		if(this.circle){
			this.context.beginPath();
			this.context.arc(this.width/2,this.width/2,this.width/2,0,Math.PI*2,true);
			this.context.clip();
			this.context.closePath();
		}
		this.context.translate(-this.left, -this.top);
		
		
		
		//拖放文件
		this.addHandler(this.canvas, "dragenter", function(event){
			_this.preventDefault(_this.getEvent(event));
		});
		this.addHandler(this.canvas, "dragover", function(event){
			_this.preventDefault(_this.getEvent(event));
		});
		this.addHandler(this.canvas, "drop", function(event){
			event = _this.getEvent(event);
			_this.preventDefault(event);
			_this.stopPropagation(event);
			var files = event.dataTransfer.files;
			if(files.length > 0){
				var file = event.dataTransfer.files[0];
				if(/image/.test(file.type)){
					var url = FileUtil.getImgURL(file);
					_this.loadImg(url);
				}
			}
		});
		
		//图片移动
		this.addHandler(this.canvas, "mousedown", function(event){
			event = _this.getEvent(event);
			_this.stopPropagation(event);
			if(null != _this.customImg){
				_this.lastPageX = event.pageX;
				_this.lastPageY = event.pageY;
				_this.active = true;
				_this.canvas.style.cursor = "move";
			}
		});
		$(document.body).mouseup(function(){
			_this.active = false;
			_this.canvas.style.cursor = "default";
		});
		this.addHandler(this.canvas, "mousemove", function(event){
			event = _this.getEvent(event);
			_this.stopPropagation(event);
			if(_this.active && null != _this.customImg){
				_this.customImg.left = _this.customImg.left + event.pageX - _this.lastPageX;
				_this.customImg.top = _this.customImg.top + event.pageY - _this.lastPageY;
				_this.lastPageX = event.pageX;
				_this.lastPageY = event.pageY;
				_this.reDraw();
			}
		});
		
		
		
		//图片放大缩小
		function mousewheelHandle(event){
			event = _this.getEvent(event);
			_this.preventDefault(event);
			_this.stopPropagation(event);
			var delta = 0;
			if(event.wheelDelta){
				delta = event.wheelDelta;
			}else if(event.detail){
				delta = event.detail;
			}
			
			if(delta > 0){
				if(_this.customImg.width < 1000 && _this.customImg.height < 1000){
					_this.customImg.width = _this.customImg.width * 1.1;
					_this.customImg.height = _this.customImg.height * 1.1;
				}
			}else if(delta < 0){
				if(_this.customImg.width > 40 && _this.customImg.height > 40){
					_this.customImg.width = _this.customImg.width * 0.9;
					_this.customImg.height = _this.customImg.height * 0.9;
				}
			}
			_this.reDraw();
		}
		
		this.addHandler(this.canvas, "mousewheel", mousewheelHandle);
		this.addHandler(this.canvas, "DOMMouseScroll", mousewheelHandle);
		
	}
	ImgUploadModal.prototype.loadImg = function(url){
		$("#imgUploadModal_control img").attr("src",url);
		var baseCanvas = this;
		var img = new Image();
		img.src = url;
		img.onload = function(){
			var customImg = new CustomImg(this);
			customImg.width = img.width;
			customImg.height = img.height;
			customImg.left = 0;
			customImg.top = 0;
			baseCanvas.customImg = customImg;
			baseCanvas.reDraw();
			img = null;
			baseCanvas = null;
		}
	};
	ImgUploadModal.prototype.cleanAll = function(){
		this.bgContext.clearRect(0, 0, this.bgWidth, this.bgHeight);
		this.context.clearRect(0, 0, this.width + this.left, this.height + this.top);
	};
	ImgUploadModal.prototype.draw = function(){
		this.bgContext.save();
		this.bgContext.drawImage(this.customImg.img, this.customImg.left, this.customImg.top, this.customImg.width, this.customImg.height);
		this.bgContext.fillStyle = "#66CCFF";
		this.bgContext.globalAlpha = 0.7;
		this.bgContext.fillRect(0, 0, this.bgWidth, this.bgHeight);
		this.bgContext.restore();
		
		this.context.fillStyle = "#FFF";
		this.context.fillRect(0, 0, this.width + this.left, this.height + this.top);
		this.context.drawImage(this.customImg.img, this.customImg.left, this.customImg.top, this.customImg.width, this.customImg.height);
	};
	ImgUploadModal.prototype.reDraw = function(){
		this.cleanAll();
		this.draw();
	};
	ImgUploadModal.prototype.exportImg = function(){
		var url = this.canvas.toDataURL("image/png");
		return url;
	};
	ImgUploadModal.prototype.addHandler = function(element, type, handler){
		if(element.addEventListener){
			element.addEventListener(type, handler, false);
		}else if(element.attachEvent){
			element.attachEvent(type, handler);
		}else{
			element["on" + type] = handler;
		}
	};
	ImgUploadModal.prototype.getEvent = function(event){
		return event ? event : window.event;
	};
	ImgUploadModal.prototype.getTarget = function(event){
		return event.target || event.srcElement;
	};
	ImgUploadModal.prototype.preventDefault = function(event){
		if(event.preventDefault){
			event.preventDefault();
		}else{
			event.returnValue = false;
		}
	};
	ImgUploadModal.prototype.stopPropagation = function(event){
		if(event.stopPropagation){
			event.stopPropagation();
		}else{
			event.cancelBubble = true;
		}
	};
	
	function CustomImg(img){
		this.img = img;
		this.width = null;
		this.height = null;
		this.left = 0;
		this.top = 0;
	}
	
</script>