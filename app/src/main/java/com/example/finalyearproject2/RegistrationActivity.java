package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userName,userPassword,userEmail;
    private Button regButton;
    private TextView userLogin;
    private ImageView userProfilePic;
    private FirebaseAuth firebaseAuth;
    String name,email,password;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE =123;
    Uri imagePath;
    private StorageReference storageReference;
    private Animation atg,atgtwo,atgthree;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //Checks any Functions implemented is successful
        if(requestCode ==PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                userProfilePic.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference =firebaseStorage.getReference();

        userProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE);
            }
        });



        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    //Uploads to Firebase:
                    String user_email =userEmail.getText().toString().trim();
                    String user_password =userPassword.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                sendEmailVerification();
                            }else{
                                Toast.makeText(RegistrationActivity.this, "Registration  Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            }
        });

        //Animations
        userName.startAnimation(atg);
        userPassword.startAnimation(atg);
        userEmail.startAnimation(atg);
        regButton.startAnimation(atgtwo);
        userLogin.startAnimation(atgthree);
        userProfilePic.startAnimation(atg);
    }

    private void setupUIViews(){
       userName=(EditText)findViewById(R.id.etUserName);
       userPassword =(EditText)findViewById(R.id.etUserPassword);
        userEmail=(EditText)findViewById(R.id.etEmail);
        regButton=(Button)findViewById(R.id.btnRegister);
        userLogin=(TextView)findViewById(R.id.tvUserLogin);
        userProfilePic=(ImageView)findViewById(R.id.ivProfile);
    }

    private boolean validate(){
            Boolean result = false;

             name =userName.getText().toString();
             password =userPassword.getText().toString();
             email=userEmail.getText().toString();

            if((name.isEmpty()) || (email.isEmpty()) || password.isEmpty() || imagePath == null ){
                Toast.makeText(this,"Please enter the required fields", Toast.LENGTH_SHORT).show();
            }else{
                result=true;
            }
         return result;
        }

        private void sendEmailVerification(){
            final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if(firebaseUser != null){
                firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if(task.isSuccessful()){
                          sendUserData();
                          Toast.makeText(RegistrationActivity.this,"Registration is Successful,a verification Email has been sent to you.",Toast.LENGTH_SHORT).show();
                          firebaseAuth.signOut();
                          finish();
                          startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
                      }else{
                          Toast.makeText(RegistrationActivity.this,"Email Verification has not been sent.",Toast.LENGTH_SHORT).show();
                      }
                    }
                });
            }
        }

        private void sendUserData(){
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            //Gets the UID from Firebase
            DatabaseReference myRef = firebaseDatabase.getReference(firebaseAuth.getUid());
            StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Picture");//User ID/Images/profile_pic
            UploadTask uploadTask = imageReference.putFile(imagePath);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegistrationActivity.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(RegistrationActivity.this, "Upload Success!", Toast.LENGTH_SHORT).show();
                }
            });
            UserProfile userProfile = new UserProfile(email,name);
            myRef.setValue(userProfile);
        }

}

