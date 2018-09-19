<%@ page language="java" contentType="text/html;charset=UTF-8" %>
<%@include file="../common/includes.jsp"%>
<div   class="col-sm-6" align="left"  style="display:inline-block;margin-top:-10px;">
<p class="text-primary"><strong>
<fmt:message key="common.page.summary">
	<fmt:param value="${_pageBean.pageCount}" />
	<fmt:param value="${_pageBean.total}" />
</fmt:message></strong></p>
</div>
<script type="text/javascript">
	$(function(){
		var oldIndex = parseInt($("#page_div > #hidePage").val());
		var queryFrom = $("#queryForm");
		$("#page_div li.CP_previous a").click(function(){
			queryFrom.find("input:last").append($("<input type='hidden' name='index' value='"+(oldIndex -1)+"'>"))
			queryFrom.find("input:last").append($("<input type='hidden' name='pageSize' value='"+$("#hideSize").val()+"'>"));
			queryFrom.find("#submitbtn").click();
		});
		$("#page_div li.CP_next a").click(function(){
			queryFrom.find("input:last").append($("<input type='hidden' name='index' value='"+(oldIndex +1)+"'>"))
			queryFrom.find("input:last").append($("<input type='hidden' name='pageSize' value='"+$("#hideSize").val()+"'>"));
			queryFrom.find("#submitbtn").click();
		});
		$("#page_div li.CP_normal a").click(function(){
			queryFrom.find("input:last").append($("<input type='hidden' name='index' value='"+($(this).text() - 1)+"'>"))
			queryFrom.find("input:last").append($("<input type='hidden' name='pageSize' value='"+$("#hideSize").val()+"'>"));
			queryFrom.find("#submitbtn").click();
		});
		$("#page_div li.CP_first a").click(function(){
			queryFrom.find("input:last").append($("<input type='hidden' name='index' value='"+(0)+"'>"))
			queryFrom.find("input:last").append($("<input type='hidden' name='pageSize' value='"+$("#hideSize").val()+"'>"));
			queryFrom.find("#submitbtn").click();
		});
		$("#page_div li.CP_last a").click(function(){
			queryFrom.find("input:last").append($("<input type='hidden' name='index' value='"+($("#totalPage").val() - 1)+"'>"))
			queryFrom.find("input:last").append($("<input type='hidden' name='pageSize' value='"+$("#hideSize").val()+"'>"));
			queryFrom.find("#submitbtn").click();
		});
	});
</script>

<c:if test="${not empty _pageBean and _pageBean.pageCount != 0}">
<div id="page_div"   class="col-sm-6" style="display:inline-block;"  align="right">
<input type="hidden" value="${_pageBean.index}" id="hidePage" name="hidePage" />
<input type="hidden" value="${_pageBean.pageSize}" id="hideSize" name="hideSize" />
<input type="hidden" value="${_pageBean.pageCount}" id="totalPage" name="totalPage" />
	<ul class="pagination" style="margin-top:-10px;margin-bottom:0px;">
		<c:choose>
			<c:when test="${_pageBean.hasPrev}">
				<li class="CP_first"><a href="#"><fmt:message key="common.page.first" /></a></li>
				<li class="CP_previous"><a href="#"><fmt:message key="common.page.previous" /></a></li>
			</c:when>
			<c:otherwise>
				<li class="disabled"><a href="#"><fmt:message key="common.page.first" /></a></li>
				<li class="disabled"><a href="#"><fmt:message key="common.page.previous" /></a></li>
			</c:otherwise>
		</c:choose>
		<c:forEach begin="1" end="4" var="offset">
			<c:if test="${_pageBean.index - 5 + offset >= 0}">
				<li class="CP_normal" ><a href="#">${_pageBean.index - 5 + offset + 1}</a></li>
			</c:if>
		</c:forEach>
		<li class="active"><a href="#">${_pageBean.index + 1}</a></li>
		<c:forEach begin="1" end="4" var="offset">
			<c:if test="${_pageBean.index + offset + 1 <= _pageBean.pageCount}">
				<li class="CP_normal"><a href="#">${_pageBean.index + offset + 1}</a></li>
			</c:if>
		</c:forEach>
	     <c:choose>
			<c:when test="${_pageBean.hasNext}">
				<li class="CP_next"><a href="#"><fmt:message key="common.page.next" /></a></li>
				<li class="CP_last"><a href="#"><fmt:message key="common.page.last" /></a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#"><fmt:message key="common.page.next" /></a></li>
				<li><a href="#"><fmt:message key="common.page.last" /></a></li>
			</c:otherwise>
		</c:choose>
  	</ul>
</div>
</c:if>
