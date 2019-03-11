package com.example.a17823.getservicedemo.entities;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/8.
 */

public class LoadViewBean {
    private String id;
    private String name;
    private String pic;
    private String writer;
    private String type;
    private String hot;
    private List<LoadViewBean> Book;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getHot() {
        return hot;
    }

    public String getPic() {
        return pic;
    }

    public String getType() {
        return type;
    }

    public String getWriter() {
        return writer;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public List<LoadViewBean> getBook() {
        return Book;
    }

    public void setBook(List<LoadViewBean> book) {
        this.Book = book;
    }
}
