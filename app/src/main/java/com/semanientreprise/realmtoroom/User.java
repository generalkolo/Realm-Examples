package com.semanientreprise.realmtoroom;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int  id;

    private String name;
    private String email;
    private String address;
}
