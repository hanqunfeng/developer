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
												Notice!
											</span>
											<fmt:message key="common.userNotFound.notice"/>
											<br>
											<a href="${_contextPath}/logout.do" target="_parent" id="logout_link">
											<i class="icon-off icon-large" style="color:red"></i>
												<fmt:message key="common.logout" />
											</a>					
										</h1>										
									</div>
									
									
								</div>
								<c:if test="${!empty OpenId }">
									<blockquote>
											<h1 class="grey lighter smaller">
												<fmt:message key="common.userNotFound.openId.notice" /><br>
												OpenId : ${OpenId} <br>
												Email : ${email}
						
											</h1>
									</blockquote>
								</c:if>
								
								<c:if test="${!empty ldapuser }">
									<p>
										Some information about you, from LDAP:
									</p>
									<ul>
										<li><strong>Username:</strong> ${ldapuser.username}</li>
										<li><strong>DN:</strong> ${ldapuser.dn}</li>
										<c:if test="${isLdapPerson}">
											<li><strong>Description:</strong> ${ldapuser.description}</li>
											<li><strong>Telephone:</strong> ${ldapuser.telephoneNumber}</li>
											<li><strong>Full Name(s):</strong>
											<c:forEach items="${ldapuser.cn}" var="cn">
												${cn}<br />
											</c:forEach>
											</li>
										</c:if>
										<c:if test="${isLdapInetOrgPerson}">
											<li><strong>Email:</strong> ${ldapuser.mail}</li>
											<li><strong>Street:</strong> ${ldapuser.street}</li>
										</c:if>
									</ul>
								</c:if>	
										
</body>
</html>