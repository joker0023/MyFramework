var EventUtil = {
		addHandler: function(element, type, handler){
			if(element.addEventListener){
				element.addEventListener(type, handler, false);
			}else if(element.attachEvent){
				element.attachEvent(type, handler);
			}else{
				element["on" + type] = handler;
			}
		},

		removeHandler: function(element, type, handler){
			if(element.removeEventListener){
				element.removeEventListener(type, handler, false);
			}else if(element.detachEvent){
				element.detachEvent(type, handler);
			}else{
				element["on" + type] = null;
			}
		},
		
		getEvent: function(event){
			return event ? event : window.event;
		},
		
		getTarget: function(event){
			return event.target || event.srcElement;
		},
		
		preventDefault: function(event){
			if(event.preventDefault){
				event.preventDefault();
			}else{
				event.returnValue = false;
			}
		},
		
		stopPropagation: function(event){
			if(event.stopPropagation){
				event.stopPropagation();
			}else{
				event.cancelBubble = true;
			}
		},
		
		getCharCode : function(event){
			if(typeof event.charCode == "number"){
				return event,charCode;
			}else{
				return evnet.keyCode;
			}
		},
		
		getClipboardText: function(event){
			var clipboardData = (event.clipboardData || window.clipboardData);
			return clipboardData.getData("text");
		},
		
		setClipboardText: function(event, value){
			if(event.clipboardData){
				return event.clipboardData.setData("text/plain", value);
			}else if(window.clipboardData){
				return window.clipboardData.setData("text", value);
			}
		}
}

var browserClient = function(){
	//引擎
	var engine = {
		ie:0,
		gecko: 0,
		webkit: 0,
		khtml: 0,
		opera: 0,
		
		ver: null
	};
	
	//浏览器
	var browser = {
		ie: 0,
		firefox: 0,
		safari: 0,
		konq: 0,
		opera: 0,
		chrome: 0,
		
		ver: null
	};
	
	//平台,设备和操作系统
	var system = {
		win: false,
		mac: false,
		X11: false,
		//移动设备
		iphone: false,
		ipod: false,
		ipad: false,
		ios: false,
		android: false,
		nokiaN: false,
		winMobile: false,
		//游戏设备
		wii: false,
		ps: false
	};
	
	//检测引擎和浏览器
	var ua = navigator.userAgent;
	if(window.opera){
		engine.ver = browser.ver = window.opera.version();
		engine.opera = browser.opera = parseFloat(engine.ver);
	}else if(/AppleWebkit\/(\S+)/.test(ua)){
		engine.ver = RegExp["$1"];
		engine.webkit = parseFloat(engine.ver);
		
		//确定是chrome还是safari
		if(/Chrome\/(\S+)/.test(ua)){
			browser.ver = RegExp["$1"];
			browser.chrome = parseFloat(browser.ver);
		}else if(/Version\/(\S+)/.test(ua)){
			browser.ver = RegExp["$1"];
			browser.safari = parseFloat(browser.ver);
		}else{
			var safariVersion = 1;
			if(engine.webkit < 100){
				safariVersion = 1;
			}else if(engine.webkit < 312){
				safariVersion = 1.2;
			}else if(engine.webkit < 412){
				safariVersion = 1.3;
			}else{
				safariVersion = 2;
			}
			
			browser.safari = browser.ver = safariVersion;
		}
	}else if(/KHTML\/(\S+)/.test(ua)){
		engine.ver = browser.ver = RegExp["$1"];
		engine.khtml = browser.konq = parseFloat(engine.ver);
	}else if(/rv:([^\)]+)\) Gecko\/\d{8}/.test(ua)){
		engine.ver = RegExp["$1"];
		engine.gecko = parseFloat(engine.ver);
		
		//确定是不是firefox
		if(/FireFox\/(\S+)/.test(ua)){
			engine.ver = RegExp["$1"];
			engine.firefox = parseFloat(browser.ver);
		}
	}else if(/MSIE ([^;]+)/.test(ua)){
		engine.ver = browser.ver = RegExp["$1"];
		engine.ie = browser.ie = parseFloat(engine.ver);
	}
	
	//检测平台
	var p = navigator.platform;
	system.win = p.indexOf("Win") == 0;
	system.mac = p.indexOf("Mac") == 0;
	system.X11 = (p == "X11") || (p.indexOf("Linux") == 0);
	
	//检测Windows操作系统
	if(system.win){
		if(/Win(?:dows)?([^do]{2})\s?(\d+\.\d+)?/.test(ua)){
			if(RegExp["$1"] == "NT"){
				switch (RegExp["$2"]) {
				case "5.0":
					system.win = "2000";
					break;
				case "5.1":
					system.win = "XP";
					break;
				case "6.0":
					system.win = "Vista";
					break;
				case "6.1":
					system.win = "7";
					break;
				default:
					system.win = "NT";
					break;
				}
			}else if(RegExp["$1"] == "9x"){
				system.win = "ME";
			}else{
				system.win = RegExp["$1"];
			}
		}
	}
	
	//移动设备
	system.iphone = ua.indexOf("iphone") > -1;
	system.ipod = ua.indexOf("ipod") > -1;
	system.ipad = ua.indexOf("ipad") > -1;
	system.nokiaN = ua.indexOf("nokiaN") > -1;

	if(system.win == "CE"){
		system.winMobile = system.win;
	}else if(system.win == "Ph"){
		if(/Windows Phone OS (\d+\.\d+)/.test(ua)){
			system.win = "Phone";
			system.winMobile = parseFloat(RegExp["$1"]);
		}
	}
	
	//检测iOS版本
	if(system.mac && ua.indexOf("Mobile") > -1){
		if(/CPU (?:iphone)?OS (\d+_\d+)/.test(ua)){
			system.ios = parseFloat(RegExp.$1.replace("_", "."));
		}else{
			system.ios = 2;
		}
	}
	
	//检测Android版本
	if(/Android (\d+\.\d+)/.test(ua)){
		system.android = parseFloat(RegExp.$1);
	}
	
	//游戏系统
	system.wii = ua.indexOf("Wii") > -1;
	system.ps = /playstation/i.test(ua);
	
	return {
		engine: engine,
		browser: browser,
		system: system
	};
}();

var FileUtil = {
	getImgURL: function(file){
		 var url;
		 if(window.URL){
			 url = window.URL.createObjectURL(file);
		 }else if(window.webkitURL){
			 url = window.webkitURL.createObjectURL(file);
		 }else{
			 var reader = new FileReader();
			 reader.readAsDataURL(file);
			 reader.onload = function(){
				 url = reader.result;
	         }
		 }
		 return url;
	}
}
