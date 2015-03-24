var currentDraggingTarget = {
	subImgCanvas: null,
	subTextCanvas: null
};

/**
 * 画板基类,用于被继承
 */
function JoBaseCanvas(option){
	if(null != option){
		this.canvas = option.canvas;
		this.left = option.left;
		this.top = option.top;
		this.width = option.width;
		this.height = option.height;
		this.foreground = null;	//不能直接赋值,要调用loadForeground
		this.background = null; //不能直接赋值,要调用loadBackground
		this.context = this.canvas.getContext("2d");
		this.active = false;
		this.lastPageX = null;
		this.lastPageY = null;
		
		this.setWidth(option.width);
		this.setHeight(option.height);
		
		EventUtil.addHandler(this.canvas, "dragenter", function(event){
			EventUtil.preventDefault(EventUtil.getEvent(event));
		});
		EventUtil.addHandler(this.canvas, "dragover", function(event){
			EventUtil.preventDefault(EventUtil.getEvent(event));
		});
		EventUtil.addHandler(this.canvas, "drop", function(event){
			event = EventUtil.getEvent(event);
			EventUtil.preventDefault(event);
			EventUtil.stopPropagation(event);
		});
	}
}
JoBaseCanvas.prototype.setWidth = function(width){
	this.width = width;
	if(null != this.width){
		this.canvas.width = this.width;
	}
}
JoBaseCanvas.prototype.setHeight = function(height){
	this.height = height;
	if(null != this.height){
		this.canvas.height = this.height;
	}
}
//清空画布
JoBaseCanvas.prototype.cleanAll = function(){
	this.context.clearRect(0, 0, this.width, this.height);
};
//画图
JoBaseCanvas.prototype.draw = function(){
	this.drawBackground();
	this.drawForeground();
};
//画前景图
JoBaseCanvas.prototype.drawForeground = function(){
	if(null != this.foreground){
		this.context.drawImage(this.foreground, 0, 0);
	}
};
//load前景图
JoBaseCanvas.prototype.loadForeground = function(imgurl){
	var img = new Image();
	var baseCanvas = this;
	img.src = imgurl;
	img.onload = function(){
		baseCanvas.cleanAll();
		baseCanvas.foreground = this;
		baseCanvas.draw();
		img = null;
		baseCanvas = null;
	}
};
//画背景图
JoBaseCanvas.prototype.drawBackground = function(){
	if(null != this.background){
		this.context.drawImage(this.background, 0, 0);
	}
}
//load背景图
JoBaseCanvas.prototype.loadBackground = function(imgurl){
	var img = new Image();
	var baseCanvas = this;
	img.src = imgurl;
	img.onload = function(){
		baseCanvas.cleanAll();
		baseCanvas.background = this;
		baseCanvas.draw();
		img = null;
		baseCanvas = null;
	}
};


/**
 * 画板类(主画板)
 */
function MainCanvas(option){
	JoBaseCanvas.call(this, option);
	var varThis = this;
	this.moduleCanvasArr = []
}
//继承方法
MainCanvas.prototype = new JoBaseCanvas();

//添加模块
MainCanvas.prototype.addModuleCanvas = function(moduleCanvas){
	this.moduleCanvasArr.push(moduleCanvas);
	this.height += moduleCanvas.height;
	this.canvas.height = this.height;
	this.cleanAll();
	this.draw();
};

//删除模块
MainCanvas.prototype.delModuleCanvas = function(moduleId){
	if(null != this.moduleCanvasArr){
		var hasRemove = false;
		var height = 0;
		var moduleCanvas = null;
		for(var i in this.moduleCanvasArr){
			moduleCanvas = this.moduleCanvasArr[i];
			if(hasRemove){
				moduleCanvas.top -= height;
				moduleCanvas.div.style = moduleCanvas.top + "px";
			}else if(moduleCanvas.div.id == moduleId){
				height = moduleCanvas.height;
				this.moduleCanvasArr.splice(i, 1);
				this.setHeight(this.height - height)
				this.cleanAll();
				this.draw();
				hasRemove = true;
			}
		}
	}
};

