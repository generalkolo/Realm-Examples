package com.semanientreprise.realmtoroom;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    public void addUser(User user);

    @Query("Select * from users")
    public List<User> getAllUsers();
}
