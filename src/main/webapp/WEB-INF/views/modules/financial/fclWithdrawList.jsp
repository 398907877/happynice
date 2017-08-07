<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>挖矿分提现管理</title>
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
		
		<shiro:hasPermission name="financial:fclWithdraw:edit"><li ><a href="${ctx}/financial/fclWithdraw/form">挖矿分提现</a></li></shiro:hasPermission>
		<li  class="active"><a href="${ctx}/financial/fclWithdraw/">挖矿分提现记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="fclWithdraw" action="${ctx}/financial/fclWithdraw/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>转出姓名：</label>
				<form:input path="loginName" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>转出登录名</th>
				<th>转出姓名</th>
				<th>提现金额</th>
				<th>手续费</th>
				<th>实发金额</th>
				<th>创建时间</th>
			</tr>
			
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="fclWithdraw">
			<tr>
				<td>${fclWithdraw.user.loginName}</td>
				<td>${fclWithdraw.user.name}</td>
				<td>${fclWithdraw.money}</td>
				<td>${fclWithdraw.money*0.1}</td>
				<td>${fclWithdraw.money*0.9}</td>
				<td>
					<fmt:formatDate value="${fclWithdraw.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>