//画图(画全部,包括前景图和可修改图和canvas)
MainCanvas.prototype.drawAll = function(){
	this.drawBackground();
	if(null != this.moduleCanvasArr){
		var moduleCanvas = null;
		for(var i in this.moduleCanvasArr){
			moduleCanvas = this.moduleCanvasArr[i];
			if(null != moduleCanvas){
				moduleCanvas.cleanAll();
				moduleCanvas.drawAll();
				this.context.drawImage(moduleCanvas.canvas, moduleCanvas.left, moduleCanvas.top);
				moduleCanvas.cleanAll();
				moduleCanvas.draw();;
			}
		}
	}
	
	this.drawForeground();
};
//导出画布成为图片
MainCanvas.prototype.exportImg = function(imgTab){
	this.cleanAll();
	this.drawAll();
	var imgURI = this.canvas.toDataURL("image/png");
	
	imgTab.src = imgURI;
	
	this.cleanAll();
	this.draw();
};

/**
 * 模块画板
 */
function ModuleCanvas(option){
	JoBaseCanvas.call(this, option);
	var varThis = this;
	this.div = option.div;
	this.subCanvasArr = option.subCanvasArr;
	this.subTextCanvasArr = option.subTextCanvasArr;
	
	if(null != this.left){
		this.div.style.left = this.left + "px";
	}
	if(null != this.top){
		this.div.style.top = this.top + "px";
	}
	
	EventUtil.addHandler(this.div, "mousemove", function(event){
		event = EventUtil.getEvent(event);
		EventUtil.stopPropagation(event);
		if(null != currentDraggingTarget.subTextCanvas){
			var newLeft = currentDraggingTarget.subTextCanvas.left + event.pageX - currentDraggingTarget.subTextCanvas.lastPageX;
			var newTop = currentDraggingTarget.subTextCanvas.top + event.pageY - currentDraggingTarget.subTextCanvas.lastPageY;
			if(newLeft >= 0 && newLeft + currentDraggingTarget.subTextCanvas.width <= varThis.width){
				currentDraggingTarget.subTextCanvas.left = newLeft;
				currentDraggingTarget.subTextCanvas.div.style.left = newLeft + "px";
				currentDraggingTarget.subTextCanvas.lastPageX = event.pageX;
			}
			if(newTop >=0 && newTop + currentDraggingTarget.subTextCanvas.height <= varThis.height){
				currentDraggingTarget.subTextCanvas.top = newTop;
  				currentDraggingTarget.subTextCanvas.div.style.top = newTop + "px";
  				currentDraggingTarget.subTextCanvas.lastPageY = event.pageY;
			}
		}
	});
}
//继承方法
ModuleCanvas.prototype = new JoBaseCanvas();

ModuleCanvas.prototype.drawAll = function(){
	this.drawBackground();
	if(null != this.subCanvasArr){
		var subCanvas = null;
		for(var i in this.subCanvasArr){
			subCanvas = this.subCanvasArr[i];
			this.context.drawImage(subCanvas.canvas, subCanvas.left, subCanvas.top);
		}
		subCanvas = null;
	}
	if(null != this.subTextCanvasArr){
		var subDiv = null;
		for(var j in this.subTextCanvasArr){
			subDiv = this.subTextCanvasArr[j];
			this.context.drawImage(subDiv.canvas, subDiv.left, subDiv.top);
		}
		subDiv = null;
	}
	
	this.drawForeground();
};

/**
 * 子画板
 */
