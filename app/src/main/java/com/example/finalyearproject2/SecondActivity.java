package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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

public class SecondActivity extends AppCompatActivity {

    private ImageView Dict;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private Animation atg, atgtwo, atgthree;


    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ImageView profilePicture2;
    private TextView profileEmail2, profileName2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        profilePicture2 = (ImageView) findViewById(R.id.ivProfilePic2);
        profileEmail2 = (TextView) findViewById(R.id.tvProfileEmail2);
        profileName2 = (TextView) findViewById(R.id.tvProfileName2);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        mAuth = firebaseAuth.getInstance();


            //Get Database Storage Reference from Firebase
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseStorage = FirebaseStorage.getInstance();

            //Opens Up Dictionary Function
            Dict = (ImageView) findViewById(R.id.btnDictionary);
            Dict.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(SecondActivity.this, DictionaryActivity.class));
                }
            });

            //Animation
            Dict.startAnimation(atgtwo);

        }

        //links Menu Layout
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;

        }

        @Override
        public boolean onOptionsItemSelected (@NonNull MenuItem item){

            switch (item.getItemId()) {
                case R.id.logoutMenu: {
                    Logout();
                }
                case R.id.profileMenu: {
                    {
                        startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                    }
                }

            }
            return super.onOptionsItemSelected(item);
        }

        private void Logout () {
            mAuth.signOut();
            startActivity(new Intent(SecondActivity.this, MainActivity.class));
            finish();
        }


    }



