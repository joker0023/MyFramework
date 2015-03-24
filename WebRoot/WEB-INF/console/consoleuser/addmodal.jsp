<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="modal fade" id="addModal">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span>&times;</span>
				</button>
				<h4 class="modal-title">新增</h4>
			</div>
			<div class="modal-body">
				<form action="" onsubmit="return false;" class="form-horizontal">
					<div class="form-group">
						<label for="add_account" class="col-xs-3 control-label">账号</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="add_account" placeholder="账号" required="required">
						</div>
					</div>
					<div class="form-group">
						<label for="add_password" class="col-xs-3 control-label">密码</label>
						<div class="col-xs-7">
							<input type="password" class="form-control" id="add_password" placeholder="密码" required="required">
						</div>
					</div>
					<div class="form-group">
						<label for="add_confirmPassword" class="col-xs-3 control-label">确认密码</label>
						<div class="col-xs-7">
							<input type="password" class="form-control" id="add_confirmPassword" placeholder="确认密码" required="required">
						</div>
					</div>
					<div class="form-group">
						<label for="add_role" class="col-xs-3 control-label">角色</label>
						<div class="col-xs-5">
							<select class="form-control" id="add_role">
								<c:forEach items="${requestScope.data.roleList}" var="role">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="add_status" class="col-xs-3 control-label">状态</label>
						<div class="col-xs-4">
							<select class="form-control" id="add_state">
								<c:forEach items="${requestScope.data.consoleUserStateMap}" var="stateMap">
									<option value="${stateMap.key }">${stateMap.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</form>
			</div>
			<div class="modal-footer">
	        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        	<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
	      	</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function(){
		$("#saveBtn").click(function(){
			var url = "${pageContext.request.contextPath }/console/consoleuser/add.do";
			
			var account = $("#add_account").val();
			var password = $("#add_password").val();
			var confirmPassword = $("#add_confirmPassword").val();
			var role = $("#add_role").val();
			var state = $("#add_state").val();
			
			if(null == account || account == ""){
				alert("账号不能空!");
				return;
			}
			if(null == password || password == ""){
				alert("密码不能空!");
				return;
			}
			if(password != confirmPassword){
				alert("两次输入密码不一致!");
				return;
			}
			
			$.ajax({
				type: "post",
				url: url,
				data: {
					account: account,
					password: password,
					role: role,
					state: state
				},
				dataType: "json",
				success: function(data){
					showTipAndReload(data,"保存");
				},
				error: function(data, status, e){
					alert(e);
				}
			});
		});
	});
</script>