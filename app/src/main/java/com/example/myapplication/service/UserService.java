package com.example.myapplication.service;

import com.example.myapplication.entity.User;

public interface UserService {
    User get(String username);
    void save(User userInfo);
    void modify(User userIfo);
}
