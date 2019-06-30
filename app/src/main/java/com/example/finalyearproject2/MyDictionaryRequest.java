package com.example.finalyearproject2;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


    public class MyDictionaryRequest extends AsyncTask<String,Integer,String> {


    final String app_id = "05fb067e";
    final String app_key = "e5bdc5be07010d4d6c99802a7b5448d6";
        private final Object TextView;
        String myurl;
    Context context;
    private TextView D1;

        MyDictionaryRequest(Context context,TextView D1){
     this.context = context;
    TextView = D1;

 }


    @Override
    protected String doInBackground(String... params) {

        myurl = params[0];
        String result;
        try {

            URL url = new URL(myurl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            result = stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
        
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String def;
        try {
            JSONObject js = new JSONObject(s);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject jsonObject = e.getJSONObject(0);
            JSONArray sensesArray = jsonObject.getJSONArray("senses");

            JSONObject d = sensesArray.getJSONObject(0);
            JSONArray de = d.getJSONArray("definitions");

           def = de.getString(0);


           Toast.makeText(context,def,Toast.LENGTH_SHORT).show();

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}