package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdatePassword extends AppCompatActivity {

    private Button update;
    private EditText newPassword;
    private FirebaseUser firebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        update = (Button)findViewById(R.id.btnChangePassword);
        newPassword =(EditText)findViewById(R.id.etNewPassword);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();




        update.setOnClickListener(new View.OnClickListener() {

            String userPasswordNew = newPassword.getText().toString();
            @Override
            public void onClick(View view) {
                firebaseUser.updatePassword(userPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(UpdatePassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(UpdatePassword.this,"Password has not been updated", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


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
