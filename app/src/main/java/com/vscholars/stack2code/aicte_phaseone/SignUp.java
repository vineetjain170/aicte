package com.vscholars.stack2code.aicte_phaseone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vineet_jain on 31/7/17.
 */

public class SignUp extends AppCompatActivity{

    EditText name,email,phoneno;
    Button submit;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);
        name=(EditText)findViewById(R.id.x_signup_screen_name);
        email=(EditText)findViewById(R.id.x_signup_screen_email);
        phoneno=(EditText)findViewById(R.id.x_signup_screen_phoneno);
        submit=(Button)findViewById(R.id.x_signup_screen_submit);

    }

}
