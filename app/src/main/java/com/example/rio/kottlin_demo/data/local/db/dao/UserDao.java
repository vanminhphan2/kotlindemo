package com.example.rio.kottlin_demo.data.local.db.dao;

import android.arch.persistence.room.*;
import com.example.rio.kottlin_demo.data.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM users WHERE name LIKE :name LIMIT 1")
    User findByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<User> loadAllByIds(List<Integer> userIds);

    @Query("SELECT * FROM users WHERE phone IN (:phone)")
    User findUserByPhone(String phone);

    @Query("SELECT * FROM users WHERE id IN (:id)")
    User findUserById(String id);

    @Query("SELECT * FROM users WHERE phone IN (:phone) AND pass In (:pass)")
    User loginByPhone(String phone,String pass);

}
