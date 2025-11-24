package com.board.service;

import com.board.dao.BoardDao;
import com.board.dto.BoardDto;

import java.util.ArrayList;

public class BoardService {
    public ArrayList<BoardDto> getMsgList() {
        return BoardDao.getInstance().selectList();
    }

    public BoardDto getMsg(int num) {
        BoardDto dto = BoardDao.getInstance().selectOne(num, true);

        dto.setTitle(dto.getTitle().replace(" ", "&nbsp;"));
        dto.setContent(dto.getContent().replace(" ", "&nbsp;").replace("\n", "<br>"));
        return dto;
    }

    public BoardDto getMsgForWrite(int num) {
        return BoardDao.getInstance().selectOne(num, false);
    }

    public void writeMsg(String writer, String title, String content) throws Exception {
        if (writer != null && !writer.isEmpty() &&
                title != null && !title.isEmpty() &&
                content != null && !content.isEmpty()) {

            BoardDto dto = new BoardDto();
            dto.setWriter(writer);
            dto.setTitle(title);
            dto.setContent(content);
            BoardDao.getInstance().insertOne(dto);
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
            BoardDao.getInstance().updateOne(dto);
        } else {
            throw new Exception("모든 항목이 빈칸 없이 입력되어야 합니다.");
        }
    }

    public void deleteMsg(int num) {
        BoardDao.getInstance().deleteOne(num);
    }
}
