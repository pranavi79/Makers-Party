package com.example.first;

import com.google.firebase.database.Exclude;

public class Upload {
    private String mname;
    private String mImageUrl;
    private String mkey;
    private String muploader;
    private String mcontact;
    private String mprice;

    public Upload (){
        
    }
    public Upload(String name,String uploader,String contact,String price,String ImageUrl)//java class to store detail of the thing put to sale
    {
        if(name.trim().equals("")){
            name="No Name";
        }
        mname=name;
        mcontact=contact;
        muploader=uploader;                           //details of the selling object
        mImageUrl =ImageUrl;
        mprice=price;
    }
    public String getname(){
        return mname;
    }
    public void setName(String name){
        mname=name;
    }
    public String getImageUrl(){
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;                        //all the getter and setter data that is called in ImageAdapter activity in holder
    }

    public String getuploader() {
        return muploader;
    }

    public void setuploader(String muploader) {
        this.muploader = muploader;
    }

    public String getcontact() {
        return mcontact;
    }

    public void setcontact(String mcontact) {
        this.mcontact = mcontact;
    }

    public String getprice() {
        return mprice;
    }

    public void setprice(String mprice) {
        this.mprice = mprice;
    }

    @Exclude
    public String getkey() {
        return mkey;
    }                           // this is created to return data to delete the image and all data of the image from database and storage

    @Exclude
    public void setkey(String key) {
        mkey = key;
    }
}
