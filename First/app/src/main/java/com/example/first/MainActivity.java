package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button signup, signin;
    EditText t1,t2,t3,t4;
    TextView showpass;
    FirebaseAuth mAuth;
    DatabaseReference firebaseDatabase;
    Boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signup = (Button) findViewById(R.id.button);
        signin= (Button) findViewById(R.id.button2);
        showpass=findViewById(R.id.showpassword);
        t1=findViewById(R.id.editText4);//giving IDs to the various objects in xml file to refer the .java file to the respective element in .xml file
        t2=findViewById(R.id.editText5);
        t3=findViewById(R.id.editText6);
        t4=findViewById(R.id.editText7);
        showpass.setVisibility(View.GONE);//this feature is only visible only when some text is written in password text view
        t3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(t3.getText().length()>0)
                    showpass.setVisibility(View.VISIBLE);//show password button visible
                else
                    showpass.setVisibility(View.GONE);//show password button vanished

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");//connect to Users folder in firebase database
        showpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(showpass.getText()=="Show Password") {
                    showpass.setText("Hide Password");
                   t3.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//this shows the password
                }
                else{
                    showpass.setText("Show Password");
                    t3.setTransformationMethod(PasswordTransformationMethod.getInstance());//this hides the password
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = t1.getText().toString().trim();//extracting string from the edit text
                final String password = t3.getText().toString().trim();
                final String email = t2.getText().toString().trim();
                final String phone = t4.getText().toString().trim();
                if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()))
                {
                    Toast.makeText(MainActivity.this, "Invalid Email Type", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(MainActivity.this, "Please Enter email", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(MainActivity.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(MainActivity.this, "Please Enter username", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(MainActivity.this, "Please Enter phone number", Toast.LENGTH_SHORT).show();
                }
                else if (!(TextUtils.isEmpty(email)) && !(TextUtils.isEmpty(password)) && !(TextUtils.isEmpty(username)) && !(TextUtils.isEmpty(phone))) {
                    mAuth.fetchSignInMethodsForEmail(t2.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    check = task.getResult().getSignInMethods().isEmpty();             // checks if the new registration email is already present or not
                                    if (check) {
                                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if ((task.isSuccessful())) {
                                                    Userdetail information = new Userdetail(username, email, phone);//object created java class Userdetail and fetching the userdatails from that class
                                                    Toast.makeText(MainActivity.this, "User successfully Registered", Toast.LENGTH_SHORT).show();
                                                    FirebaseDatabase.getInstance().getReference("Users")
                                                            .child(FirebaseAuth.getInstance().getUid())
                                                            .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {//the user details are stored in the firebase database in Users and authentification and database are connected
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(MainActivity.this, "User successfully Registered", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(MainActivity.this, Login.class));//using intent for transition from 1 activity to another
                                                        }
                                                    });
                                                }

                                            }
                                        });
                                    }
                                    else
                                        Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);//for sign in options
            }
        });
    }
}
