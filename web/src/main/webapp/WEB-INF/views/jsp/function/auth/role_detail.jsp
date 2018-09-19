<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/common/includes.jsp" %>
<script type="text/javascript" src="${_contextPath}/${detailjs}"></script>
<link href="${_contextPath}/resource/css/datepicker.css"
      rel="stylesheet"/>
<link href="${_contextPath}/resource/css/jquery.validator.css"
      rel="stylesheet"/>
<script type="text/javascript"
        src="${_contextPath}/resource/js/jquery.validator.js"></script>
<script type="text/javascript"
        src="${_contextPath}/resource/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript"
        src="${_contextPath}/resource/js/i18n/jquery.validator.<fmt:message key='locale'/>.js"></script>
<script type="text/javascript"
        src="${_contextPath}/resource/js/i18n/datepicker/bootstrap-datepicker.<fmt:message key='locale'/>.js"></script>
<script type="text/javascript">
    $(function () {
        $('#establishTime').datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        }).next().on("click", function () {
            $(this).prev().focus();
        });
        $('#contentForm').validator({
            theme: "yellow_right"
        });
        $("#demo2Btn .btn").click(function () {
            $("#demo2").val($(this).find("input").val()).trigger("validate");

        });


        $("#search_link").click(function () {
            var val = $("#authkey").val().toLowerCase();
            $("#authsdiv li label").filter(function (index) {
                var text = $(this).text().toLowerCase();
                var re = new RegExp(val);
                if (text.match(re))
                    $(this).parent().show();
                else
                    $(this).parent().hide();
            });
            return false;
        });

        $("#show_all_link").click(function () {
            $("#authkey").val("");
            $("#authsdiv li").show();
            return false;
        });

        $("#selected_link").click(function () {
            $("#authkey").val("");
            $("#authsdiv li").hide();
            $("#authsdiv li :checkbox:checked").parent().show();
            return false;
        });

    });
</script>
<div class="highlight">


<form:form modelAttribute="dataObj" class="form-horizontal" role="form"
           action="${_contextPath}/auth/role/detail.do" id="detailForm" name="detailForm"
           method="post" cssClass="form-horizontal">

    <input type="hidden" name="modify" value="${modify}"/> <input
        type="hidden" name="id" value="${dataObj.id}"/>

    <div class="form-group ">
        <label for="name" class="col-sm-3 control-label"><fmt:message
                key="system.role.name"/></label>
        <div class="col-sm-4">
            <input type="text" class="form-control input-sm" name="name"
                   id="name" data-rule="required" value="${dataObj.name}">
        </div>
        <div class="col-sm-5"></div>
    </div>

    <div class="form-group ">
        <label for="nameEn" class="col-sm-3 control-label"><fmt:message
                key="system.role.nameEn"/></label>
        <div class="col-sm-4">
            <input type="text" class="form-control input-sm" name="nameEn"
                   id="nameEn" data-rule="required" value="${dataObj.nameEn}">
        </div>
        <div class="col-sm-5"></div>
    </div>

    <div class="form-group ">
        <label for="description" class="col-sm-3 control-label"><fmt:message
                key="system.role.desc"/></label>
        <div class="col-sm-4">
				<textarea class="form-control input-sm" name="description"
                          id="description" data-rule="required">${dataObj.description}</textarea>
        </div>
        <div class="col-sm-5"></div>
    </div>

    <div class="form-group ">
        <label for="authkey" class="col-sm-3 control-label"><fmt:message key="system.role.authorities"/></label>
        <div class="col-sm-4">
            <c:if test="${not dataObj.reserved}">
                <input type="text" name="authkey" id="authkey"/>
                <a href="#" id="search_link"><fmt:message key="common.search"/></a>
                <a href="#" id="show_all_link"><fmt:message key="common.showall"/></a>
                <a href="#" id="selected_link"><fmt:message key="common.selected"/></a>
            </c:if>
            <div id="authsdiv" style="height:400px;width:100%;overflow-y:scroll;border:1px solid gray;">
                <ul style="list-style-type:none;margin:0;padding:0;">
                    <c:choose>
                        <c:when test="${dataObj.reserved}">
                            <form:checkboxes items="${dataObj.authorities}" itemValue="id" path="authorities"
                                             itemLabel="showNameRole" cssClass="noborder" element="li"
                                             cssStyle="display:none;"/>

                        </c:when>
                        <c:otherwise>
                            <%--<form:checkboxes items="${authorities}" itemValue="id" path="authorities" itemLabel="showNameRole" cssClass="noborder" element="li"  />--%>
                            <c:forEach items="${authorities}" var="item">
                                <li>
                                    <c:set var="authoritysString">${dataObj.authoritysHtmlString}</c:set>
                                    <c:set var="shwoName">${item.showNameRole}</c:set>
                                    <c:choose>
                                        <c:when test="${fn:contains(authoritysString, shwoName)}">
                                            <input type="checkbox" name="authorities" value="${item.id}"
                                                   checked="checked">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="authorities" value="${item.id}">
                                        </c:otherwise>
                                    </c:choose>

                                    <label>${item.showNameRole}</label>
                                    <br>
                                    <c:forEach items="${accessTypes}" var="type">
                                        <c:set var="accessAuthShowIds">${accessAuthShowIds}</c:set>
                                        <c:set var="accessAuthshwoName">${item.id}_${type.id}</c:set>
                                        <c:choose>
                                            <c:when test="${fn:contains(accessAuthShowIds, accessAuthshwoName)}">
                                                -- <input type="checkbox" name="accessTypes"
                                                          value="${item.id}_${type.id}" checked="checked"> ${type.name}
                                            </c:when>
                                            <c:otherwise>
                                              --  <input type="checkbox" name="accessTypes"
                                                       value="${item.id}_${type.id}"> ${type.name}
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <br>---------- ----------- ------------ ----------- ----------- ------------<br>
                                </li>
                            </c:forEach>

                        </c:otherwise>
                    </c:choose>


                </ul>
            </div>
        </div>
        <div class="col-sm-5"></div>
    </div>

    </div>
    <div class="form-group">

        <div class="col-sm-offset-3 col-sm-1 text-right">
            <c:if test="${not dataObj.reserved}">
                <button type="submit" class="btn btn-primary">
                    <fmt:message key="common.submit"/>
                </button>
            </c:if>
        </div>

        <div class="col-sm-8 text-left">
            <a href="${_contextPath}${parentUrl}" class="btn"> <fmt:message
                    key="common.back"/></a>
        </div>
    </div>
</form:form>
</div>