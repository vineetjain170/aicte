package com.vscholars.stack2code.aicte_phaseone;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OtpVerify extends AppCompatActivity{
    EditText otp;
    Button submit,resend;
    jsonClasses executer;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.otp_page);
        otp=(EditText)findViewById(R.id.x_otp_verify_otp_input);
        resend=(Button)findViewById(R.id.x_otp_resend_otp);
        submit=(Button)findViewById(R.id.x_otp_verify_submit);
        final String email=getIntent().getStringExtra("email");

        new Thread(new Runnable() {
            @Override
            public void run() {

                resend.setOnClickListener(null);
                resend.setBackgroundColor(Color.GRAY);
                final int[] slept = {0};
                while(slept[0] <300000) {
                    try {

                        Thread.sleep(100);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                slept[0] = slept[0] + 100;
                                resend.setText("Resend OTP in "+((300000 - slept[0]) / 1000) + " seconds...");

                            }
                        });
                    } catch (InterruptedException e) {


                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        resend.setBackgroundColor(Color.RED);
                        resend.setText("RESEND OTP");
                        submit.setOnClickListener(null);
                        submit.setBackgroundColor(Color.GRAY);
                        resend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                executer=new jsonClasses("otpResend");
                                String[] params={email,"resend"};
                                executer.J_otpResend.execute(params);
                                final ProgressDialog pd = new ProgressDialog(OtpVerify.this);
                                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pd.setMessage("Fetching another OTP...");
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

                                            Intent intent=new Intent(OtpVerify.this,OtpVerify.class);
                                            startActivity(intent);

                                        }else if (executer.success==0){

                                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                                @Override
                                                public void handleMessage(Message message) {
                                                    Toast.makeText(OtpVerify.this,"Some error occured...",Toast.LENGTH_LONG).show();
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
                });

            }
        }).start();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otpInput=otp.getText().toString();
                String[] params={email,otpInput,"validate"};
                executer=new jsonClasses("otpVerify");
                executer.J_otpVerify.execute(params);
                final ProgressDialog pd = new ProgressDialog(OtpVerify.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Verifying OTP...");
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
                                    sharedPreferences=getSharedPreferences("cache", Context.MODE_PRIVATE);
                                    final SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email",email);
                                    editor.putString("token",executer.registrationStatus);
                                    editor.commit();
                                    Toast.makeText(OtpVerify.this,"success",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(OtpVerify.this,mpin.class);
                                    startActivity(intent);
                                }
                            };

                            Message message = mHandler.obtainMessage();
                            message.sendToTarget();

                        }else if (executer.success==0){

                            Handler mHandler=new Handler(Looper.getMainLooper()) {
                                @Override
                                public void handleMessage(Message message) {
                                    Toast.makeText(OtpVerify.this,"Wrong OTP",Toast.LENGTH_LONG).show();
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

}