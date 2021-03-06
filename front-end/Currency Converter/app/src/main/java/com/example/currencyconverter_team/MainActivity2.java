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



    //the class DownloadTask is responsible for sending the user's input and the rate (already received

    // and saved into api_rate) to getAmount.php
    //which in its turn returns the calculated amount.
    public class DownloadTask extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            URL url;
            HttpURLConnection http;


            try{
                url = new URL(urls[0]);

                http = (HttpURLConnection) url.openConnection(); // connection established between our application and API
                http.setConnectTimeout(30000);


                InputStream in = http.getInputStream();

                InputStreamReader reader = new InputStreamReader(in); // used to read the output of the API

                int data = reader.read();

                while( data != -1){
                    char current = (char) data; // parse each character
                    result2 += current; //add this character to array
                    data = reader.read(); //Data is changed not to run into infinite loop (not optimal solution)

                }


            }catch(Exception e){
                e.printStackTrace();

                return null;
            }

            return result2;
        }


        protected void onPostExecute(String s){ // We get the string returned from the function above using onPostExecute
            super.onPostExecute(s);

            try{
                //Handle the data(fetch and convert string s to json)

                JSONObject json = new JSONObject(s);
                //extract the result calculated by getAmount.php from the json object
                //and display it to string while modifying the text based on the currency used.
                String amount_result = json.getString("result");
                String currency= json.getString("currency");
                if (currency.equalsIgnoreCase("lbp")){
                    result.setText("Result : " + amount_result + " L.L");

                }
                else
                {
                    result.setText("Result : " + amount_result + " $");

                }

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }




    //Download task2 is responsible for getting the exchange rate from api_lirarate.php

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


                    //once the data is read and saved into result3, we turned it into a json object
                    //and extracted the rate and saved it in api_rate, we also made it appear on screen.
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




        //we laod this url on create so that the exchange rate can appear on screen
        // once activity 2 is initialized.
        String url = "http://192.168.1.13/apis/api_lirarate.php";
        DownloadTask2 task = new DownloadTask2();
        task.execute(url);



    }


    public void convert(View view){
        //We start by getting the amount entered by the user;
        amount= (EditText)  findViewById(R.id.amount);
        int amount_res= Integer.parseInt(amount.getText().toString());

        //if the user's input is not empty, we proceed with the following:
        if (!amount.getText().toString().isEmpty()) {
            int amount_final=0;
            //The radio buttons are usede to select the currency for which the user
            //wishes to convert the amount entered to.
            //If no button is selected, the application toasts a msg asking the user
            //to select one currency;
            if (radioGrp.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please check a currency", Toast.LENGTH_LONG).show();
            } else {
                if(lbp.isChecked()){
                    //we then create a new DownloadTask which is reponsible for gathering the
                    //user's input (as mentionned above) and sending it in the url to  getAmount.php
                    //which returns the final result and displays it to the screen.
                    String url = "http://192.168.1.13/apis/getAmount.php?amount="+amount_res+"&currency=lbp&rate=" + api_rate;
                    DownloadTask task = new DownloadTask();
                    task.execute(url);




                }
                else
                {

                    //Same as above but in the case the currency is in USD.
                    String url = "http://192.168.1.13/apis/getAmount.php?amount="+amount_res+"&currency=usd&rate=" + api_rate;
                    DownloadTask task = new DownloadTask();
                    task.execute(url);


                }

            }
        }

        //If the user did not enter an amount, a message appears requesting the user to enter an amount;
        else {
            Toast.makeText(getApplicationContext(), "Please enter an amount", Toast.LENGTH_LONG).show();
            result.setText("");

        }
    }


}