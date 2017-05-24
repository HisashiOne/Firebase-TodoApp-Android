package com.hisashione.todoapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hisashione.todoapp.LoginActivity;
import com.hisashione.todoapp.Models.MyFirebaseIdService;
import com.hisashione.todoapp.Models.SharedPrefManager;
import com.hisashione.todoapp.Models.UserInformation;
import com.hisashione.todoapp.Models.UserToken;
import com.hisashione.todoapp.R;

import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    Button  saveBTN, listBTN, crashBtn;
    TextView emailText, nameText, adressText;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    String s;
    private ProgressDialog progressDialog;
    CheckBox notifiCheckbox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        progressDialog = new ProgressDialog(this);



        saveBTN = (Button) findViewById(R.id.saveAdressBTN);
        emailText = (TextView) findViewById(R.id.emailUserText);
        nameText = (TextView) findViewById(R.id.registerNameTxt);
        adressText = (TextView) findViewById(R.id.registerAdressTxt);
        listBTN = (Button) findViewById(R.id.listBTN);
        crashBtn = (Button) findViewById(R.id.crashBTN);
        notifiCheckbox = (CheckBox) findViewById(R.id.checkBoxNotif);



        progressDialog.setMessage("Loading....");
        progressDialog.show();


        MyFirebaseIdService firebaseIdService = new MyFirebaseIdService();


        String token = SharedPrefManager.getmInstance(getApplicationContext()).getToken();
        Log.d("Tag_2", "Token" + token);








        final FirebaseUser user = firebaseAuth.getCurrentUser();
        emailText.setText("Hola: " + user.getEmail());


        ///Load User Data
        loadUserData();


        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if (nameText.getText().toString().length() == 0  || adressText.getText().toString().length() == 0){

                    Toast.makeText(getApplicationContext(), "Completa los campos vacios",Toast.LENGTH_SHORT).show();


                    if (nameText.getText().toString().length() == 0){

                        nameText.setError("Campo Vacio");
                    }
                    if (adressText.getText().toString().length() == 0){
                        adressText.setError("Campo Vacio");

                    }


                }else {


                    UserInformation userInformation = new UserInformation(nameText.getText().toString(), adressText.getText().toString());

                    FirebaseUser user1  = firebaseAuth.getCurrentUser();
                    databaseReference.child(user1.getUid()).setValue(userInformation);

                    Toast.makeText(getApplicationContext(), "Datos Guardados",Toast.LENGTH_SHORT).show();

                }



            }
        });



        notifiCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notifiCheckbox.isChecked()){

                    String token = SharedPrefManager.getmInstance(getApplicationContext()).getToken();
                    Log.d("Tag_2", "Token" + token);

                    final FirebaseUser user = firebaseAuth.getCurrentUser();
                    UserToken userToken = new UserToken(token, user.getEmail());

                    databaseReference.child("token" + user.getUid()).setValue(userToken);

                }
            }
        });

        listBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);

            }
        });






        crashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               // testText.setText("Esto es Una Prueba");
                try{

                    s = null;
                    s.trim();


                }catch (Exception e){

                    FirebaseCrash.logcat(Log.ERROR, "Tag_2", s + " ");
                    FirebaseCrash.report(e);

                }

            }
        });



        /*saveBTN2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {


                    FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                    UserInformation userInformation = new UserInformation(nameText.getText().toString(), adressText.getText().toString());
                    DatabaseReference reference = database1.getReference();
                    reference.child("foo").setValue(userInformation);

                }catch (Exception e){

                    e.printStackTrace();

                }
            }
        });*/



    }


    @Override
    public void onBackPressed() {

    }

    public void loadUserData(){


        try {

            System.out.print("Kamikaze Lab");

            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
            DatabaseReference reference = database2.getReference();
            FirebaseUser user1  = firebaseAuth.getCurrentUser();

            reference.child(user1.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    //Log.d("Tag_2", "Values" + dataSnapshot);

                    progressDialog.dismiss();

                    String name = (String) dataSnapshot.child("name").getValue();
                    String user = (String) dataSnapshot.child("user").getValue();

                    adressText.setText(user);
                    nameText.setText(name);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }catch (Exception e){


            Log.d("Tag_2", "Data Error");
            progressDialog.dismiss();

            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logOutBTN_1) {
            firebaseAuth.signOut();

            Intent intentn = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentn);

        }
        if (id == R.id.refresh){



            loadUserData();
            Log.d("Tag_2", "Refresh");



        }


        return super.onOptionsItemSelected(item);
    }
}
