<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="utf-8">
  	<title>Login info</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
  	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
  	<style type="text/css">
  		body {
  			color: #999;
  		}
  		.row {
  			margin-bottom: 20px;
  			border: 1px solid #EEE;
  		}
  		.note {
  			padding: 5px;
  		}
  		.mainInfo {
  			padding: 10px;
  			display: block;
  		}
  		.mainInfo .avatar img {
  			width: 100%;
  			cursor: pointer;
  		}
  		.mainInfo .comm-info .account-info {
  			font-style: italic;
  		}
  		.mainInfo .comm-info .nick-info {
		    font-family: inherit;
		    font-weight: 500;
		    line-height: 40px;
			font-size: 24px;
			height: 40px;
  		}
  		.mainInfo .comm-info .nick-info .nick-change {
  			display: none;
  		}
  		.mainInfo .comm-info .nick-info .nick-change input {
  			width: 100px;
  			display: inline-block;
  			height: 35px;
  		}
  		.other-info .detail-info {
  			padding: 10px;
  		}
  		.other-info .wait-ctrl-container {
  			padding: 0;
  			margin-bottom: 50px;
  		}
  		.wait-ctrl {
  			border-left: 1px solid #ddd;
  			border-bottom: 1px solid #ddd;
  		}
  		.wait-ctrl > a {
  			display: block;
  			text-decoration: none;
  			padding: 10px;
  			color: #CCC;
  			font-size: 24px;
  		}
  		.wait-ctrl > a:HOVER {
			color: #FFF;
			background-color: #3399CC;
		}
		.modal-sm {
    		width: 500px;
    	}
    	
  	</style>
  </head>
  <body>
  	<div class="container">
  		<div class="row alert alert-info note">
  			<button type="button" class="close" data-dismiss="alert">
  				<span>&times;</span>
  			</button>
  			this is note
  		</div>
  		<div class="row mainInfo">
  			<div class="avatar col-sm-2 col-xs-4">
  				<img src="${pageContext.request.contextPath }${requestScope.data.loginUser.avatar }" alt="头像" class="img-circle" id="avatarUp" title="上传头像">
  			</div>
  			<div class="comm-info col-sm-10 col-xs-8">
  				<div>
  					<h3 class="account-info">账号: (${requestScope.data.loginUser.account })(${requestScope.data.loginRole.name })</h3>
  					<div class="nick-info">
  						昵称:
  						<span class="nick-static">${requestScope.data.loginUser.nick }</span>
  						<span class="nick-change">
  							<input type="text" class="form-control" value="${requestScope.data.loginUser.nick }">
  						</span>
  					</div>
  				</div>
  				<div>
  					<a href="javascript:void(0);" class="btn btn-info btn-xs" data-toggle="modal" data-target="#editPwdModal">修改密码</a>
  				</div>
  			</div>
  		</div>
  		<div class="row other-info">
  			<div class="col-xs-8 detail-info"> 
  				<div style="width: 30%;">
  					<a href="javascript:void(0);" class="btn btn-info btn-block">其他操作</a>
  					<a href="javascript:void(0);" class="btn btn-primary btn-block">其他操作</a>
  					<a href="javascript:void(0);" class="btn btn-success btn-block">其他操作</a>
  					<a href="javascript:void(0);" class="btn btn-warning btn-block">其他操作</a>
  					<a href="javascript:void(0);" class="btn btn-danger btn-block">其他操作</a>
  				</div>
  			</div> 
  			<div class="col-xs-4 wait-ctrl-container">
  				<div class="wait-ctrl">
  					<a href="javascript:void(0);">0 待处理信息</a>
  				</div>
  				<div class="wait-ctrl">
  					<a href="javascript:void(0);">0 待处理信息</a>
  				</div>
  				<div class="wait-ctrl">
  					<a href="javascript:void(0);">1 待处理信息</a>
  				</div>
  			</div>
  		</div>
  	</div>
  	
  	<div class="modal fade" id="editPwdModal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span>&times;</span>
					</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
				<div class="modal-body">
					<form action="" onsubmit="return false;" class="form-horizontal">
						<div class="form-group">
							<label for="add_password" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-7">
								<input type="password" class="form-control" id="editPwd_password" placeholder="密码" required="required">
							</div>
						</div>
						<div class="form-group">
							<label for="add_confirmPassword" class="col-sm-3 control-label">确认密码</label>
							<div class="col-sm-7">
								<input type="password" class="form-control" id="editPwd_confirmPassword" placeholder="确认密码" required="required">
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
		        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		        	<button type="button" class="btn btn-primary" id="editPwdBtn">保存</button>
		      	</div>
			</div>
		</div>
	</div>
	
	<div id="alert-tip">
		<span class="glyphicon glyphicon-ok"></span>
		<span>保存成功</span>
	</div>

  	<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/imgUploadModal.js"></script>
  	<script type="text/javascript">
  		$(function(){
  			$(".mainInfo .comm-info .nick-info .nick-static").dblclick(function(){
  				var $this = $(this);
  				$this.hide();
  				$this.next(".nick-change").show();
  				$this.next(".nick-change").find("input").focus();
  			});
  			
  			// 更改昵称
  			$(".mainInfo .comm-info .nick-info .nick-change input").blur(function(){
  				var $parent = $(this).parent();
  				var oldNick = $parent.prev(".nick-static").text();
  				var newNick = $(this).val();
  				if(oldNick == newNick){
  					$parent.hide();
	  	  			$parent.prev(".nick-static").show();
	  	  			return;
  				}
  				if(null != newNick && $.trim(newNick) != ""){
  					$.ajax({
  						type: "post",
  						url: "${pageContext.request.contextPath }/console/console_updateBySelf.do",
  						data: {nick:newNick},
  						dataType: "json",
  						success: function(data){
  							showTip(data, "修改");
  						},
  						error: function(data, status, e){
  							alert(e);
  						}
  					});
  				}
  			});
  			
  			$("#editPwdModal").on("show.bs.modal", function (e) {
				$(this).find("form input").val("");
			});
  			
  			// 更改密码
  			$("#editPwdBtn").click(function(){
  				var password = $("#editPwd_password").val();
  				var confirmPassword = $("#editPwd_confirmPassword").val();
  				if(null == password || $.trim(password) == ""){
  					alert("密码不能为空");
  					return;
  				}
  				if(password != confirmPassword){
  					alert("两次输入密码不一致");
  					return;
  				}
  				
  				$.ajax({
						type: "post",
						url: "${pageContext.request.contextPath }/console/console_updateBySelf.do",
						data: {password: password},
						dataType: "json",
						success: function(data){
							showTip(data, "修改");
						},
						error: function(data, status, e){
							alert(e);
						}
					});
  			});
  			
  			// 上传头像
  			var imgModal = new ImgUploadModal({
  				circle:true,
  				action: "${pageContext.request.contextPath }/console/console_uploadAvatar.do",
  				img: "${pageContext.request.contextPath }${requestScope.data.loginUser.avatar }",
  				success: function(data){
  					showTip(data, "上传");
  				}
  			});
  			
  			$("#avatarUp").click(function(){
  				imgModal.show();
  			});
  			
  		});
  		
  		function showTip(data, tip){
  			if(null != data && data.isSuccess == true){
  				$("#alert-tip").find("span").last().text(tip + "成功");
				$("#alert-tip").show();
				setTimeout(function(){
					$("#alert-tip").hide();
					window.location.reload();
				}, 1000);
				imgModal.hide();
			}else {
				alert(tip + "失败: " + data.errMsg);
			}
  		}
  	</script>
  </body>
</html>
