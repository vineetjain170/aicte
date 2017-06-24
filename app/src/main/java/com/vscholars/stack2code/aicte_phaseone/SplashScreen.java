package com.vscholars.stack2code.aicte_phaseone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by shaggy on 12-06-2017.
 */

public class SplashScreen extends AppCompatActivity{
    private String[] j_yearList;
    private ProgressBar J_DataLoading;
    private TextView J_DataLoadingText;

    @Override
        public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initializer();

        if(haveNetworkConnection()) {
            new json_yearFetch().execute("");
            J_DataLoading.setVisibility(View.VISIBLE);
            J_DataLoadingText.setVisibility(View.VISIBLE);
        }
        else{
            Toast.makeText(SplashScreen.this,"no network",Toast.LENGTH_LONG).show();
            J_DataLoading.setVisibility(View.INVISIBLE);
            J_DataLoadingText.setVisibility(View.INVISIBLE);
         }

    }

    private void initializer() {

        setContentView(R.layout.splash_screen);

        Window window = SplashScreen.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0);

        J_DataLoading=(ProgressBar)findViewById(R.id.x_splash_screen_progress_bar);
        J_DataLoadingText=(TextView)findViewById(R.id.x_splash_screen_loading_text);

        //actionbar hide
        ActionBar ac = getSupportActionBar();
        ac.hide();

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //JSON code to fetch year
    protected class json_yearFetch extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonA =JSONParser.makeHttpRequest("http://anurag.gear.host/colleges/year_select.php", "POST", params);

                int success=jsonA.getInt("success");
                if (success == 1) {
                    int count=jsonA.getInt("count");

                    j_yearList=new String[count];
                    JSONArray jsona= jsonA.getJSONArray("ayear");

                    for(int i=1;i<=count;i++){

                        JSONObject temp =jsona.getJSONObject(i-1);
                        j_yearList[i-1]=temp.getString(""+i);

                    }


                } else {

                    Toast.makeText(SplashScreen.this,"Error Occured Please try after sometime",Toast.LENGTH_LONG).show();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            SplashScreen.this.finish();
            String[] defaultValues={"1","1","1","1","1","1","1"};
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putExtra("yearList",j_yearList);
            intent.putExtra("selectedValues",defaultValues);
            startActivity(intent);
        }
    }
}
