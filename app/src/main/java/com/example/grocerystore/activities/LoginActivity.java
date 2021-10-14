package com.example.grocerystore.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button signin;
    TextView singup;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);

        email=findViewById (R.id.edit_email_login);
        password=findViewById (R.id.edit_pass_login);
        signin=findViewById (R.id.sign_in_login);
        singup=findViewById (R.id.signup_text);
        progressBar=findViewById (R.id.progressbar);
        progressBar.setVisibility (View.GONE);

        auth=FirebaseAuth.getInstance ();


        signin.setOnClickListener (view -> {
                progressBar.setVisibility (View.VISIBLE);
                userlogin();
                Toast.makeText (getApplicationContext (),"Hi",Toast.LENGTH_SHORT).show ();
            Intent intent=new Intent (LoginActivity.this, MainActivity.class);
            startActivity (intent);
        });
        singup.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent (LoginActivity.this, RegisterActivity.class);
                startActivity (intent);
            }
        });


    }

    private void userlogin() {
        String emails = email.getText ().toString ();
        String passwords = password.getText ().toString ();
        if (TextUtils.isEmpty (emails)) {
            Toast.makeText (getApplicationContext (), "NameEmpty Field", Toast.LENGTH_SHORT).show ();
            return;}
            if (TextUtils.isEmpty (passwords) && passwords.length () < 6)
            {Toast.makeText (getApplicationContext (),"Password ",Toast.LENGTH_SHORT).show ();return;}

            auth.signInWithEmailAndPassword (emails,passwords).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful ()) {
                        progressBar.setVisibility (View.GONE);
                        Toast.makeText (getApplicationContext (),"Login successfully",Toast.LENGTH_SHORT).show ();
                    }else { Toast.makeText (getApplicationContext (), "Error: "+task. getException (),Toast.LENGTH_LONG).show (); }
                }
            });

        }
    }

