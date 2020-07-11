package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class Forgotpassword extends AppCompatActivity {
    Button resetpassword;
    EditText email;
    FirebaseAuth mAuth;
    boolean check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        resetpassword=findViewById(R.id.button6);
        email=findViewById(R.id.editText9);
        mAuth=FirebaseAuth.getInstance();

        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString().trim();
                mAuth.fetchSignInMethodsForEmail(Email)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                check = task.getResult().getSignInMethods().isEmpty();//checks if the user has registered or not and non registered user cannot send the verification link
                            }
                        });

                if(TextUtils.isEmpty(Email)){
                    Toast.makeText(Forgotpassword.this, "Please Enter email address", Toast.LENGTH_SHORT).show();
                }
                else  if (!(Patterns.EMAIL_ADDRESS.matcher(Email).matches()))
                {
                    Toast.makeText(Forgotpassword.this, "Invalid Email Type", Toast.LENGTH_SHORT).show();
                }
                else if(!check)
                {
                    mAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {//this send the email for password reset
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Forgotpassword.this, "Reset password link has been send to the registered email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Forgotpassword.this, Login.class));//bring back to the login page after email is sent
                            }
                        }
                    });
                }
                else
                    Toast.makeText(Forgotpassword.this, "User not registered", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
