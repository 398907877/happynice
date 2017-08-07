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
		
		<shiro:hasPermission name="equity:equitySell:edit"><li ><a href="${ctx}/equity/equitySell/form">欢乐豆卖出</a></li></shiro:hasPermission>
		<li class="active"><a href="${ctx}/equity/equitySell/">欢乐豆卖出记录</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="equitySell" action="${ctx}/equity/equitySell/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>卖出登录名：</label>
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
				<th>登录名</th>
				<th>姓名</th>
				<th>欢乐豆数量</th>
				<th>总金额</th>
				<th>单价</th>
				<th>已卖出数量</th>
				<th>手续费</th>
				<th>游戏分</th>
				<th>挖矿分</th>
				
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="equitySell">
			<tr>
				<td>${equitySell.user.loginName}</td>
				<td>${equitySell.user.name}</td>
				<td>${equitySell.tradingNum}</td>
				<td>${equitySell.totalMoney}</td>
				<td>${equitySell.tradingMoney}</td>
				<td>${equitySell.buyNum}</td>
				<td>${equitySell.totalMoney*0.1}</td>
				<td>${equitySell.totalMoney*0.05}</td>
				<td>${equitySell.totalMoney*0.85}</td>
				
				<td>
					<fmt:formatDate value="${equitySell.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="equity:equitySell:edit"><td>
					<a href="${ctx}/equity/equitySell/repealSell?id=${equitySell.id}" onclick="return confirmx('确认要撤销该欢乐豆吗？', this.href)">撤销</a>
				</td></shiro:hasPermission> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>