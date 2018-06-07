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
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoubleNumericQuery extends Fragment {

    @BindView(R.id.query_name)
    EditText queryName;
    @BindView(R.id.query_name_2)
    EditText queryName2;
    @BindView(R.id.result)
    TextView resultTv;
    Unbinder unbinder;

    private Realm realm;

    public DoubleNumericQuery() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.double_numeric_query_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();

        return view;
    }

    @OnClick(R.id.go_btn)
    public void onViewClicked() {
        String age = queryName.getText().toString();
        String age2 = queryName2.getText().toString();

        StringBuilder toDisplay = new StringBuilder();

        if ((!(age.isEmpty() && age2.isEmpty()))&& (Integer.valueOf(age2) > Integer.valueOf(age))) {

            RealmResults<Person> result = realm.where(Person.class)
                    .between("age", Integer.valueOf(age),Integer.valueOf(age2))
                    .distinct("name")
                    .findAll();

            toDisplay.append("between() and distinct() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons between " + age + " and "+age2+"\n\n");

            result = result.sort("age", Sort.ASCENDING);
            toDisplay.append("ASCENDING ORDER BY AGE\n\n");
            int i = 0;

            while (i < result.size()) {
                toDisplay.append(result.get(i).name + " with phone number : " + result.get(i).phone_number + " email : " + result.get(i).email + " Address :" + result.get(i).address + " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }

            /*
             *uncomment for sort() in descending order
             *

            result = result.sort("age",Sort.DESCENDING);
            toDisplay.append("DESCENDING ORDER BY AGE\n\n");
            i = 0;

            while (i < result.size()) {
                toDisplay.append(result.get(i).name + " with phone number : " + result.get(i).phone_number + " email : " + result.get(i).email + " Address :" + result.get(i).address + " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }*/
            resultTv.setText(toDisplay.toString());
        }
        else
            showToast("No Field can be Empty and First Age must be greater than Second Age");
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}