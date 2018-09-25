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
				   email1: [/^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.(?:com|cn)$/, '<fmt:message key="vali.email.error" />']
				}
				  

		
	});
	
	
	$("#search_link").click(function(){
		var val = $("#rolekey").val().toLowerCase();
		$("#authsdiv li label").filter(function(index){
			var text = $(this).text().toLowerCase();
			var re = new RegExp(val);
			if(text.match(re))
				$(this).parent().show();
			else
				$(this).parent().hide();
		});
		return false;
	});
	
	$("#show_all_link").click(function(){
		$("#rolekey").val("");
		$("#authsdiv li").show();
		return false;
	});

	$("#selected_link").click(function(){
		$("#rolekey").val("");
		$("#authsdiv li").hide();
		$("#authsdiv li :checkbox:checked").parent().show();
		return false;
	});	
	
	var modify = "${modify}";
	if(modify != "add"){
		$("#password").attr("placeholder","<fmt:message key='user.detail.changeNotice' />");
	}
	
	
});

</script>
<div class="highlight">

<form:form modelAttribute="dataObj" method="post" action="${_contextPath}/auth/user/userSave.do" id="contentForm" name="contentForm" role="form" cssClass="form-horizontal">

	
	<div class="form-group ">
		<label for="userId" class="col-sm-3 control-label"><fmt:message key="system.user.userid" /></label>
		<div class="col-sm-4">
		<c:choose>
			<c:when test="${modify == 'add'}">
				<form:input path="userId" cssClass="form-control"  data-rule="required"/>
			</c:when>
			<c:otherwise>
				${dataObj.userId}
				<input type="hidden" id="userId" name="userId" value="${dataObj.userId}" />
			</c:otherwise>
		</c:choose>
		
	</div>
	
	<div class="col-sm-5"></div>
	</div>
	
	<div class="form-group ">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="system.user.name" /></label>
		<div class="col-sm-4">
			<form:input path="name" cssClass="form-control"  data-rule="required"/>
		</div>
		<div class="col-sm-5"></div>
	</div>
	
	<div class="form-group ">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="user.detail.password" /></label>
		<div class="col-sm-4">
		<c:choose>
			<c:when test="${modify == 'add'}">
			<form:password path="password" cssClass="form-control"  data-rule="required"/>
			</c:when>
			<c:otherwise>
			<form:password path="password" cssClass="form-control"  />
			</c:otherwise>
		</c:choose>
			
		</div>
		<div class="col-sm-5"></div>
	</div>
	
	<div class="form-group">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="system.user.mobile" /></label>
		<div class="col-sm-2">
			
			<form:input path="mobileNumber" cssClass="form-control"  />
		</div>
		
	
		<label for="name" class="col-sm-1 control-label"><fmt:message key="system.user.phoneNumber" /></label>
		<div class="col-sm-2">
			<form:input path="phoneNumber" cssClass="form-control"  />
		</div>
		<div class="col-sm-4"></div>
		
	</div>
	
	<div class="form-group">
		<label for="name" class="col-sm-3 control-label"><fmt:message key="system.user.email" /></label>
		<div class="col-sm-4">
			<form:input path="email" cssClass="form-control"  data-rule="required;email"/>
		</div>
		<div class="col-sm-5"></div>
	</div>
	

	
	<div class="form-group ">
		<label class="col-sm-3 control-label"><fmt:message key="user.detail.status" /></label>
		<div class="col-sm-4">
			<div class="control-group">
					<form:radiobutton path="status" cssClass="ace form-control" value="true" data-rule="checked"/><span class="lbl control-label"><fmt:message key="common.enabled" /></span>
					<form:radiobutton path="status" cssClass="ace form-control" value="false" data-rule="checked"/><span class="lbl control-label"><fmt:message key="common.disabled" /></span>
					
			</div>
			<div class="col-sm-5"></div>
		</div>
	</div>
	
	<input type="hidden" id="modify" name="modify" value="${modify}" />

	
	<c:if test="${modify != 'add'}">
	<div class="form-group ">
			
				<label class="col-sm-3 control-label"><fmt:message key="system.user.rolesetter" /></label>
			<div class="col-sm-4">
				<c:if test="${not reserved}">
					<input type="text" name="rolekey" id="rolekey"/>
					<a href="#" id="search_link" ><fmt:message key="common.search" /></a>
					<a href="#" id="show_all_link" ><fmt:message key="common.showall" /></a>
					<a href="#" id="selected_link" ><fmt:message key="common.selected" /></a>
				</c:if>
				
				<div id="authsdiv" style="height:200px;overflow-y:scroll;border:1px solid gray;">
					<ul style="list-style-type:none;margin:0;padding:0;">
						<c:choose>
							<c:when test="${reserved}">
								<form:checkboxes items="${dataObj.roles}" itemValue="id" path="roles" itemLabel="name" cssClass="noborder" element="li" cssStyle="display:none;"/>
							</c:when>
							<c:otherwise>
								<form:checkboxes items="${roles}" itemValue="id" path="roles" itemLabel="name" cssClass="noborder" element="li"/>
							</c:otherwise>
						</c:choose>
					</ul>
				</div>
				</div>
	</div>		
	</c:if>	
	<div class="form-group">
		<div class="col-sm-offset-3 col-sm-1 text-right">
			<button type="submit" class="btn btn-primary"><fmt:message key="common.submit"/></button>
		</div>
		<div class="col-sm-8 text-left">
		<a href="${_contextPath}${parentUrl}" class="btn">
					<fmt:message key="common.back"/></a>
		</div>
	</div>
</form:form>
</div>