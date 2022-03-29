package com.example.currencyconverter_team;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    RadioGroup radioGrp;
    RadioButton lbp;
    RadioButton usd;
    EditText amount;
    TextView result;


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
       /* if (lbp_input.isEmpty() && usd_input.isEmpty() ){
            Toast.makeText(getApplicationContext(),"Please enter an amount", Toast.LENGTH_LONG).show();
        }
        else if (!lbp_input.isEmpty() && !usd_input.isEmpty() ){
            Toast.makeText(getApplicationContext(),"Please choose one currency.", Toast.LENGTH_LONG).show();
        }
        else if (!lbp_input.isEmpty() && usd_input.isEmpty() ) {
            float lbp_int= (float) Integer.parseInt(lbp_input);
            float usd_int= lbp_int/22000;
            lbp.setText();
        }
*/

}