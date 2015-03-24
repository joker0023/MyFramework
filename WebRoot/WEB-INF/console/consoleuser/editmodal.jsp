<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="modal-dialog modal-sm">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span>&times;</span>
			</button>
			<h4 class="modal-title">编辑</h4>
		</div>
		<div class="modal-body">
			<c:if test="${null == requestScope.data.consoleUser}">
				没有此用户信息!!
			</c:if>
			<c:if test="${null != requestScope.data.consoleUser}">
				<form action="" onsubmit="return false;" class="form-horizontal">
					<div class="form-group">
						<label class="col-xs-4 control-label">账号</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" placeholder="账号" disabled value="${requestScope.data.consoleUser.account }">
							<input type="hidden" value="${requestScope.data.consoleUser.id }" id="edit_userId">
						</div>
					</div>
					<div class="form-group">
						<label for="edit_password" class="col-xs-4 control-label">密码</label>
						<div class="col-xs-7">
							<input type="password" class="form-control" id="edit_password" placeholder="密码">
						</div>
					</div>
					<div class="form-group">
						<label for="edit_confirmPassword" class="col-xs-4 control-label">确认密码</label>
						<div class="col-xs-7">
							<input type="password" class="form-control" id="edit_confirmPassword" placeholder="确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="edit_role" class="col-xs-4 control-label">角色</label>
						<div class="col-xs-4">
							<select class="form-control" id="edit_role" data-val="${requestScope.data.consoleUser.role }">
								<c:forEach items="${requestScope.data.roleList}" var="role">
									<option value="${role.id}">${role.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="edit_status" class="col-xs-4 control-label">状态</label>
						<div class="col-xs-4">
							<select class="form-control" id="edit_state" data-val="${requestScope.data.consoleUser.state }">
								<c:forEach items="${requestScope.data.consoleUserStateMap}" var="stateMap">
									<option value="${stateMap.key }">${stateMap.value }</option>
								</c:forEach>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-xs-4 control-label">创建时间</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" disabled 
							value='<fmt:formatDate value="${requestScope.data.consoleUser.createTime }" type="both"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">最后登录时间</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" disabled 
							value='<fmt:formatDate value="${requestScope.data.consoleUser.lastLoginTime }" type="both"/>'>
						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-4 control-label">最后登录IP</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" disabled value="${requestScope.data.consoleUser.lastLoginIp }">
						</div>
					</div>
				</form>
			</c:if>
		</div>
		<div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        	<c:if test="${null != requestScope.data.consoleUser}">
        		<button type="button" class="btn btn-primary" id="updateBtn">保存</button>
        	</c:if>
      	</div>
	</div>
</div>
