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
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
-->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="mainpage"/></title>
    <script src="../../resources/js/bootstrap.min.js" type="text/javascript"></script>
    <link href="../../resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="../../resources/css/postman.css" rel="stylesheet" type="text/css"/>
    <script src="../../resources/js/jquery-2.2.1.min.js" type="text/javascript"></script>
    <script src="../../resources/js/postman.js" type="text/javascript"></script>
    <jsp:include page="/WEB-INF/jsp/menu.jsp"/>
    <script>
        var track = ${track.id};
        var user = ${user.id};
    </script>
</head>
<body>
<c:set var="datepattern" value="dd.MM.yyyy HH:mm"/>
<div class="container">
    <div class="row">
        <div class="col-sm-8">
            <div class="panel panel-primary window">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code="tracksearch"/></h4>
                </div>
                <div class="panel-body">
                    <div class="progress" id="loading" style="display: none">
                        <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar"
                             aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                            <span class="sr-only">40% Complete (success)</span>
                        </div>
                    </div>
                    <form:form modelAttribute="searchForm" class="form-horizontal" id="newtrackform" action="/home"
                               method="post">
                        <div class="form-group">
                            <div class="col-sm-7">
                                <spring:message code="entertracknumber" var="entertracknumber"/>
                                <div class="input-group">
                                    <span class="input-group-addon"><span
                                            class="glyphicon glyphicon-search"></span> </span>
                                    <form:input path="trackNumber" class="form-control input-lg" name="newtrack"
                                                id="newtrack" placeholder="${entertracknumber}"/>
                                </div>
                            </div>
                            <div class="col-sm-5">
                                <sec:authorize access="isAuthenticated()">
                                    <c:set var="authenticated" value="authenticated"/>
                                </sec:authorize>
                                <c:choose>
                                    <c:when test="${searchForm.trackNumber!=null&&authenticated!=null}">
                                        <input class="btn btn-primary col-sm-5 btn-lg" type="submit"
                                               value="<spring:message code="find"/>">
                                        <a class="btn btn-primary col-sm-6 col-sm-offset-1 btn-lg" role="button"
                                           id="notifybutton" href="#"><spring:message code="notify"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <input class="btn btn-primary col-sm-12 btn-lg" type="submit"
                                               value="<spring:message code='find'/>">
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </form:form>
                    <div id="success" class="alert alert-success" role="alert" style="display: none"><spring:message
                            code="trackadded"/></div>
                    <div id="exists" class="alert alert-warning" role="alert" style="display: none"><spring:message
                            code="trackexists"/></div>
                    <div id="dberror" class="alert alert-danger" role="alert" style="display: none"><spring:message
                            code="dberror"/></div>
                    <c:if test="${param.trackerror!=null}">
                        <div class="alert alert-warning" role="alert"><spring:message code="parselnotfound"/></div>
                    </c:if>
                    <sec:authorize access="isAnonymous()">
                        <div class="alert alert-info" role="alert"><spring:message code="signintoreceiveemail"/></div>
                    </sec:authorize>
                    <c:if test="${track.messages!=null and track.messages.isEmpty()}">
                        <div class="alert alert-info" role="alert"><spring:message code="emptymessage"/></div>
                    </c:if>
                </div>
                <table class="table table-striped">
                    <c:forEach var="message" items="${track.messages}">
                        <tr>
                            <td class="col-sm-3"><fmt:formatDate value="${message.date}" pattern="${datepattern}"/></td>
                            <td class="col-sm-9">${message.text}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div class="col-sm-4">
            <sec:authorize access="isAnonymous()"><c:set var="signinpanel" value="panel-warning"/></sec:authorize>
            <sec:authorize access="isAuthenticated()"><c:set var="signinpanel" value="panel-success"/> </sec:authorize>
            <div class="panel ${signinpanel} autowindow">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code="account"/></h4>
                </div>
                <div class="panel-body">
                    <sec:authorize access="isAnonymous()">
                        <form class="form-horizontal" id="sign in" action="/j_spring_security_check" method="post">
                            <div class="form-group">
                                <label for="j_username" class="col-sm-4 control-label"><spring:message
                                        code="email"/> </label>
                                <div class="col-sm-8">
                                    <input name="j_username" type="text" class="form-control" id="j_username"
                                           placeholder="email">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="j_password" class="col-sm-4 control-label"><spring:message
                                        code="password"/> </label>
                                <div class="col-sm-8">
                                    <input name="j_password" type="password" class="form-control" id="j_password"
                                           placeholder="password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-6">
                                    <a class="btn btn-primary col-sm-12" href="/users/add"><spring:message
                                            code="registration"/></a>
                                </div>
                                <div class="col-sm-6 rightbutton">
                                    <spring:message code="login" var="login"/>
                                    <input class="btn btn-primary col-sm-12" type="submit" value="${login}">
                                </div>
                            </div>
                        </form>
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        <h4 align="center"><spring:message
                                code="hello"/>, ${user.name eq null||user.name eq '' ? user.login : user.name}</h4>
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
                            <spring:message code="logedout"/>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h4 class="panel-title"><spring:message code="yourtracks"/></h4>
                </div>
                <sec:authorize access="isAnonymous()">
                    <div class="panel-body">
                        <div class="alert alert-info" role="alert"><spring:message code="signintoviewhistory"/></div>
                    </div>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <table class="table table-striped table-hover historytable" id="historytable">
                        <tr>
                            <th class="col-sm-4"><spring:message code="added"/></th>
                            <th class="col-sm-8"><spring:message code="trackandname"/></th>
                        </tr>
                        <c:forEach var="track" items="${user.tracks}">
                            <tr href="#" data-value="${track.number}" style="cursor: pointer">
                                <td class="col-sm-4"><fmt:formatDate value="${track.dateCreated}"
                                                                     pattern="${datepattern}"/></td>
                                <td class="col-sm-8">${track.number}<br>${track.name}</td>
                            </tr>
                        </c:forEach>
                    </table>
                    <div class="panel-body">
                        <c:if test="${user.tracks.isEmpty()}">
                            <div class="alert alert-info" role="alert"><spring:message code="notracksadded"/></div>
                        </c:if>
                        <a class="btn btn-primary col-sm-7 col-sm-offset-5" href="/commingsoon"><spring:message
                                code="managetracks"/></a>
                    </div>
                </sec:authorize>
            </div>
        </div>
    </div>
</div>
</body>
</html>
