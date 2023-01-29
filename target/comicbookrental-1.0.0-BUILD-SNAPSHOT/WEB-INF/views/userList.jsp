<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page session="true"%>
<c:set var="loginId" value="${sessionScope.id}"/>
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
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <style>
    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
      font-family: "Noto Sans KR", sans-serif;
    }

    a {
      text-decoration: none;
      color: black;
    }
    button,
    input {
      border: none;
      outline: none;
    }

    .user-container {
      width: 60%;
      height: 1200px;
      margin: 0 auto;
      /* border: 1px solid black; */
    }
    .search-container {
      background-color: rgb(253, 253, 250);
      width: 100%;
      height: 110px;
      border: 1px solid #ddd;
      margin-top : 10px;
      margin-bottom: 30px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .search-form {
      height: 37px;
      display: flex;
    }
    .search-option {
      width: 100px;
      height: 100%;
      outline: none;
      margin-right: 5px;
      border: 1px solid #ccc;
      color: gray;
    }

    .search-option > option {
      text-align: center;
    }

    .search-input {
      color: gray;
      background-color: white;
      border: 1px solid #ccc;
      height: 100%;
      width: 300px;
      font-size: 15px;
      padding: 5px 7px;
    }
    .search-input::placeholder {
      color: gray;
    }

    .search-button {
      /* 메뉴바의 검색 버튼 아이콘  */
      width: 20%;
      height: 100%;
      background-color: rgb(22, 22, 22);
      color: rgb(209, 209, 209);
      display: flex;
      align-items: center;
      justify-content: center;
      font-size: 15px;
    }
    .search-button:hover {
      color: rgb(165, 165, 165);
    }

    table {
      border-collapse: collapse;
      width: 100%;
      border-top: 2px solid rgb(39, 39, 39);
    }

    tr:nth-child(even) {
      background-color: #f0f0f070;
    }

    th,
    td {
      width:300px;
      text-align: center;
      padding: 10px 12px;
      border-bottom: 1px solid #ddd;
    }

    td {
      color: rgb(53, 53, 53);
    }

    .no      { width:150px;}
    .title   { width:50%;  }

    td.title   { text-align: center;  }
    td.writer  { text-align: center;  }
    td.publisher  { text-align: center;  }
    /*td.release  { text-align: left;  }*/
    td.viewcnt { text-align: center; }

    td.title:hover {
      text-decoration: underline;
    }

    .paging {
      color: black;
      width: 100%;
      align-items: center;
    }

    .page {
      color: black;
      padding: 6px;
      margin-right: 10px;
    }
    .paging-active {
      background-color: rgb(216, 216, 216);
      border-radius: 5px;
      color: rgb(24, 24, 24);
    }

    .paging-container {
      width:100%;
      height: 70px;
      display: flex;
      margin-top: 50px;
      margin : auto;
    }
    .btn-write {
      background-color: rgb(236, 236, 236); /* Blue background */
      border: none; /* Remove borders */
      color: black; /* White text */
      padding: 6px 12px; /* Some padding */
      font-size: 16px; /* Set a font size */
      cursor: pointer; /* Mouse pointer on hover */
      border-radius: 5px;
      margin-left: 30px;
    }

    .btn-write:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div id="menu">
  <ul>
    <li id="logo">만화책 볼래?</li>
    <li><a href="<c:url value='/'/>">Home</a></li>
    <li><a href="<c:url value='/user/list'/>">Comic</a></li>
    <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
    <c:if test="${loginId==''}">
      <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </c:if>
    <c:if test="${isAdmin=='true'}">
    <li><a href="<c:url value='/admin/list'/>">Admin</a></li>
    </c:if>
  </ul>
</div>
<script>
  let msg = "${msg}";
  if(msg=="LIST_ERR")  alert("사용자 목록을 가져오는데 실패했습니다. 다시 시도해 주세요.");
  if(msg=="READ_ERR")  alert("삭제되었거나 없는 사용자입니다.");
  if(msg=="DEL_ERR")   alert("삭제되었거나 없는 사용자입니다.");

  if(msg=="DEL_OK")    alert("성공적으로 삭제되었습니다.");
  if(msg=="WRT_OK")    alert("성공적으로 등록되었습니다.");
  if(msg=="MOD_OK")    alert("성공적으로 수정되었습니다.");
</script>
<div style="text-align:center">
  <div class="user-container">
    <div class="search-container">
      <form action="<c:url value="/admin/list"/>" class="search-form" method="get">
        <select class="search-option" name="option">
          <option value="A" ${ph.sc.option=='A' || ph.sc.option=='' ? "selected" : ""}>이름+아이디</option>
          <option value="T" ${ph.sc.option=='N' ? "selected" : ""}>이름</option>
          <option value="W" ${ph.sc.option=='id' ? "selected" : ""}>아이디</option>
        </select>

        <input type="text" name="keyword" class="search-input" type="text" value="${ph.sc.keyword}" placeholder="검색어를 입력해주세요">
        <input type="submit" class="search-button" value="검색">
      </form>

    </div>

    <table>
      <tr>
        <th class="uno">고객번호</th>
        <th class="id">아이디</th>
        <th class="name">이름</th>
<%--        <th class="publisher">출판사</th>--%>
<%--        <th class="release">출간일</th>--%>
<%--        <th class="regdate">등록일</th>--%>
<%--        <th class="viewcnt">조회수</th>--%>
<%--        <th class="">대여하기</th>--%>
      </tr>
      <c:forEach var="userDto" items="${list}">
        <tr>
          <td class="uno">${userDto.uno}</td>
          <td class="id"><a href="<c:url value="/admin/read${ph.sc.queryString}&id=${userDto.id}"/>">${userDto.id}</a></td>
          <td class="name">${userDto.name}</td>
<%--          <td class="publisher">${userDto.publisher}</td>--%>
<%--          <td class="viewcnt">${userDto.view_cnt}</td>--%>
        </tr>
      </c:forEach>
    </table>
    <br>
    <div class="paging-container">
      <div class="paging">
        <c:if test="${totalCnt==null || totalCnt==0}">
          <div> 사용자가 없습니다. </div>
        </c:if>
        <c:if test="${totalCnt!=null && totalCnt!=0}">
          <c:if test="${ph.showPrev}">
            <a class="page" href="<c:url value="/admin/list${ph.sc.getQueryString(ph.beginPage-1)}"/>">&lt;</a>
          </c:if>
          <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
            <a class="page ${i==ph.sc.page? "paging-active" : ""}" href="<c:url value="/admin/list${ph.sc.getQueryString(i)}"/>">${i}</a>
          </c:forEach>
          <c:if test="${ph.showNext}">
            <a class="page" href="<c:url value="/admin/list${ph.sc.getQueryString(ph.endPage+1)}"/>">&gt;</a>
          </c:if>
        </c:if>
      </div>
    </div>
  </div>
</div>
</body>
</html>
