package com.semanientreprise.realmrelationship;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneToMany extends Fragment {
    @BindView(R.id.relationshipMany)
    TextView relationshipMany;

    Unbinder unbinder;
    private Realm realm;

    public OneToMany() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.one_to_many_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        Realm.init(getActivity());
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        TeacherMany teacher = realm.createObject(TeacherMany.class);
        teacher.name = "Edet Ebenezer";
        teacher.course = "Microbiolgy";

        Group blue_group = realm.createObject(Group.class);
        blue_group.name = "Blue Group";
        blue_group.number_of_students = 15;

        Group purple_group = realm.createObject(Group.class);
        purple_group.name = "Purple Group";
        purple_group.number_of_students = 18;

        teacher.groups.add(blue_group);
        teacher.groups.add(purple_group);

        realm.commitTransaction();

        String toDisplay = teacher.name+" is teaching "+teacher.groups.size()+" Groups Namely - "+teacher.groups.get(0).name+" with "+teacher.groups.get(0).number_of_students+" students"
                +" and "+teacher.groups.get(1).name+" with "+teacher.groups.get(1).number_of_students;

        relationshipMany.setText(toDisplay);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }
}
