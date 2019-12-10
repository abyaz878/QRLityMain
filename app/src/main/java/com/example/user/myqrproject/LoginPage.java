package com.example.user.myqrproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    private TextView userRegistration;
    private TextView Info;
    private EditText LName;
    private EditText LPass;
    private Button LSignIn;
    private int counter = 3;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        userRegistration = (TextView)findViewById(R.id.tvRegister);
        LName = (EditText)findViewById(R.id.btnName);
        LPass = (EditText)findViewById(R.id.btnPass);
        LSignIn = (Button) findViewById(R.id.btnSignIn);
        Info = (TextView) findViewById(R.id.tvInfo);

         Info.setText( " No of attempts remaining: 3 " );

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user= null;

        if(user!=null){
           finish();
            startActivity(new Intent(LoginPage.this,General.class));
        }

         user = firebaseAuth.getCurrentUser();

        LSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(LName.getText().toString(),LPass.getText().toString());
            }
        });

        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this,Registration.class));
            }
        });


    }

    private void validate(String username, String password){

        progressDialog.setMessage("Processing your request.... ");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginPage.this,"Login successful!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginPage.this,General.class));
                }else {
                    Info.setText("No of attempts remaining: " + String.valueOf(counter-1));
                    Toast.makeText(LoginPage.this,"Please enter corrent credentials",Toast.LENGTH_SHORT).show();
                    counter --;
                    progressDialog.dismiss();
                    if(counter<=0){
                        LSignIn.setEnabled(false);
                    }
                }
            }
        });


    }


}
