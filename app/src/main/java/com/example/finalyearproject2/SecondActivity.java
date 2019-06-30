package com.example.finalyearproject2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private Button Dict;



    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        firebaseAuth.getInstance();

        Dict = (Button)findViewById(R.id.btnDictionary);

       Dict.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(SecondActivity.this,DictionaryActivity.class));
           }
       });

    }



    //links Menu Layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logoutMenu: {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
            case R.id.profileMenu: {
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
            }

        }
        return super.onOptionsItemSelected(item);
    }
}

