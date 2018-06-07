package com.semanientreprise.realmrelationship.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class TeacherMany extends RealmObject {
    public String name;
    public String course;
    public RealmList<Group> groups;
}