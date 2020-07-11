package com.example.first;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Buy extends AppCompatActivity implements ImageAdapter.OnItemClickListener {
    private RecyclerView mRecyclerview;//implemented to view image in form of card view
    private ImageAdapter imageAdapter;
    private  DatabaseReference mdatabaseref;//this help us to refer to the real time database
    private FirebaseStorage firebaseStorage;//this help us refer to the firebse storage
    private ValueEventListener mDBListener;

    private List<Upload> mUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        mRecyclerview=findViewById(R.id.recycler_view);
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mUpload=new ArrayList<>();// this is used to extract all the elements present in the firebase database
        imageAdapter= new ImageAdapter(Buy.this,mUpload);
        mRecyclerview.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(Buy.this);
        mdatabaseref = FirebaseDatabase.getInstance().getReference("uploads");// refer to the folder uploads in our firebase database
        firebaseStorage=FirebaseStorage.getInstance();
        mDBListener=mdatabaseref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUpload.clear();
                for(DataSnapshot postseason: dataSnapshot.getChildren()){
                    Upload upload=postseason.getValue(Upload.class);
                    upload.setkey(postseason.getKey());
                    mUpload.add(upload);
                }
             imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Buy.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void OnItemClick(int position) {
        Toast.makeText(this, "You clicked the image"+position, Toast.LENGTH_SHORT).show();//on clicking the image
    }

    @Override
    public void onWhateverClick(int position) {
        Toast.makeText(this, "Function coming soon", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnDeleteClick(int position) {
       Upload selectedItem=mUpload.get(position);
       final String selectedkey=selectedItem.getkey();
        StorageReference imageREf=firebaseStorage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageREf.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {              //deleting the image successfully
                mdatabaseref.child(selectedkey).removeValue();
                Toast.makeText(Buy.this, "Item deleted(Only upload can delete the image he uploaded feature coming soon))", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mdatabaseref.removeEventListener(mDBListener);
    }
}
