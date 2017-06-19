package com.vscholars.stack2code.aicte_phaseone;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends DoubleNavigationWithoutEditText {

    //This is the main activity of AICTE Dashboard to display statistics

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializer();


    }

    private void initializer() {

        setContentView(R.layout.activity_main);

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
