<%@ include file="/WEB-INF/views/jsp/common/includes.jsp"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
 <c:if test="${result==100}">
  <div class="form-group">
		<div class="col-sm-2"></div>
		<div class="col-sm-8 text-center">
			<div class="alert alert-info">
			<fmt:message key="user.detail.forgotPassword.${result}" />
		</div>
		</div>
		<div class="col-sm-2"></div>
	</div>
  </c:if>
  <c:if test="${result!=100}">
  <div class="form-group">
		<div class="col-sm-2"></div>
		<div class="col-sm-8 text-center">
			<div class="alert alert-danger">
			<fmt:message key="user.detail.forgotPassword.${result}" />
		</div>
		</div>
		<div class="col-sm-2"></div>
	</div>
  </c:if>
</body>
</html>