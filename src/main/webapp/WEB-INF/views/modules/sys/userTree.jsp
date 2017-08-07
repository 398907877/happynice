<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>用户树</title>
    <meta name="decorator" content="default"/>
    <style type="text/css">
        .hidden {
            display: none;
        }

        .orgchart .drill-icon {
            transition: opacity .5s;
            opacity: 0;
            right: -10px;
            top: -10px;
            z-index: 2;
            color: rgba(68, 157, 68, 0.5);
            font-size: 24px;
            position: absolute;
        }

        .orgchart .drill-icon:hover {
            color: #449d44;
        }

        .orgchart .node:hover .drill-icon {
            opacity: 1;
        }
    </style>
    <script type="text/javascript">
        var $1 = $.noConflict();
    </script>
    <script src="${ctxStatic}/jquery/jquery-3.1.0.min.js" type="text/javascript"></script>
    <link href="${ctxStatic}/orgChart/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="${ctxStatic}/orgChart/css/jquery.orgchart.css" rel="stylesheet"/>
    <script src="${ctxStatic}/orgChart/js/jquery.orgchart.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function() {
            var dataUrl = "${ctx}/sys/user/linkUserTree?userId=${userId}&fromUserId=${fromUserId}";
            <c:if test="${type == '1'}">
                dataUrl = "${ctx}/sys/user/reUserTree";
            </c:if>
            $('#chart-container').orgchart({
                'data': dataUrl,
                'pan': true,
                'zoom':true,
                'nodeContent': 'title',
                'clickEvent':function(nodeData){
                    <c:if test="${type == '0'}">
                        if(nodeData.id != 'null'){
                            if(nodeData.className == "drill-up"){
                                window.location.href='${ctx}/sys/user/userTreeView?type=0&userId='+nodeData.parentId;
                            }else if(nodeData.className == "drill-down"){
                                window.location.href='${ctx}/sys/user/userTreeView?type=0&userId='+nodeData.id+'&fromUserId=${userId}<c:if test="${userId==null}">${fns:getUser().loginName}</c:if>';
                            }
                        }else{
                            window.location.href='${ctx}/sys/user/registForm?linkperson='+nodeData.parentId+'&linkSide='+$('#chart-container').orgchart('getRelatedNodes',this,"siblings").length;
                        }
                    </c:if>
                }
            });
        });
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li <c:if test="${type == '0'}">class="active"</c:if>><a href="${ctx}/sys/user/userTreeView?type=0">网络结构图</a></li>
    <li <c:if test="${type == '1'}">class="active"</c:if>><a href="${ctx}/sys/user/userTreeView?type=1">推荐关系图</a></li>
    <li><a href="${ctx}/sys/user/regRecords">注册记录</a></li>
</ul>
<div id="chart-container"></div>
</body>
</html>
