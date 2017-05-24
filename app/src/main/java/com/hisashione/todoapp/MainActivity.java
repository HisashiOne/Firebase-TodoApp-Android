package com.hisashione.todoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.hisashione.todoapp.Activities.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private TextView passText, emailText, repeatPassText;
    private Button registerBTN, loginBTN;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        registerBTN = (Button) findViewById(R.id.registerBTN);
        loginBTN = (Button) findViewById(R.id.loginBTN);
        passText = (TextView) findViewById(R.id.passUser);
        emailText = (TextView) findViewById(R.id.emailUser);
        repeatPassText = (TextView) findViewById(R.id.repeatPass);


        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loginIntent =  new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(loginIntent);


            }
        });

        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();

            }
        });


    }

    private  void registerUser(){


        if (TextUtils.isEmpty(emailText.getText().toString()) || !isValidEmail(emailText.getText().toString())){

            emailText.setError("Introduce Un Correo Valido");
            Toast.makeText(getApplicationContext(), "Introduce un correo Valido", Toast.LENGTH_SHORT).show();
            return;

        }
        if (TextUtils.isEmpty(passText.getText().toString())){

            passText.setError("Introduce un Password Valdo");
            Toast.makeText(getApplicationContext(), "Introduce un Password Valido", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( TextUtils.isEmpty(repeatPassText.getText().toString())){
            repeatPassText.setError("Introduce un Password Valdo");
            Toast.makeText(getApplicationContext(), "Introduce un Password Valido", Toast.LENGTH_SHORT).show();
            return;
        }
        if ( !passText.getText().toString().equals(repeatPassText.getText().toString())
                || !repeatPassText.getText().toString().equals(passText.getText().toString())){


            repeatPassText.setError("Password no Corresponde");
            passText.setError("Password no Corresponde");
            Toast.makeText(getApplicationContext(), "Password no Corresponde", Toast.LENGTH_SHORT).show();
            return;

        }



        progressDialog.setMessage("Register User....");
        progressDialog.show();




        firebaseAuth.createUserWithEmailAndPassword(emailText.getText().toString(), passText.getText().toString())
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Registro Exitoso", Toast.LENGTH_SHORT).show();

                    progressDialog.hide();

                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);





                }
                else {

                    Toast.makeText(getApplicationContext(),  task.getException().getLocalizedMessage()
                            , Toast.LENGTH_SHORT).show();

                    progressDialog.hide();
                }
            }
        });


    }


    @Override
    public void onBackPressed() {

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (TextUtils.isEmpty(target)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

}
