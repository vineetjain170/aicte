package com.vscholars.stack2code.aicte_phaseone;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

// This class for double navigation drawer throughout the application for filtering options throughout the application without EditText Views

public class DoubleNavigationWithoutEditText extends ActionBarActivity{

    //All Variables required for construction of Navigation Drawer

    private String[] J_MainNavigationDrawerOptions;
    private DrawerLayout J_DrawerLayout;
    private ListView J_DrawerListMainNavigationDrawer;
    private ExpandableListView J_DrawerListFilterOptions;
    private ActionBarDrawerToggle J_DrawerToggle;
    private List<String> J_ParentFilterOptions;
    private HashMap<String,String> J_SelectedOptions;
    private HashMap<String, List<String>> J_ChildFilterOptions;
    private EditText J_keywords;

    protected void onCreateDrawer(Context context, String message,String[] yearList) {

        //This method is main initializer for all the components

        initializer(context,message,yearList);

        // This code sets the drawer toggle as the DrawerListener

        J_DrawerToggle = new ActionBarDrawerToggle(
                this,                                /* host Activity */
                J_DrawerLayout,                      /* DrawerLayout object */
                R.string.drawer_open,                /* "open drawer" description */
                R.string.drawer_close                /* "close drawer" description */
        ) {

            // Called when a drawer has settled in a completely closed state.

            public void onDrawerClosed(View view) {
                getSupportActionBar().show();
                super.onDrawerClosed(view);
            }

            // Called when a drawer has settled in a completely open state.

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().hide();
                super.onDrawerOpened(drawerView);
            }
        };

        J_DrawerLayout.setDrawerListener(J_DrawerToggle);

        // This code enables the Home button on ActionBar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initializer(final Context context, String message, final String[] yearList) {

        //initialize drawer layout
        //initialize list view of navigation drawer
        J_DrawerLayout = (DrawerLayout) findViewById(R.id.x_double_navigation_drawer_layout_drawer_layout);
        J_DrawerListMainNavigationDrawer=(ListView)findViewById(R.id.x_double_navigation_drawer_layout_main_drawer);
        J_DrawerListFilterOptions=(ExpandableListView) findViewById(R.id.x_double_navigation_drawer_layout_filter_drawer);
        J_keywords=(EditText)findViewById(R.id.x_activity_all_lists_keywords);

        //this method changes the title of action bar according to current activity
        ActionBarTitleChange(message);

        //provide list items for main navigation drawer and set its adapter
        J_MainNavigationDrawerOptions= new String[]{"AICTE Home", "Dashboard", "Approved Institutes", "NRI/PIO-FN-CIWG/TP", "Faculties","Graphs and Charts","Closed Courses","Closed Institutes","Unapproved"};
        int[] icons={R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav,R.drawable.ic_dashboard_nav};
        J_DrawerListMainNavigationDrawer.setAdapter(new AdapterForMainNavigationList(context,J_MainNavigationDrawerOptions,icons));

        //initialize filter options according to message or current activity and assign an adapter to it
        FilterOptionsInitializer(message,yearList);
        J_DrawerListFilterOptions.setAdapter(new AdapterForFilterOptions(context,J_ParentFilterOptions,J_ChildFilterOptions));

        //this function is used to add header and footer view to lists of navigation drawer
        addHeaderFooter();

