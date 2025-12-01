package com.board.service;

import com.board.dao.BoardDao;
import com.board.dao.Pagination;
import com.board.dto.BoardDto;

import java.util.ArrayList;

public class BoardService {
    private final BoardDao dao = BoardDao.getInstance();

    private static final int listSize = 3;          // 게시글 리스트 한 화면에 보여줄 글의 개수
    private static final int paginationSize = 3;    // 한 화면에 보여줄 페이지 링크 개수

    public ArrayList<Pagination> getPagination(int pageNum) {
        ArrayList<Pagination> pgnList = new ArrayList<>();

        int numRecords = dao.getNumRecords();
        int numPages = (int) Math.ceil((double) numRecords / listSize);

        int firstLink = ((pageNum - 1) / listSize) * listSize + 1;
        int lastLink = Math.min(firstLink + paginationSize - 1, numPages);

        if (firstLink > 1) {
            pgnList.add(new Pagination("<", firstLink - 1, false));
        }
        for (int i = firstLink; i <= lastLink; i++) {
            pgnList.add(new Pagination("" + i, i, i == pageNum));
        }
        if (lastLink < numPages) {
            pgnList.add(new Pagination(">", lastLink + 1, false));
        }


        return pgnList;
    }

    public ArrayList<BoardDto> getMsgList(int pageNum) {
        return dao.selectList((pageNum - 1) * listSize, listSize);
    }

    public BoardDto getMsg(int num) {
        BoardDto dto = dao.selectOne(num, true);

        dto.setTitle(dto.getTitle().replace(" ", "&nbsp;"));
        dto.setContent(dto.getContent().replace(" ", "&nbsp;").replace("\n", "<br>"));
        return dto;
    }

    public BoardDto getMsgForWrite(int num) {
        return dao.selectOne(num, false);
    }

    public void writeMsg(String writer, String title, String content) throws Exception {
        if (writer != null && !writer.isEmpty() &&
                title != null && !title.isEmpty() &&
                content != null && !content.isEmpty()) {

            BoardDto dto = new BoardDto();
            dto.setWriter(writer);
            dto.setTitle(title);
            dto.setContent(content);
            dao.insertOne(dto);
        } else {
            throw new Exception("모든 항목이 빈칸 없이 입력되어야 합니다.");
        }
    }

    public void updateMsg(int num, String writer, String title, String content) throws Exception {
        if (writer != null && !writer.isEmpty() &&
                title != null && !title.isEmpty() &&
                content != null && !content.isEmpty()) {

            BoardDto dto = new BoardDto();
            dto.setNum(num);
            dto.setWriter(writer);
            dto.setTitle(title);
            dto.setContent(content);
            dao.updateOne(dto);
        } else {
            throw new Exception("모든 항목이 빈칸 없이 입력되어야 합니다.");
        }
    }

    public void deleteMsg(int num) {
        dao.deleteOne(num);
    }
}
