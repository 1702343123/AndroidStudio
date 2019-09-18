package com.example.myapplication.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.entity.User;
import com.example.myapplication.utils.DBHelper;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private DBHelper helper;
    private SQLiteDatabase db;

    public UserDaoImpl(Context context){
        helper=DBHelper.getInstance(context);//创建数据库
    }

    @Override
    public List<User> select() {
        return null;
    }

    @Override
    public User select(String username) {
        String aql="select * from tb_user where user_name=?";
        User userInfo=null;
        db=helper.getReadableDatabase();
        Cursor cursor=db.query(DBHelper.TBL_NAME_USER,null,"user_name=?",new String[]{username},null,null,null);
//       Cursor cursor=db.rawQuery(sql,new String[]{username});
        if (cursor!=null&&cursor.moveToFirst()){
            userInfo=new User();
            userInfo.setUsername(cursor.getString(cursor.getColumnIndex("user_name")));
            userInfo.setNickname(cursor.getString(cursor.getColumnIndex("nick_name")));
            userInfo.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            userInfo.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
            cursor.close();
        }
        db.close();
        return userInfo;
    }

    @Override
    public void insert(User userInfo) {
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("user_name",userInfo.getUsername());
        values.put("nick_name",userInfo.getNickname());
        values.put("sex",userInfo.getSex());
        values.put("signature",userInfo.getSignature());
        db.insert(DBHelper.TBL_NAME_USER,null,values);

//        String sql="insert into "+DBHelper.TBL_NAME_USER+" values(null,?,?,?,?)";
//        db.execSQL(sql,new String[]{
//                userInfo.getUsername(),
//                userInfo.getNickname(),
//                userInfo.getSex(),
//                userInfo.getSignature()
//        });
        db.close();
    }

    @Override
    public void update(User userInfo) {
        db=helper.getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put("user_name",userInfo.getUsername());
        values.put("nick_name",userInfo.getNickname());
        values.put("sex",userInfo.getSex());
        values.put("signature",userInfo.getSignature());
        db.update(DBHelper.TBL_NAME_USER,values,"user_name=?",new String[]{userInfo.getUsername()});

//        String sql="insert into "+DBHelper.TBL_NAME_USER+" values(null,?,?,?,?)";
//        db.execSQL(sql,new String[]{
//                userInfo.getUsername(),
//                userInfo.getNickname(),
//                userInfo.getSex(),
//                userInfo.getSignature()
//        });
        db.close();
    }

    @Override
    public void delete(User userInfo) {
        db=helper.getReadableDatabase();
        db.delete(DBHelper.TBL_NAME_USER,"user_name=?",new String[]{userInfo.getUsername()});
        db.close();
    }
}
