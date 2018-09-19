<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp" %>
<link href="${_contextPath}/resource/css/netqin.css" rel="stylesheet"/>
<script type="text/javascript"
        src="${_contextPath}/resource/js/i18n/netqin.list.<fmt:message key="locale" />.js"></script>
<div class="panel panel-info">
    <div class="panel panel-heading" style="margin-bottom: 0px;">
        <h3 class="panel-title">
            <i class="icon-search"></i>
            <fmt:message key="common.form.search"/>
        </h3>
    </div>
    <div class="panel-body">


        <form class="form-inline" id="queryForm"
              action="${_contextPath}/auth/authority/list.do" method="post" role="form">
            <div class="form-group">
                <label class="sr-only" for="name">name</label> <input type="text"
                                                                      class="form-control" id="name" name="name"
                                                                      autocomplete=off
                                                                      value="${queryBean.name}"
                                                                      placeholder="<fmt:message key="system.auth.name" />">
            </div>
            <div class="form-group">
                <label class="sr-only" for="description">description</label> <input
                    type="text" class="form-control" id="description"
                    name="description" autocomplete=off
                    value="${queryBean.description}"
                    placeholder="<fmt:message key="system.auth.desc" />">
            </div>
            <div class="form-group">
                <label class="sr-only" for="entrance">entrance</label> <input
                    type="text" class="form-control" id="entrance" name="entrance"
                    value="${queryBean.entrance}" autocomplete=off
                    placeholder="<fmt:message key="system.auth.entrance" />">
            </div>
            <input type="hidden" name="sortName" id="sortName"
                   value="${sorter.sortName}"/> <input type="hidden" name="sortType"
                                                       id="sortType" value="${sorter.sortType}"/>
            <button type="submit" id="submitbtn" class="btn btn-default btn-sm"><fmt:message key="common.submit" /></button>
        </form>
    </div>
</div>


<a href="${_contextPath}/auth/authority/detail.do"
   class="btn btn-success btn-xs"><fmt:message key="common.create"/></a>
<button class="btn btn-danger btn-xs" id="delete"><fmt:message key="common.delete"/></button>

<form id="resultForm" action="${_contextPath}/auth/authority/delete.do"
      method="POST">
    <div class="table-responsive">
        <table id="resultsTable"
               class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th><input type="checkbox" name="selectAll" id="selectAll"/><fmt:message key="common.choose"/></th>
                <th><fmt:message key="system.auth.name"/><span style="display: none;">name</span></th>
                <th><fmt:message key="system.auth.desc"/><span style="display: none;">description</span></th>
                <th><fmt:message key="system.auth.entrance"/><span style="display: none;">entrance</span></th>
                <th><fmt:message key="system.auth.url"/><span style="display: none;">url</span></th>
                <th><fmt:message key="system.auth.code"/><span style="display: none;">code</span></th>
                <th><fmt:message key="system.auth.order"/><span style="display: none;">order</span></th>
                <th><fmt:message key="common.reserved"/><span style="display: none;">reserved</span></th>
                <th><fmt:message key="common.lastupdate"/><span style="display: none;">lastupdate</span></th>
                <th><fmt:message key="common.operate"/></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${results}" var="item">
                <tr>
                    <th><input type="checkbox" value="${item.id}" name="ids"/></th>
                    <td>${item.name}</td>
                    <td>${item.description }</td>
                    <td>
                            ${item.entrance}
                    </td>
                    <td>
                            <c:set var="resourceString">${item.resourceHtmlString}</c:set>
                            <c:set var="pattern" value=","/>
                            <c:set var="br" value="<br/>"/>
                            ${fn:replace(resourceString,pattern,br)}
                            <c:remove var="resourceString"/>
                    </td>
                    <td>${item.code}</td>

                    <td>${item.order}</td>
                    <td>
                        <c:choose>
                            <c:when test="${item.reserved}">
                                <fmt:message key="common.yes"/>
                            </c:when>
                            <c:otherwise>
                                <fmt:message key="common.no"/>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:set var="tmplastupdate">
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.lastupdate}"/>
                        </c:set>
                            ${tmplastupdate}
                    </td>
                    <td>
                        <a href="${_contextPath}/auth/authority/detail.do?id=${item.id}"
                           class="btn btn-info btn-minier">
                            <c:choose>
                                <c:when test="${item.reserved}">
                                    <fmt:message key="common.view"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:message key="common.edit"/>
                                </c:otherwise>
                            </c:choose>

                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</form>
<div>
    <div>
        <div class="row" style="margin:0px;">
            <c:import url="/WEB-INF/views/jsp/common/pagination.jsp"/>
        </div>
        <!-- /.table-responsive -->