<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>股权买卖管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			
			$.ajax({ 
					type: "post", 
					url : "${ctx}/financial/fclTransfer/getUserInfo", 
					dataType:'json',
					data: {}, 
					success: function(json){
						user = json;
						console.log(user);
						var happyfood = json.happyfood;
						if(!user.admin && typeof(happyfood) =='undefined' || happyfood <= 0){
							$("#messageBox").css("display","block");
							$("#messageBox").text("用户欢乐豆为零，无法卖出");
							$("#tradingNum").attr("readOnly",true);
						}
						else {
							$("#messageBox").css("display","none");
						}
						if (typeof(happyfood) =='undefined') {
							happyfood = 0;
						}
						$("#happyfood").html("现有欢乐豆"+happyfood);
						$.ajax({ 
							type: "post", 
							url : "${ctx}/equity/equitySell/getMinTradingMoney", 
							dataType:'json',
							data: {}, 
							success: function(minTradingMoney){
								if (null == minTradingMoney || '' == minTradingMoney) {
									$("#minTradingMoney").html("当前还没有欢乐豆卖出");
								} else {
									$("#minTradingMoney").html("当前欢乐豆最低价格"+minTradingMoney);
								}
							}
						});
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
		
		function checkTradingNum(){
			var tradingNum = $("#tradingNum").val();
			var   r   =   /^[1-9]*[0-9][0-9]*$/;
			if(!r.test(tradingNum)){
				$("#messageBox").css("display","block");
				$("#messageBox").text("请输入一个正整数");
				$("#tradingNum").val("");
				return false;
			}
			
			var happyfood = user.happyfood;
			if(!user.admin && parseInt(tradingNum) > parseInt(happyfood)){
				$("#messageBox").css("display","block");
				$("#messageBox").text("卖出数量超过现有数量，请重新填写");
				$("#tradingNum").val("");
				return false;
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		
		<li class="active"><a href="${ctx}/equity/equitySell/form?id=${equityTrading.id}">欢乐豆卖出<shiro:hasPermission name="equity:equitySell:edit"></shiro:hasPermission><shiro:lacksPermission name="equity:equitySell:edit">查看</shiro:lacksPermission></a></li>
	<li><a href="${ctx}/equity/equitySell/list">欢乐豆卖出记录</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="equitySell" action="${ctx}/equity/equitySell/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div id="messageBox" class="alert alert-success hide" style="display: none;"><button data-dismiss="alert" class="close">×</button></div>
		<div class="control-group">
			<label class="control-label">交易数量：</label>
			<div class="controls">
				<form:input path="tradingNum" htmlEscape="false" maxlength="100" class="input-xlarge required" id="tradingNum" onchange="checkTradingNum()"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="happyfood" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易金额：</label>
			<div class="controls">
				<form:input path="tradingMoney" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<span id="minTradingMoney" style="color:red"></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls" >
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " style="width:270px"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="equity:equitySell:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>