<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>挖矿分转换管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var wkf = "";	
	$(document).ready(function() {
			//$("#name").focus();
			
			$.ajax({ 
				type: "post", 
				url : "${ctx}/financial/fclWkJh/getUserInfo", 
				dataType:'json',
				data: {}, 
				success: function(json){
					wkf = Math.round(json.wkf*100)/100;
					$("#tmoney").html("现有挖矿分"+wkf);
					if(wkf == 0){
						$("#messageBox").css("display","block");
						$("#messageBox").text("现有挖矿分为零，不能转换！");
						$("#money").attr("readOnly",true);
					}
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
			jQuery.validator.addMethod("tenFold", function(value, element) {
				 var patten = /^[0-9]*[0-9]$/i; 
				 return (patten.test(value) && value>0  && value%10==0);
			}, jQuery.validator.format("请输入10的倍数,且大于0的数!"));
		});
		
		function checkMoney(){
			var money = $("#money").val();
			if(parseInt(money)> parseInt(wkf)){
				$("#messageBox").css("display","block");
				$("#messageBox").text("转换分数超过现有挖矿分，请重新填写！");
				$("#money").val("");
				return false;
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<li class="active"><a href="${ctx}/financial/fclWkJh/form?id=${fclWkJh.id}">挖矿分转换<shiro:hasPermission name="financial:fclWkJh:edit"></shiro:hasPermission><shiro:lacksPermission name="financial:fclWkJh:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/financial/fclWkJh/">挖矿分转换记录</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fclWkJh" action="${ctx}/financial/fclWkJh/save" method="post" class="form-horizontal"><%--
		<form:hidden path="email" htmlEscape="false" maxlength="255" class="input-xlarge"/>
		<sys:ckfinder input="email" type="files" uploadPath="/mytask" selectMultiple="false"/> --%>
		<sys:message content="${message}"/>
		<div id="messageBox" class="alert alert-success hide" style="display: none;"><button data-dismiss="alert" class="close">×</button></div>
		<div class="control-group">
			<label class="control-label">转换类型：</label>
			<div class="controls" style="width:220px">
				<select id="changetype" name="changetype" class="required input-medium"  style="width:210px" >
					<c:forEach items="${fns:getDictList('fcl_wk_change')}" var="dict">
						<option value="${dict.value}">${dict.label}</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>	
		<div class="control-group">
			<label class="control-label">转换分数:</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" maxlength="100" class="input-xlarge required money number tenFold" onchange="checkMoney()" id="money"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="tmoney" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea name="remarks"  rows="3" maxlength="200" class="input-xlarge"/></textarea>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="financial:fclWkJh:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
		
</body>
</html>