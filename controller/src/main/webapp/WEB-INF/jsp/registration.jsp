<!DOCTYPE html>
<!--
<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 03.03.16
  Time: 21:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
-->
<html>
<head>
    <title><spring:message code="registration"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/postman.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <spring:message code="registration"/>
                </div>
                <div class="panel-body">
                    <form:form class="form-horizontal" method="post" action="/users/add" modelAttribute="userForm">
                        <spring:bind path="login">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label for="login" class="control-label col-sm-4"><spring:message code="loginemail"/> </label>
                                <div class="col-sm-7">
                                    <spring:message code="loginemail" var="loginemail"/>
                                    <form:input path="login" type="text" cssClass="form-control" id="login" placeholder="${loginemail}"/>
                                    <form:errors path="login" cssClass="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="name">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label for="name" class="control-label col-sm-4"><spring:message code="name"/> </label>
                                <div class="col-sm-7">
                                    <spring:message code="name" var="name"/>
                                    <form:input path="name" type="text" cssClass="form-control" id="name" placeholder="${name}"/>
                                    <form:errors path="name" cssClass="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="password">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label for="password" class="control-label col-sm-4"><spring:message code="password"/> </label>
                                <div class="col-sm-7">
                                    <spring:message code="password" var="password"/>
                                    <form:password path="password" cssClass="form-control" id="password" placeholder="${password}"/>
                                    <form:errors path="password" cssClass="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="confirmPassword">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label for="confirmpassword" class="control-label col-sm-4"><spring:message code="confirmpassword"/> </label>
                                <div class="col-sm-7">
                                    <spring:message code="confirmpassword" var="confirmpassword"/>
                                    <form:password path="confirmPassword" cssClass="form-control" id="confirmpassword" placeholder="${confirmpassword}"/>
                                    <form:errors path="confirmPassword" cssClass="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="notifyByEmail">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label for="notifybyemail" class="control-label col-sm-4"><spring:message code="notifybyemail"/> </label>
                                <div class="col-sm-7">
                                    <form:checkbox path="notifyByEmail" id="notifybyemail"/>
                                    <form:errors path="notifyByEmail" cssClass="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="language">
                            <div class="form-group ${status.error ? 'has-error' : ''}">
                                <label for="language" class="control-label col-sm-4"><spring:message code="language"/> </label>
                                <div class="col-sm-7">
                                    <form:select path="language" id="language" cssClass="form-control">
                                        <c:forEach var="languageItem" items="${languages}">
                                            <form:option value="${languageItem}" label="${languageItem.toString()}"/>
                                        </c:forEach>
                                    </form:select>
                                    <form:errors path="language" cssClass="control-label"/>
                                </div>
                            </div>
                        </spring:bind>
                        <div class="form-group">
                            <div class="col-sm-7 col-sm-offset-4">
                                <c:choose>
                                    <c:when test="${empty userForm.name}">
                                        <button type="submit" class="btn btn-primary btn-block"><spring:message code="register"/> </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn btn-primary btn-block"><spring:message code="save"/> </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>