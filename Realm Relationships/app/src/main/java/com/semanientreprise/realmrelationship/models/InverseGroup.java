package com.semanientreprise.realmrelationship.models;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;

public class InverseGroup extends RealmObject {
    public String name;
    public int number_of_students;
    @LinkingObjects("groups")
    public final RealmResults<TeacherInverse> teachers = null;
}
