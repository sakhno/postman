<!DOCTYPE html>
<!--
<%--
  Created by IntelliJ IDEA.
  User: antonsakhno
  Date: 01.03.16
  Time: 22:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="mainpage"/></title>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/postman.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-sm-8">
                <div class="panel panel-primary window">
                    <div class="panel-heading">
                        Find track
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal" id="newtrackform">
                            <div class="form-group">
                                <div class="col-xs-8">
                                    <spring:message code="entertracknumber" var="entertracknumber"/>
                                    <div class="input-group">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span> </span>
                                        <input class="form-control input-lg" id="newtrack"placeholder="${entertracknumber}"/>
                                    </div>
                                </div>
                                <div class="col-sm-4">
                                    <input class="btn btn-primary col-xs-10 input-lg" type="submit" value="<spring:message code="find"/>">
                                </div>
                            </div>
                        </form>
                        <div class="alert alert-warning" role="alert"><spring:message code="parselnotfound"/> </div>
                        <sec:authorize access="isAnonymous()">
                            <div class="alert alert-info" role="alert"><spring:message code="signintoreceiveemail"/></div>
                        </sec:authorize>
                    </div>
                    <table class="table table-striped">
                        <tr>
                            <td class="col-sm-2">1.02.2016
                                02:40:37</td>
                            <td class="col-sm-10">Прибыло в сортировочный центр Прибыло в сортировочный центр Прибыло в сортировочный центр</td>
                        </tr>
                        <tr>
                            <td>1.02.2016
                                02:40:37</td>
                            <td>Обработка в сортировочном центре </td>
                        </tr>
                        <tr>
                            <td>1.02.2016
                                02:40:37</td>
                            <td>[Шэньчжэнь] Подготовка к экспорту</td>
                        </tr>
                        <tr>
                            <td class="col-sm-2">1.02.2016
                                02:40:37</td>
                            <td class="col-sm-10">Прибыло в сортировочный центр Прибыло в сортировочный центр Прибыло в сортировочный центр</td>
                        </tr>
                        <tr>
                            <td>1.02.2016
                                02:40:37</td>
                            <td>Обработка в сортировочном центре </td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="col-sm-4">
                <sec:authorize access="isAnonymous()"><c:set var="signinpanel" value="panel-warning"/></sec:authorize>
                <sec:authorize access="isAuthenticated()"><c:set var="signinpanel" value="panel-success"/> </sec:authorize>
                <div class="panel ${signinpanel} autowindow">
                    <div class="panel-heading">
                        Sign in
                    </div>
                    <div class="panel-body">
                        <sec:authorize access="isAnonymous()">
                            <form class="form-horizontal" id="sign in" action="/j_spring_security_check" method="post">
                                <div class="form-group">
                                    <label for="j_username" class="col-sm-4 control-label"><spring:message code="loginemail"/> </label>
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
                                    <div class="col-sm-2">
                                        <button type="button" class="btn btn-default">
                                            <span class="glyphicon glyphicon-log-in" aria-hidden="true"></span>
                                        </button>
                                    </div>
                                    <div class="col-sm-5">
                                        <a class="btn btn-primary col-sm-12" href="/users/add"><spring:message code="registration"/></a>
                                    </div>
                                    <div class="col-sm-5 rightbutton">
                                        <spring:message code="login" var="login"/>
                                        <input class="btn btn-primary col-sm-12" type="submit" value="${login}">
                                    </div>
                                </div>
                            </form>
                        </sec:authorize>
                        <sec:authorize access="isAuthenticated()">
                            <h4 align="center"><spring:message code="hello"/>, ${user.name eq null||user.name eq '' ? user.login : user.name}</h4>
                            <div class="col-sm-6">
                                <a class="btn btn-primary col-sm-12" href="/users/edit"><spring:message code="profile"/></a>
                            </div>
                            <div class="col-sm-6">
                                <a class="btn btn-primary col-sm-12" href="/logout"><spring:message code="logout"/></a>
                            </div>
                        </sec:authorize>
                        <c:if test="${param.error!=null}">
                            <div class="alert alert-warning">
                                ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
                            </div>
                        </c:if>
                        <c:if test="${param.logout!=null}">
                            <div class="alert alert-warning">
                                You have been logged out.
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="panel panel-primary historypanel">
                    <div class="panel-heading">
                        History
                    </div>
                    <div class="panel panel-body">
                        <sec:authorize access="isAnonymous()">
                            <div class="alert alert-info" role="alert"><spring:message code="signintoviewhistory"/></div>
                        </sec:authorize>
                    </div>
                    <table class="table table-striped historytable">
                        <tr>
                            <td class="col-sm-2">TH894678361CN<br>tablet case</td>
                            <td class="col-sm-2">1.02.2016 02:40:37</td>
                            <td class="col-sm-8">Прибыло в сортировочный центр</td>
                        </tr>
                        <tr>
                            <td>RI859392561CN<br>tablet case</td>
                            <td>25.01.2016 02:40:37</td>
                            <td>Прибыло в сортировочный центр</td>
                        </tr>
                        <tr>
                            <td>KJ859424261CN<br>tablet case</td>
                            <td>20.01.2016 02:40:37</td>
                            <td>Прибыло в сортировочный центр</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
