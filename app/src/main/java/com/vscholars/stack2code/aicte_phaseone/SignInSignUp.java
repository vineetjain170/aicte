package com.vscholars.stack2code.aicte_phaseone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vineet_jain on 31/7/17.
 */

public class SignInSignUp extends AppCompatActivity{
    EditText email;
    Button signIn,signUp,googlePlus;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_signup_screen);
        email=(EditText)findViewById(R.id.x_signin_signup_screen_email);
        signIn=(Button)findViewById(R.id.x_signin_signup_screen_login);
        signUp=(Button)findViewById(R.id.x_signin_signup_screen_signup);
        googlePlus=(Button)findViewById(R.id.x_signin_signup_screen_google_plus);

    }

}
