package com.semanientreprise.realmrelationship;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.semanientreprise.realmrelationship.models.Group;
import com.semanientreprise.realmrelationship.models.Teacher;
import com.semanientreprise.realmrelationship.models.TeacherMany;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Realm;


/**
 * A simple {@link Fragment} subclass.
 */
public class ManyToMany extends Fragment {

    @BindView(R.id.relationshipManyMany)
    TextView relationshipManyMany;
    Unbinder unbinder;
    @BindView(R.id.relationshipManyMany2)
    TextView relationshipManyMany2;

    private Realm realm;

    public ManyToMany() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.many_to_many_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        Realm.init(getActivity());
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        TeacherMany teacher = realm.createObject(TeacherMany.class);
        teacher.name = "Edet Ebenezer";
        teacher.course = "Microbiolgy";


        TeacherMany teacher2 = realm.createObject(TeacherMany.class);

        teacher2.name = "Joseph Ikenna";
        teacher2.course = "Computer Science";

        Group blue_group = realm.createObject(Group.class);
        blue_group.name = "Blue Group";
        blue_group.number_of_students = 25;

        Group purple_group = realm.createObject(Group.class);
        purple_group.name = "Purple Group";
        purple_group.number_of_students = 32;

        teacher.groups.add(blue_group);
        teacher.groups.add(purple_group);

        teacher2.groups.add(blue_group);
        teacher2.groups.add(purple_group);

        realm.commitTransaction();

        String toDisplay = teacher.name + " is teaching " + teacher.groups.size() + " Groups Namely - " + teacher.groups.get(0).name + " with " + teacher.groups.get(0).number_of_students + " students"
                + " and " + teacher.groups.get(1).name + " with " + teacher.groups.get(1).number_of_students;

        String toDisplay2 = teacher2.name + " is teaching " + teacher2.groups.size() + " Groups Namely - " + teacher2.groups.get(0).name + " with " + teacher2.groups.get(0).number_of_students + " students"
                + " and " + teacher2.groups.get(1).name + " with " + teacher2.groups.get(1).number_of_students;

        relationshipManyMany.setText(toDisplay);

        relationshipManyMany2.setText(toDisplay2);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }
}