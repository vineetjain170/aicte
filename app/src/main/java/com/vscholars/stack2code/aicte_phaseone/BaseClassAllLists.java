package com.vscholars.stack2code.aicte_phaseone;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class BaseClassAllLists extends DoubleNavigationWithoutEditText implements View.OnTouchListener{

    //these as the objects which hold the reference to all the views in the activity
    private ListView J_ListMain;
    private Button J_ButtonNext,J_ButtonPrev;
    private RelativeLayout J_SearchTab,J_ListsController;
    private EditText J_keywords;
    private Button J_actionSearch;

    //these variables are used to keep track of user touch events
    private int J_xCoordinateUp,J_xCoordinateDown;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_lists);

        //message is passed by DoubleNavigationDrawerWithoutEditText class when user selects an activity from main navigation drawer
        //actions specific to message are taken
        String message=getIntent().getStringExtra("activitySelected");

        super.onCreateDrawer(BaseClassAllLists.this,message);

        //initialize the activity according to the message
        initializer(message);

    }
    public void initializer(String message){

        J_ListMain=(ListView)findViewById(R.id.x_activity_all_lists_list_main);
        J_ButtonNext=(Button)findViewById(R.id.x_activity_all_lists_next_button);
        J_ButtonPrev=(Button)findViewById(R.id.x_activity_all_lists_prev_button);
        J_SearchTab=(RelativeLayout)findViewById(R.id.x_activity_all_lists_search_tab);
        J_ListsController=(RelativeLayout)findViewById(R.id.x_activity_all_lists_list_controller);
        J_keywords=(EditText)findViewById(R.id.x_activity_all_lists_keywords);
        J_actionSearch=(Button)findViewById(R.id.x_activity_all_lists_action_search);

        //this code sets a listener to list view which takes action on swipe up and swipe down
        J_ListMain.setOnTouchListener(this);

        //this is the data received from server and is displayed using list view
        String data[][]=new String[0][0];
        J_ListMain.setAdapter(new AdapterForAllLists(BaseClassAllLists.this,message,data));

        //this method changes the title of action bar according to current activity
        ActionBarTitleChange(message);
    }

    private void ActionBarTitleChange(String message) {

        if(message.equals("approved_institutes")){

            getSupportActionBar().setTitle("Approved Institutes");
            J_keywords.setHint(null);

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            getSupportActionBar().setTitle("NRI/PIO-FN-CIWG/TP");
            J_keywords.setHint("Search College Here");

        }else if (message.equals("faculty")){

            getSupportActionBar().setTitle("Faculty");
            J_keywords.setHint(null);

        }else if (message.equals("closed_courses")){

            getSupportActionBar().setTitle("Closed Courses");
            J_keywords.setHint("Search College Here");

        }else if (message.equals("closed_institutes")){

            getSupportActionBar().setTitle("Closed Institutes");
            J_keywords.setHint("Search College Here");

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

        Intent intent=new Intent(BaseClassAllLists.this,MainActivity.class);
        startActivity(intent);

    }

}
