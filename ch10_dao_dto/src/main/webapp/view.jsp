<%@ page import="java.sql.*" %>
<%@ page import="com.board.dao.BoardDao" %>
<%@ page import="com.board.dto.BoardDto" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    int num = Integer.parseInt(request.getParameter("num"));
    BoardDto dto = BoardDao.getInstance().selectOne(num, true);

    dto.setTitle(dto.getTitle().replace(" ", "&nbsp;"));
    dto.setContent(dto.getContent().replace(" ", "&nbsp;").replace("\n", "<br>"));
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        table { width:680px; text-align:center; }
        th    { width:100px; background-color:cyan; }
        td    { text-align:left; border:1px solid gray; }
    </style>
</head>
<body>

<table>
    <tr>
        <th>제목</th>
        <td><%=dto.getTitle()%></td>
    </tr>
    <tr>
        <th>작성자</th>
        <td><%=dto.getWriter()%></td>
    </tr>
    <tr>
        <th>작성일시</th>
        <td><%=dto.getRegtime()%></td>
    </tr>
    <tr>
        <th>조회수</th>
        <td><%=dto.getHits()%></td>
    </tr>
    <tr>
        <th>내용</th>
        <td><%=dto.getContent()%></td>
    </tr>
</table>

<br>
<input type="button" value="목록보기" onclick="location.href='list.jsp'">
<input type="button" value="수정"
       onclick="location.href='write.jsp?num=<%=num%>'">
<input type="button" value="삭제"
       onclick="location.href='delete.jsp?num=<%=num%>'">

</body>
</html>
