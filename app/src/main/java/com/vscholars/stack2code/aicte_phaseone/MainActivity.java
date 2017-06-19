package com.vscholars.stack2code.aicte_phaseone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends DoubleNavigationWithoutEditText {

    //This is the main activity of AICTE Dashboard to display statistics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] J_yearList=getIntent().getStringArrayExtra("yearList");

        //This message is passed between activities to indicate which element of dashboard is under consideration
        super.onCreateDrawer(MainActivity.this,"dashboard",J_yearList);

    }
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}
