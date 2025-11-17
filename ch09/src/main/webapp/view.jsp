<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%
    int num = Integer.parseInt(request.getParameter("num"));
    String writer = "";
    String title = "";
    String content = "";
    String regtime = "";
    int hits = 0;

    try {
        Class.forName("org.mariadb.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }

    try (
            Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/jspdb", "jsp", "1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "select * from board where num=" + num);
    ) {
        if (rs.next()) {
            writer = rs.getString("writer");
            title = rs.getString("title").replace(" ", "&nbsp");
            content = rs.getString("content").replace(" ", "&nbsp").replace("\n", "<br>");
            regtime = rs.getString("regtime");
            hits = rs.getInt("hits");

            stmt.executeUpdate("update board set hits=hits+1 where num=" + num);
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
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
        <td><%=title%></td>
    </tr>
    <tr>
        <th>작성자</th>
        <td><%=writer%></td>
    </tr>
    <tr>
        <th>작성일시</th>
        <td><%=regtime%></td>
    </tr>
    <tr>
        <th>조회수</th>
        <td><%=hits%></td>
    </tr>
    <tr>
        <th>내용</th>
        <td><%=content%></td>
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
