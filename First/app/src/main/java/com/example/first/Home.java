package com.example.first;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();//if the user has already logged in in the decive,he need not need to again login unless he log out
        if (firebaseUser!=null){
            Intent i=new Intent(Home.this,Welcome.class);// this class is created to keep the session recorded for a user
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }

    }
}
