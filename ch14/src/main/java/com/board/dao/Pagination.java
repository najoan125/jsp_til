package com.board.dao;

public class Pagination {
    private String display;
    private int pageNum;
    private boolean currentPage;

    public Pagination(String display, int pageNum, boolean currentPage) {
        this.pageNum = pageNum;
        this.display = display;
        this.currentPage = currentPage;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public boolean isCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(boolean currentPage) {
        this.currentPage = currentPage;
    }
}
