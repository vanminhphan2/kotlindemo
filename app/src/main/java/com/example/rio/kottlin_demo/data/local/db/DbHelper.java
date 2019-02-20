package com.example.rio.kottlin_demo.data.local.db;

import com.example.rio.kottlin_demo.data.model.User;
import io.reactivex.Flowable;
import io.reactivex.Observable;

import java.util.List;

public interface DbHelper {

    Observable<List<User>> getAllUsers();

    Flowable<User> insertUser(final User user);

    Observable<User> findUserByPhone(String phone);

    Observable<User> getInfoUserLogin(String id);

    Observable<User> loginByPhone(String phone,String pass);
}
