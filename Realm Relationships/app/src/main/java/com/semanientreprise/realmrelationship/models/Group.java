package com.semanientreprise.realmrelationship.models;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public class Group extends RealmObject {
    public String name;
    public int number_of_students;
}