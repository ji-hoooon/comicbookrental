<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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

    .container {
      width : 50%;
      margin : auto;
    }

    .writing-header {
      position: relative;
      margin: 20px 0 0 0;
      padding-bottom: 10px;
      border-bottom: 1px solid #323232;
    }

    input {
      width: 100%;
      height: 35px;
      margin: 5px 0px 10px 0px;
      border: 1px solid #e9e8e8;
      padding: 8px;
      background: #f8f8f8;
      outline-color: #e6e6e6;
    }

    textarea {
      width: 100%;
      background: #f8f8f8;
      margin: 5px 0px 10px 0px;
      border: 1px solid #e9e8e8;
      resize: none;
      padding: 8px;
      outline-color: #e6e6e6;
    }

    .frm {
      width:100%;
    }
    .btn {
      background-color: rgb(236, 236, 236); /* Blue background */
      border: none; /* Remove borders */
      color: black; /* White text */
      padding: 6px 12px; /* Some padding */
      font-size: 16px; /* Set a font size */
      cursor: pointer; /* Mouse pointer on hover */
      border-radius: 5px;
    }

    .btn:hover {
      text-decoration: underline;
    }
  </style>
</head>
<body>
<div id="menu">
  <ul>
    <li id="logo">만화책 볼래?</li>
    <li><a href="<c:url value='/'/>">Home</a></li>
    <li><a href="<c:url value='/comicbook/list'/>">Comic</a></li>
    <li><a href="<c:url value='${loginOutLink}'/>">${loginOut}</a></li>
    <c:if test="${loginId==''}">
      <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
    </c:if>
    <c:if test="${isAdmin=='true'}">
      <li><a href="<c:url value='/admin/list'/>">Admin</a></li>
    </c:if>  </ul>
</div>
<script>
  let msg = "${msg}";
  if(msg=="WRT_ERR") alert("만화책 등록에 실패하였습니다. 다시 시도해 주세요.");
  if(msg=="MOD_ERR") alert("만화책 수정에 실패하였습니다. 다시 시도해 주세요.");
</script>
<div class="container">
  <h2 class="writing-header">만화책 ${mode=="new" ? "등록" : "정보"}</h2>
  <form id="form" class="frm" action="" method="post">
    <input type="hidden" name="cno" value="${comicbookDTO.cno}">

    <input name="title" type="text" value="<c:out value='${comicbookDTO.title}'></c:out>" placeholder="제목을 입력해 주세요."${mode=="new" ? "" : "readonly='readonly'"}><br>
    <input name="writer" type="text" value="<c:out value='${comicbookDTO.writer}'> </c:out>" placeholder=" 저자를 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>
    <input name="publisher" type="text" value="<c:out value='${comicbookDTO.publisher}'> </c:out>" placeholder=" 출판사를 입력해 주세요." ${mode=="new" ? "" : "readonly='readonly'"}><br>

    <c:if test="${mode eq 'new'}">
      <button type="button" id="writeBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 등록하기</button>
    </c:if>
    <c:if test="${mode ne 'new'}" >
      <c:if test="${isAdmin eq 'true'}">
      <button type="button" id="writeNewBtn" class="btn btn-write"><i class="fa fa-pencil"></i> 만화책 추가</button>
      <button type="button" id="modifyBtn" class="btn btn-modify" ><i class="fa fa-edit"></i> 수정</button>
      <button type="button" id="removeBtn" class="btn btn-remove"><i class="fa fa-trash"></i> 삭제</button>
      </c:if>
    </c:if>
    <button type="button" id="listBtn" class="btn btn-list"><i class="fa fa-bars"></i> 목록</button>
  </form>
</div>
<script>
  $(document).ready(function(){
    let formCheck = function() {
      let form = document.getElementById("form");
      if(form.title.value=="") {
        alert("제목을 입력해 주세요.");
        form.title.focus();
        return false;
      }

      if(form.writer.value=="") {
        alert("저자를 입력해 주세요.");
        form.writer.focus();
        return false;
      }

      if(form.publisher.value=="") {
        alert("출판사를 입력해 주세요.");
        form.publisher.focus();
        return false;
      }
      return true;
    }

    $("#writeNewBtn").on("click", function(){
      location.href="<c:url value='/comicbook/write'/>";
      $("#modifyBtn").css("display", "none");
      $("#removeBtn").css("display", "none");
    });

    $("#writeBtn").on("click", function(){
      let form = $("#form");
      $("#modifyBtn").css("display", "none");
      $("#removeBtn").css("display", "none");
      form.attr("action", "<c:url value='/comicbook/write'/>");
      form.attr("method", "post");

      if(formCheck())
        form.submit();
    });

    $("#modifyBtn").on("click", function(){
      let form = $("#form");
      $("#writeNewBtn").css("display", "none");
      let isReadonly = $("input[name=title]").attr('readonly');

      // 1. 읽기 상태이면, 수정 상태로 변경
      if(isReadonly=='readonly') {
        $(".writing-header").html("만화책 수정");
        $("input[name=title]").attr('readonly', false);
        $("input[name=writer]").attr('readonly', false);
        $("input[name=publisher]").attr('readonly', false);
        $("#modifyBtn").html("<i class='fa fa-pencil'></i> 수정완료");
        return;
      }

      // 2. 수정 상태이면, 수정된 내용을 서버로 전송
      form.attr("action", "<c:url value='/comicbook/modify${searchCondition.queryString}'/>");
      form.attr("method", "post");
      if(formCheck())
        form.submit();
    });

    $("#removeBtn").on("click", function(){
      if(!confirm("정말로 삭제하시겠습니까?")) return;

      let form = $("#form");
      form.attr("action", "<c:url value='/comicbook/remove${searchCondition.queryString}'/>");
      form.attr("method", "post");
      form.submit();
    });

    $("#listBtn").on("click", function(){
      location.href="<c:url value='/comicbook/list${searchCondition.queryString}'/>";
    });
  });
</script>
</body>
</html>
