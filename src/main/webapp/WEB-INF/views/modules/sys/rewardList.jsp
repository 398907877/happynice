<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖励明细</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/rewardList");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">

	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">奖励明细</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="rewardDetail" action="${ctx}/sys/user/rewardList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">

		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th class="sort-column create_date">结算时间</th>
			<th>领导奖</th>
			<th>对碰奖</th>
			<th>推荐奖</th>
			<th>进入挖矿分</th>
			<th>进入游戏分</th>
			<th>进入购物分</th>
		<tbody>
		<c:forEach items="${page.list}" var="reward">
			<tr>
				<td><fmt:formatDate value="${reward.createDate}" type="both"/></td>
				<td>${reward.ld}</td>
				<td>${reward.dp}</td>
				<td>${reward.tj}</td>
				<td>${reward.wkf}</td>
				<td>${reward.yxf}</td>
				<td>${reward.gwf}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>