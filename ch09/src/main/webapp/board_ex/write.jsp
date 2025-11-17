<%@ page import="java.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%
    String numStr = request.getParameter("num");
    int num = numStr == null ? 0 : Integer.parseInt(numStr);

    String title = "";
    String writer = "";
    String content = "";
    String action = "insert.jsp";

    if (num > 0) {
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
                title = rs.getString("title");
                content = rs.getString("content");
                action = "update.jsp?num=" + num;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
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
<iframe style="width:100%;height:60px;border:0;"
        src="../member/login_main.jsp"></iframe>

<form method="post" action="<%=action%>">
    <table>
        <tr>
            <th>제목</th>
            <td><input type="text" name="title" maxlength="80"
                       value="<%=title%>">
            </td>
        </tr>
        <tr>
            <th>작성자</th>
            <td><input type="text" name="writer" maxlength="20"
                       value="<%=writer%>">
            </td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea name="content" rows="10"><%=content%></textarea>
            </td>
        </tr>
    </table>

    <br>
    <input type="submit" value="저장">
    <input type="button" value="취소" onclick="history.back()">
</form>

</body>
</html>
