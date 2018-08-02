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
public class NumericQuery extends Fragment {

    @BindView(R.id.query_age)
    EditText queryAge;
    @BindView(R.id.result)
    TextView resultTv;
    @BindView(R.id.query_email)
    EditText queryEmail;
    @BindView(R.id.query_address)
    EditText queryAddress;

    Unbinder unbinder;

    private Realm realm;

    public NumericQuery() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.numeric_query_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        realm = Realm.getDefaultInstance();
        return view;
    }

    @OnClick(R.id.go_btn)
    public void onViewClicked() {
        String age = queryAge.getText().toString();
        String specificAge = queryEmail.getText().toString();
        //uncomment the below lines to use the beginGroup() - endGroup() and also the not() predicates
        //String email = queryEmail.getText().toString();
        // String address = queryAddress.getText().toString();

        StringBuilder toDisplay = new StringBuilder();

        //also uncomment this if statement and comment the one below it for
        // the beginGroup() - endGroup() and also the not() predicates
        //   if (!(age.isEmpty() && email.isEmpty() && address.isEmpty())) {
        if (!(age.isEmpty() && specificAge.isEmpty())){

            RealmResults<Person> result = realm.where(Person.class)
                    .lessThan("age",Integer.valueOf(age))
                    .findAll();

            toDisplay.append("lessThan() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons younger than " + age + "\n\n");

            toDisplay.append("These are the objects matching the lessThan() predicate");

            int i = 0;
            while (i < result.size()) {
                toDisplay.append(result.get(i).name + " with phone number : " + result.get(i).phone_number + " email : " + result.get(i).email + " Address :" + result.get(i).address + " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }

            toDisplay.append("Query Chain Result\n\n");
            Person singlePerson = result.where().equalTo("age",Integer.valueOf(specificAge)).findFirst();
            if(!(singlePerson == null)){
                toDisplay.append("This is the object returned after query chaining\n\n");
                toDisplay.append(singlePerson.name + " with phone number : " + singlePerson.phone_number + " email : " + singlePerson.email + " Address :" + singlePerson.address + " and age : "+ singlePerson.age +"\n\n\n");
            }
            else
                toDisplay.append("No Person in our result set has an age of "+specificAge);

            /*
             *uncomment to use the beginGroup() - endGroup() and the not() predicates
             *
            RealmResults<Person> result = realm.where(Person.class)
                    .greaterThan("age", Integer.valueOf(age))
                    .beginGroup()
                        .contains("email",email)
                        .and()
                        .contains("address",address)
                    .endGroup()
                    .findAll();

            toDisplay.append("beginGroup() - endGroup() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons older than " + age +
            "that their emails contains "+email+" and their address contains "+address+"\n\n");

            int i = 0;

            while (i < result.size()) {
                toDisplay.append(result.get(i).name + " with phone number : " + result.get(i).phone_number + " email : " + result.get(i).email + " Address :" + result.get(i).address + " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }

            result = realm.where(Person.class)
                    .greaterThan("age", Integer.valueOf(age))
                    .not()
                    .beginGroup()
                        .contains("email",email)
                        .and()
                        .contains("address",address)
                    .endGroup()
                    .findAll();

            toDisplay.append("not() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons older than " + age +
            "that their emails doesn't contain "+email+" and their address doesn't contain "+address+"\n\n");

            i = 0;

            while (i < result.size()) {
                toDisplay.append(result.get(i).name + " with phone number : " + result.get(i).phone_number + " email : " + result.get(i).email + " Address :" + result.get(i).address + " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }*/

            /*
             * uncomment this section of code to use the lessThan,greaterThanOrEqualTo and lessThanOrEqualTo predicates
             *
            result = realm.where(Person.class)
                                        .lessThan("age",Integer.valueOf(age))
                                        .findAll();

            toDisplay.append("lessThan() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons younger than " + age + "\n\n");

            int i = 0;
            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+ " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }

            result = realm.where(Person.class)
                    .greaterThanOrEqualTo("age",Integer.valueOf(age))
                    .findAll();

            toDisplay.append("greaterThanOrEqualTo() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons older than " + age + " or "+age+" years old"+"\n\n");

            i = 0;
            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+ " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }

            result = realm.where(Person.class)
                    .lessThanOrEqualTo("age",Integer.valueOf(age))
                    .findAll();

            toDisplay.append("lessThanOrEqualTo() Predicate\n\n");
            toDisplay.append("There are - " + result.size() + " Persons younger than " + age + " or "+age+" years old"+"\n\n");

            i = 0;
            while(i < result.size()){
                toDisplay.append(result.get(i).name+" with phone number : "+result.get(i).phone_number+" email : "+result.get(i).email+" and Address : "+result.get(i).address+ " and age : "+ result.get(i).age +"\n\n\n");
                i++;
            }*/
            resultTv.setText(toDisplay.toString());
        }
        else
            showToast("No Field can't be empty");
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