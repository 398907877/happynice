<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>挖矿分转换管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<shiro:hasPermission name="financial:fclWkJh:edit"><li  ><a href="${ctx}/financial/fclWkJh/form">挖矿分转换</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/financial/fclWkJh/">挖矿分转换记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fclWkJh" action="${ctx}/financial/fclWkJh/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转出姓名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>转入姓名：</label>
				<form:input path="intoName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>转换类型：</label>
				<form:select path="changetype" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('fcl_wk_change')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>转换类型</th>
				<th>转出登录名</th>
				<th>转出姓名</th>
				<th>转入登录名</th>
				<th>转出姓名</th>
				<th>转换分数</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fclWkJh">
			<tr>
				<td>
					${fns:getDictLabel(fclWkJh.changetype, 'fcl_wk_change', '')}
				</td>
				<td>${fclWkJh.user.loginName}</td>
				<td>${fclWkJh.user.name}</td>
				<td>${fclWkJh.user.loginName}</td>
				<td>${fclWkJh.user.name}</td>
				<td>${fclWkJh.money}</td>
				<td>
					<fmt:formatDate value="${fclWkJh.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>