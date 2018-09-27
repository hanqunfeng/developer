<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp"%>
<link href="${_contextPath}/resource/css/netqin.css" rel="stylesheet" />
<script type="text/javascript"
	src="${_contextPath}/resource/js/i18n/netqin.list.<fmt:message key='locale'/>.js"></script>
	
	
<c:if test="${! empty currentActiveUserSize}">
<div class="table-responsive">
		<table id="resultsTable"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>					
					<th colspan="2">${currentActiveUserSize} user(s) are logged in! </th>
					
				</tr>
				
			</thead>
			<tbody>
			<tr>					
					<th>user name</th>
					<th>last Active</th>
				</tr>
			<c:forEach items="${activeUsers}" var="item">
				<tr>
						<td>${item.key.username}</td>
						<td>
						<fmt:formatDate value="${item.value}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</c:if>

<p>
</p>
<form id="resultForm" action="${_contextPath}" method="POST">
	<div class="table-responsive">
		<table id="resultsTable"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					
					<th><fmt:message key="cache.group.name" /></th>
					<th><fmt:message key="common.operate" /></th>
				</tr>
			</thead>
			<tbody>
				<tr>
						<td><fmt:message key="cache.group.all" /></td>
						<td><a
							href="${_contextPath}/cache/flush_all.do"
							class="btn btn-info btn-minier"><fmt:message
									key="cache.flush" /> </a></td>
					</tr>
				<c:forEach items="${results}" var="item">
					<tr>
						<td>${item}</td>
						<td><a
							href="${_contextPath}/cache/flush_byName.do?cacheName=${item}"
							class="btn btn-info btn-minier"><fmt:message
									key="cache.flush" /> </a>
							<a
							href="${_contextPath}/cache/getCacheContent_byName.do?cacheName=${item}"
							class="btn btn-info btn-minier">List</a>			
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>

<c:if test="${!empty cacheName}">
<div class="table-responsive">
		<table id="resultsTable"
			class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
					
					<th colspan="4">${cacheName}</th>
					
				</tr>
				<tr>
					
					<th>Num.</th>
					<th>Cache name</th>
					<th>Cache content</th>
					<th>Operate</th>
					
				</tr>
			</thead>
			<tbody>
			<c:choose>
				<c:when test="${!empty cacheMap}">
					<c:forEach items="${cacheMap}" var="item" varStatus="status">
						<tr>
							<td>${status.index}</td>
							<td>${item.key}</td>
							<td>${item.value}</td>
							<td><a
							href="${_contextPath}/cache/getCacheContent_byListName.do?cacheName=${cacheName}&cacheListName=${item.key}"
							class="btn btn-info btn-minier"><fmt:message
									key="cache.flush" /> </a></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<th colspan="4">${cacheName} contents is null</th>
				</c:otherwise>
			</c:choose>
			</tbody>
		</table>
	</div>
</c:if>

<div>
	<div>
	
		<!-- /.table-responsive -->