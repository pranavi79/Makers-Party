package com.example.first;

public class Userdetail {

    public String Username,Email,Phone; //java class to store detail of the user and main activity calls this class and save the user detail in firebase database
    public Userdetail(){
                            //empty constructor
    };

    public Userdetail(String username,String email,String phone){
    Username=username;
    Email=email;
    Phone=phone;
    }
}
