package com.semanientreprise.encryptedrealm;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Countries extends RealmObject {
    String Name;
    String Age;
}
