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
import com.example.grocerystore.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText res_name,res_email,res_password;
    Button signup;
    TextView signin;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);
        res_name=findViewById (R.id.edit_name);
        res_email=findViewById (R.id.edit_email);
        res_password=findViewById (R.id.edit_pass);
        signup=findViewById (R.id.sign_up_btn);
        signin=findViewById (R.id.signin_text);
        progressBar=findViewById (R.id.progressbar);
        progressBar.setVisibility (View.GONE);

        auth=FirebaseAuth.getInstance ();
        database=FirebaseDatabase.getInstance ();

        //Go to Login
        signup.setOnClickListener (view -> {
            createuser ();
            progressBar.setVisibility (View.VISIBLE);
            Intent intent=new Intent (RegisterActivity.this,LoginActivity.class);
            startActivity (intent);
        });
        signin.setOnClickListener (v->{
            Intent intent=new Intent (RegisterActivity.this, LoginActivity.class);
            startActivity (intent);
        });
    }
    private void createuser(){
        String UserName=res_name.getText ().toString ();
        String Useremail=res_email.getText ().toString ();
        String Userpassword=res_password.getText ().toString ();
        if(TextUtils.isEmpty (UserName)){
            Toast.makeText (getApplicationContext (),"NameEmpty Field",Toast.LENGTH_SHORT).show ();return;
        }if(TextUtils.isEmpty (Useremail)){
            Toast.makeText (getApplicationContext (),"Email Empty Field",Toast.LENGTH_SHORT).show ();return;
        }if(TextUtils.isEmpty (Userpassword)&&Userpassword.length ()<6){
            Toast.makeText (getApplicationContext (),"Password Empty Field or Less",Toast.LENGTH_SHORT).show ();return;
        }
        auth.createUserWithEmailAndPassword (Useremail,Userpassword).addOnCompleteListener (new OnCompleteListener<AuthResult> () {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful ()){
                    UserModel userModel=new UserModel(UserName,Useremail,Userpassword);
                    String id=task.getResult ().getUser ().getUid ();
                    database.getReference ().child ("Admin").child (id).setValue (userModel);
                    progressBar.setVisibility (View.GONE);
                    Toast.makeText (getApplicationContext (),"Create user successful",Toast.LENGTH_SHORT).show ();
                }else Toast.makeText (getApplicationContext (),"Error"+task.getException (),Toast.LENGTH_SHORT).show ();
            }
        });
       
    }
}