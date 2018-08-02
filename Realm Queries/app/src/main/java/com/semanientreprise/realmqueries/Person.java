package com.semanientreprise.realmqueries;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends RealmObject {

    String name;
    String address;
    String email;
    int phone_number;
    int age;
}