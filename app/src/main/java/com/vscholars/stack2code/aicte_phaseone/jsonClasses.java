package com.vscholars.stack2code.aicte_phaseone;


import android.os.AsyncTask;
import android.util.Log;

import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstituteDataItem;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ClosedCoursesDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ClosedInstitutesDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.FacultyDataItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by vineet_jain on 21/6/17.
 */

public class jsonClasses{

    private DashboardDataItems J_dashboardDataItem;
    private FacultyDataItems J_facultyDataItems;
    private ApprovedInstituteDataItem J_approvedInstitutions;
    private ClosedCoursesDataItems J_closedCoursesDataItems;
    private ClosedInstitutesDataItems J_closedInstitutesDataItems;

    public json_dashboardData J_json_dashboardData;
    public json_facultyServer J_json_facultyServer;
    public json_approvedInstitute J_json_approvedInstitutions;
    public json_closedcourses J_json_closedCourses;
    public json_closedinstitutes J_json_closedInstitutes;

    public String[] defaultValuesDashboard;
    public String[][][] data;
    public LinkedHashMap<String,Integer>categories;
    public LinkedHashMap<Integer,String>categoriesNames;

    private String str;

    jsonClasses(String message){

        if (message.equals("dashboard")){

            J_json_dashboardData=new json_dashboardData();
            J_dashboardDataItem=new DashboardDataItems();
            defaultValuesDashboard=new String[6];

        }else if (message.equals("approved_institutes")){

            J_json_approvedInstitutions=new json_approvedInstitute();
            J_approvedInstitutions=new ApprovedInstituteDataItem();

        }else if (message.equals("nri/pio-fn-ciwg/tp")){

        }else if (message.equals("faculty")){

            J_json_facultyServer=new json_facultyServer();
            J_facultyDataItems=new FacultyDataItems();

        }else if (message.equals("closed_courses")){

            J_json_closedCourses=new json_closedcourses();
            J_closedCoursesDataItems=new ClosedCoursesDataItems();

        }else if (message.equals("closed_institutes")){

            J_json_closedInstitutes=new json_closedinstitutes();
            J_closedInstitutesDataItems=new ClosedInstitutesDataItems();

        }
    }


    protected class json_dashboardData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {

            try {

                 /*
                   s[0] year
                   s[1] program
                   s[2] level
                   s[3] institutiontype
                   s[4] statetype
                   s[5] Minority
                   s[6] Women
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("year", s[0]));
                params.add(new BasicNameValuePair("program", s[1]));
                params.add(new BasicNameValuePair("level", s[2]));
                params.add(new BasicNameValuePair("institutiontype", s[3]));
                params.add(new BasicNameValuePair("state", s[4]));
                params.add(new BasicNameValuePair("Minority", s[5]));
                params.add(new BasicNameValuePair("Women", s[6]));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/dashboardserver.php", "POST", params);

                int success=jsonO.getInt("success");

                if (success == 1) {

                    JSONObject Jo = jsonO.getJSONObject("records");
                    defaultValuesDashboard[0]=J_dashboardDataItem.J_faculties=Jo.getString("faculties");
                    defaultValuesDashboard[1]=J_dashboardDataItem.J_placements=Jo.getString("placed");
                    defaultValuesDashboard[2]=J_dashboardDataItem.J_studentsPassed=Jo.getString("passed");
                    defaultValuesDashboard[3]=J_dashboardDataItem.J_enrollment=Jo.getString("enrollment");
                    defaultValuesDashboard[4]=J_dashboardDataItem.J_totalIntake=Jo.getString("intake");
                    defaultValuesDashboard[5]=J_dashboardDataItem.J_totalInstitutes=Jo.getString("institutecount");

                    str=Jo.toString();

                } else {

                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {

                e.printStackTrace();
                str= "fatal error";
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);

            return;
        }

    }

    protected class json_facultyServer extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                   s[1] program
                   s[2] level
                   s[3] institutiontype
                   s[4] state
                   s[5] Minority
                   s[6] Women
                 */


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("program", s[0]));
                params.add(new BasicNameValuePair("level", s[1]));
                params.add(new BasicNameValuePair("institutiontype", s[2]));
                params.add(new BasicNameValuePair("state", s[3]));
                params.add(new BasicNameValuePair("Minority", s[4]));
                params.add(new BasicNameValuePair("Women", s[5]));
                //getting JS
                // this is the data received from server and is displayed using list viewON Object
                //Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/facultyserver.php", "POST", params);

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("count");

