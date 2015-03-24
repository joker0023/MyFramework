<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<meta charset="utf-8">
    <title>LIST</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/css/base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery-ui.css">
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
    	.description {
    		text-overflow: ellipsis;
    		overflow:hidden;
    		white-space: nowrap;
    		width:150px;
    		display: inline-block;
    	}
    </style>
  </head>
  
  <body>
  
	<div class="container-fluid">
		<div>
			<form class="form-inline search-container" action="${pageContext.request.contextPath }/console/actionrecord/list.do">
				<div class="form-group">
					<label class="radio-inline">
						<input type="radio" value="2" name="position">前台
					</label>
					<label class="radio-inline">
				  		<input type=radio value="1" name="position" checked="checked">后台
					</label>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="开始时间" name="start" value="${requestScope.data.start }">
				</div>
				<div class="form-group">
					<input type="text" class="form-control" placeholder="结束时间" name="end" value="${requestScope.data.end }">
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
					<th>操作点</th>
					<th>账号</th>
					<th>时间</th>
					<th>类型</th>
					<th>详细</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.data.actionRecordList}" var="record">
					<tr>
						<td><input type="checkbox" class="record-checkbox" value="${record.id}"></td>
						<td>${record.position==1?'后台操作':record.position==2?'前台操作':'' }</td>
						<td>${record.account }</td>
						<td><fmt:formatDate value="${record.time }"  type="both"/></td>
						<td>${record.type }</td>
						<td style="width: 160px;"><span class="description" title="${record.description }">${record.description }</span></td>
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
	
	<script src="${pageContext.request.contextPath }/assets/js/pager.js"></script>
	<script src="${pageContext.request.contextPath }/assets/js/bootstrap.js"></script>
	<script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.core.js"></script>
	<script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.widget.js"></script>
	<script src="${pageContext.request.contextPath }/assets/plugins/jquery-ui/jquery.ui.datepicker.js"></script>
	<script type="text/javascript">
		$(function(){
			$("input[name='start']").datepicker({
				altField: "input[name='start']",
				altFormat: "yy-mm-dd"
			});
			
			$("input[name='end']").datepicker({
				altField: "input[name='end']",
				altFormat: "yy-mm-dd"
			});
			
			(function(){
				var position = "${requestScope.data.position}";
				if(null != position && position.length > 0){
					$("input[name='position'][value='" + position + "']").attr("checked",true);
				}
			})();
		});
	</script>
  </body>
</html>
