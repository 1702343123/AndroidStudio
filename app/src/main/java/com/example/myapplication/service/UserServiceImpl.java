package com.example.myapplication.service;

import android.content.Context;

import com.example.myapplication.dao.UserDao;
import com.example.myapplication.dao.UserDaoImpl;
import com.example.myapplication.entity.User;

public class UserServiceImpl implements UserService {
    private UserDao dao;
    public  UserServiceImpl(Context context){
        dao=new UserDaoImpl(context);
    }
    @Override
    public User get(String username) {
        return dao.select(username);
    }

    @Override
    public void save(User userInfo) {
        dao.insert(userInfo);
    }

    @Override
    public void modify(User userIfo) {
        dao.update(userIfo);
    }
}
