package com.semanientreprise.migrationinrealm.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.semanientreprise.migrationinrealm.Models.Person;
import com.semanientreprise.migrationinrealm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.result)
    TextView resultTv;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);

        RealmConfiguration customConfig = new RealmConfiguration.Builder()
                .schemaVersion(2)
                .migration(new NewMigration())
                .build();

        realm = Realm.getInstance(customConfig);

        createRealmObjects();
    }

    private void createRealmObjects() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person Paul = realm.createObject(Person.class);
                Paul.setName("Paul");
                Paul.setAge(27);
                Paul.setBirthDay("May 05");

                Person Bola = realm.createObject(Person.class);
                Bola.setAge(31);
                Bola.setName("Bola");
                Bola.setBirthDay("June 06");
            }
        });

        StringBuilder toDisplay = new StringBuilder();
        RealmResults<Person> results = realm.where(Person.class).findAll();
        for (Person result : results) {
            toDisplay.append("Name :"+result.getName() + " Age :" + result.getAge()+" birthDay : "+result.getBirthDay()+" \n \n");
        }
        resultTv.setText(toDisplay);
    }

    private class NewMigration implements RealmMigration {
        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            RealmSchema schema = realm.getSchema();

            if (oldVersion == 1) {
                schema.get("Person")
                        .addField("birthDay", String.class);
                oldVersion++;
            }
        }
    }
}