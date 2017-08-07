<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权买卖管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var user = "";
		$(document).ready(function() {
			//$("#name").focus();
			$.ajax({ 
					type: "post", 
					url : "${ctx}/financial/fclTransfer/getUserInfo", 
					dataType:'json',
					data: {}, 
					success: function(json){
						user = json;
						var happyfood = json.happyfood;
						$("#happyNum").html("现有欢乐豆"+happyfood);
						if(parseInt(happyNum)<1000){
							$("#happyNum").html("现有欢乐豆"+happyfood+",小于1000,无法提现");
							$("#happyfood").attr("readOnly",true);
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
		
		function checkHappyfood(){
			var happyfood = $("#happyfood").val();
			var happyNum = user.happyfood;
			if(parseInt(happyfood) > parseInt(happyNum)){
				$("#messageBox").css("display","block");
				$("#messageBox").text("提现数量大于现有欢乐豆数量，请重新填写");
				$("#happyfood").val("");
				return false;
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<li class="active"><a href="${ctx}/equity/equityGency/form?id=${equityTrading.id}">应急快售<shiro:lacksPermission name="equity:equityGency:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/equity/equityGency/">应急快售记录</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="equityGency" action="${ctx}/equity/equityGency/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<div id="messageBox" class="alert alert-success hide" style="display: none;"><button data-dismiss="alert" class="close">×</button></div>	
		<div class="control-group">
			<label class="control-label">欢乐豆数量：</label>
			<div class="controls">
				<form:input path="happyfood" htmlEscape="false" maxlength="100" class="input-xlarge required happyNum" onchange="checkHappyfood()" id="happyfood"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="happyNum" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="255" class="input-xxlarge " style="width:270px"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="equity:equityGency:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>