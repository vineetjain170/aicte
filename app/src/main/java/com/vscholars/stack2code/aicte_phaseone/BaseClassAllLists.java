package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class BaseClassAllLists extends DoubleNavigationWithoutEditText implements View.OnTouchListener{

    //these as the objects which hold the reference to all the views in the activity
    private ExpandableListView J_ListMain;
    private Button J_ButtonNext,J_ButtonPrev,J_actionSearch;
    private RelativeLayout J_SearchTab;
    private LinearLayout J_ListsController;
    private EditText J_keywords;

    private String[] yearList,selectedValues;
    private String message;
    private HashMap<Integer,String>categoryNames;
    private ArrayList<String>categoriesList;
    private jsonClasses executer;

    //these variables are used to keep track of user touch events
    private int J_xCoordinateUp,J_xCoordinateDown;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //initialize the activity according to the message
        message=getIntent().getStringExtra("activitySelected");
        getSupportActionBar().hide();
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setMessage("Loading data entries...");
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getData(message);
                keepACheck();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initializer(message);
                    }
                });
                pd.dismiss();
            }
        }).start();
    }

    private void getData(String message) {

        executer=new jsonClasses(message);
        selectedValues=getIntent().getStringArrayExtra("selectedValues");

        if (message.equals("faculty")){

            executer.J_json_facultyServer.execute(selectedValues);

        }else if (message.equals("approved_institutes")){

            executer.J_json_approvedInstitutions.execute(selectedValues);

        }else if (message.equals("closed_courses")){

            executer.J_json_closedCourses.execute(selectedValues);


        }else if (message.equals("closed_institutes")){

            executer.J_json_closedInstitutes.execute(selectedValues);

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            executer.J_json_NriPioFci.execute(selectedValues);

        }

    }

    void keepACheck(){

        try {
            while (executer.data==null) {
                Thread.sleep(500);
                if (executer.data!=null){
                    categoryNames=executer.categoriesNames;
                    categoriesList=new ArrayList<String>();
                    for (int i=0;i<categoryNames.size();++i){
                        categoriesList.add(categoryNames.get(i));
                    }

                    //J_ListMain.setAdapter(new AdapterForAllLists(BaseClassAllLists.this,message,categoryNames.size(),assignParameters(message),categoriesList,executer.categories,executer.data));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();

        }

    }

    public void initializer(final String message){

        //message is passed by DoubleNavigationDrawerWithoutEditText class when user selects an activity from main navigation drawer
        //actions specific to message are taken
        getSupportActionBar().show();
        setContentView(R.layout.activity_all_lists);

        J_ListMain=(ExpandableListView) findViewById(R.id.x_activity_all_lists_list_main);
        J_ButtonNext=(Button)findViewById(R.id.x_activity_all_lists_next_button);
        J_ButtonPrev=(Button)findViewById(R.id.x_activity_all_lists_prev_button);
        J_SearchTab=(RelativeLayout)findViewById(R.id.x_activity_all_lists_search_tab);
        J_ListsController=(LinearLayout)findViewById(R.id.x_activity_all_lists_list_controller);
        J_keywords=(EditText)findViewById(R.id.x_activity_all_lists_keywords);
        J_actionSearch=(Button)findViewById(R.id.x_activity_all_lists_action_search);

        yearList=getIntent().getStringArrayExtra("yearList");

        super.onCreateDrawer(BaseClassAllLists.this,message,yearList);
        J_ListMain.setAdapter(new AdapterForAllLists(BaseClassAllLists.this,message,categoryNames.size(),assignParameters(message),categoriesList,executer.categories,executer.data));
        J_ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
                count+=10;
                selectedValues[selectedValues.length-1]=count+"";
                Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                i.putExtra("activitySelected",message);
                i.putExtra("selectedValues",selectedValues);
                i.putExtra("yearList",yearList);
                startActivity(i);
            }
        });
        J_ButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
                if (count!=0) {
                    count -= 10;
                    selectedValues[selectedValues.length - 1] = count + "";
                    Intent i = new Intent(BaseClassAllLists.this, BaseClassAllLists.class);
                    i.putExtra("activitySelected", message);
                    i.putExtra("selectedValues", selectedValues);
                    i.putExtra("yearList", yearList);
                    startActivity(i);
                }

            }
        });

    }

    private int assignParameters(String message) {

        if(message.equals("approved_institutes")){

            return 7;

        }else if (message.equals("nri/pio-fn-ciwg/tp")||message.equals("closed_courses")){

            return 11;

        }else if (message.equals("faculty")){

            return 8;

        }else if (message.equals("closed_institutes")){

            return 7;

        }else {
            return 0;
        }


    }

    //this listener handles the user touch on list view
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction()==MotionEvent.ACTION_UP){

            //this event is generated when finger is removed from screen and stores the coordinate of the destination
            J_xCoordinateUp=(int)event.getX();
            UIHandler(J_xCoordinateUp,J_xCoordinateDown);

        }
        if (event.getAction()==MotionEvent.ACTION_DOWN){

            //this event is generated when user begins motion event and stores the source coordinate
            J_xCoordinateDown=(int)event.getX();

        }

        return true;
    }

    //this methods make changes to UI depending on the swipe actions to make it responsive
    void UIHandler(int upCoordinate,int downCoordinate){

        //this block depicts the swipe up motion on list view
        if(upCoordinate-downCoordinate<-1){

            J_SearchTab.setVisibility(View.INVISIBLE);
            J_ListsController.setVisibility(View.VISIBLE);
            getSupportActionBar().hide();
            J_xCoordinateUp=0;
            J_xCoordinateDown=0;

        //this block depicts the swpe down motion on list view
        }else if (upCoordinate-downCoordinate>1){

            J_SearchTab.setVisibility(View.VISIBLE);
            J_ListsController.setVisibility(View.INVISIBLE);
            getSupportActionBar().show();
            J_xCoordinateUp=0;
            J_xCoordinateDown=0;

        }else if (upCoordinate-downCoordinate==0){

        }

    }

    public void onBackPressed(){
        moveTaskToBack(true);
    }

}
