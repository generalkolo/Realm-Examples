package com.semanientreprise.realmqueries;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cat extends RealmObject {
    public int age;
    public String name;
    public String color;
    @LinkingObjects("cats")
    public final RealmResults<PersonsWithPet> owners = null;
}