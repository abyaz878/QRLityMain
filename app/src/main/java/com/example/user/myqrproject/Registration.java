package com.example.user.myqrproject;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {

    private Button Register;
    private EditText RName;
    private EditText RPass;
    private EditText REmail;
    private TextView RAlready;
    private EditText RAge;


    String vName,vPass,vEmail,vAge;

    private FirebaseUser firebaseUser;

    private FirebaseAuth firebaseAuth;

    private DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setUpUIviews();

        firebaseAuth = FirebaseAuth.getInstance();


        db = FirebaseDatabase.getInstance().getReference().child("users");

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    String user_email = REmail.getText().toString().trim();
                    String user_password = RPass.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                updateData();
                                Toast.makeText(Registration.this,"Registration Successful! ",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registration.this,MainActivity.class));
                            }else {
                                Toast.makeText(Registration.this,"Registration Unsuccessful ",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });

        RAlready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registration.this,MainActivity.class));
            }
        });





    }



    private void updateData(){

        String uName = RName.getText().toString().trim();
        String uEmail = REmail.getText().toString().trim();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String id = firebaseUser.getUid();


       ProfileUpdate profileUpdate = new ProfileUpdate(uName,uEmail);

       db.child(id).setValue(profileUpdate);

       Toast.makeText(this,"Added info",Toast.LENGTH_SHORT).show();

    }


    private Boolean validate(){

        Boolean result = false;

         vName = RName.getText().toString();
         vPass = RPass.getText().toString();
         vEmail = REmail.getText().toString();
         vAge =   RAge.getText().toString();

        if(vName.isEmpty() || vEmail.isEmpty() || vPass.isEmpty() || vAge.isEmpty()){
            Toast.makeText(this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }

        return result;
    }


    private void setUpUIviews(){

        Register = (Button)findViewById(R.id.btnRegister);
        RName = (EditText)findViewById(R.id.etName);
        RPass = (EditText)findViewById(R.id.etPass);
        REmail = (EditText)findViewById(R.id.etMail);
        RAlready = (TextView)findViewById(R.id.tvAlready);
        RAge = (EditText)findViewById(R.id.etAge);


    }



}
