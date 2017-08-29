package com.vscholars.stack2code.aicte_phaseone;

import android.animation.ValueAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends DoubleNavigationWithoutEditText{

    String[] parameterValues,selectedValues;
    TextView J_totalInstitutions,J_faculties,J_enrollment,J_studentPassed,J_totalIntake,J_placements,J_newInstitutes,J_closedInstitutes;
    ImageView J_totalInstitutesImg,J_facultiesImg,J_enrolmentImg,J_studentPassedImg,J_placementsImg,J_totalIntakeImg,J_newInstitutionsImg,J_closedInstitutionsImg;
    int slept=0;

    //This is the main activity of AICTE Dashboard to display statistics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading Dashboard data...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializer();
                        setValue();
                    }
                });
                pd.dismiss();
            }
        }).start();
    }

    private void getData() {
        selectedValues=getIntent().getStringArrayExtra("selectedValues");
        jsonClasses executer=new jsonClasses("dashboard");
        executer.J_json_dashboardData.execute(selectedValues);
        try {
            while (executer.defaultValuesDashboard[5]==null) {
                if(slept<10000) {
                    Thread.sleep(500);
                    if (executer.defaultValuesDashboard[5] != null) {
                        parameterValues = executer.defaultValuesDashboard;
                    }else {

                        slept=slept+500;

                    }
                }else{

                    break;

                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setValue() {

        if(parameterValues!=null) {

            int max=-1;
            for(int i=0;i<8;++i){

                if(max<=Integer.parseInt(parameterValues[i])){

                    max=Integer.parseInt(parameterValues[i]);

                }

            }

            final int finalMax = max;
            new Thread(new Runnable() {
                @Override
                public void run() {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                ValueAnimator animator8 = ValueAnimator.ofInt(0,finalMax);
                                animator8.setDuration(10000);
                                animator8.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public void onAnimationUpdate(ValueAnimator animation) {

                                        int i=Integer.parseInt(animation.getAnimatedValue().toString());
                                        if (i <= Integer.parseInt(parameterValues[5])) {
                                            J_totalInstitutions.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[0])) {
                                            J_faculties.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[3])) {
                                            J_enrollment.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[2])) {
                                            J_studentPassed.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[4])) {
                                            J_totalIntake.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[1])) {
                                            J_placements.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[6])) {
                                            J_newInstitutes.setText(i + "");
                                        }
                                        if (i <= Integer.parseInt(parameterValues[7])) {
                                            J_closedInstitutes.setText(i + "");
                                        }

                                    }
                                });
                                animator8.start();

                            }
                        });

                }
            }).start();

        }else {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Looks like server isn't responding...please check your mobile data")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            intent.putExtra("yearList",getIntent().getStringArrayExtra("yearList"));
                            intent.putExtra("selectedValues",selectedValues);
                            startActivity(intent);

                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            System.exit(0);

                        }
                    });
            builder.create();
            builder.setCancelable(false);
            builder.show();

        }
    }

    private void initializer() {

        getSupportActionBar().show();
        setContentView(R.layout.activity_main);
        J_totalInstitutions=(TextView)findViewById(R.id.x_activity_main_total_institutions);
        J_faculties=(TextView)findViewById(R.id.x_activity_main_faculties);
        J_enrollment=(TextView)findViewById(R.id.x_activity_main_enrollment);
        J_studentPassed=(TextView)findViewById(R.id.x_activity_main_student_passed);
        J_totalIntake=(TextView)findViewById(R.id.x_activity_main_total_intake);
        J_placements=(TextView)findViewById(R.id.x_activity_main_placement);
        J_newInstitutes=(TextView)findViewById(R.id.x_activity_main_new_institutes);
        J_closedInstitutes=(TextView)findViewById(R.id.x_activity_main_closed_institutes);
        J_totalInstitutesImg=(ImageView)findViewById(R.id.x_activity_main_total_institutions_img);
        J_facultiesImg=(ImageView)findViewById(R.id.x_activity_main_faculties_img);
        J_enrolmentImg=(ImageView)findViewById(R.id.x_activity_main_enrolment_img);
        J_studentPassedImg=(ImageView)findViewById(R.id.x_activity_main_student_passed_img);
        J_placementsImg=(ImageView)findViewById(R.id.x_activity_main_placements_img);
        J_totalIntakeImg=(ImageView)findViewById(R.id.x_activity_main_total_intake_img);
        J_newInstitutionsImg=(ImageView)findViewById(R.id.x_activity_main_new_institutes_img);
        J_closedInstitutionsImg=(ImageView)findViewById(R.id.x_activity_main_closed_institutes_img);

        Window window = MainActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);

        String[] J_yearList=getIntent().getStringArrayExtra("yearList");

        //This message is passed between activities to indicate which element of dashboard is under consideration
        super.onCreateDrawer(MainActivity.this,"dashboard",J_yearList);

        J_totalInstitutesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","totalInstitutions");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_facultiesImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","faculties");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_enrolmentImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","enrolment");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_studentPassedImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","studentsPassed");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_placementsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","placements");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_totalIntakeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","totalIntake");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_newInstitutionsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","newInstitutions");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });
        J_closedInstitutionsImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,MainActivityPopUp.class);
                intent.putExtra("imageRes","closedInstitutions");
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

    }
}
