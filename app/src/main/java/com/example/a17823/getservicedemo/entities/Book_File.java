package com.example.a17823.getservicedemo.entities;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/20.
 */

public class Book_File {
    private List<Book_File> FILE;
    private String book_Id;
    private String book_name;
    private String book_file;

    public List<Book_File> getFILE() {
        return FILE;
    }

    public void setFILE(List<Book_File> FILE) {
        this.FILE = FILE;
    }

    public String getId() {
        return book_Id;
    }

    public void setId(String id) {
        this.book_Id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_file() {
        return book_file;
    }

    public void setBook_file(String book_file) {
        this.book_file = book_file;
    }
}
