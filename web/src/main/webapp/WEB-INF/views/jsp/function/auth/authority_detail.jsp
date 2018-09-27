<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp"%>
<script type="text/javascript" src="${_contextPath}/${detailjs}"></script>
<link href="${_contextPath}/resource/css/datepicker.css"
	rel="stylesheet" />
<link href="${_contextPath}/resource/css/jquery.validator.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${_contextPath}/resource/js/jquery.validator.js"></script>
<script type="text/javascript"
	src="${_contextPath}/resource/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
	src="${_contextPath}/resource/js/i18n/jquery.validator.<fmt:message key='locale'/>.js"></script>
<script type="text/javascript"
	src="${_contextPath}/resource/js/i18n/datepicker/bootstrap-datepicker.<fmt:message key='locale'/>.js"></script>
<script type="text/javascript">
	$(function() {
		$('#establishTime').datepicker({
			autoclose : true,
			format : "yyyy-mm-dd"
		}).next().on("click", function() {
			$(this).prev().focus();
		});
		$('#contentForm').validator({
			theme : "yellow_right"
		});
		$("#demo2Btn .btn").click(function() {
			$("#demo2").val($(this).find("input").val()).trigger("validate");

		});
		
	});
</script>
<div class="highlight">
	<form class="form-horizontal" role="form"
		action="${_contextPath}/auth/authority/detail.do" id="detailForm" name="detailForm"
		method="post">

		<input type="hidden" name="modify" value="${modify}" /> <input
			type="hidden" name="id" value="${dataObj.id}" />

		<div class="form-group ">
			<label for="name" class="col-sm-3 control-label"><fmt:message
					key="system.auth.name" /></label>
			<div class="col-sm-4">
				<input type="text" class="form-control input-sm" name="name"
					id="name" data-rule="required" value="${dataObj.name}">
			</div>
			<div class="col-sm-5"></div>
		</div>
		
		<div class="form-group ">
			<label for="nameEn" class="col-sm-3 control-label"><fmt:message
					key="system.auth.nameEn" /></label>
			<div class="col-sm-4">
				<input type="text" class="form-control input-sm" name="nameEn"
					id="nameEn" data-rule="required" value="${dataObj.nameEn}">
			</div>
			<div class="col-sm-5"></div>
		</div>
		
		<div class="form-group ">
			<label for="description" class="col-sm-3 control-label"><fmt:message
					key="system.auth.desc" /></label>
			<div class="col-sm-4">
				<textarea class="form-control input-sm" name="description"
					id="description" >${dataObj.description}</textarea>
			</div>
			<div class="col-sm-5"></div>
		</div>
		
		<div class="form-group ">
			<label for="entrance" class="col-sm-3 control-label"><fmt:message
					key="system.auth.entrance" /></label>
			<div class="col-sm-4">
				<input type="text" class="form-control input-sm" name="entrance"
					id="entrance" value="${dataObj.entrance}">
			</div>
			<div class="col-sm-5"></div>
		</div>
		
		
		<div class="form-group ">
			<label for="urls" class="col-sm-3 control-label"><fmt:message
					key="system.auth.url" /></label>
			<div class="col-sm-4">
				<textarea class="form-control input-sm" name="urls"
					id="urls" >${dataObj.resourceHtmlString}</textarea>
			</div>
			<div class="col-sm-5"></div>
		</div>

		<div class="form-group ">
			<label for="code" class="col-sm-3 control-label"><fmt:message
					key="system.auth.code" /></label>
			<div class="col-sm-4">
				<input type="text" class="form-control input-sm" name="code"
					   id="code" value="${dataObj.code}">
			</div>
			<div class="col-sm-5"></div>
		</div>

		<div class="form-group ">
			<label for="code" class="col-sm-3 control-label"><fmt:message
					key="system.auth.order" /></label>
			<div class="col-sm-4">
				<input type="text" class="form-control input-sm" name="order"
					   id="order" value="${dataObj.order}">
			</div>
			<div class="col-sm-5"></div>
		</div>



</div>
<div class="form-group">

	<div class="col-sm-offset-3 col-sm-1 text-right">
	<c:if test="${not dataObj.reserved}">
		<button type="submit" class="btn btn-primary">
			<fmt:message key="common.submit" />
		</button>
	</c:if>	
	</div>

	<div class="col-sm-8 text-left">
		<a href="${_contextPath}${parentUrl}" class="btn"> <fmt:message
				key="common.back" /></a>
	</div>
</div>
</form>
</div>