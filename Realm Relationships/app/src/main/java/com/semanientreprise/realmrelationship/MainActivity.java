package com.semanientreprise.realmrelationship;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Realm.init(this);
    }

    @OnClick({R.id.oneToOne, R.id.oneToMany, R.id.manyToMany,R.id.inverseRelationship})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.oneToOne:
                showOneToOneFragment();
                break;
            case R.id.oneToMany:
                showOneToManyFragment();
                break;
            case R.id.manyToMany:
                showManyToManyFragment();
                break;
            case R.id.inverseRelationship:
                showInverseFragment();
                break;
        }
    }
    private void showInverseFragment() {
        InverseRelationship inverse = new InverseRelationship();
        showFragment(inverse);
    }

    private void showManyToManyFragment() {
        ManyToMany manyToMany = new ManyToMany();
        showFragment(manyToMany);
    }

    private void showOneToManyFragment() {
        OneToMany oneToMany = new OneToMany();
        showFragment(oneToMany);
    }

    private void showOneToOneFragment() {
        OneToOne oneToOne = new OneToOne();
        showFragment(oneToOne);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager  fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.mainLayout,fragment).addToBackStack(fragment.getTag());
        ft.commit();
    }
}
