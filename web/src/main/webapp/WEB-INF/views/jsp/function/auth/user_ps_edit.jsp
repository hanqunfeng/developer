<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp"%>
<link href="${_contextPath}/resource/css/jquery.validator.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${_contextPath}/resource/js/jquery.validator.js"></script>
	
<script type="text/javascript"
	src="${_contextPath}/resource/js/i18n/jquery.validator.<fmt:message key='locale'/>.js"></script> 

<script type="text/javascript">
$(function(){
	
	$('#contentForm').validator(
			{
				theme:"yellow_right",		
				rules: {
					newpasswd: function(element, params){
						 var check = true;
						 var ls = 0;
						 
						 if($(element).val().match(/^[0-9a-zA-Z#@_]{6,16}$/)){  ls++; } 
						 if($(element).val().match(/([a-zA-Z])+/)){  ls++; }  
						 if($(element).val().match(/([0-9])+/)){  ls++; }
						 if($(element).val().match(/([#@_])+/)){  ls++; }
						 if(ls < 4){
							 check = false;
						 }
			             return check || "<fmt:message key='user.detail.passwd.rule' />";
			        }
				}
		
	});
	
	
	

	$("#password").attr("placeholder","<fmt:message key='user.detail.ps.oldps' />");
	$("#newPassword").attr("placeholder","<fmt:message key='user.detail.ps.newps' />");
	$("#checkPassword").attr("placeholder","<fmt:message key='user.detail.ps.cknewps' />");
	
	
	
});

</script>
<div class="highlight">

<form:form modelAttribute="dataObj" method="post" action="${_contextPath}/auth/user/userUpdatePS.do" id="contentForm" name="contentForm" role="form" cssClass="form-horizontal">

	
	<div class="form-group ">
		<label for="userId" class="col-sm-3 control-label"><fmt:message key="system.user.userid" /></label>
		<div class="col-sm-4">
		
			${dataObj.userId}
			<input type="hidden" id="userId" name="userId" value="${dataObj.userId}" />
			
		
		
	</div>
	
	<div class="col-sm-5"></div>
	</div>
	
	<div class="form-group ">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="system.user.name" /></label>
		<div class="col-sm-4">
			${dataObj.name}
		</div>
		<div class="col-sm-5"></div>
	</div>
	
	
	<div class="form-group">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="system.user.mobile" /></label>
		<div class="col-sm-2">
			
		
			${dataObj.mobileNumber}
		</div>
		
	
		<label for="name" class="col-sm-1 control-label"><fmt:message key="system.user.phoneNumber" /></label>
		<div class="col-sm-2">
			
			${dataObj.phoneNumber}
		</div>
		<div class="col-sm-4"></div>
		
	</div>
	
	<div class="form-group">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="system.user.email" /></label>
		<div class="col-sm-4">
			
			${dataObj.email}
		</div>
		<div class="col-sm-5"></div>
	</div>
	

	
	<div class="form-group ">
		<label for="password" class="col-sm-3 control-label"><fmt:message key="user.detail.oldPw" /></label>
		<div class="col-sm-4">
			<input id="password" name="password" type="password" class="form-control" placeholder="" data-rule="required">
		</div>
		<div class="col-sm-5"></div>
	</div>
	<div class="form-group ">
		<label for="newPassword" class="col-sm-3 control-label"><fmt:message key="user.detail.newPw" /></label>
		<div class="col-sm-4">
			<input id="newPassword" name="newPassword" type="password" class="form-control" placeholder="" data-rule="required;length[6~16];">
		</div>
		<div class="col-sm-5"></div>
	</div>
	<div class="form-group ">
		<label for="checkPassword" class="col-sm-3 control-label"><fmt:message key="user.detail.checknewPw" /></label>
		<div class="col-sm-4">
			<input id="checkPassword" name="checkPassword" type="password" class="form-control" placeholder="" data-rule="required;match[newPassword]">
		</div>
		<div class="col-sm-5"></div>
	</div>
	

	
	
	
	<div class="form-group">
		<div class="col-sm-offset-3 col-sm-1 text-right">
			<button type="submit" class="btn btn-primary"><fmt:message key="common.submit"/></button>
		</div>
		<div class="col-sm-8 text-left">
	
		</div>
	</div>
</form:form>
</div>