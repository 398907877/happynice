<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品类别</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#productTypeForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/productType/list">产品类别列表</a></li>
		<shiro:hasPermission name="product:productyType:edit"><li><a href="${ctx}/product/productType/form">产品类别添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="productTypeForm" modelAttribute="productType" action="${ctx}/product/productType/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>产品类别 ：</label><form:input path="typeName" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>分类名</th>
				<shiro:hasPermission name="product:productyType:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="productType">
			<tr>
				<td>${productType.typeName}</td>
				<shiro:hasPermission name="product:productyType:edit">
				<td>
    				<a href="${ctx}/product/productType/form?id=${productType.id}">修改</a>
					<a href="${ctx}/product/productType/delete?id=${productType.id}" onclick="return confirmx('确认要删除该类别吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>