package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePassword extends AppCompatActivity {

    private Button update;
    private ImageView gridlock;
    private EditText currentPassword,newPassword;
    private FirebaseUser firebaseUser;
    private Animation atg,atgtwo,atgthree;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        update = (Button)findViewById(R.id.btnUpdatePassword);
        newPassword =(EditText)findViewById(R.id.etNewPassword);
        currentPassword =(EditText)findViewById(R.id.etCurrentPassword);
        gridlock=(ImageView)findViewById(R.id.ivLock);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




        update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("The password field is: " + newPassword.getText().toString());
                if(newPassword.getText().toString().isEmpty() || currentPassword.getText().toString().isEmpty()){
                    Toast.makeText(UpdatePassword.this, "Please Fill Required Fields", Toast.LENGTH_SHORT).show();
                }else{
                    AuthCredential credential = EmailAuthProvider.getCredential(firebaseUser.getEmail(), currentPassword.getText().toString());
                    firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                firebaseUser.updatePassword(""+newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(UpdatePassword.this, "Password Updated Successfully.", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(UpdatePassword.this, "Password Update Failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(UpdatePassword.this, "Password Update Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });


           //Animations
        gridlock.startAnimation(atg);
        currentPassword.startAnimation(atg);
        newPassword.startAnimation(atgtwo);
        update.startAnimation(atgthree);



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
