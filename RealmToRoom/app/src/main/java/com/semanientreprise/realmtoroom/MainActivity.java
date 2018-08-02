package com.semanientreprise.realmtoroom;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.realmDetails)
    TextView realmDetails;
    @BindView(R.id.roomDetails)
    TextView roomDetails;

    private Realm realm;
    private RealmResults<Person> result;
    public static UserDatabase userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Realm.init(this);
        Realm.deleteRealm(Realm.getDefaultConfiguration());

        realm = Realm.getDefaultInstance();

        userDatabase = Room.
                databaseBuilder(getApplicationContext(),UserDatabase.class,"users")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        
        createRealmObjects();

        showRealmDBContent();
    }

    private void showRealmDBContent() {
       result = realm.where(Person.class).findAll();

        showContent(result,realmDetails);
    }

    private void showContent(RealmResults<Person> result, TextView textView){
        String detailsToDisplay = "";

        for(int i = 0; i < result.size(); i++){

            detailsToDisplay+="Name - "+result.get(i).getName()+" \n"+
                    "Email -  "+result.get(i).getEmail()+" \n"+
                    "Address - "+result.get(i).getAddress() +" \n\n";
        }
        textView.setText(detailsToDisplay);
    }

    private void showContent(List<User> users, TextView textView){
        String detailsToDisplay = "";

        for(int i = 0; i < users.size(); i++){

            detailsToDisplay+="Name - "+users.get(i).getName()+" \n"+
                    "Email -  "+users.get(i).getEmail()+" \n"+
                    "Address - "+users.get(i).getAddress() +" \n\n";
        }
        textView.setText(detailsToDisplay);
        textView.setVisibility(View.VISIBLE);
    }

    private void createRealmObjects() {
        realm.beginTransaction();

        //First Person
        Person Eben = realm.createObject(Person.class);
        Eben.setName("Ebenezer");
        Eben.setAddress("Area One Abuja");
        Eben.setEmail("sample@gmail.com");

        //Second Person
        Person Joe = realm.createObject(Person.class);
        Joe.setName("Joseph");
        Joe.setAddress("Area Seven Abuja");
        Joe.setEmail("sample@yahoo.com");

        //Third Person
        Person Sam = realm.createObject(Person.class);
        Sam.setName("Samuel");
        Sam.setAddress("Area Eleven Abuja");
        Sam.setEmail("sample@rocket.com");

        realm.commitTransaction();
    }

    private void copyFromRealmToRoom() {

        for (Person person : result){

            User user = new User();
            user.setName(person.getName());
            user.setEmail(person.getEmail());
            user.setAddress(person.getAddress());

            MainActivity.userDatabase.personDao().addUser(user);
        }
    }

    private void displayRoomContent() {
        List<User> users = MainActivity.userDatabase.personDao().getAllUsers();

        showContent(users,roomDetails);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        copyFromRealmToRoom();
        displayRoomContent();
    }

}
