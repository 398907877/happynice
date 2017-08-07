<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
	</ul><br/>
	<div class="form-actions" style="padding-left:70px">
			<input  class="btn" type="button" value="个人结算账户开户" />
			<input  class="btn" type="button" value="个人电子银行服务" />
			<input  class="btn" type="button" value="短信服务申请" />
			<input  class="btn" type="button" value="网上支付通" />
			<input  class="btn" type="button" value="缘卡外汇卡申请书" />
			<input  class="btn" type="button" value="收汇申请" />
			<input  class="btn" type="button" value="个人结汇" />
			<input  class="btn" type="button" value="涉外收入申请单" />
			<input  class="btn" type="button" value="个人购汇业务" />
			<input  class="btn" type="button" value="定期账户开户" />
			<input  class="btn" type="button" value="个人跨行汇款" />
			<input  class="btn" type="button" value="大额跨行转账服务" />
			<input  class="btn" type="button" value="挂失申请书" />
		</div>
	<form:form id="inputForm" modelAttribute="fclWkJh"  method="post" class="form-horizontal" style="width:700px">
		
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="money"  class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				<label class="control-label" style="float:none;width:80px">性别:</label>
				<form:input path="money"  class="required" style="width:50px;"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			
			
		</div>
		<div class="control-group">
			<label class="control-label">拼音:</label>
			<div class="controls">
                <form:input path="money"  class="required"/>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">出生日期:</label>
			<div class="controls">
                <form:input path="money"  class="required"/>
                <label class="control-label" style="float:none;width:95px">民族:</label>
                <form:input path="money"  class="required" style="width:50px;"/>
			</div>
			
		</div>
		<div class="control-group">
			<label class="control-label">证件号码:</label>
			<div class="controls">
				<form:input path="money" class="required"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">有效期限:</label>
			<div class="controls">
				<form:input path="money" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发证机关:</label>
			<div class="controls">
				<form:input path="money" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件地址:</label>
			<div class="controls">
				<form:input path="money"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号码:</label>
			<div class="controls">
				<form:input path="money"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">固定电话:</label>
			<div class="controls">
				<form:input path="money"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现居住地:</label>
			<div class="controls">
				<form:input path="money"  class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="money" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮件:</label>
			<div class="controls">
				<form:input path="money"  class="required"/>
			</div>
		</div>
		<div class="form-actions" style="padding-left:70px">
			<input  class="btn" type="button" value="清除内容" />
			<input  class="btn" type="button" value="开户人资料" />
			<input  class="btn" type="button" value="代理人资料" />
			<input  class="btn" type="button" value="联网核查" />
			<input  class="btn" type="button" value="打印凭单" />
			<input  class="btn" type="button" value="业务办理" />
		</div>
	</form:form>

	<form:form id="inputForm1" modelAttribute="fclWkJh"  method="post" class="form-horizontal" style="width:300px;margin-left:800px;margin-top:-650px">
		<input type='file' id='iptfileupload' onchange='show()' value='' />
 <img src='' alt='' id='img' />
<input type='file' id='iptfileupload1' onchange='show1()' value='' />
 <img src='' alt='' id='img1' />

 <script>
 function getPath(obj,fileQuery,transImg) {

	  var imgSrc = '', imgArr = [], strSrc = '' ;

	  if(window.navigator.userAgent.indexOf("MSIE")>=1){ // IE浏览器判断
	  if(obj.select){
	   obj.select();
	   var path=document.selection.createRange().text;
	   alert(path) ;
	   obj.removeAttribute("src");
	   imgSrc = fileQuery.value ;
	   imgArr = imgSrc.split('.') ;
	   strSrc = imgArr[imgArr.length - 1].toLowerCase() ;
	   if(strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0){
	   obj.setAttribute("src",transImg);
	   obj.style.filter=
	    "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='"+path+"', sizingMethod='scale');"; // IE通过滤镜的方式实现图片显示
	   }else{
	   //try{
	   throw new Error('File type Error! please image file upload..'); 
	   //}catch(e){
	   // alert('name: ' + e.name + 'message: ' + e.message) ;
	   //}
	   }
	  }else{
	   // alert(fileQuery.value) ;
	   imgSrc = fileQuery.value ;
	   imgArr = imgSrc.split('.') ;
	   strSrc = imgArr[imgArr.length - 1].toLowerCase() ;
	   if(strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0){
	   obj.src = fileQuery.value ;
	   }else{
	   //try{
	   throw new Error('File type Error! please image file upload..') ;
	   //}catch(e){
	   // alert('name: ' + e.name + 'message: ' + e.message) ;
	   //}
	   }

	  }

	  } else{
	  var file =fileQuery.files[0];
	  var reader = new FileReader();
	  reader.onload = function(e){

	   imgSrc = fileQuery.value ;
	   imgArr = imgSrc.split('.') ;
	   strSrc = imgArr[imgArr.length - 1].toLowerCase() ;
	   if(strSrc.localeCompare('jpg') === 0 || strSrc.localeCompare('jpeg') === 0 || strSrc.localeCompare('gif') === 0 || strSrc.localeCompare('png') === 0){
	   obj.setAttribute("src", e.target.result) ;
	   }else{
	   //try{
	   throw new Error('File type Error! please image file upload..') ;
	   //}catch(e){
	   // alert('name: ' + e.name + 'message: ' + e.message) ;
	   //}
	   }

	   // alert(e.target.result); 
	  }
	  reader.readAsDataURL(file);
	  }
	 }

	 function show(){
	  //以下即为完整客户端路径
	  var file_img=document.getElementById("img"),
	  iptfileupload = document.getElementById('iptfileupload') ;
	  getPath(file_img,iptfileupload,file_img) ;
	 }
	 function show1(){
		  //以下即为完整客户端路径
		  var file_img=document.getElementById("img1"),
		  iptfileupload = document.getElementById('iptfileupload1') ;
		  getPath(file_img,iptfileupload,file_img) ;
		 }

 </script>
	</form:form>
</body>
</html>