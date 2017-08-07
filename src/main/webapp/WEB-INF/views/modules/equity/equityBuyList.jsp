<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权买卖管理</title>
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
		
		<shiro:hasPermission name="equity:equityBuy:edit"><li ><a href="${ctx}/equity/equityBuy/form">欢乐豆买入</a></li></shiro:hasPermission>
		<li   class="active"><a href="${ctx}/equity/equityBuy/">欢乐豆买入记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="equityBuy" action="${ctx}/equity/equityBuy/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>购买登录名：</label>
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
				<th>购买登录名</th>
				<th>购买姓名</th>
				<th>购买数量</th>
				<th>当前金额</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="equityBuy">
			<tr>
				<td>${equityBuy.user.loginName}</td>
				<td>${equityBuy.user.name}</td>
				<td>${equityBuy.buyNum}</td>
				<td>${equityBuy.buyMoney}</td>
				<td>
					<fmt:formatDate value="${equityBuy.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<!-- 
				<shiro:hasPermission name="etd:equityTrading:edit"><td>
    				<a href="${ctx}/etd/equityTrading/form?id=${equityTrading.id}">修改</a>
					<a href="${ctx}/etd/equityTrading/delete?id=${equityTrading.id}" onclick="return confirmx('确认要删除该股权买卖吗？', this.href)">删除</a>
				</td></shiro:hasPermission> -->
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>