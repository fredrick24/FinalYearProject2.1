package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilePicture;
    private TextView profileName,profileEmail;
    private ImageView profileUpdate,changePassword;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth mAuth;
    private Animation atg,atgtwo,atgthree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth.getInstance();

        profilePicture =(ImageView)findViewById(R.id.ivProfilePic);
        profileEmail =(TextView)findViewById(R.id.tvProfileEmail);
        profileName=(TextView)findViewById(R.id.tvProfileName);
        profileUpdate=(ImageView) findViewById(R.id.btnProfileUpdate);
        changePassword=(ImageView) findViewById(R.id.btnChangePassword);

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



             firebaseAuth =FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference.child(firebaseAuth.getUid()).child("Images/Profile Picture").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //Retrieving URI
                    Picasso.get().load(uri).fit().centerCrop().into(profilePicture);
                }
            });


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                //Records any changes on database:
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    profileEmail.setText("UserEmail: " + userProfile.getUserEmail());
                    profileName.setText("Username: " + userProfile.getUserName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });

        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,UpdateProfile.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,UpdatePassword.class));
            }
        });

        //Pass Animations:

        profilePicture.startAnimation(atg);
        profileEmail.startAnimation(atgtwo);
        profileName.startAnimation(atgtwo);
        profileUpdate.startAnimation(atgtwo);
        changePassword.startAnimation(atgthree);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
