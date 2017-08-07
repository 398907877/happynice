<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/regRecords");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li <c:if test="${type == '0'}">class="active"</c:if>><a href="${ctx}/sys/user/userTreeView?type=0">网络结构图</a></li>
    	<li <c:if test="${type == '1'}">class="active"</c:if>><a href="${ctx}/sys/user/userTreeView?type=1">推荐关系图</a></li>
		<li class="active"><a href="${ctx}/sys/user/regRecords">用户列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/regRecords" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li class="clearfix"></li>
			<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th class="sort-column name">姓名</th>
				<th>激活分</th>
				<th>权证分</th>
				<th>购物分</th>
				<th>挖矿分</th>
				<th>游戏分</th>
				<th>滋养液</th>
				<th>实际欢乐豆</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="user">
			<tr>
				<td>${user.name}</td>
				<td>${user.jhf}</td>
				<td>${user.qzf}</td>
				<td>${user.gwf}</td>
				<td>${user.wkf}</td>
				<td>${user.yxf}</td>
				<td>${user.zyy}</td>
				<td>${user.happyfood}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>