package com.board.dao;

import com.board.dto.BoardDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class BoardDao {
    private static final BoardDao instance = new BoardDao();

    // singleton pattern
    public static BoardDao getInstance() {
        return instance;
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.mariadb.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/jspdb", "jsp", "1234");
    }

    public int getNumRecords() {
        int numRecords = 0;
        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select count(*) from board")
        ) {
            while (rs.next()) {
                numRecords = rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }

        return numRecords;
    }

    private String getCurrentTime() {
        return LocalDate.now() + " " + LocalTime.now().toString().substring(0, 8);
    }

    public ArrayList<BoardDto> selectList(int start, int listSize) {
        ArrayList<BoardDto> result = new ArrayList<>();

        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        String.format("select * from board order by num desc limit %d, %d", start, listSize))
        ) {
            while (rs.next()) {
                BoardDto dto = new BoardDto();
                dto.setNum(rs.getInt("num"));
                dto.setWriter(rs.getString("writer"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setRegtime(rs.getString("regtime"));
                dto.setHits(rs.getInt("hits"));

                result.add(dto);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }

        return result;
    }

    public void updateOne(BoardDto dto) {
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "update board set writer=?, title=?, content=?, regtime=? where num=?" // sql injection 방지
                )
        ) {
            ps.setString(1, dto.getWriter());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, getCurrentTime());
            ps.setInt(5, dto.getNum());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public void insertOne(BoardDto dto) {
        try (
                Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "insert into board (writer, title, content, regtime, hits) values (?, ?, ?, ?, 0)"
                )
        ) {
            ps.setString(1, dto.getWriter());
            ps.setString(2, dto.getTitle());
            ps.setString(3, dto.getContent());
            ps.setString(4, getCurrentTime());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    public BoardDto selectOne(int num, boolean incHits) {
        BoardDto dto = new BoardDto();

        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(
                        "select * from board where num=" + num);
        ) {
            if (rs.next()) {
                dto.setNum(rs.getInt("num"));
                dto.setWriter(rs.getString("writer"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setRegtime(rs.getString("regtime"));
                dto.setHits(rs.getInt("hits"));

                if (incHits)
                    stmt.executeUpdate("update board set hits=hits+1 where num=" + num);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }

        return dto;
    }

    public void deleteOne(int num) {

        try (
                Connection conn = getConnection();
                Statement stmt = conn.createStatement();
//                PreparedStatement stmt = conn.prepareStatement("delete from board where num=?"); // sql injection 방지
        ) {
//            stmt.setInt(1, num);
            stmt.executeUpdate("delete from board where num=" + num);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(System.out);
        }
    }
}
