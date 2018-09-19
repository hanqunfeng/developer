<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp"%>
<link href="${_contextPath}/resource/css/netqin.css" rel="stylesheet" />
<script type="text/javascript"
	src="${_contextPath}/resource/js/i18n/netqin.list.<fmt:message key="locale" />.js"></script>
	
<script type="text/javascript">
$(function(){
	$("#userId").attr("placeholder","<fmt:message key='user.notice.userid' />");
	$("#name").attr("placeholder","<fmt:message key='user.notice.name' />");
});
</script>
<div class="panel panel-info">
	<div class="panel panel-heading" style="margin-bottom: 0px;">
		<h3 class="panel-title">
			<i class="icon-search"></i>
			<fmt:message key="common.form.search" />
		</h3>
	</div>
	<div class="panel-body">


		<form class="form-inline" id="queryForm" action="${_contextPath}/auth/user/list.do"
			method="post" role="form">

			<div class="form-group">
				<label class="sr-only" for="userId">userId</label> <input
					type="text" class="form-control" id="userId" name="userId"
					autocomplete=off value="${queryBean.userId}" placeholder="">
			</div>
			<div class="form-group">
				<label class="sr-only" for="name">name</label> <input
					type="text" class="form-control" id="name" name="name"
					autocomplete=off value="${queryBean.name}" placeholder="">
			</div>



			<input type="hidden" name="sortName" id="sortName"
				value="${sorter.sortName}" /> 
			<input type="hidden" name="sortType"
				id="sortType" value="${sorter.sortType}" />
			<button type="submit" id="submitbtn" class="btn btn-default btn-sm"><fmt:message key="common.submit" /></button>
			
		</form>
	</div>
</div>


<a href="${_contextPath}/auth/user/userAdddetail.do"
				class="btn btn-success btn-xs"><fmt:message key="common.create" /></a>

<button class="btn btn-danger btn-xs" id="delete"><fmt:message key="common.delete"/></button>				
<form id="resultForm" action="${_contextPath}/auth/user/delete.do"
	method="POST">
	<div class="table-responsive">
		<table id="resultsTable"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					<th><input type="checkbox"  name="selectAll" id="selectAll" /><fmt:message key="common.choose" /></th>
					<th class="header"><fmt:message key="system.user.userid" /><i
						class="icon-sort"></i><span style="display: none;">userId</span></th>
						
					<th class="header"><fmt:message key="system.user.name" /><i
						class="icon-sort"></i><span style="display: none;">name</span></th>

						
					<th class="header"><fmt:message key="system.user.roles" /></th>
					<th class="header"><fmt:message key="system.user.mobile" /></th>
					<th class="header"><fmt:message key="system.user.phoneNumber" /></th>
					<th class="header"><fmt:message key="system.user.email" /></th>
					<th class="header"><fmt:message key="user.detail.status" /></th>
					
					<th><fmt:message key="common.operate" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${results}" var="item">
					<tr>
						<th><input type="checkbox" value="${item.userId}" name="ids" /></th>
						<td>
						<a href="${_contextPath}/auth/user/detail.do?userId=${item.userId}">
						${item.userId}
						</a>
						</td>
						<td>${item.name}</td>

						
						<td>
						<c:set var="rolesdesc" value=""></c:set>
						<c:if test="${not empty item.roles}">
							<c:forEach items="${item.roles}" var="roleitem" varStatus="status">
								<c:set var="rolesdesc">
									${rolesdesc}${roleitem.name}
									<c:if test="${not status.last}">,</c:if>
								</c:set>
							</c:forEach>
						</c:if>
						<div style="width:170px;" class="breaktext" title="${rolesdesc}">
							${rolesdesc}
						</div>
						</td>
						<td>${item.mobileNumber}</td>
						<td>${item.phoneNumber}</td>
						<td>${item.email}</td>
						<td>
							<c:choose>
							<c:when test="${item.status == true}"> <fmt:message key="common.enabled" /></c:when>
							<c:otherwise><fmt:message key="common.disabled" /></c:otherwise>
							</c:choose>			
						</td>
						
						<td><a
							href="${_contextPath}/auth/user/detail.do?userId=${item.userId}"
							class="btn btn-info btn-minier"><fmt:message key="system.user.rolesetter" />
							</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>
<div>
	<div>
		<div class="row" style="margin:0px;">
			<c:import url="/WEB-INF/views/jsp/common/pagination.jsp" />
		</div>
		<!-- /.table-responsive -->