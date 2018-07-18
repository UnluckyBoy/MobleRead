package com.example.a17823.getservicedemo.entities;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/20.
 */

public class Book_File {
    private List<Book_File> FILE;
    private String id;
    private String name;
    private String file;

    public List<Book_File> getFILE() {
        return FILE;
    }

    public void setFILE(List<Book_File> FILE) {
        this.FILE = FILE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBook_name() {
        return name;
    }

    public void setBook_name(String book_name) {
        this.name = book_name;
    }

    public String getBook_file() {
        return file;
    }

    public void setBook_file(String book_file) {
        this.file = book_file;
    }
}
