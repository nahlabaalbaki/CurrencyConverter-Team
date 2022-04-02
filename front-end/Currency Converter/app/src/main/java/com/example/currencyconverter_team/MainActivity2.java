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
    TextView rate;
    String result2 = "";
    String result3 = "";
    int api_rate;


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
                JSONObject json = new JSONObject(s);

                String amount_result = json.getString("result");
                String currency= json.getString("currency");
                if (currency.equalsIgnoreCase("lbp")){
                    result.setText("Result : " + amount_result + " L.L");

                }
                else
                {
                    result.setText("Result : " + amount_result + " $");

                }
                Log.i("amount", amount_result);
                Toast.makeText(getApplicationContext(), amount_result, Toast.LENGTH_LONG).show();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }




        public class DownloadTask2 extends AsyncTask<String, Void, String> {

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
                    result3 += current;
                    data = reader.read();

                }



                try{

                    JSONObject json = new JSONObject(result3);
                    String rate_result = json.getString("rate");
                    api_rate= Integer.parseInt(rate_result);
                    rate.setText("Rate: 1 USD at  " + rate_result + "L.L");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }catch(Exception e){
                e.printStackTrace();

                return null;
            }

            return result3;
        }


        protected void onPostExecute(String s){
            super.onPostExecute(s);

        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent x = getIntent();
        radioGrp= (RadioGroup) findViewById(R.id.radioGroup);
        lbp= (RadioButton) findViewById(R.id.lbp);
        usd= (RadioButton) findViewById(R.id.usd);
        result= (TextView) findViewById(R.id.result);
        rate= (TextView) findViewById(R.id.rate);



        String amount =  ""; //get the amount from the view
        String url = "http://192.168.1.13/apis/api_lirarate.php";
        DownloadTask2 task = new DownloadTask2();
        task.execute(url);



    }


    public void convert(View view){
        amount= (EditText)  findViewById(R.id.amount);
        int amount_res= Integer.parseInt(amount.getText().toString());


        if (!amount.getText().toString().isEmpty()) {
            int amount_final=0;

            if (radioGrp.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please check a currency", Toast.LENGTH_LONG).show();
            } else {
                if(lbp.isChecked()){
                    amount_final= amount_res*22000;
                    Integer amnt= new Integer(amount_final);
//                    String url2 = "http://10.21.149.208/apis/test.php?currency=lbp";
//                    DownloadTask2 task2 = new DownloadTask2();
//                    task2.execute(url2);
                    String res=amnt.toString()+ " L.L";
                    String url = "http://192.168.1.13/apis/test.php?amount="+amount_res+"&currency=lbp&rate=" + api_rate;
                    DownloadTask task = new DownloadTask();
                    task.execute(url);




                }
                else
                {
                    amount_final= amount_res/22000;
                    Integer amnt= new Integer(amount_final);
                    String res=amnt.toString()+ " $";
                    String url = "http://192.168.1.13/apis/test.php?amount="+amount_res+"&currency=usd&rate=" + api_rate;
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