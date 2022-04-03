package com.example.currencyconverter_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view){
        Intent intent= new Intent(this,MainActivity2.class);
        startActivity(intent); //The following function is used in order to take the user to MainActivity2
                               //upon clicking on the button "Start Converting"

    }
}