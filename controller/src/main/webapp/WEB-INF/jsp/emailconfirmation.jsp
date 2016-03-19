<!DOCTYPE html>
<!--
<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 14.03.16
  Time: 00:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Postman - <spring:message code="confirmemail"/></title>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/postman.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="../../resources/js/postman.js" type="text/javascript"></script>
    <jsp:include page="/WEB-INF/jsp/menu.jsp" />
    <script>
        window.setTimeout(function() {
            window.location.href = '/home';
        }, 5000);
    </script>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <div class="panel panel-primary window">
                    <div class="panel-heading"></div>
                    <div class="panel-body">
                        <c:choose>
                            <c:when test="${message eq 'emailconfirmationsuccessful'}">
                                <div class="alert alert-success" role="alert"><spring:message code="emailconfirmationsuccessful"/></div>
                            </c:when>
                            <c:when test="${message eq 'confirmationerror'}">
                                <div class="alert alert-danger" role="alert"><spring:message code="emailconfirmationerror"/></div>
                            </c:when>
                            <c:when test="${message eq 'dberror'}">
                                <div class="alert alert-danger" role="alert"><spring:message code="dberror"/></div>
                            </c:when>
                            <c:when test="${message eq 'tokenexpired'}">
                                <div class="alert alert-danger" role="alert"><spring:message code="tokenexpired"/></div>
                            </c:when>
                            <c:otherwise>
                                <div class="alert alert-success" role="alert"><spring:message code="emaillink"/></div>
                            </c:otherwise>
                        </c:choose>
                        <a class="btn btn-primary col-sm-6 col-sm-offset-3 btn-lg" role="button" href="/home"><span class="glyphicon glyphicon-home" aria-hidden="true"></span></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