function SubCanvas(option){
	JoBaseCanvas.call(this, option);
	var varThis = this;
	this.joImg = null;	//不能直接赋值,要调用loadImg
	
	if(null != this.left){
		this.canvas.style.left = this.left + "px";
	}
	if(null != this.top){
		this.canvas.style.top = this.top + "px";
	}
	
	//可移动可拖放图片的canvas绑定相应事件
	EventUtil.addHandler(this.canvas, "drop", function(event){
		event = EventUtil.getEvent(event);
		EventUtil.preventDefault(event);
		EventUtil.stopPropagation(event);
		var files = event.dataTransfer.files;
		if(files.length > 0){
			var file = event.dataTransfer.files[0];
			if(/image/.test(file.type)){
				var url = FileUtil.getImgURL(file);
				varThis.cleanAll();
				varThis.loadImg(url);
			}
		}
	});
	
	EventUtil.addHandler(this.canvas, "mousedown", function(event){
		event = EventUtil.getEvent(event);
		EventUtil.stopPropagation(event);
		if(null != varThis.joImg){
			currentDraggingTarget.subImgCanvas = varThis;
			varThis.lastPageX = event.pageX;
			varThis.lastPageY = event.pageY;
			varThis.active = true;
			varThis.canvas.style.cursor = "move";
		}
	});
	
	EventUtil.addHandler(this.canvas, "mouseup", function(event){
		//EventUtil.stopPropagation(EventUtil.getEvent(event));
		varThis.active = false;
		varThis.canvas.style.cursor = "default";
		if(null != currentDraggingTarget.subImgCanvas){
			if(currentDraggingTarget.subImgCanvas.canvas != varThis.canvas){
				currentDraggingTarget.subImgCanvas.active = false;
				currentDraggingTarget.subImgCanvas.canvas.style.cursor = "default";
				if(null != currentDraggingTarget.subImgCanvas.joImg){
					currentDraggingTarget.subImgCanvas.joImg.imgLeft = 0;
					currentDraggingTarget.subImgCanvas.joImg.imgTop = 0;
				}
				if(null != varThis.joImg){
					varThis.joImg.imgLeft = 0;
					varThis.joImg.imgTop = 0;
				}
				
				
				var joImg = currentDraggingTarget.subImgCanvas.joImg;
				currentDraggingTarget.subImgCanvas.joImg = varThis.joImg
				varThis.joImg = joImg;
				joImg = null;
				currentDraggingTarget.subImgCanvas.cleanAll();
				currentDraggingTarget.subImgCanvas.draw();
				varThis.cleanAll();
				varThis.draw();
			}
		}
		currentDraggingTarget.subImgCanvas = null;
	});
	
	EventUtil.addHandler(this.canvas, "mousemove", function(event){
		event = EventUtil.getEvent(event);
		EventUtil.stopPropagation(event);
		if(varThis.active && null != varThis.joImg){
			varThis.joImg.imgLeft = varThis.joImg.imgLeft + event.pageX - varThis.lastPageX;
			varThis.joImg.imgTop = varThis.joImg.imgTop + event.pageY - varThis.lastPageY;
			varThis.lastPageX = event.pageX;
			varThis.lastPageY = event.pageY;
			varThis.cleanAll();
			varThis.draw();
		}
	});
	
	function mousewheelHandle(event){
		event = EventUtil.getEvent(event);
		var delta = 0;
		if(event.wheelDelta){
			delta = event.wheelDelta;
		}else if(event.detail){
			delta = event.detail;
		}
		
		if(delta > 0){
			if(varThis.joImg.imgWidth < 1000 && varThis.joImg.imgHeight < 1000){
				varThis.joImg.imgWidth = varThis.joImg.imgWidth * 1.1;
				varThis.joImg.imgHeight = varThis.joImg.imgHeight * 1.1;
			}
		}else if(delta < 0){
			if(varThis.joImg.imgWidth > 40 && varThis.joImg.imgHeight > 40){
				varThis.joImg.imgWidth = varThis.joImg.imgWidth * 0.9;
				varThis.joImg.imgHeight = varThis.joImg.imgHeight * 0.9;
			}
		}
		varThis.cleanAll();
		varThis.draw();
	}
	
	EventUtil.addHandler(this.canvas, "mousewheel", mousewheelHandle);
	EventUtil.addHandler(this.canvas, "DOMMouseScroll", mousewheelHandle);
	
}
//继承方法
SubCanvas.prototype = new JoBaseCanvas();
//画图(包括前景图和可修改图)
SubCanvas.prototype.draw = function(){
	this.drawBackground();
	if(null != this.joImg){
		this.context.drawImage(this.joImg.img, this.joImg.imgLeft, this.joImg.imgTop, this.joImg.imgWidth, this.joImg.imgHeight);
	}
	this.drawForeground();
};
//load可修改图
SubCanvas.prototype.loadImg = function(imgurl){
	var img = new Image();
	var baseCanvas = this;
	img.src = imgurl;
	img.onload = function(){
		baseCanvas.cleanAll();
		var joImg = new JoImg(this);
		joImg.imgWidth = img.width;
		joImg.imgHeight = img.height;
		baseCanvas.joImg = joImg;
		baseCanvas.draw();
		img = null;
		varThis = null;
	}
};

/**
 * 子画板的图片
 * @param img
 * @returns {JoImg}
 */
function JoImg(img){
	this.img = img;
	this.imgWidth = null;
	this.imgHeight = null;
	this.imgLeft = 0;
	this.imgTop = 0;
}

/**
 * 子画板,文字画板(外层div,里面有canvas和textarea)
 * @param option
 * @returns {SubDivCanvas}
 */
