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

        $('#contentForm').validator({
            theme : "yellow_right"
        });


    });
</script>
<div class="highlight">
    <form class="form-horizontal" role="form"
          action="${_contextPath}/auth/accesstype/detail.do" id="detailForm" name="detailForm"
          method="post">

        <input type="hidden" name="modify" value="${modify}" /> <input
            type="hidden" name="id" value="${dataObj.id}" />

        <div class="form-group ">
            <label for="name" class="col-sm-3 control-label"><fmt:message
                    key="system.accesstype.name" /></label>
            <div class="col-sm-4">
                <input type="text" class="form-control input-sm" name="name"
                       id="name" data-rule="required" value="${dataObj.name}">
            </div>
            <div class="col-sm-5"></div>
        </div>


        <div class="form-group ">
            <label for="code" class="col-sm-3 control-label"><fmt:message
                    key="system.accesstype.code" /></label>
            <div class="col-sm-4">
                <input type="text" class="form-control input-sm" name="code"
                       id="code" data-rule="required" value="${dataObj.code}" >
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