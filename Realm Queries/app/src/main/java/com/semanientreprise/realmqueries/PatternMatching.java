package com.semanientreprise.realmqueries;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class PatternMatching extends Fragment {

    @BindView(R.id.result)
    TextView resultTV;
    Unbinder unbinder;

    private Realm realm;

    public PatternMatching() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pattern_matching_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();
        
        initViews();
        return view;
    }

    private void initViews() {

        StringBuilder toDisplay = new StringBuilder();
        String pattern = "?det*";

        RealmResults<Person> result = realm.where(Person.class)
                .like("name", pattern, Case.INSENSITIVE)
                .findAll();

        toDisplay.append("like() Predicate\n");
        toDisplay.append("Pattern - "+pattern+"\n");
        toDisplay.append("There are - "+result.size()+" Person's name that match the pattern - "+pattern+"\n\n");

        int i = 0;

        while(i < result.size()){
            toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
            i++;
        }

        pattern = "*Jo*";

        result = realm.where(Person.class)
                .like("name", pattern, Case.INSENSITIVE)
                .findAll();

        toDisplay.append("Pattern - "+pattern+"\n\n");
        toDisplay.append("There are - "+result.size()+" Person's name that match the pattern - "+pattern+"\n\n");

        i = 0;

        while(i < result.size()){
            toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
            i++;
        }

        pattern = "*gmail.com";

        result = realm.where(Person.class)
                .like("email", pattern, Case.INSENSITIVE)
                .findAll();

        toDisplay.append("Pattern - "+pattern+"\n\n");
        toDisplay.append("There are - "+result.size()+" Person's with email with the pattern - "+pattern+"\n\n");

        i = 0;

        while(i < result.size()){
            toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
            i++;
        }

        resultTV.setText(toDisplay.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }
}