<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权买卖管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			var date = new Date();
			var year = date.getFullYear();
			var month = date.getMonth()+1;
			var day = date.getDate();
			var startDate = year + "-" + month + "-" + day + " 00:00:00";
			var endDate = year + "-" + month + "-" + day + " 59:59:59";
			$.ajax({ //一个Ajax过程 
				type: "post", //以post方式与后台沟通 
				url : "${ctx}/etd/equityTrading/getMoney", //与此php页面沟通 
				dataType:'json',//从php返回的值以 JSON方式 解释 
				data: {"startDate":startDate,"endDate":endDate}, //发给php的数据有两项，分别是上面传来的u和p 
				success: function(json){//如果调用php成功 
					$("#tradingMoney").val(json.tradingMoney);
				}
				}); 
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
		<li><a href="${ctx}/etd/equityTrading/equityBuyList">股权买卖列表</a></li>
		<li class="active"><a href="${ctx}/etd/equityTrading/equityBuyform?id=${equityTrading.id}">股权买卖<shiro:hasPermission name="etd:equityTrading:edit">${not empty equityTrading.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="etd:equityTrading:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="equityTrading" action="${ctx}/etd/equityTrading/equityBuySave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">购买数量：</label>
			<div class="controls">
				<form:input path="tradingNum" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">当前金额：</label>
			<div class="controls">
				<form:input path="tradingMoney" htmlEscape="false" maxlength="100" class="input-xlarge required" id="tradingMoney" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="etd:equityTrading:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>