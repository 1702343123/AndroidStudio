package com.example.myapplication.entity;

import java.io.Serializable;

public class AdImage implements Serializable {
    private int id;
    private  String desc;
    private String img;

    public AdImage() {
    }
    public AdImage(int id, String desc, String img) {
        this.id = id;
        this.desc = desc;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "AdImage{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
