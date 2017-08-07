<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>积分转账管理</title> 
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
						var jhf = Math.round(json.jhf*100)/100;
						$("#tmoney").html("现有激活分"+jhf);
						if(!user.admin && jhf <= 0){
							$("#messageBox").css("display","block");
							$("#messageBox").text("现有激活分为零，不能转账！");
							$("#transMoney").attr("readOnly",true);
						}
					}
				}); 
			
			$("#inputForm").validate({
				rules: {
					intonumberId: {remote: "${ctx}/financial/fclTransfer/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					intonumberId: {remote: "该用户不存在"},
				},
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
			jQuery.validator.addMethod("hundredFold", function(value, element) {
				 var patten = /^[0-9]*[0-9]$/i; 
				 return (patten.test(value) && value>0  && value%100==0);
			}, jQuery.validator.format("请输入100的倍数,且大于0的数!"));
		});
		
		function changeType(){
			var transType = $('#transType option:selected').val();
			var transMoney = $("transMoney").val();
			$("#transMoney").val("");
			if(transType == "1"){
				var jhf = user.jhf;
				$("#tmoney").html("现有激活分"+jhf);
				$("#transMoney").removeClass("money");
				$("#transMoney").addClass("transMoney");
			}else {
				var qzf = user.qzf;
				$("#tmoney").html("现有权证分"+qzf);
				$("#transMoney").removeClass("transMoney");
				$("#transMoney").addClass("money");
			}
		}
		function checkMoney(){
			var transType = $('#transType option:selected').val();
			
			var transMoney = $("#transMoney").val();
			if(transType == "1"){
				var jhf = user.jhf;
				if(!user.admin && parseInt(jhf)<parseInt(transMoney)){
					$("#messageBox").css("display","block");
					$("#messageBox").text("用户激活分不足，无法转账！");
					$("#transMoney").val("");
				}
			}else {
				var qzf = user.qzf;
				if(!user.admin && parseInt(qzf) < parseInt(transMoney)){
					$("#messageBox").css("display","block");
					$("#messageBox").text("用户权证分不足，无法转账！");
					$("#transMoney").val("");
				}
			}
			return false;
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<li class="active"><a href="${ctx}/financial/fclTransfer/form?id=${fclJhTransfer.id}">积分转账<shiro:hasPermission name="financial:fclTransfer:edit"></shiro:hasPermission><shiro:lacksPermission name="financial:fclTransfer:edit">查看</shiro:lacksPermission></a></li>
	<li><a href="${ctx}/financial/fclTransfer/">积分转账记录</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="fclTransfer" action="${ctx}/financial/fclTransfer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div id="messageBox" class="alert alert-success hide" style="display: none;"><button data-dismiss="alert" class="close">×</button></div>
		<div class="control-group">
			<label class="control-label">转账类型：</label>
			<div class="controls" style="width:230px">
				<select id="transType" name="transType" class="required input-medium" onchange="changeType()" style="width:220px" >
					<c:forEach items="${fns:getDictList('fcl_transfer')}" var="dict">
						<option value="${dict.value}">${dict.label}</option>
					</c:forEach>
				</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转入编号:</label>
			<div class="controls">
				<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
				<form:input path="intonumberId" htmlEscape="false" maxlength="50" class="required userName"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">转账金额：</label>
			<div class="controls">
				<form:input path="transMoney" htmlEscape="false" maxlength="64" class="input-xlarge required transMoney hundredFold" style="width:206px" id="transMoney" onchange="checkMoney()"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="tmoney" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="2" maxlength="255" class="input-xxlarge " style="width:206px"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="financial:fclTransfer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>