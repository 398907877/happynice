<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>量碰表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/lpList");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">

	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/lpList">量碰表</a></li>

	</ul>

	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
			<th rowspan="2" align="center" valign="middle">层数</th>
			<th colspan="2" align="center">左边</th>
			<th colspan="2" align="center">右边</th>
			</tr>
		<tr>
			<th align="center">总单数</th>
			<th align="center">已碰</th>
			<th align="center">总单数</th>
			<th align="center">已碰</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="lp">
			<tr>
				<td align="center">${lp.level}</td>
				<td align="center">${lp.ltotal}</td>
				<td align="center">${lp.lp}</td>
				<td align="center">${lp.rtotal}</td>
				<td align="center">${lp.rp}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>