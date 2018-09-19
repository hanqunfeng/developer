<%@ include file="../common/includes.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META http-equiv="Pragma" content="no-cache">
<META http-equiv="Cache-Control" content="no-cache">
<META http-equiv="Expire" content="0">
<title><fmt:message key="common.htitle" /></title>

<link href="${_contextPath}/resource/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${_contextPath}/resource/css/ace.min.css" rel="stylesheet" />
<link rel="stylesheet"
	href="${_contextPath}/resource/css/font-awesome.min.css" />
<script src="${_contextPath}/resource/js/jquery-1.11.0.min.js"></script>

<script src="${_contextPath}/resource/js/bootstrap.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="${_contextPath}/resource/js/html5shiv.js"></script>
		<script src="${_contextPath}/resource/js/respond.min.js"></script>
		<![endif]-->
<!--[if IE ]>
		<script src="${_contextPath}/resource/js/jquery.placeholder.ie.js"></script>
		<![endif]-->
<body>

<div class="error-container">
									<div class="well center">
										<h1 class="grey lighter smaller">
											<span class="red bigger-125">
												<i class="icon-ban-circle icon-1x"></i>
												403
											</span>
											<fmt:message key="common.access.denied"/>!
											<br>
											<a href="${_contextPath}/logout.do">
												<i class="icon-off icon-large" style="color:red"></i>
												<fmt:message key="common.logout"/>
											</a>
										</h1>
										
										
									</div>
									
									<p>
									Access to the specified resource has been denied for 
									the following reason: <strong>${errorDetails}</strong>.
									</p>
									<em>Error Details (for Support Purposes only):</em><br />
									<blockquote>
									<pre>${errorTrace}</pre>
									</blockquote>
								</div>
</body>
</html>