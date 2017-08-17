package com.vscholars.stack2code.aicte_phaseone;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by vineet_jain on 31/7/17.
 */

public class SignInSignUp extends AppCompatActivity{
    EditText email;
    Button signIn;
    TextView signUp;
    //FloatingActionButton googlePlus;

    @Override
    public void onCreate(Bundle savedInstanceState){

        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_signup_screen);
        email=(EditText)findViewById(R.id.x_signin_signup_screen_email);
        signIn=(Button)findViewById(R.id.x_signin_signup_screen_login);
        signUp=(TextView)findViewById(R.id.x_signin_signup_screen_signup);
        //googlePlus=(FloatingActionButton) findViewById(R.id.x_signin_signup_screen_google_plus);

    }

}
