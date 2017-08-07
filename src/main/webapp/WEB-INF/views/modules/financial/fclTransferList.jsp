<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分转账管理</title>
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
		
		<shiro:hasPermission name="financial:fclTransfer:edit"><li ><a href="${ctx}/financial/fclTransfer/form">积分转账</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/financial/fclTransfer/">积分转账记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fclTransfer" action="${ctx}/financial/fclTransfer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转出姓名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>转入姓名：</label>
				<form:input path="username" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>转账类型：</label>
				<form:select path="transType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('fcl_transfer')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
				<th>转账类型</th>
				<th>转出登录名</th>
				<th>转出姓名</th>
				<th>转入登陆名</th>
				<th>转入姓名</th>
				<th>转账金额</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fclTransfer">
			<tr>
				<td>
					${fns:getDictLabel(fclTransfer.transType, 'fcl_transfer', '')}
				</td>
				<td>${fclTransfer.user.loginName}</td>
				<td>${fclTransfer.user.name}</td>
				<td>${fclTransfer.username}</td>
				<td>${fclTransfer.uname}</td>
				<td>${fclTransfer.transMoney}</td>
				<td>
					<fmt:formatDate value="${fclTransfer.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>