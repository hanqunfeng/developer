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
<style>
body {
	margin: 0;
	font-size: 18px;
	font-family: Arial;
	background: #d6dedf url(${_contextPath}/resource/images/body_bg.gif)
		repeat-x left top;
	color: #333333;
}

.main {
	width: 1000px;
	background: url(${_contextPath}/resource/images/main_bg.jpg);
	min-height: 485px;
	margin: 0 auto;
	padding-top: 160px;
}

.login {
	background: url(${_contextPath}/resource/images/login_bg.png) no-repeat;
	width: 707px;
	height: 461px;
	margin: 0 auto;
	padding-top: 15px;
}

.login .title_1 {
	height: 82px;
	line-height: 82px;
}

.form {
	margin-left: 275px;
	padding-top: 30px;
}
</style>
<script type="text/javascript">

	$(function() {
		$("#submitBtn").on("click", validate);
	});
	
	function validate() {
		$("#foo").submit();
	}
	function checkKey() {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == 13) {
			validate();
		}
	}
</script>
<body>


	<div class="main" onkeydown="checkKey()">
		<div class="login">
			<div class="title_1">
				<center>
					<span class="btn  btn-primary no-hover "
						style="width: auto; font-size: 30px;"><fmt:message key="common.htitle"/></span>
				</center>
			</div>
			<div class="form">
				<form id="foo" action="j_spring_openid_security_check" method="post"
					class="form-horizontal" role="form">

					<div class="form-group">
						<div class="col-sm-3 text-right no-padding-right">
							<label for="openid_identifier"
								class=" control-label label label-xlg label-primary arrowed-in">OpenId</label>
						</div>
						<div class="col-sm-7">
							Login by google openId
							<input type="hidden" class="form-control" name="openid_identifier"
								id="openid_identifier" size="50" value="https://www.google.com/accounts/o8/id">
						</div>
						<div class="col-sm-2"></div>
					</div>
					
					
				
					
					
					


					<div class="row" style="margin-top: 25px;">
						<div class="col-sm-1"></div>
						<div class="col-sm-5">
							<button type="button" class="btn btn-primary btn-lg btn-block"
								id="submitBtn" value="Sign in with Google"><fmt:message key="common.login"/></button>
							
						</div>
						<div class="col-sm-5">
							
						</div>
						<div class="col-sm-1"></div>

					</div>

				</form>
				<input type="hidden" id="sErrorCause" name="sErrorCause"
					value="${sErrorCause}" />
			</div>
		</div>
	</div>
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
	
</script>
</html>