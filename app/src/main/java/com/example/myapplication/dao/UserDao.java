package com.example.myapplication.dao;

import com.example.myapplication.entity.User;

import java.util.List;

//dao的作用：完成对一张表的增删改查的原子性操作
public interface UserDao {
    List<User> select();
    User select(String username);
    void insert(User userInfo);
    void update(User userInfo);
    void delete(User userInfo);
}
