package com.semanientreprise.migrationinrealm.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.semanientreprise.migrationinrealm.Models.Person;
import com.semanientreprise.migrationinrealm.R;

import java.util.Date;

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
                .name("aggregation.realm")
                .schemaVersion(2)
                .migration(new NewMigration())
                .build();

        Realm.deleteRealm(customConfig);

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
                Paul.setDateOfBirth(new Date("May 05, 2006"));

                Person Bola = realm.createObject(Person.class);
                Bola.setAge(31);
                Bola.setName("Bola");
                Bola.setDateOfBirth(new Date("June 06, 2001"));

                Person Sam = realm.createObject(Person.class);
                Sam.setAge(15);
                Sam.setName("Samuel");
                Sam.setDateOfBirth(new Date("Feb 02, 2010"));

                Person Blee = realm.createObject(Person.class);
                Blee.setAge(12);
                Blee.setName("Blessing");
                Blee.setDateOfBirth(new Date("Jan 01, 2008"));

                Person Isi = realm.createObject(Person.class);
                Isi.setAge(19);
                Isi.setName("Israel");
                Isi.setDateOfBirth(new Date("Dec 12, 2012"));

                Person Samanta = realm.createObject(Person.class);
                Samanta.setAge(52);
                Samanta.setName("Samanta");
                Samanta.setDateOfBirth(new Date("Nov 11, 2011"));
            }
        });

        StringBuilder toDisplay = new StringBuilder();
        RealmResults<Person> results = realm.where(Person.class).findAll();
        /*for (Person result : results) {
            toDisplay.append("Name :"+result.getName() + " Age :" + result.getAge()+" birthDay : "+result.getBirthDay()+" \n \n");
        }*/

        int sum = results.sum("age").intValue();
        int min = results.min("age").intValue();
        int max = results.max("age").intValue();
        double average = results.average("age");

        Date maxDate = results.maxDate("dateOfBirth");
        Date minDate = results.minDate("dateOfBirth");

        Person youngest = results.where().equalTo("age",min).findFirst();

        Person oldest = results.where().equalTo("age",max).findFirst();

        Person firstCelebrator = results.where().equalTo("dateOfBirth",minDate).findFirst();

        Person lastCelebrator = results.where().equalTo("dateOfBirth",maxDate).findFirst();

        toDisplay.append("Statistics of Objects in our Database --- \n\n");

        toDisplay.append("The youngest Person is "+youngest.getName()+" with age : "+youngest.getAge()+" and DOB of "+youngest.getDateOfBirth()+"\n\n");

        toDisplay.append("The oldest Person is "+oldest.getName()+" with age : "+oldest.getAge()+" and DOB of "+oldest.getDateOfBirth()+"\n\n");

        toDisplay.append("The person with the earliest Date of Birth "+firstCelebrator.getName()+" with age : "+firstCelebrator.getAge()+" and DOB of "+firstCelebrator.getDateOfBirth()+"\n\n");

        toDisplay.append("The person with the lastest Date of Birth "+lastCelebrator.getName()+" with age : "+lastCelebrator.getAge()+" and DOB of "+lastCelebrator.getDateOfBirth()+"\n\n");

        toDisplay.append("The sum of all age's is "+sum+"\n\n");

        toDisplay.append("The average of all age's is "+average+"\n\n");

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