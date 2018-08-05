package com.semanientreprise.encryptedrealm;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Countries extends RealmObject {
    private String Name;
    private String Age;
}