        //this code adds listeners to expandable list view for filtering options i.e., actions taken when group item is clicked and actions taken when child item is clicked
        J_DrawerListFilterOptions.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        J_DrawerListFilterOptions.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //on each child click the child is added to selected options hash map
                J_SelectedOptions.put(J_ParentFilterOptions.get(groupPosition),J_ChildFilterOptions.get(J_ParentFilterOptions.get(groupPosition)).get(childPosition));
                return true;
            }
        });

        //this code sets listener to main navigation drawer list and sends message to BaseClassAllLists depending on user choice
        J_DrawerListMainNavigationDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                if (position==1){
                    String url = "http://www.aicte-india.org/";
                    i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } else if (position==2){
                    i=new Intent(context,MainActivity.class);
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==3){
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","approved_institutes");
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==4){
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","nri/pio-fn-ciwg/tp");
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==5){
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","faculty");
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==6){

                } else if (position==7){
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","closed_courses");
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==8){
                    i=new Intent(context,BaseClassAllLists.class);
                    i.putExtra("activitySelected","closed_institutes");
                    i.putExtra("yearList",yearList);
                    startActivity(i);

                } else if (position==9){

                }
            }
        });

    }

    private void addHeaderFooter() {

        LayoutInflater inflater = getLayoutInflater();

        //add header to main navigation drawer and filter list view
        View listHeaderView = inflater.inflate(R.layout.navigation_header_main,null,false);
        J_DrawerListMainNavigationDrawer.addHeaderView(listHeaderView);
        View filterHeaderView=inflater.inflate(R.layout.navigation_header_filter,null,false);
        J_DrawerListFilterOptions.addHeaderView(filterHeaderView);

        //add footer to expandable list view filtering options
        //actually this footer works as a button which when clicked submit the selected options to the server
        View filterFooterView=inflater.inflate(R.layout.filter_option_submit_button,null,false);
        J_DrawerListFilterOptions.addFooterView(filterFooterView);

    }

    // This method is invoked when home button us clicked and is meant to close drawer if it is open and vice versa

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            //this code defines the action to be taken when home button is clicked
            case android.R.id.home:
                if (J_DrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    J_DrawerLayout.closeDrawers();
                } else {getSupportActionBar().setHomeButtonEnabled(true);
                    J_DrawerLayout.openDrawer(Gravity.LEFT);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void FilterOptionsInitializer(String message,String[] yearList){

        //this code initializes filter options according to the current activity or received message
        J_ParentFilterOptions=new ArrayList<String>();
        J_ChildFilterOptions=new HashMap<String, List<String>>();
        J_SelectedOptions=new HashMap<String,String>();
        List<String>temp;

        if (message.equals("approved_institutes")||message.equals("dashboard")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsParentApprovedInstitutesOrDashboard))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.stateOptions))temp.add(value);
            J_ChildFilterOptions.put("Select State",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.programOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Program",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.levelOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Level",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.institutionType))temp.add(value);
            J_ChildFilterOptions.put("Institution Type",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.minorityOptions))temp.add(value);
            J_ChildFilterOptions.put("Minority",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.womenOptions))temp.add(value);
            J_ChildFilterOptions.put("Women",temp);

            //This code provide initial default value to selected options in case no option is selected by user
            J_SelectedOptions.put("Year","2016-2017");
            J_SelectedOptions.put("Select State","--All--");
            J_SelectedOptions.put("Select Program","--All--");
            J_SelectedOptions.put("Select Level","--All--");
            J_SelectedOptions.put("Institution Type","--All--");
            J_SelectedOptions.put("Minority",null);
            J_SelectedOptions.put("Women",null);

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsParentNriPioFnCiwgTp))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for(String value:getResources().getStringArray(R.array.courseType))temp.add(value);
            J_ChildFilterOptions.put("Course Type",temp);
            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);


            //This code provide initial default value to selected options in case no option is selected by user
            J_SelectedOptions.put("Course Type","NRI");
            J_SelectedOptions.put("Year","2017-2018");



        }else if (message.equals("faculty")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsFaculty))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.stateOptions))temp.add(value);
            J_ChildFilterOptions.put("Select State",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.programOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Program",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.levelOptions))temp.add(value);
            J_ChildFilterOptions.put("Select Level",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.institutionType))temp.add(value);
            J_ChildFilterOptions.put("Institution Type",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.minorityOptions))temp.add(value);
            J_ChildFilterOptions.put("Minority",temp);
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.womenOptions))temp.add(value);
            J_ChildFilterOptions.put("Women",temp);


            //This code provide initial default value to selected options in case no option is selected by user
            J_SelectedOptions.put("Select State","Andaman And Nicobar Island");
            J_SelectedOptions.put("Select Program","Engineering And Technology");
            J_SelectedOptions.put("Select Level","--All--");
            J_SelectedOptions.put("Institution Type","--All--");
            J_SelectedOptions.put("Minority",null);
            J_SelectedOptions.put("Women",null);

        }else if (message.equals("closed_courses")||message.equals("closed_institutes")){

            //initialize parent options for filter list according to the message
            temp=new ArrayList<String>();
            for (String value:getResources().getStringArray(R.array.filterOptionsParentClosedCoursesOrInstitute))J_ParentFilterOptions.add(value);

            temp=new ArrayList<String>();
            for (String year:yearList)temp.add(year);
            J_ChildFilterOptions.put("Year",temp);

            //This code provide initial default value to selected options in case no option is selected by user
            J_SelectedOptions.put("Year","2017-2018");

        }
    }

    public void filterList(View v){

        //on click of filter button the selected option is submitted to server through this module

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

        }else if (message=="dashboard"){
            getSupportActionBar().setTitle("AICTE Dashboard");
        }

    }
}
