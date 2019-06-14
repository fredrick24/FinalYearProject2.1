package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter =3;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
   private ProgressDialog progressDialog;
   private  TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etUserPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login =(Button)findViewById(R.id.btnLogin);
        userRegistration =(TextView)findViewById(R.id.tvRegister);
        forgotPassword =(TextView)findViewById(R.id.tvForgotPassword);

        Info.setText("Number of Attempts Remaining: 3");

        firebaseAuth = FirebaseAuth.getInstance();
       progressDialog = new ProgressDialog(this);

        /* Checks if user has login to the application// */
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //Directs User to Registration Page:
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationActivity.class));
            }
        });

        System.out.println("The current user is: " + user);
        //Doesn't prompt user to enter credentials again:
       if(user != null){
           finish();
           startActivity(new Intent(MainActivity.this,SecondActivity.class));
       }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());

            }
        });

       forgotPassword.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this,PasswordActivity.class));
           }
       });






    }

    private void validate(String userName, String userPassword){

        progressDialog.setMessage("Verifying Details");
        progressDialog.show();
    firebaseAuth.signInWithEmailAndPassword(userName,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        if(task.isSuccessful()){
           progressDialog.dismiss();
          //  Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
            checkEmailVerification();
        }else{

            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
            counter--;
            Info.setText("Number of Attempts Remaining: " + counter);
            if(counter==0){
               progressDialog.dismiss();
                Login.setEnabled(false);
            }
        }
        }
    });
    }

    //Verifies User Email
    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();

        if(emailFlag){
            finish();
            startActivity(new Intent(MainActivity.this,SecondActivity.class));
        }else{
            Toast.makeText(MainActivity.this, "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
