<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="../common/includes.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
       
	<meta http-equiv="X-UA-Compatible" content="IE=IE9" />

		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
       
<!--[if IE]>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
	    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
		<META HTTP-EQUIV="Expires" CONTENT="0">
		<meta charset="utf-8" />
		<title><fmt:message key="common.htitle" /></title>

		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!-- basic styles -->

		<link href="${_contextPath}/resource/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${_contextPath}/resource/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="${_contextPath}/resource/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->

		<!-- ace styles -->

		<link rel="stylesheet" href="${_contextPath}/resource/css/ace.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${_contextPath}/resource/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		<!-- ace settings handler -->

		<script src="${_contextPath}/resource/js/ace-extra.min.js"></script>

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${_contextPath}/resource/js/html5shiv.js"></script>
		<script src="${_contextPath}/resource/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body >
		<div class="navbar navbar-default" id="navbar">
			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
						<img alt="" src="${_contextPath}/resource/images/netqin_<fmt:message key="locale"/>.png" width="121px" height="50px">
				</div>
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							CP-FRAMEWORK-WEB-DEMO
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
					
						<li class="light-green">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<i class="icon-globe icon-large"></i>
								<span class="user-info">
									<small><fmt:message key="common.language"/>：</small>
									<font id="language_show"></font>
									
								</span>
								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="${_contextPath}/index.do?locale=zh_CN" >&nbsp;<i class="icon-yen" style="color:deepskyblue"></i>&nbsp;<fmt:message key="common.language.chinese"/></a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${_contextPath}/index.do?locale=en_US"><i class="icon-usd" style="color:deepskyblue"></i><fmt:message key="common.language.english"/></a>
								</li>
							</ul>
						</li>

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<i class="icon-smile icon-large"></i>
								<span class="user-info">
									<small>Welcome,</small>
									<c:if test="${empty user}">
									${_userName}
									</c:if>
									<c:if test="${! empty user}">
									${user.name}
									</c:if>
									
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="javascript:void(0);" onclick="profile();">
										<i class="icon-user icon-large" style="color:aqua"></i>
										<fmt:message key="common.profile"/>
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="${_contextPath}/logout.do">
										<i class="icon-off icon-large" style="color:red"></i>
										<fmt:message key="common.logout"/>
									</a>
								</li>
							</ul>
						</li>
						
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>

		<div class="main-container" id="main-container">
			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<div class="sidebar" id="sidebar">
					<div class="sidebar-shortcuts" id="sidebar-shortcuts">
						<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
							<button class="btn btn-success no-hover" style="width:100%">
								<span><fmt:message key="common.menu.title"/></span>
							</button>
						</div>

						<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
							<span class="btn btn-success no-hover"></span>
						</div>
					</div><!-- #sidebar-shortcuts -->

					<ul class="nav nav-list">
					<c:forEach items="${roles}" var="role" varStatus="roleStatus">
					<c:if test="${not empty  role.authorities }">
					<li>
						<a href="#" class="dropdown-toggle">
							<i class="icon-star" style="color:orange"></i>
							<span class="menu-text">${role.showName }</span>
							<b class="arrow icon-angle-down"></b>
						</a>
						<ul class="submenu">
							<c:forEach items="${role.authorities }" var="authority" varStatus="authorityStatus">
							 <li>
								<a href="${_contextPath}${authority.entrance}" target="contentIframe">
									<i class="icon-star-empty"></i>
									<span class="menu-text">${authority.showName}</span>
								</a>
							</li>
							</c:forEach>
							</ul>
						
					</li></c:if>
					</c:forEach>
					</ul><!-- /.nav-list -->

					<div class="sidebar-collapse" id="sidebar-collapse">
						<i class="icon-double-angle-left" data-icon1="icon-double-angle-left" data-icon2="icon-double-angle-right"></i>
					</div>
				</div>

				<div class="main-content">
					<div class="breadcrumbs" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="icon-home home-icon"></i>
								<a href="${_contextPath}">Home</a>
							</li>
							<li>
								<fmt:message key="common.welcome"/>
							</li>
						</ul><!-- .breadcrumb -->
					</div>

						<div class="page-content"  id="page-content" style="padding:8px;">
					 <iframe id="contentIframe"  class=" well well-sm" src="${_contextPath}/welcome.do" name="contentIframe" style="position:relative;width:100%; border:0;margin:0px;" ></iframe>
						
					</div><!-- /.page-content -->
				</div><!-- /.main-content -->

			</div><!-- /.main-container-inner -->

		</div><!-- /.main-container -->

		<script src="resource/js/jquery-1.11.0.min.js"></script>


		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='resource/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="resource/js/bootstrap.min.js"></script>
		<script src="resource/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="resource/js/bootstrap-datepicker.min.js"></script>

		<script src="resource/js/ace-elements.min.js"></script>
		<script src="resource/js/ace.min.js"></script>

		<script type="text/javascript">
			jQuery(function($) {
				$(window).resize(function(){
					if(/chrome/.test(navigator.userAgent.toLowerCase())){
					//	$("#main-container").height($(window).height()-$("#navbar").height()-50);
					}
					$("#contentIframe").height($(window).height()-$("#navbar").height()-$("#breadcrumbs").height()-18-18-5);
				});
				$(window).resize();
				
				var language = '<fmt:message key="locale"/>';
				if(language == "en_US"){
					$("#language_show").html('<fmt:message key="common.language.english"/>');
				}else{
					$("#language_show").html('<fmt:message key="common.language.chinese"/>');
				}
			});
			
			function profile(){
				window.open("${_contextPath}/auth/user/userUpdatePS.do?userId=${_userName}","contentIframe");
			}
		</script>
</body>
</html>

