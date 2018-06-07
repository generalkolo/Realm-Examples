package com.semanientreprise.realmrelationship;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semanientreprise.realmrelationship.models.Group;
import com.semanientreprise.realmrelationship.models.Teacher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneToOne extends Fragment {
    @BindView(R.id.relationshipOne)
    TextView relationshipOne;
    Unbinder unbinder;

    private Realm realm;

    public OneToOne() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.one_to_one_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        Realm.init(getActivity());
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        Teacher eben = realm.createObject(Teacher.class);

        eben.name = "Ebenezer";
        eben.course = "Computer Science";

        Group red_group = realm.createObject(Group.class);
        red_group.name = "RED GROUP";
        red_group.number_of_students = 15;

        eben.group = red_group;

        realm.commitTransaction();

        String toDisplay = eben.name+" Teaches "+eben.group.name+" with a number of "+eben.group.number_of_students;

        relationshipOne.setText(toDisplay);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }
}
