package com.semanientreprise.realmiterationandsnapshot.models;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person extends RealmObject {
    private String name;
    private int age;
    private Boolean married;
}
