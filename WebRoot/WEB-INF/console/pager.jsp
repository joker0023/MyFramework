<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<form id="page-form" action="${requestScope.pager.URL}" method="post">
		<input type="hidden" id="pager_currentPage" name="pager.currentPage" value="${requestScope.pager.currentPage }">
		<input type="hidden" name="pager.pageSize" value="${requestScope.pager.pageSize }">
		<input type="hidden" id="pager_params" value="${pager.params}" >
		<input type="hidden" id="pager_ajaxTag" value="${pager.ajaxTag}" >
	</form>
	<ul class="pagination">
		<li class="${requestScope.pager.hasPrevious==true?'':'disabled'}">
			<a href="javascript:void(0);">&laquo;</a>
		</li>
		<c:forEach items="${requestScope.pager.pageNums }" var="num">
			<li class="${requestScope.pager.currentPage==num?'active':'' }">
				<a class="pageNum-btn" href="javascript:void(0);">${num }</a>
			</li>
		</c:forEach>
		<li class="${requestScope.pager.hasNext==true?'':'disabled'}">
			<a href="javascript:void(0);">&raquo;</a>
		</li>
	</ul>
</div>
