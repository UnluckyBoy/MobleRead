package com.example.a17823.getservicedemo.entities;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/5.
 */

public class LoadallBean {
    private List<LoadallBean> BOOK;
    private String id;
    private String name;
    private String pic;
    private String writer;
    private String type;
    private String hot;

    public List<LoadallBean> getBooklist() {
        return BOOK;
    }

    public void setBooklist(List<LoadallBean> booklist) {
        this.BOOK = booklist;
    }

    public String getB_ID() {
        return id;
    }

    public void setB_ID(String b_ID) {
        id = b_ID;
    }

    public String getB_Name() {
        return name;
    }

    public void setB_Name(String b_Name) {
        name = b_Name;
    }

    public String getB_Pic() {
        return pic;
    }

    public void setB_Pic(String b_Pic) {
        pic = b_Pic;
    }

    public String getB_Writer() {
        return writer;
    }

    public void setB_Writer(String b_Writer) {
        writer = b_Writer;
    }

    public String getB_Type() {
        return type;
    }

    public void setB_Type(String b_Type) {
        type = b_Type;
    }

    public void setB_Hot(String b_Hot) {
        hot = b_Hot;
    }

    public String getB_Hot() {
        return hot;
    }
}
