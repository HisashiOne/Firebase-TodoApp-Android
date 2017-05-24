package com.hisashione.todoapp;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hisashione.todoapp.Activities.HomeActivity;
import com.hisashione.todoapp.Activities.PasswordRecover;
import com.hisashione.todoapp.Models.MyFirebaseIdService;
import com.hisashione.todoapp.Models.SharedPrefManager;

public class LoginActivity extends AppCompatActivity {

    Button loginBTN, registerBTN, passRecoverBTN;
    TextView mailTxt, pasTxt;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private BroadcastReceiver broadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);






        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null){

            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);


        }

        loginBTN = (Button) findViewById(R.id.loginBTN_1);
        registerBTN = (Button) findViewById(R.id.registerBTN_1);
        passRecoverBTN = (Button) findViewById(R.id.passRecoverActivity);
        mailTxt = (TextView) findViewById(R.id.emailUser_1);
        pasTxt = (TextView) findViewById(R.id.passUser_1);




        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                userLogin();

            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
        passRecoverBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), PasswordRecover.class);
                startActivity(intent);
            }
        });



        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String token = SharedPrefManager.getmInstance(getApplicationContext()).getToken();
                Log.d("Tag_2", "Token" + token);

            }
        };

        registerReceiver(broadcastReceiver, new IntentFilter(MyFirebaseIdService.TOKEN_BROADCAST));






    }


    private void  userLogin(){


        if (TextUtils.isEmpty(mailTxt.getText().toString()) || !isValidEmail(mailTxt.getText().toString())){

            mailTxt.setError("Introduce Un Correo Valido");
            Toast.makeText(getApplicationContext(), "Introduce un correo Valido", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(pasTxt.getText().toString())){

            pasTxt.setError("Introduce un Password Valdo");
            Toast.makeText(getApplicationContext(), "Introduce un password Valido", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Login User....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(mailTxt.getText().toString(), pasTxt.getText().toString())
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();

                if (task.isSuccessful()){
                    finish();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);



                }else {


                    Toast.makeText(getApplicationContext(),  task.getException().getLocalizedMessage()
                            , Toast.LENGTH_SHORT).show();

                }

            }
        });





    }


    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