                if (success == 1) {

                    JSONArray Ja=jsonO.getJSONArray("list");
                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();

                    for(int i=0;i<count;i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_facultyDataItems.J_ins_name=Jo.getString("ins_name");
                        if (categories.containsKey(J_facultyDataItems.J_ins_name)) {
                            categorySize=categories.get(J_facultyDataItems.J_ins_name);
                            ++categorySize;
                            categories.put(J_facultyDataItems.J_ins_name,categorySize);
                        }else {
                            categories.put(J_facultyDataItems.J_ins_name,1);
                            ++nameCount;
                            categoriesNames.put(nameCount,J_facultyDataItems.J_ins_name);
                        }
                    }
                    for (int i=0;i<categories.size();++i){
                        if (i==0){
                            data=new String[categories.size()][][];
                        }
                        for (int j=0;j<categories.get(categoriesNames.get(i));++j){
                            if (j==0){
                                data[i]=new String[categories.get(categoriesNames.get(i))][];
                            }
                            for (int k=0;k<8;++k) {
                                if (k==0) {
                                    data[i][j] = new String[8];
                                }
                            }
                        }
                    }
                    for (int i=0;i<categories.size();++i) {
                        ArrayList<Integer>used=new ArrayList<Integer>();
                        for (int k=0;k<categories.get(categoriesNames.get(i));++k) {
                            for (int j = 0; j < count; j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("ins_name"))&&used.contains(j)==false) {
                                    data[i][k][0]=J_facultyDataItems.J_facid = Jo.getString("facid");
                                    data[i][k][1]=J_facultyDataItems.J_name = Jo.getString("name");
                                    data[i][k][2]=J_facultyDataItems.J_appointment = Jo.getString("appointment");
                                    data[i][k][3]=J_facultyDataItems.J_designation = Jo.getString("designation");
                                    data[i][k][4]=J_facultyDataItems.J_gender = Jo.getString("gender");
                                    data[i][k][5]=J_facultyDataItems.J_ins_name = Jo.getString("ins_name");
                                    data[i][k][6]=J_facultyDataItems.J_joindate = Jo.getString("joindate");
                                    data[i][k][7]=J_facultyDataItems.J_specialisation = Jo.getString("specialisation");
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    str= "error in data fetch please try latter";
                }
            } catch (Exception e) {
                e.printStackTrace();
                str= e.toString();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

    protected class json_approvedInstitute extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                   s[1] program
                   s[2] level
                   s[3] institutiontype
                   s[4] state
                   s[5] Minority
                   s[6] Women
                 */


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("year", s[0]));
                params.add(new BasicNameValuePair("program", s[1]));
                params.add(new BasicNameValuePair("level", s[2]));
                params.add(new BasicNameValuePair("institutiontype", s[3]));
                params.add(new BasicNameValuePair("state", s[4]));
                params.add(new BasicNameValuePair("Minority", s[5]));
                params.add(new BasicNameValuePair("Women", s[6]));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/approvedinstitute.php", "POST", params);

                int success=jsonO.getInt("success");
                if (success == 1) {
                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");
                    for(int i=0;i<Ja.length();i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_approvedInstitutions.J_district=Jo.getString("c_dist");
                        if (categories.containsKey(J_approvedInstitutions.J_district)) {
                            categorySize=categories.get(J_approvedInstitutions.J_district);
                            ++categorySize;
                            categories.put(J_approvedInstitutions.J_district,categorySize);
                        }else {
                            categories.put(J_approvedInstitutions.J_district,1);
                            ++nameCount;
                            categoriesNames.put(nameCount,J_approvedInstitutions.J_district);
                        }
                    }

                    for (int i=0;i<categories.size();++i){
                        if (i==0){
                            data=new String[categories.size()][][];
                        }
                        for (int j=0;j<categories.get(categoriesNames.get(i));++j){
                            if (j==0){
                                data[i]=new String[categories.get(categoriesNames.get(i))][];
                            }
                            for (int k=0;k<7;++k) {
                                data[i][j]=new String[7];
                            }
                        }
                    }
                    for (int i=0;i<categories.size();++i) {
                        ArrayList<Integer>used=new ArrayList<Integer>();
                        for (int k=0;k<categories.get(categoriesNames.get(i));++k) {
                            for (int j = 0; j < Ja.length(); j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("c_dist"))&&used.contains(j)==false) {
                                    data[i][k][0]=J_approvedInstitutions.J_aicteid=Jo.getString("c_id");
                                    data[i][k][1]=J_approvedInstitutions.J_name=Jo.getString("c_name");
                                    data[i][k][2]=J_approvedInstitutions.J_address=Jo.getString("c_adds");
                                    data[i][k][3]=J_approvedInstitutions.J_district=Jo.getString("c_dist");
                                    data[i][k][4]=J_approvedInstitutions.J_institutionType=Jo.getString("c_type");
                                    data[i][k][5]=J_approvedInstitutions.J_women=Jo.getString("c_women");
                                    data[i][k][6]=J_approvedInstitutions.J_minority=Jo.getString("c_minior");
                                    //data[i][k][7]=J_approvedInstitutions.J_courseDetails=Jo.getString("");
                                    //data[i][k]][8]=J_approvedInstitutions.J_facultyDetails=Jo.getString("");
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    str= "error in data fetch please try latter";
                }

            } catch (Exception e) {
                e.printStackTrace();
                str= e.toString();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

    protected class json_closedcourses extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("year", s[0]));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/closedcourses.php", "POST", params);

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    for(int i=0;i<count;i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_closedCoursesDataItems.J_state=Jo.getString("state");
                        if (categories.containsKey(J_closedCoursesDataItems.J_state)) {
                            categorySize=categories.get(J_closedCoursesDataItems.J_state);
                            ++categorySize;
                            categories.put(J_closedCoursesDataItems.J_state,categorySize);
                        }else {
                            categories.put(J_closedCoursesDataItems.J_state,1);
                            ++nameCount;
                            categoriesNames.put(nameCount,J_closedCoursesDataItems.J_state);
                        }
                    }

                    for (int i=0;i<categories.size();++i){
                        if (i==0){
                            data=new String[categories.size()][][];
                        }
                        for (int j=0;j<categories.get(categoriesNames.get(i));++j){
                            if (j==0){
                                data[i]=new String[categories.get(categoriesNames.get(i))][];
                            }
                            for (int k=0;k<11;++k) {
                                data[i][j]=new String[11];
                            }
                        }
                    }

                    for (int i=0;i<categories.size();++i) {
                        ArrayList<Integer>used=new ArrayList<Integer>();
                        for (int k=0;k<categories.get(categoriesNames.get(i));++k) {
                            for (int j = 0; j < Ja.length(); j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("state"))&&used.contains(j)==false) {
                                    data[i][k][0]=J_closedCoursesDataItems.J_aicteid=Jo.getString("colid");
                                    data[i][k][1]=J_closedCoursesDataItems.J_insName=Jo.getString("collegename");
                                    data[i][k][2]=J_closedCoursesDataItems.J_insType=Jo.getString("type");
                                    data[i][k][3]=J_closedCoursesDataItems.J_state=Jo.getString("state");
                                    data[i][k][4]=J_closedCoursesDataItems.J_district=Jo.getString("district");
                                    data[i][k][5]=J_closedCoursesDataItems.J_courseId=Jo.getString("corid");
                                    data[i][k][6]=J_closedCoursesDataItems.J_university=Jo.getString("university");
                                    data[i][k][7]=J_closedCoursesDataItems.J_level=Jo.getString("corlevel");
                                    data[i][k][8]=J_closedCoursesDataItems.J_course=Jo.getString("corname");
                                    data[i][k][9]=J_closedCoursesDataItems.J_shift=Jo.getString("corshift");
                                    data[i][k][10]=J_closedCoursesDataItems.J_fullOrPartTime=Jo.getString("corfullpart");
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                str= e.toString();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

    protected class json_closedinstitutes extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("year", s[0]));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/closedinstitutes.php", "POST", params);

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("objects");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    for(int i=0;i<count;i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_closedInstitutesDataItems.J_state=Jo.getString("state");
                        if (categories.containsKey(J_closedInstitutesDataItems.J_state)) {
                            categorySize=categories.get(J_closedInstitutesDataItems.J_state);
                            ++categorySize;
                            categories.put(J_closedInstitutesDataItems.J_state,categorySize);
                        }else {
                            categories.put(J_closedInstitutesDataItems.J_state,1);
                            ++nameCount;
                            categoriesNames.put(nameCount,J_closedInstitutesDataItems.J_state);
                        }
                    }
                    for (int i=0;i<categories.size();++i){
                        if (i==0){
                            data=new String[categories.size()][][];
                        }
                        for (int j=0;j<categories.get(categoriesNames.get(i));++j){
                            if (j==0){
                                data[i]=new String[categories.get(categoriesNames.get(i))][];
                            }
                            for (int k=0;k<11;++k) {
                                data[i][j]=new String[11];
                            }
                        }
                    }
                    for (int i=0;i<categories.size();++i) {
                        ArrayList<Integer>used=new ArrayList<Integer>();
                        for (int k=0;k<categories.get(categoriesNames.get(i));++k) {
                            for (int j = 0; j < Ja.length(); j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("state"))&&used.contains(j)==false) {
                                    data[i][k][0]=J_closedInstitutesDataItems.J_aicteId=Jo.getString("colid");
                                    data[i][k][1]=J_closedInstitutesDataItems.J_instituteName=Jo.getString("collegename");
                                    data[i][k][2]=J_closedInstitutesDataItems.J_state=Jo.getString("state");
                                    data[i][k][3]=J_closedInstitutesDataItems.J_district=Jo.getString("district");
                                    data[i][k][4]=J_closedInstitutesDataItems.J_institute=Jo.getString("type");
                                    data[i][k][5]=J_closedInstitutesDataItems.J_address=Jo.getString("address");
                                    data[i][k][6]=J_closedInstitutesDataItems.J_city=Jo.getString("City");
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    str= "error in data fetch please try latter";
                }


            } catch (Exception e) {
                e.printStackTrace();
                str= e.toString();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
        }
    }

}
