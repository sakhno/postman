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
    <title>Postman - <spring:message code="managetracks"/></title>
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
            <div class="col-sm-5">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title"><spring:message code="activetracks"/></h4>
                    </div>
                    <table class="table table-striped table-hover" style="cursor: pointer">
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-6">чехольчик</td>
                            <td class="col-sm-1"><span class="badge">2</span></td>
                        </tr>
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-6"></td>
                            <td class="col-sm-1"><span class="badge">10</span></td>
                        </tr>
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-6"></td>
                            <td class="col-sm-1"><span class="badge"></span></td>
                        </tr>
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-6">чехольчик</td>
                            <td class="col-sm-1"><span class="badge"></span></td>
                        </tr>
                    </table>
                    <div class="panel-body">
                        <a class="btn btn-success col-sm-4 pull-right" href="#"><spring:message code="addnewtrack"/></a>
                    </div>
                </div>
                <div class="panel panel-success">
                    <div class="panel-heading">
                        <h4 class="panel-title"><spring:message code="receivedtracks"/></h4>
                    </div>
                    <table class="table table-striped table-hover" style="cursor: pointer">
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-7"></td>
                        </tr>
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-7"></td>
                        </tr>
                        <tr href="#">
                            <td class="col-sm-2">22.03.2016</td>
                            <td class="col-sm-3">RE819137245SE</td>
                            <td class="col-sm-7">name</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="col-sm-7">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4 class="panel-title"><spring:message code="trackinfo"/></h4>
                    </div>
                    <div class="panel-body">
                        fgkjhdskjfsjk
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
