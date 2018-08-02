package com.semanientreprise.realmqueries;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        createRealmFromJSON();
    }

    private void createRealmFromJSON() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                InputStream inputStream = getResources().openRawResource(R.raw.persons);
                try {
                    realm.createAllFromJson(Person.class, inputStream);
                } catch (IOException e) {
                    if (realm.isInTransaction())
                        realm.cancelTransaction();
                }
            }
        });
    }

    @OnClick({R.id.single_query, R.id.double_string_query, R.id.multiple_string_query,R.id.pattern_string_query
    ,R.id.numeric_string_query,R.id.double_numeric_string_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.single_query:
                showSingleStringFragment();
                break;
            case R.id.double_string_query:
                showDoubleStringFragment();
                break;
            case R.id.multiple_string_query:
                showMultipleStringFragment();
                break;
            case R.id.pattern_string_query:
                showPatternQueryFragment();
                break;
            case R.id.numeric_string_query:
                showNumericQueryFragment();
                break;
            case R.id.double_numeric_string_query:
                showDoubleNumericQueryFragment();
                break;
        }
    }

    private void showDoubleNumericQueryFragment() {
        DoubleNumericQuery doubleNumericQuery = new DoubleNumericQuery();
        showFragment(doubleNumericQuery);
    }

    private void showNumericQueryFragment() {
        NumericQuery numericQuery = new NumericQuery();
        showFragment(numericQuery);
    }

    private void showPatternQueryFragment() {
        PatternMatching patternMatching = new PatternMatching();
        showFragment(patternMatching);
    }

    private void showMultipleStringFragment() {
        MultipleStringQuery multipleStringQuery = new MultipleStringQuery();
        showFragment(multipleStringQuery);
    }

    private void showDoubleStringFragment() {
        DoubleStringQuery doubleStringQuery = new DoubleStringQuery();
        showFragment(doubleStringQuery);
    }

    private void showSingleStringFragment() {
        SingleQuery singleQuery = new SingleQuery();
        showFragment(singleQuery);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.mainLayout, fragment).addToBackStack(fragment.getTag());
        ft.commit();
    }
}