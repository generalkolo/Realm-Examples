package com.semanientreprise.realmqueries;


import io.realm.RealmList;
import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonsWithPet extends RealmObject{
    String name;
    int phone_number;
    int age;
    public RealmList<Cat> cats;
}
