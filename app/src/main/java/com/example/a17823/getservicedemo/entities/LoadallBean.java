package com.example.a17823.getservicedemo.entities;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/5.
 */

public class LoadallBean {
    private List<LoadallBean> BOOK;
    private String B_ID;
    private String B_Name;
    private String B_Pic;
    private String B_Writer;
    private String B_Type;

    public List<LoadallBean> getBooklist() {
        return BOOK;
    }

    public void setBooklist(List<LoadallBean> booklist) {
        this.BOOK = booklist;
    }

    public String getB_ID() {
        return B_ID;
    }

    public void setB_ID(String b_ID) {
        B_ID = b_ID;
    }

    public String getB_Name() {
        return B_Name;
    }

    public void setB_Name(String b_Name) {
        B_Name = b_Name;
    }

    public String getB_Pic() {
        return B_Pic;
    }

    public void setB_Pic(String b_Pic) {
        B_Pic = b_Pic;
    }

    public String getB_Writer() {
        return B_Writer;
    }

    public void setB_Writer(String b_Writer) {
        B_Writer = b_Writer;
    }

    public String getB_Type() {
        return B_Type;
    }

    public void setB_Type(String b_Type) {
        B_Type = b_Type;
    }
}
