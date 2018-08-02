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


/**
 * A simple {@link Fragment} subclass.
 */
public class LinkFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.cat_color_one)
    EditText cat_color_one;
    @BindView(R.id.cat_age)
    EditText catAge;
    @BindView(R.id.result)
    TextView result;
    @BindView(R.id.cat_color_two)
    EditText catColorTwo;
    @BindView(R.id.cat_name)
    EditText catName;
    private Realm realm;

    public LinkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.link_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.go_btn)
    public void onViewClicked() {
        String string_color_one = cat_color_one.getText().toString();
        String string_color_two = catColorTwo.getText().toString();
        String cat_age = catAge.getText().toString();
        String cat_name = catName.getText().toString();

        StringBuilder toDisplay = new StringBuilder();

        if (!(string_color_one.isEmpty() && cat_age.isEmpty())) {

            RealmResults<PersonsWithPet> persons = realm.where(PersonsWithPet.class)
                    .equalTo("cats.age", Integer.valueOf(cat_age))
                    .findAll()
                    .where()
                    .equalTo("cats.color", string_color_one)
                    .findAll()
                    .where()
                    .equalTo("cats.color", string_color_two)
                    .findAll();

            toDisplay.append("Query Relationship Result\n\n");
            toDisplay.append("There are - " + persons.size() + " Persons that are below " + string_color_one + " and have a cat thats " + cat_age + " years old\n\n");

            int i = 0;
            while (i < persons.size()) {
                toDisplay.append((i +1)+ ". Name : " + persons.get(i).name + " with phone number : " + persons.get(i).phone_number + " Age : " + persons.get(i).age + "\n\n\n");
                i++;
            }

            toDisplay.append("Reverse Relationships Linking\n\n");
            RealmResults<Cat> cats = realm.where(Cat.class).contains("owners.name", "Edet").findAll();

            toDisplay.append("There are - " + cats.size() + " cats that has an owner with the name such as Edet\n\n");

            i = 0;
            while (i < cats.size()) {
                toDisplay.append((i +1)+ ". Name : " + cats.get(i).name + " Age : " + cats.get(i).age + " Color : " + cats.get(i).color + "\n\n\n");
                i++;
            }

            toDisplay.append("Reverse Relationships Linking 2\n\n");

            i = 0;
            RealmResults<Cat> kittens = realm.where(Cat.class).equalTo("color", string_color_one).equalTo("name", cat_name).findAll();
            for (Cat kitten : kittens) {
                RealmResults<PersonsWithPet> owners = kitten.getOwners();
                toDisplay.append((i +1)+ ". Name : " +owners.get(i).name + " with phone number : " + owners.get(i).phone_number + " Age : " + owners.get(i).age + "\n\n\n");
            }

            result.setText(toDisplay.toString());
        } else
            showToast("No Fields can be empty");
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}