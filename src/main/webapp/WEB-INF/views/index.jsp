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

    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
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
      <li><a href="<c:url value='/admin/list'/>">Admin</a></li>
  </c:if>
    <%--    <li><a href=""><i class="fas fa-search small"></i></a></li>--%>
  </ul>
</div>
<div style="text-align:center">
    <h1>만화책 대여 시스템</h1>


    <h1>로그인한 회원의 경우 홈에서 대여한 만화책을 반납할 수 있어요</h1>
    <h1>Comic 메뉴를 눌러 만화책을 대여할 수 있어요</h1>
</div>
<div class="rentalList"></div>
<div id="rentalList"></div>
</body>
<script>
    //# : id
    //. : class


    //문자열을 HTML로 변환해 리스트 보여주는 함수
    //: ajax를 이용해 비동기로 처리하는 함수 작성
    let id="${loginId}";
    let showList = function (id){
        $.ajax({
            type: 'GET',
            //REST Controller는 쿼리 스트링은 자동으로 추가한다.
            //하지만, ajax에서 전달하기 위해서는 직접 추가해야 한다.
            url:'/comicbookrental/rentals?id='+id,
            headers:{"content-type": "application/json"},
            //data: "json" //json 사용시 생략 가능
            //성공하면 json문자열인 result를 문자열을 html로 만드는 함수를 이용해 html로 만들고, 해당 html이 id가 rentalList인 곳에 넣는다.
            success:function (result){
                // alert(result);
                //리턴받은 List<CommentDto>를 jacson-databind가 json으로 변환해주고 json 문자열인 result에 저장
                //: json 그대로 html로 변환해 출력
                $(".rentalList").html(toHtml(result));
                // alert("received="+toHtml(result));       // result는 서버가 전송한 데이터
            },
            //실패하면 error 메시지 출력
            error: function (){alert("error")}
        });

    }
    //문자열을 HTML로 만드는 함수
    //: List<rentalDto>를 rentals로 받는다.
    let toHtml=function (rentals){
        //: <ul>태그 시작하는 변수 만들어 해당 변수에 forEach문으로 문자열들을 HTML로 추가
        let tmp="<ul>";

        rentals.forEach(function (rental) {
            tmp += '<li data-rno=' + rental.rno
            tmp += ' data-cno=' + rental.cno
            tmp += ' data-duedate=' + rental.due_date + '>'
            //각각의 댓글들에 대해서 수정 삭제가 가능하도록 버튼 추가
            //rentaler와 rental는 나중에 참조하기 쉽도록 span태그 클래스로 감싼다.
            tmp += ' 대여 번호=' + rental.rno
            tmp += '<button class="delBtn">반납하기</button>'
            tmp += '</li>'
        })
        return tmp+"</ul>";
    }

    //페이지 로딩시 실행되는 함수들
    //1. 작성 버튼 함수 작성
    //비동기로 동작하는 ajax 함수 작성
    // 자바 객체이므로 stringify메서드로 json으로 변환해 bno와 커멘트 내용 전달
    //성공하면, "작성 완료" 메시지 출력하고, 추가된 댓글 목록 갱신
    //실패하면, error 메시지 출력
    //:목록 출력
    $(document).ready(function (){
        showList(id);

        //2. 동적으로 생성된 요소인 삭제 버튼 함수 작성
        $(".rentalList").on("click", ".delBtn", function () {
            //: li에 존재하는 cno을 가져오고, 사용자 정의 속성으로 만들어진 'data-'로 시작하는 cno와 bno를 변수로 저장
            let cno=$(this).parent().attr("data-cno");
            let rno=$(this).parent().attr("data-rno");

            //비동기로 동작하는 ajax 함수 작성
            $.ajax({
                type:'DELETE',
                //cno은 구해서 넣고, 쿼리 스트링은 직접 만들기
                url:'/comicbookrental/rentals/'+rno+'?id='+id,
                headers:{"content-type":"application/json"},
                // dataType:'json', //생략하면 자동 결정
                //성공하면, "삭제완료" 알림 전송하고, 삭제가 반영된 목록을 보여주도록 목록 갱신
                success: function (result){
                    alert(result)
                    showList(id)
                },
                //실패하면, error 메시지 출력
                error: function (){
                    alert("error")
                }
            });
        });
    });
</script>
</html>