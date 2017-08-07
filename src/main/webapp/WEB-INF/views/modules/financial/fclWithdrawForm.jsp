<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>挖矿分提现管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	var wkf = "";	
	$(document).ready(function() {
			//$("#name").focus();
			
				$.ajax({ 
					type: "post", 
					url : "${ctx}/financial/fclWithdraw/getUserInfo", 
					dataType:'json',
					data: {}, 
					success: function(json){
						wkf = Math.round(json.wkf*100)/100;
						$("#tmoney").html("现有挖矿分"+ wkf);
						if(wkf == 0){
							$("#messageBox").css("display","block");
							$("#messageBox").text("现有挖矿分为零，不能提现！");
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
		});
		
		function checkMoney(){
			var money = $("#money").val();
			if(parseInt(money)>parseInt(wkf)){
				$("#messageBox").css("display","block");
				$("#messageBox").text("提现金额超过现有挖矿分，请重新填写！");
				$("#money").val("");
				return false;
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<li class="active"><a href="${ctx}/financial/fclWithdraw/form?id=${fclWithdraw.id}">挖矿分提现<shiro:hasPermission name="financial:fclWithdraw:edit"></shiro:hasPermission><shiro:lacksPermission name="financial:fclWithdraw:edit">查看</shiro:lacksPermission></a></li>
	<li><a href="${ctx}/financial/fclWithdraw/">挖矿分提现记录</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fclWithdraw" action="${ctx}/financial/fclWithdraw/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div id="messageBox" class="alert alert-success hide" style="display: none;"><button data-dismiss="alert" class="close">×</button></div>		
		<div class="control-group">
			<label class="control-label">提现金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" maxlength="100" class="input-xlarge required transMoney number" onchange="checkMoney()" id="money"/>
				<span class="help-inline"><font color="red">*</font> 提现需扣除10%手续费</span>
				<span id="tmoney" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="255" class="input-xxlarge " style="width:270px"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="financial:fclWithdraw:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>