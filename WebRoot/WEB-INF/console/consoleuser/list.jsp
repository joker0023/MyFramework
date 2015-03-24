<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta charset="utf-8">
    <title>LIST</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
	<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
    <style type="text/css">
    	.container-fluid {
    		padding-top: 30px;
    		min-height: 600px;
    	}
    	.form-inline .form-group {
    		display: inline-block;
    		margin-bottom: 0;
   	 		vertical-align: middle;
    	}
    	.search-container {
    		float: right;
    		margin-bottom: 30px;
    	}
    	.pagination {
    		float: right;
    		margin: 0;
    	}
    	.modal-sm {
    		width: 500px;
    	}
    </style>
  </head>
  
  <body>
  
	<div class="container-fluid">
		<div class="col-xs-5">
			<a href="javascript:void(0);" class="btn btn-primary" id="add" data-toggle="modal" data-target="#addModal">增加</a>
			<a href="javascript:void(0);" class="btn btn-primary" id="batchDel">批量删除</a>
		</div>
		<div class="col-xs-7">
			<form class="form-inline search-container" action="${pageContext.request.contextPath }/console/consoleuser/list.do">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search" name="searchVal" value="${requestScope.data.searchVal }">
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</form>
		</div>
		<table class="table table-hover table-striped table-bordered">
			<thead>
				<tr>
					<th><input type="checkbox" class="select-all"></th>
					<th>头像</th>
					<th>账号</th>
					<th>昵称</th>
					<th>角色</th>
					<th>状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.data.userList}" var="user">
					<tr>
						<td><input type="checkbox" class="userId-checkbox" value="${user.id}"></td>
						<td><img src="${pageContext.request.contextPath }${user.avatar}" width="30"></td>
						<td>${user.account}</td>
						<td>${user.nick}</td>
						<td>${user.roleName}</td>
						<td>
							<span style="color:${user.state == 2?'red':'green' }">${requestScope.data.consoleUserStateMap[user.state]}</span>
						</td>
						<td>
							<a href="javascript:void(0);" class="btn btn-info btn-xs detail-btn">详细</a>
							<a href="javascript:void(0);" class="btn btn-danger btn-xs del-btn">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="7" style="background-color: #EEE;">
					</td>
				</tr>
			</tfoot>
		</table>
		<%@ include file="/WEB-INF/console/pager.jsp"%>
	</div>
	
	<div class="modal fade" id="editModal"></div>
	<%@ include file="/WEB-INF/console/consoleuser/addmodal.jsp"%>
	<script src="${pageContext.request.contextPath }/assets/js/pager.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
	<script type="text/javascript">
		$(function(){
			$(".select-all").click(function(){
				if($(this).is(":checked")) {
					$(this).parents("table").find("input:checkbox.userId-checkbox").each(function(){
		  				this.checked = true;
		  			});
				} else {
					$(this).parents("table").find("input:checkbox.userId-checkbox").each(function(){
		  				this.checked = false;
		  			});
				}
			});
			
			$("#addModal").on("show.bs.modal", function (e) {
				$(this).find("form input").val("");
				$("#add_role").val($("#add_role > option:first").val());
				$("#add_state").val($("#add_state > option:first").val());
			});
			
			$("#batchDel").click(function(){
				if(confirm("确定删除选中项?")){
					var ids = "";
					$(".userId-checkbox:checked").each(function(){
						ids += $(this).val() + ",";
					});
					if(ids.length > 0){
						$.ajax({
							type: "post",
							url: "${pageContext.request.contextPath }/console/consoleuser/del.do",
							data: {ids: ids},
							dataType: "json",
							success: function(data){
								showTipAndReload(data,"删除");
							},
							error: function(data, status, e){
								alert(e);
							}
						});
					}else{
						alert("请至少选择一项");
					}
				}
			});
			
			$(".del-btn").click(function(){
				if(confirm("确定删除?")){
					var id = $(this).parents("tr").find("input[type='checkbox']").val();
					if(null == id){
						return;
					}
					
					$.ajax({
						type: "post",
						url: "${pageContext.request.contextPath }/console/consoleuser/del.do",
						data: {ids: id},
						dataType: "json",
						success: function(data){
							showTipAndReload(data,"删除");
						},
						error: function(data, status, e){
							alert(e);
						}
					});
				}
			});
			
			$(".detail-btn").click(function(){
				var id = $(this).parents("tr").find("input[type='checkbox']").val();
				
				$.ajax({
					type: "post",
					url: "${pageContext.request.contextPath }/console/consoleuser/edit.do",
					data: {
						id: id
					},
					success: function(data){
						$("#editModal").html(data);
						$("#edit_role").val($("#edit_role").data("val"));
						$("#edit_state").val($("#edit_state").data("val"));
						$("#editModal").modal("show");
					},
					error: function(data, status, e){
						alert(e);
					}
				});
			});
			
			$("#editModal").on("click", "#updateBtn", function(){
				var url = "${pageContext.request.contextPath }/console/consoleuser/update.do";
				
				var id = $("#edit_userId").val();
				var password = $("#edit_password").val();
				var confirmPassword = $("#edit_confirmPassword").val();
				var role = $("#edit_role").val();
				var state = $("#edit_state").val();
				
				if(password != confirmPassword){
					alert("两次输入密码不一致!");
					return;
				}
				
				$.ajax({
					type: "post",
					url: url,
					data: {
						id: id,
						password: password,
						role: role,
						state: state
					},
					dataType: "json",
					success: function(data){
						showTipAndReload(data,"修改");
					},
					error: function(data, status, e){
						alert(e);
					}
				});
			});
			
		});
	</script>
  </body>
</html>
