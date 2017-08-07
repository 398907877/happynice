<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#productForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
			$("#hideProduct").click(function(){
				var ids= '';
				$('input:checkbox[name=productId]:checked').each(function(i){
					if(0==i){
						ids = $(this).val();
					}else{
						ids += (","+$(this).val());
					}
				});
				if ('' == ids) {
					alert('请选择产品!');
					return;
				}
				$("#productForm").attr("action","${ctx}/product/product/updateProductState?productState=0&ids="+ids);
				$("#productForm").submit();
			});
			$("#showProduct").click(function(){
				var ids= '';
				$('input:checkbox[name=productId]:checked').each(function(i){
					if(0==i){
						ids = $(this).val();
					}else{
						ids += (","+$(this).val());
					}
				});
				if ('' == ids) {
					alert('请选择产品!');
					return;
				}
				$("#productForm").attr("action","${ctx}/product/product/updateProductState?productState=1&ids="+ids);
				$("#productForm").submit();
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/product/list">产品信息列表</a></li>
		<shiro:hasPermission name="product:producty:edit"><li><a href="${ctx}/product/product/form">产品信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="productForm" modelAttribute="product" action="${ctx}/product/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>分类：</label>
		<form:select id="productTypeId" path="productTypeId" class="input-medium">
			<form:option value="" label="请选择"/>
			<form:options items="${fns:getDictList('product_type_id')}" itemValue="value" itemLabel="label" htmlEscape="false"/>
		</form:select>
		<label>产品名称 ：</label><form:input path="productName" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<shiro:hasPermission name="product:producty:edit">
					<input id="hideProduct" class="btn btn-primary" type="button" value="屏蔽产品"/>
					<input id="showProduct" class="btn btn-primary" type="button" value="解除屏蔽"/>
				</shiro:hasPermission>
			</li>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			<th>#</th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>分类</th>
			<th>发布时间</th>
			<th>屏蔽</th>
			<th>图片地址</th>
				<shiro:hasPermission name="product:producty:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<tr>
				<td><input type="checkbox" name="productId" id="productId" value="${product.id}"></td>
				<td>${product.productNo}</td>
				<td>${product.productName}</td>
				<td>${fns:getDictLabel(product.productTypeId, 'product_type_id', '')}</td>
				<td><fmt:formatDate value="${product.pulishDate}" type="both"/></td>
				<td>${product.productState eq '1'?'不屏蔽':'屏蔽'}</td>
				<td>${product.picPath}</td>
				<shiro:hasPermission name="product:producty:edit">
				<td>
    				<a href="${ctx}/product/product/form?id=${product.id}">修改</a>
					<a href="${ctx}/product/product/delete?id=${product.id}" onclick="return confirmx('确认要删除该产品吗？', this.href)">删除</a>
				</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>