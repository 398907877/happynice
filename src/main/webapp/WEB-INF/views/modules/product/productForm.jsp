<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/product/list">产品列表</a></li>
		<li class="active"><a href="${ctx}/product/product/form?id=${product.id}">产品信息<shiro:hasPermission name="product:producty:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:producty:view">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/product/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">产品名称:</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品编号:</label>
			<div class="controls">
				<form:input path="productNo" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品分类:</label>
			<div class="controls">
				<form:select path="productTypeId" class="required input-xlarge">
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('product_type_id')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">零售价:</label>
			<div class="controls">
				<form:input path="productPrice" htmlEscape="false" maxlength="50" class="required number"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">产品图片:</label>
			<div class="controls">
				<form:input path="picPath" htmlEscape="false"/>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">本地上传:</label>
			<div class="controls">
				<form:hidden id="picPath" path="picPath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="picPath" type="images" uploadPath="/test/test" selectMultiple="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容:</label>
			<div class="controls">
				<form:textarea id="productContent" htmlEscape="true" path="productContent" rows="4" maxlength="100" cols="50" class="input-xxlarge"/>
				<sys:ckeditor replace="productContent" uploadPath="/test/test" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="product:producty:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>