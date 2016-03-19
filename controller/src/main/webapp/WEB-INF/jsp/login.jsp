<!DOCTYPE html>
<!--
<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 01.03.16
  Time: 22:58
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Postman - <spring:message code="auth"/></title>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/postman.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="../../resources/js/postman.js" type="text/javascript"></script>
    <jsp:include page="/WEB-INF/jsp/menu.jsp" />
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-4">
                <div class="panel panel-warning autowindow">
                    <div class="panel-heading">
                        <spring:message code="auth"/>
                    </div>
                    <div class="panel-body">
                        <sec:authorize access="isAnonymous()">
                            <form class="form-horizontal" id="sign in" action="/j_spring_security_check" method="post">
                                <div class="form-group">
                                    <label for="j_username" class="col-sm-4 control-label"><spring:message code="email"/> </label>
                                    <div class="col-sm-8">
                                        <input name="j_username" type="text" class="form-control" id="j_username" placeholder="email">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="j_password" class="col-sm-4 control-label"><spring:message code="password"/> </label>
                                    <div class="col-sm-8">
                                        <input name="j_password" type="password" class="form-control" id="j_password" placeholder="password">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-6">
                                        <a class="btn btn-primary col-sm-12" href="/users/add"><spring:message code="registration"/></a>
                                    </div>
                                    <div class="col-sm-6 rightbutton">
                                        <spring:message code="login" var="login"/>
                                        <input class="btn btn-primary col-sm-12" type="submit" value="${login}">
                                    </div>
                                </div>
                            </form>
                        </sec:authorize>
                        <c:if test="${param.error!=null}">
                            <div class="alert alert-warning">
                                    ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                            </div>
                        </c:if>
                        <c:if test="${param.logout!=null}">
                            <div class="alert alert-warning">
                                <spring:message code="logedout"/>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
