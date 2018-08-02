package com.semanientreprise.realmtoroom;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends RealmObject{
    private String name;
    private String address;
    private String email;
}
