package com.vscholars.stack2code.aicte_phaseone;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private Thread splashTread;
    private String[] j_yearList;
    private   String str=" ";
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

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.gear.host/colleges/year_select.php", "POST", params);

                int success=jsonO.getInt("success");

                if (success == 1) {
                    int count=jsonO.getInt("count");

                    j_yearList=new String[count];
                    JSONArray jsonA= jsonO.getJSONArray("ayear");
                    //str=jsonA.toString();

                    for(int i=1;i<=count;i++){

                        JSONObject temp =jsonA.getJSONObject(i-1);
                        j_yearList[i-1]=temp.getString(""+i);


                    }


                } else {

                    //Toast.makeText(SplashScreen.this,"Error Occured Please try after sometime",Toast.LENGTH_LONG).show();

                }


            } catch (Exception e) {
                e.printStackTrace();

                  //Toast.makeText(SplashScreen.this,"Please Check Network",Toast.LENGTH_LONG).show();

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            intent.putExtra("yearList",j_yearList);
            startActivity(intent);
            //SplashScreen.this.finish();

        }
    }

}
