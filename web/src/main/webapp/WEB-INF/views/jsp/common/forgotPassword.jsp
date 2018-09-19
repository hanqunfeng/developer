<%@ include file="../common/includes.jsp"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
<script type="text/javascript" src="${_contextPath}/${detailjs}"></script>
		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${_contextPath}/resource/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="${_contextPath}/resource/js/bootstrap.min.js"></script>
		<script src="${_contextPath}/resource/js/bootbox.min.js"></script>
		<script src="${_contextPath}/resource/js/jquery.bootstrap-growl.min.js"></script>
		<script src="${_contextPath}/resource/js/ace-elements.min.js"></script>
		<script src="${_contextPath}/resource/js/ace.min.js"></script>
		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and m
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
		<script src="${_contextPath}/resource/js/html5shiv.js"></script>
		<script src="${_contextPath}/resource/js/respond.min.js"></script>
		<![endif]-->
<!--[if IE ]>
		<script src="${_contextPath}/resource/js/jquery.placeholder.ie.js"></script>
		<![endif]-->
<script type="text/javascript">
	$(function() {
		$("#submitBtn").on("click", validate);
	});
	function validate() {
		var username = $("#userId");
		if (username.val() == "") {
			alert('<fmt:message key="user.detail.forgotPassword.userAccount"/>');
			username.foucs();
			return;
		}
		var password = $("#name");
		if (password.val() == "") {
			alert('<fmt:message key="user.detail.forgotPassword.username"/>');
			password.foucs();
			return;
		}
		var code = $("#j_code");
		if (code.val() == "") {
			alert('<fmt:message key="login.checkkey.check"/>');
			code.foucs();
			return;
		}
		$("#formForgotPassword").submit();
	}
	function checkKey() {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == 13) {
			validate();
		}
	}
</script>
<body >
<form id="formForgotPassword" class="form-horizontal" action="${_contextPath}/forgotPasswordEmail.do" method="post" role="form">
<div class="row" style="padding-top: 160px;"> 
<div class="col-sm-4" >
</div>
<div class="col-sm-4" >
<div class="panel panel-info" >
<div class="panel-heading text-center">
          <h3 class="panel-title"><i class="icon-cogs"></i> <b><fmt:message key="common.reset.password" /></b></h3>
        </div>
  <div class="panel-body">
  <c:if test="${result!=null }">
  <c:if test="${result==100}">
  <div class="form-group">
		<div class="col-sm-2"></div>
		<div class="col-sm-8 text-center">
			<div class="alert alert-success">
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
  </c:if>
  <c:if test="${result!=100}">
    <div class="form-group">
		<label class="col-sm-4 control-label no-padding-right" for="form-field-1"><fmt:message key="system.user.userid" /></label>
		<div class="col-sm-4">
			<input type="text" id="userId" name="userId" placeholder="<fmt:message key="system.user.userid" />" >
		</div>
		<div class="col-sm-4"></div>
	</div>
    <div class="form-group">
		<label class="col-sm-4 control-label no-padding-right" for="form-field-1"><fmt:message key="system.user.name" /></label>
		<div class="col-sm-4">
			<input type="text" id="name" name="name" placeholder="<fmt:message key="system.user.name" />" >
		</div>
		<div class="col-sm-4"></div>
	</div>
    <div class="form-group">
		<label class="col-sm-4 control-label no-padding-right" for="form-field-1"><fmt:message key="login.checkkey.name"/></label>
		<div class="col-sm-2 no-padding-right">
			<input type="text" name="j_code" id="j_code" placeholder="<fmt:message key="login.checkkey.name"/>" class="col-sm-12 no-padding-right">
		</div>
		<div class="col-sm-5">
			<a href="javascript:void(0);" onclick="getNewkey()" title="<fmt:message key="login.image.alt"/>"> <img id="yzkey" width="80px" height="30px" align="bottom" border="0"
									src="<%=request.getContextPath() %>/resource/image.jsp"> </a>
		</div>
	</div>
    <div class="form-group">
		<div class="col-sm-3"></div>
		<div class="col-sm-3">
			<button type="button" class="btn btn-primary btn-sm btn-block"
								id="submitBtn"><fmt:message key="user.detail.applyForResettingPassword"/></button>
		</div>
			<div class="col-sm-3">
			<a href="https://contracts.nq.com/login.do" class="btn btn-primary btn-sm btn-block"><fmt:message key="common.back"/></a>
		</div>
		<div class="col-sm-3"></div>
	</div>
	</c:if>
	<c:if test="${result==100}">
	<div class="col-sm-4"></div>
	<div class="col-sm-4">
	<a href="https://contracts.nq.com/login.do" class="btn btn-primary btn-sm btn-block"><fmt:message key="common.back"/></a>
</div>
<div class="col-sm-4"></div>
	</c:if>
  </div>
</div>
</div>
<div class="col-sm-4" >
</div>
</div>
</form>
</body>
<script type="text/javascript">
	jQuery(function($) {
		var sErrorCause = $("#sErrorCause").val();
		if (sErrorCause != "") {
			//$(".tips span").show();
			if("1" == sErrorCause){
				alert('<fmt:message key="login.error.check"/>');
			}else if("2" == sErrorCause){
				alert('<fmt:message key="login.checkkey.error"/>');
			}
		}
	});
	
	//刷新验证码
	function getNewkey(){
		jQuery("#yzkey").attr("src","${_contextPath}/resource/image.jsp?tem="+Math.floor(Math.random() * 100)).fadeIn();
		//var tem = jQuery("#foo").serialize();
		//window.open("${_contextPath}/login.do?"+tem,"_self");
	}
</script>
</html>