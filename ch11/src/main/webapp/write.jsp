<%@ page import="java.sql.*" %>
<%@ page import="com.board.dto.BoardDto" %>
<%@ page import="com.board.dao.BoardDao" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%
    String numStr = request.getParameter("num");
    int num = numStr == null ? 0 : Integer.parseInt(numStr);

    BoardDto dto = new BoardDto();
    String action = "insert.jsp";

    if (num > 0) {
        dto = BoardDao.getInstance().selectOne(num, false);
        action = "update.jsp?num=" + num;
    }

    request.setAttribute("msg", dto);
    request.setAttribute("action", action);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        table {
            width: 680px;
            text-align: center;
        }

        th {
            width: 100px;
            background-color: cyan;
        }

        input[type=text], textarea {
            width: 100%;
        }
    </style>
</head>
<body>

<form method="post" action="${action}">
    <table>
        <tr>
            <th>제목</th>
            <td><input type="text" name="title" maxlength="80"
                       value="${msg.title}">
            </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input type="text" name="writer" maxlength="20"
                       value="${msg.writer}">
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea name="content" rows="10">${msg.content}</textarea>
            </td>
        </tr>
    </table>

    <br>
    <input type="submit" value="저장">
    <input type="button" value="취소" onclick="history.back()">
</form>

</body>
</html>
