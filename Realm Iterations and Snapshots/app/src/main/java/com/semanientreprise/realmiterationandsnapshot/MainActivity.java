package com.semanientreprise.realmiterationandsnapshot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.semanientreprise.realmiterationandsnapshot.models.Person;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollectionSnapshot;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.result)
    TextView result;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        createRealmObjects();

        iterationAndSnapshot();
    }

    private void iterationAndSnapshot() {
        // Use an iterator to get singles to married
        RealmResults<Person> singles = realm.where(Person.class).lessThanOrEqualTo("age", 20).findAll();

        realm.beginTransaction();
        for (Person single : singles) {
            single.setMarried(true);
        }
        realm.commitTransaction();

        // Use a snapshot to set Married to single
        RealmResults<Person> married = realm.where(Person.class).greaterThan("age", 20).findAll();

        realm.beginTransaction();
        OrderedRealmCollectionSnapshot<Person> marriedSnapShot = married.createSnapshot();
        for (int j = 0; j < marriedSnapShot.size(); j++) {
            marriedSnapShot.get(j).setMarried(false);
        }
        realm.commitTransaction();


        RealmResults<Person> everyBody = realm.where(Person.class).findAll();

        StringBuilder persons = new StringBuilder();
        for(Person person : everyBody){
            persons.append("Name "+person.getName()+" age : "+person.getAge()+" is Married ? "+person.getMarried()+"\n\n");
        }
        result.setText(persons.toString());
    }

    private void createRealmObjects() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person Eben = realm.createObject(Person.class);
                Eben.setName("Edet Ebenezer");
                Eben.setAge(17);
                Eben.setMarried(false);

                Person Joe = realm.createObject(Person.class);
                Joe.setName("Joseph Ikenna");
                Joe.setAge(15);
                Joe.setMarried(false);

                Person Yomi = realm.createObject(Person.class);
                Yomi.setName("Yomi Banks");
                Yomi.setAge(20);
                Yomi.setMarried(false);

                Person Lizzy = realm.createObject(Person.class);
                Lizzy.setName("Elizabeth Ignore");
                Lizzy.setAge(37);
                Lizzy.setMarried(true);

                Person Mercy = realm.createObject(Person.class);
                Mercy.setName("Mercy Ruth");
                Mercy.setAge(22);
                Mercy.setMarried(true);

                Person Shola = realm.createObject(Person.class);
                Shola.setName("Shola Shittu");
                Shola.setAge(27);
                Shola.setMarried(false);
            }
        });
    }
}
