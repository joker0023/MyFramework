<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-dialog modal-sm">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span>&times;</span>
			</button>
			<h4 class="modal-title">详情</h4>
		</div>
		<div class="modal-body">
			<c:if test="${null == requestScope.data.webUser}">
				没有此用户信息!!
			</c:if>
			<c:if test="${null != requestScope.data.webUser}">
				<div style="border-bottom: 1px solid #ccc;padding: 10px;margin-bottom: 10px;">
					<div style="display: inline-block;">
						<img alt="avatar" src="${pageContext.request.contextPath }${requestScope.data.webUserInfo.avatar }" width="120">
					</div>
					<div style="display: inline-block;margin-left: 42px;">
						<h3>${requestScope.data.webUserInfo.nick }</h3>
						<h4>(${requestScope.data.webUser.account})</h4>
					</div>
				</div>
				<form action="" onsubmit="return false;" class="form-horizontal" style="max-width: 500px;">
					<div class="form-group">
						<label class="col-xs-4 control-label">状态:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserStateMap[requestScope.data.webUser.state]}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">等级:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserGradeMap[requestScope.data.webUserInfo.grade]}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">积分:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.integral}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">邮箱:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.email}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">年龄:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.age}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">性别:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.age==1?'男':requestScope.data.webUserInfo.age==2?'女':''}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">电话:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.phone}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">地址:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.address}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">真实姓名:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.realName}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">邮编:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.zip}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">职业:</label>
						<div class="col-xs-7">
							<input class="form-control" type="text" value="${requestScope.data.webUserInfo.profession}" readonly>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">创建时间:</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" disabled 
							value='<fmt:formatDate value="${requestScope.data.webUserInfo.createTime }" type="both"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">最后登录时间:</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" disabled 
							value='<fmt:formatDate value="${requestScope.data.webUserInfo.lastLoginTime }" type="both"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">最后登录IP:</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" disabled value="${requestScope.data.webUserInfo.lastLoginIp }">
						</div>
					</div>
				</form>
			</c:if>
		</div>
		<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	</div>
	</div>
</div>
