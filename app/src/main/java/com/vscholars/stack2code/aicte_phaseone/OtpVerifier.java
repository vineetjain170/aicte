package com.vscholars.stack2code.aicte_phaseone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vineet_jain on 31/7/17.
 */

public class OtpVerifier extends AppCompatActivity{
    EditText otp;
    Button submit;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verify);


    }

}
