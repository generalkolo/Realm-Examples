package com.semanientreprise.migrationinrealm.Models;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends RealmObject {
    private String name;
    private int age;
    private String birthDay;
}