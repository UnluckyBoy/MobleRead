package com.example.a17823.getservicedemo.entities;

import java.util.List;

/**
 * Created by 清风明月 on 2018/6/8.
 */

public class LoadViewBean {
    private List<LoadViewBean> advertise;
    private String name;
    private String pic;
    private String head;

    public List<LoadViewBean> getViewBean() {
        return advertise;
    }

    public void setViewBean(List<LoadViewBean> viewBean) {
        this.advertise = viewBean;
    }
    public String getPic_url() {
        return pic;
    }

    public void setPic_url(String pic_url) {
        this.pic = pic;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    /*public static class AdvertiseView {
        private String name;
        private String pic;
        private String head;

        public String getPic_url() {
            return pic;
        }

        public void setPic_url(String pic_url) {
            this.pic = pic;
        }

        public String getTitle() {
            return name;
        }

        public void setTitle(String name) {
            this.name = name;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }
    }*/
}
