package com.semanientreprise.realmqueries;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleQuery extends Fragment {

    @BindView(R.id.query_name)
    EditText queryName;
    @BindView(R.id.result)
    TextView result;
    Unbinder unbinder;

    private Realm realm;

    public SingleQuery() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.single_query_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();

        return view;
    }

    @OnClick(R.id.go_btn)
    public void onViewClicked() {
        String string_queryName = queryName.getText().toString();
        StringBuilder toDisplay = new StringBuilder();

        if (!string_queryName.isEmpty()){
            RealmResults<Person> result = realm.where(Person.class)
                    .contains("name", string_queryName, Case.INSENSITIVE)
                    .findAll();

            toDisplay.append("contains() Predicate\n\n");
            toDisplay.append("There are - "+result.size()+" Persons with a name like that\n\n");

            int i = 0;

            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address :"+result.get(i).address+"\n\n\n");
                i++;
            }

            result = realm.where(Person.class)
                    .beginsWith("name",string_queryName,Case.SENSITIVE)
                    .findAll();

            toDisplay.append("beginsWith() Predicate\n\n");
            toDisplay.append("There are - "+result.size()+" Persons that their name starts with - "+string_queryName+"\n\n");

            i = 0;
            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
                i++;
            }

            result = realm.where(Person.class)
                    .endsWith("name",string_queryName,Case.SENSITIVE)
                    .findAll();

            toDisplay.append("endsWith() Predicate\n\n");
            toDisplay.append("There are - "+result.size()+" Persons that their name ends with - "+string_queryName+"\n\n");

            i = 0;
            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
                i++;
            }

            toDisplay.append("equalTo() Predicate\n\n");
            result = realm.where(Person.class)
                    .equalTo("name",string_queryName,Case.INSENSITIVE)
                    .findAll();

            toDisplay.append("There are - "+result.size()+" Persons match the name - "+string_queryName+"\n\n");

            i = 0;
            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
                i++;
            }
        }
        else
            showToast("Name cannot be empty");

        result.setText(toDisplay.toString());
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        realm.close();
    }
}