function SubTextCanvas(option){
	JoBaseCanvas.call(this, option);
	var varThis = this;
	this.div = option.div;
	this.textarea = option.textarea;
	//文字style
	this.color = option.color;
	this.font = option.font;
	this.fontSize = option.fontSize;
	
	if(null != option.paddingLeft){
		this.paddingLeft = option.paddingLeft;
	}else{
		this.paddingLeft = 0;
	}
	
	if(null != option.paddingTop){
		this.paddingTop = option.paddingTop;
	}else{
		this.paddingTop = 0;
	}
	
	if(null != option.paddingRight){
		this.paddingRight = option.paddingRight;
	}else{
		this.paddingRight = 0;
	}
	
	if(null != option.paddingBottom){
		this.paddingBottom = option.paddingBottom;
	}else{
		this.paddingBottom = 0;
	}
	
	//设置文字样式
	this.context.textAlign = "start";
	this.context.textBaseline = "bottom";
	
	this.textarea.style.paddingLeft = this.paddingLeft + "px";
	this.textarea.style.paddingTop = this.paddingTop + "px";
	this.textarea.style.paddingRight = this.paddingRight + "px";
	this.textarea.style.paddingBottom = this.paddingBottom + "px";
	
	if(null != this.font){
		this.context.font = this.font;
		this.textarea.style.font = this.font;
	}
	if(null != this.color){
		this.context.fillStyle = this.color;
		this.textarea.style.color = this.color;
	}
	if(null == this.fontSize){
		this.fontSize = 14;
	}
	this.textarea.style.lineHeight = this.fontSize + "px";
	
	if(null != this.width){
		this.div.style.width = this.width + "px";
		this.textarea.style.width = this.width + "px";
	}
	if(null != this.height){
		this.div.style.height = this.height + "px";
		this.textarea.style.height = this.height + "px";
	}
	if(null != this.left){
		this.div.style.left = this.left + "px";
	}
	if(null != this.top){
		this.div.style.top = this.top + "px";
	}
	
	EventUtil.addHandler(this.canvas, "dblclick", function(){
		varThis.cleanAll();
		varThis.drawBackground();
		varThis.drawForeground();
		varThis.textarea.style.display = "block";
		varThis.textarea.focus();
		currentDraggingTarget.subTextCanvas = null;
	});
	
	EventUtil.addHandler(this.textarea, "blur", function(){
		varThis.textarea.style.display = "none";
		varThis.cleanAll();
		varThis.draw();
	});
	
	EventUtil.addHandler(this.canvas, "mousedown", function(event){
		event = EventUtil.getEvent(event);
		EventUtil.stopPropagation(event);
		currentDraggingTarget.subTextCanvas = varThis;
		varThis.lastPageX = event.pageX;
		varThis.lastPageY = event.pageY;
		varThis.active = true;
		varThis.div.style.cursor = "move";
	});
	
	
}
//继承方法
SubTextCanvas.prototype = new JoBaseCanvas();

SubTextCanvas.prototype.drawText = function(){
	var val = this.textarea.value;
	if(null != val && val.length > 0){
		var startIndex = 0,
		currentIndex = 0,
		currentStr,
		drawY = this.paddingTop + this.fontSize + 2,
		textContainLength = this.width - this.paddingLeft - this.paddingRight;
		
		var textLength = this.context.measureText(val).width
		while(currentIndex < val.length){
			currentStr = val.substring(startIndex, currentIndex + 1);
			if(textContainLength - this.context.measureText(currentStr).width < 0){
				this.context.fillText(val.substring(startIndex, currentIndex), this.paddingLeft + 1, drawY);
				drawY += this.fontSize;
				startIndex = currentIndex;
			}
			currentIndex++;
		}
		currentStr = val.substring(startIndex, val.length);
		this.context.fillText(val.substring(startIndex, currentIndex), this.paddingLeft + 1, drawY);
	}
}
SubTextCanvas.prototype.draw= function(){
	this.drawBackground();
	this.drawText();
	this.drawForeground();
}

EventUtil.addHandler(document.body, "mouseup", function(){
	for(var i in currentDraggingTarget){
		if(null != currentDraggingTarget[i]){
			currentDraggingTarget[i].active = false;
			if(null != currentDraggingTarget[i].div){
				currentDraggingTarget[i].div.style.cursor = "default";
			}else if(null != currentDraggingTarget[i].canvas){
				currentDraggingTarget[i].canvas.style.cursor = "default";
			}
		}
		currentDraggingTarget[i] = null;
	}
});


