<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>参数配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			var price = $('#price').val();
			var ratio = $('#ratio').val();
			if (price == ''|| ratio == '') {
				$('#ratioedPrice').text(0);
				return ;
			}
			var ratios = ratio.split(":");
			var a = ratios[0];
			var b = ratios[1];
			var result = 1.00*price*b/a;
			$('#ratioedPrice').text(result);
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
			
			$("#price").bind('input porpertychange',function(){
				var price = $('#price').val();
				var ratio = $('#ratio').val();
				if (price == ''|| ratio == '') {
					$('#ratioedPrice').text(0);
					return ;
				}
				var ratios = ratio.split(":");
				var a = ratios[0];
				var b = ratios[1];
				var result = 1.00*price*b/a;
				$('#ratioedPrice').text(result);
			});
			$("#ratio").bind('input porpertychange',function(){
				var price = $('#price').val();
				var ratio = $('#ratio').val();
				if (price == ''|| ratio == '') {
					$('#ratioedPrice').text(0);
					return ;
				}
				var ratios = ratio.split(":");
				var a = ratios[0];
				var b = ratios[1];
				var result = 1.00*price*b/a;
				$('#ratioedPrice').text(result);
			});
			
			$("#resetAllSell").click(function(){
				$.ajax({
					type: "post",
		             url: "${ctx}/config/equityConfig/resetAllSell",
		             data: {},
		           //  dataType: "json",
		             success: function(data){
		            	 $("#messageBox").text(data);
		             }
				});
			});
			$("#rateSplitId").click(function(){
				alert("未完成");
			});
			
			
			
			jQuery.validator.addMethod("validateRodit", function(value, element) {
				 var patten = /^[1-9]\d{0,}[:][1-9]\d{0,}$/;
				 if(patten.test(value)) {
					 return true;
				 } else {
					 $('#ratioedPrice').text(0);
					 return false;
				 }
			}, jQuery.validator.format("比值格式不正确"));
		});
	</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li>欢乐豆参数设置</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="equityConfig" action="${ctx}/config/equityConfig/save" method="post" class="form-horizontal">
		<form:hidden path="id" value="${equityConfig.id}"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">欢乐豆价格:</label>
			<div class="controls">
				<form:input path="price" id="price" htmlEscape="false" maxlength="50" value="${equityConfig.price}" class="required number input-small"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易手续费:</label>
			<div class="controls">
				<form:input path="charge" htmlEscape="false" maxlength="50" value="${equityConfig.charge}" class="required number input-small"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易分账:</label>
			<div class="controls">
				<form:input path="splitBonus" htmlEscape="false" maxlength="50" value="${equityConfig.splitBonus}" class="required number input-mini"/>%进入奖金分，
				<form:input path="splitReg" htmlEscape="false" maxlength="50" value="${equityConfig.splitReg}" class="required number input-mini"/>%进入注册分，其余进入游戏分，奖金分拿投资额的
				<form:input path="splitTimes" htmlEscape="false" maxlength="50" value="${equityConfig.splitTimes}" class="required number input-mini"/>倍封顶
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">拆分欢乐豆（比率）:</label>
			<div class="controls">
				<form:input id="ratio" path="ratio" htmlEscape="false" maxlength="50" value="${equityConfig.ratio}" class="required validateRodit input-small"/>【拆分后的价格：<span id="ratioedPrice" style="color: red;">0</span>，如果不拆分请填写1:1】
				<input id="rateSplitId" class="btn btn-primary" type="button" value="立刻按此设置进行拆分"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">涨价设定:</label>
			<div class="controls">
				<form:input path="riseLimit" htmlEscape="false" maxlength="50" value="${equityConfig.riseLimit}" class="required number input-mini"/>涨价
				<form:input path="riseRate" htmlEscape="false" maxlength="50" value="${equityConfig.riseRate}" class="required number input-mini"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">欢乐豆开关:</label>
			<div class="controls">
				<form:radiobuttons path="openState" items="${fns:getDictList('open_state')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"> (关闭将无法进行交易)</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<input id="resetAllSell" class="btn btn-primary" type="button" value="撤销所有会员挂卖"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="config:equityConfig:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnReset" class="btn" type="reset" value="还 原"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>