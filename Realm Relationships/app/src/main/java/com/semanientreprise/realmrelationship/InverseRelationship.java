package com.semanientreprise.realmrelationship;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semanientreprise.realmrelationship.models.Group;
import com.semanientreprise.realmrelationship.models.InverseGroup;
import com.semanientreprise.realmrelationship.models.Teacher;
import com.semanientreprise.realmrelationship.models.TeacherInverse;
import com.semanientreprise.realmrelationship.models.TeacherMany;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class InverseRelationship extends Fragment {
    @BindView(R.id.relationshipInverse)
    TextView relationshipInverse;
    @BindView(R.id.relationshipInverse2)
    TextView relationshipInverse2;
    Unbinder unbinder;

    private Realm realm;

    public InverseRelationship() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.inverse_relationship_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        Realm.init(getActivity());
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        TeacherInverse teacher = realm.createObject(TeacherInverse.class);
        teacher.name = "Smith Williams";
        teacher.course = "French";


        TeacherInverse teacher2 = realm.createObject(TeacherInverse.class);

        teacher2.name = "Frank Houston";
        teacher2.course = "Agriculture";

        InverseGroup green_group = realm.createObject(InverseGroup.class);
        green_group.name = "Green Group";
        green_group.number_of_students = 8;

        InverseGroup white_group = realm.createObject(InverseGroup.class);
        white_group.name = "White Group";
        white_group.number_of_students = 20;

        teacher.groups.add(green_group);
        teacher.groups.add(white_group);

        teacher2.groups.add(green_group);
        teacher2.groups.add(white_group);

        realm.commitTransaction();

        String stringTeacher1 = green_group.name+" has "+green_group.teachers.size()+" teachers - "+ green_group.teachers.get(0).name+" teaching them "+green_group.teachers.get(0).course
                +" and "+green_group.teachers.get(1).name+" teaching them "+green_group.teachers.get(1).course;

        String stringTeacher2 = white_group.name+" has "+white_group.teachers.size()+" teachers - "+ white_group.teachers.get(0).name+" teaching them "+white_group.teachers.get(0).course
                +" and "+white_group.teachers.get(1).name+" teaching them "+white_group.teachers.get(1).course;

        relationshipInverse.setText(stringTeacher1);

        relationshipInverse2.setText(stringTeacher2);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }

}
