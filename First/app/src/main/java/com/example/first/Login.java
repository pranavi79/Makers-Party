package com.example.first;

import android.content.Intent;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class Login extends AppCompatActivity {
    Button b1,b2;
    TextView t1,showpass,restpassword;
    EditText e1,e2;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b1=(Button)findViewById(R.id.button3);
        b2=(Button)findViewById(R.id.button4);
        e1=(EditText)findViewById(R.id.editText);
        e2=(EditText)findViewById(R.id.editText2);
        restpassword=findViewById(R.id.textView2);
        showpass=findViewById(R.id.showpassword2);
        showpass.setVisibility(View.GONE);
        e2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(e2.getText().length()>0)
                    showpass.setVisibility(View.VISIBLE);
                else
                    showpass.setVisibility(View.GONE);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAuth=FirebaseAuth.getInstance();
        mAuthlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null){
                    Toast.makeText(Login.this, "You are logged in.", Toast.LENGTH_SHORT).show();//directly logs you in if already logged in
                    startActivity(new Intent(getApplicationContext(),Welcome.class));
            }
                else
                    Toast.makeText(Login.this, "Please Log in", Toast.LENGTH_SHORT).show();
        }
        };
        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//same show/hide password button as in main activity
                if(showpass.getText()=="Show Password") {
                    showpass.setText("Hide Password");
                    e2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    showpass.setText("Show Password");
                    e2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        restpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Forgotpassword.class));//user will be directed to the forgot password activity and then can reset the password
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = e2.getText().toString();
                final String email = e1.getText().toString();
                if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()))//checks for the valid email type(a.domain)
                {
                    Toast.makeText(Login.this, "Invalid Email Type", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                }
                else if (!(TextUtils.isEmpty(email)) && !(TextUtils.isEmpty(password))) {
                  mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {//if the user credentials were present in the authentification ,user will be logged in task is successful
                              Toast.makeText(Login.this, "USer successfully logged in", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(), Welcome.class));
                          } else
                              Toast.makeText(Login.this, "Please log in again,Incorrect Details", Toast.LENGTH_SHORT).show();
                      }
                  });
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,MainActivity.class));
            }
        });
        }
    }

