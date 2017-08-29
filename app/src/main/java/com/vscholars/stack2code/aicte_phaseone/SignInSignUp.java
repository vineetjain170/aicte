package com.vscholars.stack2code.aicte_phaseone;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vineet_jain on 31/7/17.
 */

public class SignInSignUp extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText email,name,phoneNo;
    Button next;
    SignInButton googlePlus;
    jsonClasses executer;
    SharedPreferences sharedPreferences;
    GoogleApiClient googleApiClient;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getString("token",null)!=null){

            String[] params={sharedPreferences.getString("email",null),sharedPreferences.getString("token",null)};
            executer=new jsonClasses("smsLogin");
            executer.J_smsLogin.execute(params);
            final ProgressDialog pd = new ProgressDialog(SignInSignUp.this);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Logging you in...");
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.show();
            new Thread(new Runnable() {
                @Override
                public void run() {

                    while(executer.success==-1){

                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {

                        }

                    }
                    pd.dismiss();
                    if (executer.success==1){

                        Handler mHandler=new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message message) {

                                Intent intent=new Intent(SignInSignUp.this,mpin.class);
                                startActivity(intent);
                            }
                        };

                        Message message = mHandler.obtainMessage();
                        message.sendToTarget();

                    }else if (executer.success==0){

                        Handler mHandler=new Handler(Looper.getMainLooper()) {
                            @Override
                            public void handleMessage(Message message) {

                                Toast.makeText(SignInSignUp.this,"You have been logged out",Toast.LENGTH_LONG).show();
                                editor.putString("email",null);
                                editor.putString("token",null);
                                editor.commit();

                            }
                        };

                        Message message = mHandler.obtainMessage();
                        message.sendToTarget();

                    }

                }
            }).start();

        }
        initializer();

    }

    private void initializer() {

        getSupportActionBar().hide();
        setContentView(R.layout.signin_page);
        name=(EditText)findViewById(R.id.x_signup_screen_name);
        email=(EditText)findViewById(R.id.x_signup_screen_email);
        phoneNo=(EditText)findViewById(R.id.x_signup_screen_phoneno);
        next=(Button)findViewById(R.id.x_signup_screen_submit);
        googlePlus=(SignInButton)findViewById(R.id.sign_in_button);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(SignInSignUp.this).enableAutoManage(SignInSignUp.this,this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] values={name.getText().toString(),email.getText().toString(),phoneNo.getText().toString(),"generate"};
                executer=new jsonClasses("signUp");
                executer.J_signUp.execute(values);
                final ProgressDialog pd = new ProgressDialog(SignInSignUp.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Generating OTP...");
                pd.setIndeterminate(true);
                pd.setCancelable(false);
                pd.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while(executer.success==-1){

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {

                            }

                        }
                        pd.dismiss();
                        if (executer.success==1){

                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {

                                    Intent intent=new Intent(SignInSignUp.this,OtpVerify.class);
                                    intent.putExtra("email",email.getText().toString());
                                    startActivity(intent);
                                }
                            };

                            Message message = mHandler.obtainMessage();
                            message.sendToTarget();

                        }else if (executer.success==0){

                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {

                                    Toast.makeText(SignInSignUp.this,"Please Retry...",Toast.LENGTH_LONG).show();

                                }
                            };

                            Message message = mHandler.obtainMessage();
                            message.sendToTarget();

                        }

                    }
                }).start();
            }
        });

        googlePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(i,9001);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        GoogleSignInResult googleSignInResult=Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        handleresult(googleSignInResult);
    }
    private void handleresult(GoogleSignInResult result){
        if(result.isSuccess()==true){

            GoogleSignInAccount googleSignInAccount=result.getSignInAccount();
            final String g_email = googleSignInAccount.getEmail();
            final String g_name=googleSignInAccount.getDisplayName();
            name.setVisibility(View.INVISIBLE);
            email.setVisibility(View.INVISIBLE);
            googlePlus.setVisibility(View.INVISIBLE);
            next.setOnClickListener(null);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String[] values={g_name,g_email,phoneNo.getText().toString(),"generate"};
                    executer=new jsonClasses("signUp");
                    executer.J_signUp.execute(values);
                    final ProgressDialog pd = new ProgressDialog(SignInSignUp.this);
                    pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pd.setMessage("Generating OTP...");
                    pd.setIndeterminate(true);
                    pd.setCancelable(false);
                    pd.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            while(executer.success==-1){

                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {

                                }

                            }
                            pd.dismiss();
                            if (executer.success==1){

                                Handler mHandler=new Handler(Looper.getMainLooper()) {
                                    @Override
                                    public void handleMessage(Message message) {

                                        Intent intent=new Intent(SignInSignUp.this,OtpVerify.class);
                                        intent.putExtra("email",g_email);
                                        startActivity(intent);
                                    }
                                };

                                Message message = mHandler.obtainMessage();
                                message.sendToTarget();

                            }else if (executer.success==0){

                                Handler mHandler=new Handler(Looper.getMainLooper()) {
                                    @Override
                                    public void handleMessage(Message message) {

                                        Toast.makeText(SignInSignUp.this,"Please Retry...",Toast.LENGTH_LONG).show();

                                    }
                                };

                                Message message = mHandler.obtainMessage();
                                message.sendToTarget();

                            }

                        }
                    }).start();

                }
            });
        }
        else {

            Toast.makeText(SignInSignUp.this,"Data fetch error...",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Toast.makeText(SignInSignUp.this,"Please check your network",Toast.LENGTH_LONG).show();

    }
}
