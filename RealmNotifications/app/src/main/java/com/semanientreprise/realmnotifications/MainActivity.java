package com.semanientreprise.realmnotifications;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.ObjectChangeSet;
import io.realm.Realm;
import io.realm.RealmObjectChangeListener;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.search_name)
    EditText searchName;
    @BindView(R.id.person_name)
    EditText personName;
    @BindView(R.id.person_age)
    EditText personAge;
    @BindView(R.id.person_married)
    EditText personMarried;
    @BindView(R.id.update_person_layout)
    LinearLayout updatePersonLayout;

    private Realm realm;
    Person singlePerson;

    private RealmObjectChangeListener<Person> listener = new RealmObjectChangeListener<Person>() {
        @Override
        public void onChange(Person person, @Nullable ObjectChangeSet changeSet) {
            for (String fieldName : changeSet.getChangedFields()) {
                showToast("Field " + fieldName + " was changed.");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        createRealmObjects();
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
            }
        });
    }

    @OnClick({R.id.search_btn, R.id.update_btn})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.search_btn:
                String search_name = searchName.getText().toString();
                if (!search_name.isEmpty()){
                    singlePerson = realm.where(Person.class).equalTo("name", search_name).findFirst();

                    if (!(singlePerson == null)){
                        singlePerson.addChangeListener(listener);
                        personName.setText(singlePerson.getName());
                        personAge.setText(String.valueOf(singlePerson.getAge()));
                        personMarried.setText(String.valueOf(singlePerson.getMarried()));

                        updatePersonLayout.setVisibility(View.VISIBLE);
                    }
                    else
                        showToast("No Such person with that name in the database");
                }
                else
                    showToast("Name Field cannot be empty");
                break;
            case R.id.update_btn:
                String update_name = personName.getText().toString();
                String update_age = personAge.getText().toString();
                String update_married = personMarried.getText().toString();
                if (!(update_age.isEmpty() || update_name.isEmpty() || update_married.isEmpty())){
                    realm.beginTransaction();
                    singlePerson.setName(update_name);
                    singlePerson.setAge(Integer.valueOf(update_age));
                    singlePerson.setMarried(Boolean.valueOf(update_married));
                    realm.commitTransaction();
                    updatePersonLayout.setVisibility(View.GONE);
                }
                else
                    showToast("No Update Fields can be Empty");
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        realm.cancelTransaction();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
