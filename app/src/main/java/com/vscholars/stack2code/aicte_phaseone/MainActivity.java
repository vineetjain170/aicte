package com.vscholars.stack2code.aicte_phaseone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
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
    TextView J_totalInstitutions,J_faculties,J_enrollment,J_studentPassed,J_totalIntake,J_placements;

    //This is the main activity of AICTE Dashboard to display statistics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
        initializer();
        setValue();

    }

    private void getData() {
        selectedValues=getIntent().getStringArrayExtra("selectedValues");
        jsonClasses executer=new jsonClasses("dashboard");
        executer.J_json_dashboardData.execute(selectedValues);
        try {
            while (executer.defaultValuesDashboard[5]==null) {
                Thread.sleep(500);
                if (executer.defaultValuesDashboard[5] != null) {
                    parameterValues = executer.defaultValuesDashboard;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setValue() {
        J_totalInstitutions.setText(parameterValues[5]);
        J_faculties.setText(parameterValues[0]);
        J_enrollment.setText(parameterValues[3]);
        J_studentPassed.setText(parameterValues[2]);
        J_totalIntake.setText(parameterValues[4]);
        J_placements.setText(parameterValues[1]);
    }

    private void initializer() {

        setContentView(R.layout.activity_main);

        J_totalInstitutions=(TextView)findViewById(R.id.x_activity_main_total_institutions);
        J_faculties=(TextView)findViewById(R.id.x_activity_main_faculties);
        J_enrollment=(TextView)findViewById(R.id.x_activity_main_enrollment);
        J_studentPassed=(TextView)findViewById(R.id.x_activity_main_student_passed);
        J_totalIntake=(TextView)findViewById(R.id.x_activity_main_total_intake);
        J_placements=(TextView)findViewById(R.id.x_activity_main_placement);

        Window window = MainActivity.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.BLACK);

        String[] J_yearList=getIntent().getStringArrayExtra("yearList");

        //This message is passed between activities to indicate which element of dashboard is under consideration
        super.onCreateDrawer(MainActivity.this,"dashboard",J_yearList);

    }
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
