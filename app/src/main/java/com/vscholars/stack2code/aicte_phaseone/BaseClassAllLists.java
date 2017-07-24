package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class BaseClassAllLists extends DoubleNavigationWithoutEditText{

    //these as the objects which hold the reference to all the views in the activity
    private ExpandableListView J_ListMain;
    private Button J_ButtonNext,J_ButtonPrev,J_actionSearch;
    private RelativeLayout J_SearchTab;
    private RelativeLayout J_ListsController;
    private EditText J_keywords,J_pageNo;
    private TextView J_totalPages;

    private String[] yearList,selectedValues;
    private String message;
    private HashMap<Integer,String>categoryNames;
    private ArrayList<String>categoriesList;
    private jsonClasses executer;
    private String totalPages;

    //these variables are used to keep track of user touch events
    private int mLastFirstVisibleItem=0;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //initialize the activity according to the message
        message=getIntent().getStringExtra("activitySelected");
        if(getIntent().getStringExtra("totalEntries")!=null){

            totalPages=getIntent().getStringExtra("totalEntries");

        }
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
                }else if (executer.categoriesNames!=null){
                    if(executer.categoriesNames.get(-1)!=null)
                        break;
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
        J_ListsController=(RelativeLayout)findViewById(R.id.x_activity_all_lists_list_controller);
        J_keywords=(EditText)findViewById(R.id.x_activity_all_lists_keywords);
        J_actionSearch=(Button)findViewById(R.id.x_activity_all_lists_action_search);
        J_totalPages=(TextView)findViewById(R.id.x_activity_all_lists_total_pages);
        J_pageNo=(EditText)findViewById(R.id.x_activity_all_lists_page_no);

        int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
        if (count==0){
            J_ButtonPrev.setVisibility(View.INVISIBLE);
        }else {
            J_ButtonPrev.setVisibility(View.VISIBLE);
        }
        if(totalPages!=null) {
            if (count + 20 > Integer.parseInt(totalPages)) {
                J_ButtonNext.setVisibility(View.INVISIBLE);
            } else {
                J_ButtonNext.setVisibility(View.VISIBLE);
            }
        }
        J_pageNo.setText((count/20)+"");

        yearList=getIntent().getStringArrayExtra("yearList");

        super.onCreateDrawer(BaseClassAllLists.this,message,yearList);
        if (executer.categoriesNames.get(-1)!=null){
            if (executer.categoriesNames.get(-1).equals("-1")) {
                RelativeLayout noMoreData = (RelativeLayout) findViewById(R.id.x_noMoreData);
                noMoreData.setVisibility(View.VISIBLE);
                J_SearchTab.setVisibility(View.INVISIBLE);
                J_ListsController.setVisibility(View.INVISIBLE);
            }
        }else {
            RelativeLayout noMoreData=(RelativeLayout)findViewById(R.id.x_noMoreData);
            noMoreData.setVisibility(View.INVISIBLE);
            J_SearchTab.setVisibility(View.VISIBLE);
            J_ListsController.setVisibility(View.VISIBLE);
            J_ListMain.setAdapter(new AdapterForAllLists(BaseClassAllLists.this, message, categoryNames.size(), assignParameters(message), categoriesList, executer.categories, executer.data,selectedValues[0],J_keywords.getText().toString()));
            if(!executer.totalEntries.equals("previousOne")) {
                J_totalPages.setText("/" + (Integer.parseInt(executer.totalEntries) / 20));
            }else {

                J_totalPages.setText("/"+(Integer.parseInt(totalPages)/20));
            }
        }

        J_ListMain.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                ExpandableListView mainListView = (ExpandableListView)findViewById(R.id.x_activity_all_lists_list_main);
                if (view.getId() == mainListView.getId()) {
                    int currentFirstVisibleItem = mainListView.getFirstVisiblePosition();
                    if (currentFirstVisibleItem > mLastFirstVisibleItem) {

                        J_SearchTab.setVisibility(View.INVISIBLE);
                        J_ListsController.setVisibility(View.VISIBLE);
                        getSupportActionBar().hide();
                        J_ListMain.setPadding(0,0,0,75);

                    } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {

                        J_SearchTab.setVisibility(View.VISIBLE);
                        J_ListsController.setVisibility(View.INVISIBLE);
                        getSupportActionBar().show();
                        J_ListMain.setPadding(0,105,0,0);
                    }
                    mLastFirstVisibleItem = currentFirstVisibleItem;
                }
            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
        J_ButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
                count+=20;
                selectedValues[selectedValues.length-1]=count+"";
                Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                i.putExtra("activitySelected",message);
                i.putExtra("selectedValues",selectedValues);
                i.putExtra("yearList",yearList);
                if(!executer.totalEntries.equals("previousOne")){

                    i.putExtra("totalEntries",executer.totalEntries);

                }else {

                    i.putExtra("totalEntries",totalPages);

                }
                startActivity(i);
            }
        });
        J_ButtonPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count=Integer.parseInt(selectedValues[selectedValues.length-1]);
                if (count!=0) {
                    count -= 20;
                    selectedValues[selectedValues.length - 1] = count + "";
                    Intent i = new Intent(BaseClassAllLists.this, BaseClassAllLists.class);
                    i.putExtra("activitySelected", message);
                    i.putExtra("selectedValues", selectedValues);
                    i.putExtra("yearList", yearList);
                    if(!executer.totalEntries.equals("previousOne")){

                        i.putExtra("totalEntries",executer.totalEntries);

                    }else {

                        i.putExtra("totalEntries",totalPages);

                    }
                    startActivity(i);
                }

            }
        });
        J_pageNo.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {

                    case EditorInfo.IME_ACTION_DONE:

                        int offset=Integer.parseInt(v.getText().toString());
                        selectedValues[selectedValues.length-1]=offset*20+"";
                        Log.d("offset",selectedValues[selectedValues.length-1]);
                        Intent i=new Intent(BaseClassAllLists.this,BaseClassAllLists.class);
                        i.putExtra("activitySelected",message);
                        i.putExtra("selectedValues",selectedValues);
                        i.putExtra("yearList",yearList);
                        if(!executer.totalEntries.equals("previousOne")){

                            i.putExtra("totalEntries",executer.totalEntries);

                        }else {

                            i.putExtra("totalEntries",totalPages);

                        }
                        startActivity(i);
                        return true;

                    default:
                        return false;
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

    public void onBackPressed(){
        moveTaskToBack(true);
    }

}
