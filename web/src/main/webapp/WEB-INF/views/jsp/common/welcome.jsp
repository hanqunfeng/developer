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
	热烈欢迎!

	<sec:authorize access="hasRole('FILE_ADD')">

		<a href="admin.jsp">admin page</a>

	</sec:authorize>

	<sec:authorize access="hasRole('ROLE_FILE_ADD')">

		<a href="admin.jsp">admin page1</a>

	</sec:authorize>


	<sec:authorize url="/welcome.do">

		<a href="admin.jsp">admin page2</a>

	</sec:authorize>

	<sec:authorize access="hasRole('FILE_MODIFY')">

		<a href="admin.jsp">admin page3</a>

	</sec:authorize>

	<sec:authorize access="hasAnyAuthority('ROLE_FILE_MODIFY')">

		<a href="admin.jsp">admin page4</a>

	</sec:authorize>
</body>
</html>