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
    		width: 95%;
    	}
    	.btn.update, .btn.cancel {
    		display: none;
    	}
    	.stateEdit, .gradeEdit, .integralEdit {
    		width: 100px;
    	}
    	.control {
    		width: 140px;
    	}
    	.stateEdit select, .gradeEdit select, .integralEdit input {
    		padding-top: 3px;
    		padding-bottom: 3px;
    		height: 30px;
    	}
    </style>
  </head>
  
  <body>
	<div class="container-fluid">
		<div>
			<form class="form-inline search-container" action="${pageContext.request.contextPath }/console/webuser/list.do">
				<div class="form-group">
					<select name="userState" class="form-control">
						<option value="-1">未选择</option>
						<c:forEach items="${requestScope.data.webUserStateMap}" var="stateMap">
							<option value="${stateMap.key }">${stateMap.value }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="账号" name="account" value="${requestScope.data.account }">
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
					<th>状态</th>
					<th>等级</th>
					<th>积分</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.data.userList}" var="userMap">
					<tr>
						<td><input type="checkbox" class="userId-checkbox" value="${userMap.webUser.id}"></td>
						<td><img src="${pageContext.request.contextPath }${userMap.webUserInfo.avatar}" width="30"></td>
						<td>${userMap.webUser.account}</td>
						<td>${userMap.webUserInfo.nick}</td>
						<td class="stateEdit">
							<span data-state="${userMap.webUser.state }" style="color:${userMap.webUser.state == 2?'#660000':userMap.webUser.state == 1?'#3399FF':'#999'};">
								${requestScope.data.webUserStateMap[userMap.webUser.state]}
							</span>
							<select style="display: none;" class="form-control">
								<c:forEach items="${requestScope.data.webUserStateMap}" var="stateMap">
									<option value="${stateMap.key }">${stateMap.value }</option>
								</c:forEach>
							</select>
						</td>
						<td class="gradeEdit">
							<span data-grade="${userMap.webUserInfo.grade }" style="color:${userMap.webUserInfo.grade == 2?'#FF0000':'#3399FF'};">
								${requestScope.data.webUserGradeMap[userMap.webUserInfo.grade]}
							</span>
							<select style="display: none;" class="form-control">
								<c:forEach items="${requestScope.data.webUserGradeMap}" var="gradeMap">
									<option value="${gradeMap.key }">${gradeMap.value }</option>
								</c:forEach>
							</select>
						</td>
						<td class="integralEdit">
							<span>${userMap.webUserInfo.integral}</span>
							<input type="text" value="${userMap.webUserInfo.integral}" class="form-control" style="display: none;">
						</td>
						<td class="control">
							<a href="javascript:void(0);" class="btn btn-info btn-xs detail">详细</a>
							<a href="javascript:void(0);" class="btn btn-primary btn-xs edit">修改</a>
							<a href="javascript:void(0);" class="btn btn-primary btn-xs update">保存</a>
							<a href="javascript:void(0);" class="btn btn-danger btn-xs cancel">取消</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="8" style="background-color: #EEE;"></td>
				</tr>
			</tfoot>
		</table>
		<%@ include file="/WEB-INF/console/pager.jsp"%>
	</div>
	
	<div class="modal fade" id="detailModal"></div>
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
			
			(function(){
				var state = "${requestScope.data.userState}";
				if(null != state && state != ""){
					$("select[name='userState'] > option[value='" + state + "']").attr("selected",true);
				}
			})();
			
			$(".detail").click(function(){
				var id = $(this).parents("tr").find("input[type='checkbox']").val();
				
				$.ajax({
					type: "post",
					url: "${pageContext.request.contextPath }/console/webuser/detail.do",
					data: {
						id: id
					},
					success: function(data){
						$("#detailModal").html(data);
						$("#detailModal").modal("show");
						parent.autoIframeHeight();
					},
					error: function(data, status, e){
						alert(e);
					}
				});
			});
			
			$(".edit").click(function(){
				var $this = $(this);
				$this.hide();
				$this.parent().find(".update").show();
				$this.parent().find(".cancel").show();
				
				var $stateEdit = $this.parents("tr").find(".stateEdit");
				$stateEdit.find("span").hide();
				$stateEdit.find("select").val($stateEdit.find("span").data("state"));
				$stateEdit.find("select").show();
				
				var $gradeEdit = $this.parents("tr").find(".gradeEdit");
				$gradeEdit.find("span").hide();
				$gradeEdit.find("select").val($gradeEdit.find("span").data("grade"));
				$gradeEdit.find("select").show();
				
				var $integralEdit = $this.parents("tr").find(".integralEdit");
				$integralEdit.find("span").hide();
				$integralEdit.find("input").val($integralEdit.find("span").text());
				$integralEdit.find("input").show();
				
			});

			$(".update").click(function(){
				var $this = $(this);
				
				var id = $this.parents("tr").find("input[type='checkbox']").val();
				var state = $this.parents("tr").find(".stateEdit select").val();
				var grade = $this.parents("tr").find(".gradeEdit select").val();
				var integral = $this.parents("tr").find(".integralEdit input").val();
				
				$.ajax({
					type: "post",
					url: "${pageContext.request.contextPath }/console/webuser/update.do",
					data: {id:id,state:state,grade:grade,integral:integral},
					dataType: "json",
					success: function(data){
						showTipAndReload(data,"修改");
					},
					error: function(data, status, e){
						alert(e);
					}
				});
				
			});
			
			$(".cancel").click(function(){
				var $this = $(this);
				$this.parent().find(".update").hide();
				$this.parent().find(".cancel").hide();
				$this.parent().find(".edit").show();
				
				$this.parents("tr").find(".stateEdit span").show();
				$this.parents("tr").find(".stateEdit select").hide();
				
				$this.parents("tr").find(".gradeEdit span").show();
				$this.parents("tr").find(".gradeEdit select").hide();
				
				$this.parents("tr").find(".integralEdit span").show();
				$this.parents("tr").find(".integralEdit input").hide();
			});
			
		});
	</script>
  </body>
</html>
