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
	<%--background: url(${_contextPath}/resource/images/login_bg.png) no-repeat;--%>
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
	margin-left: 125px;
	padding-top: 30px;
}
</style>
<script type="text/javascript">
	$(function() {
		$("#resetBtn").on("click", function() {
			$("#j_username").val("");
			$("#j_password").val("");
			$("#j_code").val("");
		});
		$("#submitBtn").on("click", validate);
	});
	function validate() {
		var username = $("#j_username").val();
		if (username == "") {
			alert('<fmt:message key="login.username.check"/>');
			username.foucs();
			return;
		}
		var password = $("#j_password").val();
		if (password == "") {
			alert('<fmt:message key="login.passwd.check"/>');
			password.foucs();
			return;
		}
		var code = $("#j_code").val();
		if (code == "") {
			alert('<fmt:message key="login.checkkey.check"/>');
			code.foucs();
			return;
		}
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
						style="width: auto; font-size: 30px;"><fmt:message key="common.htitle"/>
                    </span>
				</center>
			</div>
			<div class="form">
				<form id="foo" action="j_spring_security_check" method="post"
					class="form-horizontal" role="form">

					<div class="form-group">
						<div class="col-sm-3 text-right no-padding-right">
							<label for="j_username"
								class=" control-label label label-xlg label-primary arrowed-in"><fmt:message key="system.user.name"/></label>
						</div>
						<div class="col-sm-7">
							<input type="text" class="form-control" name="j_username"
								id="j_username" placeholder="<fmt:message key="system.user.name"/>">
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<div class="col-sm-3 text-right no-padding-right">
							<label for="j_password"
								class=" control-label  label label-xlg label-primary arrowed-in"><fmt:message key="user.detail.password"/></label>
						</div>
						<div class="col-sm-7">
							<input type="password" class="form-control" name="j_password"
								id="j_password" placeholder="<fmt:message key="user.detail.password"/>">
						</div>
						<div class="col-sm-2"></div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-3 text-right no-padding-right">
							<label for="j_code"
								class=" control-label  label label-xlg label-primary arrowed-in"><fmt:message key="login.checkkey.name"/></label>
						</div>
						<div class="col-sm-4 ">
							<input type="text" class="form-control" name="j_code"
								id="j_code" placeholder="<fmt:message key="login.checkkey.name"/>">
							 
						</div>
						<div class="col-sm-5 no-padding">
						<a href="javascript:void(0);" onclick="getNewkey()" title="<fmt:message key="login.image.alt"/>"> <img id="yzkey" width="80px" height="30px" align="bottom" border="0"
									src="<%=request.getContextPath() %>/resource/image.jsp"> </a>
									
									
									
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-3">
							
						</div>
						<div class="col-sm-4">
							<input type="checkbox" value="true"  name="_spring_security_remember_me"
								id="_spring_security_remember_me" style="height: 20px;width: 10%;"><fmt:message key="login.remember.notice"/>


							 
						</div>

						<div class="col-sm-3">
							<a	 class="btn btn-danger btn-xs" href="${_contextPath}/forgotPassword.do" ><fmt:message key="user.detail.forgotPassword"/></a>
						</div>
						
					</div>


					


					<div class="row" style="margin-top: 20px;">
						<div class="col-sm-1"></div>
						<div class="col-sm-5">
							<button type="button" class="btn btn-primary btn-lg btn-block"
								id="submitBtn"><fmt:message key="common.login"/></button>
						</div>
						<div class="col-sm-5">
							<button type="button" class="btn btn-default btn-lg btn-block"
								id="resetBtn"><fmt:message key="common.reset"/></button>
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
	
	//刷新验证码
	function getNewkey(){
		jQuery("#yzkey").attr("src","${_contextPath}/resource/image.jsp?tem="+Math.floor(Math.random() * 100)).fadeIn();
		//var tem = jQuery("#foo").serialize();
		//window.open("${_contextPath}/login.do?"+tem,"_self");
	}
</script>
</html>