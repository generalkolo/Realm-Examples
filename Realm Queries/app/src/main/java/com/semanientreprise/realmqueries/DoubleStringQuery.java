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
public class DoubleStringQuery extends Fragment {

    @BindView(R.id.query_name)
    EditText queryName;
    @BindView(R.id.query_name_2)
    EditText queryName2;
    @BindView(R.id.result)
    TextView result;
    Unbinder unbinder;

    private Realm realm;

    public DoubleStringQuery() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.double_string_query_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();

        return view;
    }

    @OnClick(R.id.go_btn)
    public void onViewClicked() {
        String string_queryname,string_queryname2;

        string_queryname = queryName.getText().toString();
        string_queryname2 = queryName2.getText().toString();

        StringBuilder toDisplay = new StringBuilder();

        if (!(string_queryname.isEmpty() && string_queryname2.isEmpty())){
            RealmResults<Person> result = realm.where(Person.class)
                    .contains("name", string_queryname, Case.INSENSITIVE)
                    .or()
                    .contains("name",string_queryname2,Case.SENSITIVE)
                    .findAll();

            toDisplay.append("There are - "+result.size()+" Persons with names like that\n\n");

            int i = 0;

            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+"\n\n\n");
                i++;
            }
        }
        else
            showToast("No Field can be left empty");

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