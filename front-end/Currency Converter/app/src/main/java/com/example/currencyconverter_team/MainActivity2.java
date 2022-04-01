package com.example.currencyconverter_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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
    String result2 = "";


    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            URL url;
            HttpURLConnection http;


            try{
                url = new URL(urls[0]);

                http = (HttpURLConnection) url.openConnection();
                http.setConnectTimeout(30000);


                InputStream in = http.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while( data != -1){
                    char current = (char) data;
                    result2 += current;
                    data = reader.read();

                }


            }catch(Exception e){
                e.printStackTrace();

                return null;
            }

            return result2;
        }


        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
//



            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }




//    public class DownloadTask2 extends AsyncTask<String, Void, String> {
//
//        protected String doInBackground(String... urls){
//            URL url;
//            HttpURLConnection http;
//
//
//            try{
//                url = new URL(urls[0]);
//
//                http = (HttpURLConnection) url.openConnection();
//                http.setConnectTimeout(30000);
//
//
//                InputStream in = http.getInputStream();
//
//                InputStreamReader reader = new InputStreamReader(in);
//
//                int data = reader.read();
//
//                while( data != -1){
//                    char current = (char) data;
//                    result2 += current;
//                    data = reader.read();
//
//                }
//
//
//            }catch(Exception e){
//                e.printStackTrace();
//
//                return null;
//            }
//
//            return result2;
//        }
//
//
//        protected void onPostExecute(String s){
//            super.onPostExecute(s);
//
//            try{
//
//                //  JSONObject json = new JSONObject(s);
//                //  String created_at = json.getString("amount_result");
//                Log.i("currency", s);
//
//                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
//
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }
//
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
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
                    String url = "http://10.21.149.208/apis/test.php?amount="+amount_res+"&currency=lbp";
                    DownloadTask task = new DownloadTask();
                    task.execute(url);



                }
                else
                {
                    amount_final= amount_res/22000;
                    Integer amnt= new Integer(amount_final);
                    String res=amnt.toString()+ " $";
                    String url = "http://10.21.149.208/apis/test.php?amount="+amount_res+"&currency=usd";
                    DownloadTask task = new DownloadTask();
                    task.execute(url);


                }

            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter an amount", Toast.LENGTH_LONG).show();
            result.setText("");

        }
    }


}