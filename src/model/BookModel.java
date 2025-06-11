package model;

import http.BookHttp;
import vo.Book;

import java.util.ArrayList;
import java.util.List;

public class BookModel {
    private List<Book> books = new ArrayList<>();

    // 전체 목록 불러오기
    public void loadAllBooks() throws Exception {
        books = BookHttp.findAll();
    }

    // 검색 결과 불러오기
    public void searchBooks(String type, String keyword) throws Exception {
        books = BookHttp.searchBooks(type, keyword);
    }

    // 현재 리스트 반환
    public List<Book> getBooks() {
        return books;
    }

    // 결과 수 반환
    public int getCount() {
        return books.size();
    }
}
