<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>

<%
    if (session.getAttribute("userId") != null) {          // 로그인 상태일 때의 출력
%>
        <form action="logout.jsp" method="post"> <!--8-3.jsp:로그아웃 처리-->
            <%=session.getAttribute("userName")%>님 로그인
            <input type="submit" value="로그아웃">
        </form>
<%
    } else {                       // 로그인되지 않은 상태일 때의 출력
%>
        <form action="login.jsp" method="post"> <!--8-2.jsp:로그인 처리-->
            아이디:   <input type="text"     name="id">&nbsp;&nbsp;
            비밀번호: <input type="password" name="pw">
            <input type="submit" value="로그인">
            <input type="button" value="회원가입" onclick="window.open('member_join_form.jsp', '', 'width=400,height=200')">
        </form>
<%
    }
%>

</body>
</html>
