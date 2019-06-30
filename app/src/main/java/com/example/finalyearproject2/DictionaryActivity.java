package com.example.finalyearproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DictionaryActivity extends AppCompatActivity {

    private EditText edit1;
    private TextView D1;
    String url;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        edit1 =(EditText)findViewById(R.id.etWord);
        D1 = (TextView)findViewById(R.id.tvDefine);

    }


    public void requestApiButtonClick(View view){

        MyDictionaryRequest myDictionaryRequest = new MyDictionaryRequest(this,D1);
        url = dictionaryEntries();
        myDictionaryRequest.execute(edit1.getText().toString());

    }

    private String dictionaryEntries() {
        final String language = "en-gb";
        final String word = "Word.getText().toString()";
        final String fields = "pronunciations";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }


}
