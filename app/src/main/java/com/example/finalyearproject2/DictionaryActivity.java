package com.example.finalyearproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Locale;

public class DictionaryActivity extends AppCompatActivity {

    private EditText edit1;
    private TextView D1;
    private Button define,btnTTS;
    private TextToSpeech tts1;
    String url;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        edit1 = (EditText) findViewById(R.id.etWord);
        D1 = (TextView) findViewById(R.id.tvDefine);
        define = (Button) findViewById(R.id.btnDefine);
        btnTTS = (Button) findViewById(R.id.btnTTS);

        tts1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts1.setLanguage(Locale.UK);
                }
            }
        });

        btnTTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak = edit1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                tts1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

        public void onPause(){
            if(tts1 != null){
                tts1.stop();
                tts1.shutdown();
            }
            super.onPause();
        }






    public void requestApiButtonClick(View view){

        MyDictionaryRequest myDictionaryRequest = new MyDictionaryRequest(this,D1);
        url = dictionaryEntries();
        myDictionaryRequest.execute(url);

    }

    private String dictionaryEntries() {
        final String language = "en-gb";
        final String word = edit1.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }


}
