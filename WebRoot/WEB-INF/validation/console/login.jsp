<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>后台登录</title>
		<link rel="shortcut icon" href="${pageContext.request.contextPath }/favicon.ico">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
		<style type="text/css">
			.container {
				border: #ccc 1px solid;
				border-width: 0px 1px;;
				border-radius: 0 0 100px 100px;
				min-width: 350px;
			}
			.form-signin {
			    margin: 100px auto;
			    max-width: 350px;
			    padding-bottom: 20px;;
			    border: 1px solid #CFDAE6;
			    border-radius: 10px;
			    box-shadow: 0 1px 1px #E6EFF2;
			    background-color: #F7FCFF;
			}
			.form-signin-activite {
				 box-shadow : 2px 2px 10px lightskyblue,
			    			 2px -2px 10px lightskyblue,
			    			 -2px 2px 10px lightskyblue,
			    			 -2px -2px 10px lightskyblue;
			}
			.form-signin .form-signin-head {
				border-radius: 10px 10px 0 0;
				border-bottom: 1px solid #CFDAE6;
				background-color: #F3F8FF;
				background: linear-gradient(to bottom, #F7FCFF, #F3F8FF, #F7FCFF);
				height: 40px;
				text-align: center;
				font-size: 27px;
				font-weight: bold;
				color: #428BCA;
			}
			.form-signin .form-signin-body {
				width: 250px;
				margin: 0 auto;
			}
			.control-label {
				margin-bottom: 0;
				display: none;
			}
			.errLabel {
				height: 30px;
				color: #a94442;
				line-height: 30px;
			}
		</style>
	</head>
	<body>
		<div class="container">
			<form action="${pageContext.request.contextPath }/validation/console_login_handle.do" class="form-signin form-signin-activite" method="post">
				<div class="form-signin-head">
					后台登录
				</div>
				<div class="form-signin-body">
					<div class="errLabel">
						<label class="control-label" for="inputError">Input with error</label>
					</div>
					<div class="form-group">
  						<input type="text" class="form-control" name="account" placeholder="账号" autofocus value="${requestScope.data.account}">
					</div>
					<div class="form-group">
  						<input type="password" class="form-control" name="password" placeholder="密码" autocomplete="off">
					</div>
					<label class="checkbox">
						<input type="checkbox" value="记住我">记住我
					</label>
					<button class="btn btn-lg btn-primary btn-block">登录</button>
				</div>
			</form>
		</div>
	</body>
	
	<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(function(){
			var errMsg = "${requestScope.data.errMsg}";
			
			if(null != errMsg && errMsg != ""){
				$("input[name='account']").parent().addClass("has-error");
				$(".control-label").text(errMsg);
				$(".control-label").show();
			}
			
			$("input").click(function(){
				$(".control-label").hide();
				$(".form-group").removeClass("has-error");
			});
			
			$(".form-signin").click(function(event){
				if(typeof event.stopPropagation != "undefined" ){
			   		event.stopPropagation();
			    } else{
			    	event.cancelBubble = true;
			    }
				$(this).addClass("form-signin-activite");
			});
			
			$("body").click(function(){
				$(".form-signin").removeClass("form-signin-activite");
			});
			
			$("form").submit(function(){
				var account = $("input[name='account']").val();
				var password = $("input[name='password']").val();
				
				if(null == account || "" == $.trim(account)){
					$("input[name='account']").focus();
					$("input[name='account']").parent().addClass("has-error");
					$(".control-label").text("账号不能为空");
					$(".control-label").show();
					return false;
				}else if(null == password || "" == $.trim(password)){
					$("input[name='password']").focus();
					$("input[name='password']").parent().addClass("has-error");
					$(".control-label").text("密码不能为空");
					$(".control-label").show();
					return false;
				}
				
				return true;
			});
		});
	</script>
</html>