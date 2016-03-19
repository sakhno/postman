<!--
<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 15.03.16
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
-->
<html>
<head>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/postman.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="../../resources/js/postman.js" type="text/javascript"></script>
</head>
<body>
    <nav class="navbar navbar-default" id="menu">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/home"><span class="glyphicon glyphicon-envelope" aria-hidden="true"></span> Postman</a>
            </div>
            <div class="collapse navbar-collapse" id="navbar">
                <ul class="nav navbar-nav">
                    <li><a href="/home">Home</a></li>
                    <li><a href="/commingsoon"><spring:message code="mytracks"/></a></li>
                    <li><a href="/users/edit"><spring:message code="profile"/></a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Language <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="?language=en">English</a></li>
                            <li><a href="?language=ru">Русский</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>
