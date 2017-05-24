package com.hisashione.todoapp.Activities;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.hisashione.todoapp.R;

public class PasswordRecover extends AppCompatActivity {


    TextView emailRecoverTxt;
    Button recoverBTN;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover);

        progressDialog = new ProgressDialog(this);
        emailRecoverTxt = (TextView) findViewById(R.id.emailRecoverTXT);
        recoverBTN = (Button) findViewById(R.id.passRecoverBTN);
        firebaseAuth = FirebaseAuth.getInstance();




        recoverBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (emailRecoverTxt.getText().toString().length() == 0 || !isValidEmail(emailRecoverTxt.getText().toString())){

                    Toast.makeText(getApplicationContext(), "Introduce un correo Valido", Toast.LENGTH_SHORT).show();
                    emailRecoverTxt.setError("Correo Invalido");

                }else {


                    progressDialog.setMessage("Recuperando Contraseña....");
                    progressDialog.show();

                    firebaseAuth.sendPasswordResetEmail(emailRecoverTxt.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {


                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        progressDialog.hide();

                                        Toast.makeText(getApplicationContext(), "Password enviado a tu correo", Toast.LENGTH_SHORT).show();

                                    }
                                    else {
                                        Log.d("Tag_2","Error: " + task.getException().getLocalizedMessage());

                                        progressDialog.hide();

                                        Toast.makeText(getApplicationContext(),  task.getException().getLocalizedMessage()
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

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
}
