package com.semanientreprise.gridrealm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contestant extends RealmObject {
    @PrimaryKey
    private String Name;
    private String Image;
    private long Votes;

}
