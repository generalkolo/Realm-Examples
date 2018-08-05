package com.semanientreprise.encryptedrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.countriesRecView)
    RecyclerView countriesRecView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        RealmConfiguration encryptedConfiguration = new RealmConfiguration.Builder()
                .encryptionKey(ApplicationClass.key)
                .name("realEncrypted.realm")
                .build();

        Realm.deleteRealm(encryptedConfiguration);

        // Open the Realm with encryption enabled
        realm = Realm.getInstance(encryptedConfiguration);

        fillUpCountriesRealm();

        realm.beginTransaction();

        final RealmResults<Countries> countriesResults = realm.where(Countries.class).findAll();

        realm.commitTransaction();

        countriesRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        countriesRecView.setHasFixedSize(true);

        countriesRecView.setAdapter(new MyAdapter(countriesResults));
    }

    private void fillUpCountriesRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = getResources().openRawResource(R.raw.countries);
                try {
                    realm.createAllFromJson(Countries.class, inputStream);
                } catch (IOException e) {
                    if (realm.isInTransaction())
                        realm.cancelTransaction();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}