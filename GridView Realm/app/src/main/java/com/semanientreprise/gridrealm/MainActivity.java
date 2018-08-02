package com.semanientreprise.gridrealm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.contestant_list)
    GridView contestantList;
    private ContestantAdapter adapter;

    private Realm realm;
    private RealmResults<Contestant> contestants;

    private RealmChangeListener<RealmResults<Contestant>> realmChangeListener = new RealmChangeListener<RealmResults<Contestant>>() {
        @Override
        public void onChange(RealmResults<Contestant> constestants) {
            adapter.setData(constestants);
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

        fillUpDatabaseFromJSON();

        realm.beginTransaction();

        contestants = realm.where(Contestant.class).findAllAsync();

        realm.commitTransaction();

        adapter = new ContestantAdapter(contestants);

        contestantList.setAdapter(adapter);
        contestantList.setOnItemClickListener(this);

        contestants.addChangeListener(realmChangeListener);
    }

    private void fillUpDatabaseFromJSON() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = getResources().openRawResource(R.raw.contestants);
                try {
                    realm.createAllFromJson(Contestant.class,inputStream);
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
        contestants.removeAllChangeListeners();
        realm.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Contestant modifiedContestant = adapter.getItem(i);

        // Acquire the name of the clicked City, in order to be able to query for it.
        final String name = modifiedContestant.getName();

        realm.beginTransaction();

        Contestant contestant = realm.where(Contestant.class).equalTo("Name",name).findFirst();
        contestant.setVotes(contestant.getVotes() + 1);

        realm.commitTransaction();
    }
}