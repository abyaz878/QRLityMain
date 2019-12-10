package com.example.user.myqrproject;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.example.user.myqrproject.R.id.btnLogin;

public class MainActivity extends AppCompatActivity {



    private Button login;

    /*

    private Button Signin;
    private EditText Name;
    private EditText Password;
    private TextView Info;
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        login = (Button) findViewById(R.id.btnLogin);


       /*
        Signin = (Button)findViewById(R.id.btnSignIn);
        Name = (EditText) findViewById(R.id.btnName);
        Password = (EditText) findViewById(R.id.btnPass);
        Info = (TextView)findViewById(R.id.tvInfo);
            */





        setSupportActionBar(toolbar);
        //Scan Button
        Button buttonBarCodeScan = findViewById(R.id.buttonScan);
        buttonBarCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiate scan with our custom scan activity
                new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginPage.class));

            }
        });







    }







    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
                showResultDialogue(result.getContents());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(MainActivity.this, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .show();
    }

}
