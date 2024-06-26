package com.example.std;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main extends AppCompatActivity {
    EditText inputEmail;
    EditText inputPassword;
    EditText inputConfirmPassword;

    String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    Button signbtn;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputEmail = findViewById(R.id.inputEmaiL);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin=findViewById(R.id.btnLogin);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        signbtn = findViewById(R.id.Signbutton);
        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent i = new Intent(Main.this,MySignupApp.class);
                    startActivity(i);
                    finish();
            }});
//
//        });
//            btnLogin = findViewById(R.id.btnLogin);
//            btnLogin.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    Intent i = new Intent(Main.this,MainActivity.class);
//                    startActivity(i);
//                    finish();
//                }
//
//            });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                perforLogin();

            }
        });
    }

    private void perforLogin() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();

        if (!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email");
        } else if (password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter Proper Password");
        }else {
            progressDialog.setMessage("Please Wait While Login...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(Main.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(Main.this, "No User found", Toast.LENGTH_SHORT).show();
                }}
            });
        }}
    private void sendUserToNextActivity() {
        Intent intent=new Intent(Main.this,Main2.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


        }
