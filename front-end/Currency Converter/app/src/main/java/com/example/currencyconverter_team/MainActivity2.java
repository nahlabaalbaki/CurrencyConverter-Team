package com.example.currencyconverter_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity2 extends AppCompatActivity {

    RadioGroup radioGrp;
    RadioButton lbp;
    RadioButton usd;
    EditText amount;
    TextView result;

    public class DownloadTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {

            String result="";

            URL url;
            HttpURLConnection http;

            try{
                //Reading json Objects from API
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection(); // connection established between our application and API
                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in); // used to read the output of the API
                int data =  reader.read();

                while(data != -1){
                    char current = (char) data; // parse each character
                    result += current; //add this character to array
                    data =reader.read(); //Data is changed not to run into infinite loop (not optimal solution)

                }
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) { // We get the string returned from the function above
            Log.i("Result", s);

            //Handle the data(fetch and convert string s to json)
            //Use this function to post the rate on the screen from the website lirarate.com
            try {
                JSONObject json = new JSONObject(s);
                String created_at = json.getString("created_at");
                Log.i("created_at",created_at);

            }catch(Exception e){

            }

            super.onPostExecute(s);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        String amount1 = "44";//get the amount from the view
        String url="http://localhost/Server/api1.php?amount =" + amount1;

        DownloadTask task = new DownloadTask();
        task.execute(url);



        Intent x = getIntent();
        radioGrp= (RadioGroup) findViewById(R.id.radioGroup);
        amount= (EditText)  findViewById(R.id.amount);
        lbp= (RadioButton) findViewById(R.id.lbp);
        usd= (RadioButton) findViewById(R.id.usd);
        result= (TextView) findViewById(R.id.result);

    }


    public void convert(View view){
        if (!amount.getText().toString().isEmpty()) {
            int amount_res= Integer.parseInt(amount.getText().toString());
            int amount_final=0;

            if (radioGrp.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please check a currency", Toast.LENGTH_LONG).show();
            } else {
                if(lbp.isChecked()){
                    amount_final= amount_res*22000;
                    Integer amnt= new Integer(amount_final);
                    String res=amnt.toString()+ " L.L";
                    result.setText(res);


                }
                else
                {
                    amount_final= amount_res/22000;
                    Integer amnt= new Integer(amount_final);
                    String res=amnt.toString()+ " $";
                    result.setText(res);

                }

            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter an amount", Toast.LENGTH_LONG).show();
            result.setText("");

        }
    }


}