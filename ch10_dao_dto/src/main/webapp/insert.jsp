<%@ page import="java.sql.*" %>
<%@ page import="com.board.dao.BoardDao" %>
<%@ page import="com.board.dto.BoardDto" %><%--
  Created by IntelliJ IDEA.
  User: 412-16
  Date: 2025-10-13
  Time: 오후 3:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    String writer = request.getParameter("writer");
    String title = request.getParameter("title");
    String content = request.getParameter("content");

    try {
        Class.forName("org.mariadb.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }

    if (writer != null && !writer.isEmpty() &&
            title != null && !title.isEmpty() &&
            content != null && !content.isEmpty()) {

        BoardDto dto = new BoardDto();
        dto.setWriter(writer);
        dto.setTitle(title);
        dto.setContent(content);
        BoardDao.getInstance().insertOne(dto);

        response.sendRedirect("list.jsp");
        return;
    }
%>

<!-- 에러 출력 -->
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>error</title>
</head>
<body>
    <script>
        alert('모든 항목이 빈칸 없이 입력되어야 합니다.');
        history.back();
    </script>
</body>
</html>
</body>
</html>
