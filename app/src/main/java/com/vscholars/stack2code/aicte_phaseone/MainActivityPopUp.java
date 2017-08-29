package com.vscholars.stack2code.aicte_phaseone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vineet_jain on 4/8/17.
 */

public class MainActivityPopUp extends AppCompatActivity{

    ImageView J_img;
    TextView J_head;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_up_activity_main);
        getSupportActionBar().hide();
        getWindow().setLayout(500,500);
        J_img=(ImageView)findViewById(R.id.x_pop_up_activity_main_image);
        J_head=(TextView)findViewById(R.id.x_pop_up_activity_main_head);
        if(getIntent().getStringExtra("imageRes").equals("totalInstitutions")){

            J_img.setImageResource(R.drawable.dash_total_institutes);
            J_head.setText("Total Institutions");

        }else if(getIntent().getStringExtra("imageRes").equals("faculties")){

            J_img.setImageResource(R.drawable.dash_faculties);
            J_head.setText("Faculties");

        }else if(getIntent().getStringExtra("imageRes").equals("enrolment")){

            J_img.setImageResource(R.drawable.dash_enrollment);
            J_head.setText("Enrolment");

        }else if(getIntent().getStringExtra("imageRes").equals("studentsPassed")){

            J_img.setImageResource(R.drawable.dash_students_passed);
            J_head.setText("Student Passed");

        }else if(getIntent().getStringExtra("imageRes").equals("placements")){

            J_img.setImageResource(R.drawable.dash_placement);
            J_head.setText("Placements");

        }else if(getIntent().getStringExtra("imageRes").equals("totalIntake")){

            J_img.setImageResource(R.drawable.dash_total_intake);
            J_head.setText("Total Intake");

        }else if(getIntent().getStringExtra("imageRes").equals("newInstitutions")){

            J_img.setImageResource(R.drawable.dash_new_institutes);
            J_head.setText("New Institutions");

        }else if(getIntent().getStringExtra("imageRes").equals("closedInstitutions")){

            J_img.setImageResource(R.drawable.dash_closed_institutions);
            J_head.setText("Closed Institutions");

        }

    }

}
