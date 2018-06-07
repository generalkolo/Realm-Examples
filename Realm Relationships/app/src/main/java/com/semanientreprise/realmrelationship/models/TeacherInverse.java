package com.semanientreprise.realmrelationship.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class TeacherInverse extends RealmObject {
    public String name;
    public String course;
    public RealmList<InverseGroup> groups;
}
