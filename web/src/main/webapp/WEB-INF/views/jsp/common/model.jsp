<%@ include file="../common/includes.jsp"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html >
	<head>
		<title><fmt:message key="common.htitle" /></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<META http-equiv="Pragma" content="no-cache">
		<META http-equiv="Cache-Control" content="no-cache">
		<META http-equiv="Expire" content="0">
		<link href="${_contextPath}/resource/css/bootstrap.min.css" rel="stylesheet" />
		<link href="${_contextPath}/resource/css/ace.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${_contextPath}/resource/css/font-awesome.min.css" />
		<script src="${pageContext.request.contextPath}/resource/js/jquery-1.11.0.min.js"></script>


		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${_contextPath}/resource/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${_contextPath}/resource/js/bootstrap.min.js"></script>
		<script src="${_contextPath}/resource/js/bootbox.min.js"></script>
		<script src="${_contextPath}/resource/js/jquery.bootstrap-growl.min.js"></script>
		
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${_contextPath}/resource/js/html5shiv.js"></script>
		<script src="${_contextPath}/resource/js/respond.min.js"></script>
		<![endif]-->
		<!--[if IE ]>
		<script src="${_contextPath}/resource/js/jquery.placeholder.ie.js"></script>
		<![endif]-->
		<script type="text/javascript">
		$(function(){
			var message="${message}";
			var messageStatus="${messageStatus}";
			var msg ="<Strong><s:message code="${message}" arguments="${arguments}" text=""/></Strong>";
			
			if(message!=""){
				if(messageStatus!="error"){
					$.bootstrapGrowl(msg, { type: "warning", width: 'auto',align:"right"});
				}else{
					$.bootstrapGrowl(msg, { type: "warning", width: 'auto',align:"right",delay:0 });
				}
			}
		});
		<%
		request.getSession().removeAttribute("message");
		request.getSession().removeAttribute("messageStatus");
		%>
		</script>
	</head>
	<body >
	

	
	
	<div class="row" style="margin:0px;"> <div class="col-sm-12"  style="padding:0px;">
	<div class="panel panel-primary" style="margin-bottom:1px;">
        <div class="panel-heading">
          <h3 class="panel-title"><i class="icon-list"></i> <fmt:message key="${htitle}" /></h3>
        </div>
        <div class="panel-body" style="background-color:#EEEEEE;padding:8px;">
       <c:import url="${content}"></c:import>
        </div>
        </div></div></div>
</body>
</html>