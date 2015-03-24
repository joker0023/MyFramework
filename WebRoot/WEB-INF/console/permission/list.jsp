<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta charset="utf-8">
  	<title>Permission List</title>
  	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
  	<link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
  	<style type="text/css">
    	.container-fluid {
    		padding-top: 30px;
    	}
    	.control-container {
    		
    	}
    	.pagination {
    		float: right;
    		margin: 0;
    	}
    	.newRole {
    		padding: 10px 0;
    		visibility: hidden;
    	}
    	.newRole table {
    		
    	}
    	.newRole table td {
    		padding-right: 10px;
    	}
    	.table .cancel, .table .save {
    		display: none;
    	}
    	.table td {
    		height: 48px;
    	}
    	.table td input[type='text'] {
    		display: none;
    		width: 100px;
    		padding-top: 3px;
    		padding-bottom: 3px;
    		height: 30px;
    	}
  	</style>
  </head>
  <body>
	<div class="container-fluid">
		<div class="control-container">
			<a href="javascript:void(0);" class="btn btn-primary" id="addRole">增加</a>
			<div class="newRole">
				<table>
					<tr>
						<td style="width: 120px;"><input type="text" class="form-control addName" placeholder="名称"></td>
						<td style="width: 120px;"><input type="text" class="form-control addDescription" placeholder="说明"></td>
						<td><input type="text" class="form-control addNamespace" placeholder="namespace"></td>
						<td style="width: 140px;">
							<a href="javascript:void(0);" class="btn btn-info addAsve">保存</a>
							<a href="javascript:void(0);" class="btn btn-default addCancel">取消</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<table class="table table-hover table-striped table-bordered">
			<thead>
				<tr>
					<th style="width: 40px;"><input type="checkbox" class="select-all"></th>
					<th style="width: 40px;">Id</th>
					<th style="width: 120px;">名称</th>
					<th style="width: 120px;">说明</th>
					<th>namespace</th>
					<th style="width: 120px;">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.data.consoleRoleList}" var="role">
					<tr>
						<td><input type="checkbox" class="roleId-checkbox" value="${role.id}"></td>
						<td>${role.id}</td>
						<td class="editTd" >
							<span>${role.name}</span>
							<input type="text" class="form-control name" value="${role.name}">
						</td>
						<td class="editTd">
							<span>${role.description}</span>
							<input type="text" class="form-control description" value="${role.description}">
						</td>
						<td class="editTd">
							<span>${role.namespace}</span>
							<input type="text" class="form-control namespace" value="${role.namespace}" style="width: 90%;">
						</td>
						<td>
							<c:if test="${role.id != 1}">
								<a href="javascript:void(0);" class="btn btn-info btn-xs update">修改</a>
								<a href="javascript:void(0);" class="btn btn-info btn-xs save">保存</a>
								<a href="javascript:void(0);" class="btn btn-default btn-xs cancel">取消</a>
								<a href="javascript:void(0);" class="btn btn-danger btn-xs delete">删除</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6" style="background-color: #EEE;"></td>
				</tr>
			</tfoot>
		</table>
		<%@ include file="/WEB-INF/console/pager.jsp"%>
	</div>
	
  	<script src="${pageContext.request.contextPath }/assets/js/jquery-1.10.2.min.js"></script>
  	<script src="${pageContext.request.contextPath }/assets/js/pager.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
  	<script type="text/javascript">
  		$(function(){
  			$(".select-all").click(function(){
				if($(this).is(":checked")) {
					$(this).parents("table").find("input:checkbox.roleId-checkbox").each(function(){
		  				this.checked = true;
		  			});
				} else {
					$(this).parents("table").find("input:checkbox.roleId-checkbox").each(function(){
		  				this.checked = false;
		  			});
				}
			});
  			
  			//弹出新增框
  			$("#addRole").click(function(){
  				$(".newRole input").val("");
  				$(".newRole").css("visibility","visible");
  			});
  			
  			//新增保存
  			$(".newRole .addAsve").click(function(){
  				var $tr = $(this).parents("tr");
  				var name = $tr.find(".addName").val();
  				var description = $tr.find(".addDescription").val();
  				var namespace = $tr.find(".addNamespace").val();
  				
  				$.ajax({
  					type: "post",
  					url: "${pageContext.request.contextPath }/console/permission/add.do",
  					data: {
  						name: name,
  						description: description,
  						namespace, namespace
  					},
  					dataType: "json",
  					success: function(data){
  						showTipAndReload(data,"保存");
  					},
  					error: function(data, status ,e){
  						alert(e);
  					}
  				});
  			});
  			
  			//新增取消
			$(".newRole .addCancel").click(function(){
				$(".newRole").css("visibility","hidden");
  			});
  			
  			//修改按钮
  			$(".update").click(function(){
  				var $this = $(this);
  				$this.parent().find(".update").hide();
  				$this.parent().find(".save").show();
  				$this.parent().find(".cancel").show();
  				$this.parent().find(".delete").hide();
  				
  				$this.parents("tr").find(".editTd > input[type='text']").each(function(){
  					$(this).val($(this).prev("span").text());
  					$(this).show();
  					$(this).prev("span").hide();
  				});
  			});
  			
  			//修改保存
  			$(".save").click(function(){
  				var $tr = $(this).parents("tr");
  				var id = $tr.find("input[type='checkbox']").val();
  				var name = $tr.find(".name").val();
  				var description = $tr.find(".description").val();
  				var namespace = $tr.find(".namespace").val();
  				
  				$.ajax({
  					type: "post",
  					url: "${pageContext.request.contextPath }/console/permission/update.do",
  					data: {
  						id: id,
  						name: name,
  						description: description,
  						namespace, namespace
  					},
  					dataType: "json",
  					success: function(data){
  						showTipAndReload(data,"修改");
  					},
  					error: function(data, status ,e){
  						alert(e);
  					}
  				});
  			});
  			
  			//修改取消
  			$(".cancel").click(function(){
  				var $this = $(this);
  				$this.parent().find(".update").show();
  				$this.parent().find(".save").hide();
  				$this.parent().find(".cancel").hide();
  				$this.parent().find(".delete").show();
  				
  				$this.parents("tr").find(".editTd > input[type='text']").hide();
  				$this.parents("tr").find(".editTd > span").show();
  			});
  			
  			//删除按钮
  			$(".delete").click(function(){
  				if(confirm("确认删除?")){
  					var id = $(this).parents("tr").find("input[type='checkbox']").val();
  	  				
  	  				$.ajax({
  	  					type: "post",
  	  					url: "${pageContext.request.contextPath }/console/permission/delete.do",
  	  					data: {
  	  						id: id
  	  					},
  	  					dataType: "json",
  	  					success: function(data){
  	  						showTipAndReload(data,"删除");
  	  					},
  	  					error: function(data, status ,e){
  	  						alert(e);
  	  					}
  	  				});
  				}
  			});
  		});
  	</script>
  </body>
</html>
