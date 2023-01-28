<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%--<c:set var="loginId" value="${pageContext.request.getSession(false)==null ? '' : pageContext.request.session.getAttribute('id')}"/>--%>
<c:set var="loginId" value="${sessionScope.id==null ? '' : sessionScope.id}"/>
<c:set var="isAdmin" value="${sessionScope.isAdmin}"/>
<c:set var="loginOutLink" value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<%--<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'ID='+=loginId}"/>--%>
<c:set var="loginOut" value="${loginId=='' ? 'Login' : 'LogOut'}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>만화책 볼래?</title>
  <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
</head>
<body>
<div id="menu">
  <ul>
    <li id="logo">만화책 볼래?</li>
    <li><a href="<c:url value='/'/>">Home</a></li>
    <li><a href="<c:url value='/comicbook/list'/>">Comic</a></li>
<%--    <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>--%>
    <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
    <c:if test="${loginId==''}">
      <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </c:if>
  <c:if test="${isAdmin=='true'}">
      <li><a href="<c:url value='/admin'/>">Admin</a></li>
  </c:if>
    <%--    <li><a href=""><i class="fas fa-search small"></i></a></li>--%>
  </ul>
</div>
<div style="text-align:center">
    <h1>만화책 대여 시스템</h1>
    <h1>로그인한 회원의 경우 홈에서 대여한 만화책을 반납할 수 있어요</h1>
    <h1>Comic 메뉴를 눌러 만화책을 대여할 수 있어요</h1>
</div>
</body>
</html>