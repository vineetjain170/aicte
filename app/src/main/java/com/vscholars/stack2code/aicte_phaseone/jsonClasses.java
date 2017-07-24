package com.vscholars.stack2code.aicte_phaseone;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstituteDataItem;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstitutesCourseDetailsDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ApprovedInstitutesFacultyDetailsDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ClosedCoursesDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.ClosedInstitutesDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.DashboardDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.FacultyDataItems;
import com.vscholars.stack2code.aicte_phaseone.DataItems.NriPioFciDataItems;

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
    private NriPioFciDataItems J_NriPioFciDataItems;
    private ApprovedInstitutesCourseDetailsDataItems J_courseDetails;
    private ApprovedInstitutesFacultyDetailsDataItems J_facultyDetails;

    public json_dashboardData J_json_dashboardData;
    public json_facultyServer J_json_facultyServer;
    public json_approvedInstitute J_json_approvedInstitutions;
    public json_closedcourses J_json_closedCourses;
    public json_closedinstitutes J_json_closedInstitutes;
    public json_nriPio J_json_NriPioFci;
    public json_courseDetails J_json_courseDetails;
    public json_facultyDetails J_json_facultyDetails;

    public String[] defaultValuesDashboard;
    public String[][][] data;
    public LinkedHashMap<String,Integer>categories;
    public LinkedHashMap<Integer,String>categoriesNames;
    public String totalEntries;

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

            J_json_NriPioFci=new json_nriPio();
            J_NriPioFciDataItems=new NriPioFciDataItems();

        }else if (message.equals("faculty")){

            J_json_facultyServer=new json_facultyServer();
            J_facultyDataItems=new FacultyDataItems();

        }else if (message.equals("closed_courses")){

            J_json_closedCourses=new json_closedcourses();
            J_closedCoursesDataItems=new ClosedCoursesDataItems();

        }else if (message.equals("closed_institutes")){

            J_json_closedInstitutes=new json_closedinstitutes();
            J_closedInstitutesDataItems=new ClosedInstitutesDataItems();

        }else if (message.equals("course_details")){

            J_json_courseDetails=new json_courseDetails();
            J_courseDetails=new ApprovedInstitutesCourseDetailsDataItems();

        }else if (message.equals("faculty_details")){

            J_json_facultyDetails=new json_facultyDetails();
            J_facultyDetails=new ApprovedInstitutesFacultyDetailsDataItems();

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
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"program\"", s[1]));
                params.add(new BasicNameValuePair("\"level\"", s[2]));
                params.add(new BasicNameValuePair("\"institutiontype\"", s[3]));
                params.add(new BasicNameValuePair("\"state\"", s[4]));
                params.add(new BasicNameValuePair("\"Minority\"", s[5]));
                params.add(new BasicNameValuePair("\"Women\"", s[6]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/dashboardserver.php", "POST", actual);
                Log.d("key",actual.toString());
                Log.d("response",jsonO.toString());

                int success=jsonO.getInt("success");
                Log.d("success",success+"");

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
                params.add(new BasicNameValuePair("\"program\"", s[0]));
                params.add(new BasicNameValuePair("\"level\"", s[1]));
                params.add(new BasicNameValuePair("\"institutiontype\"", s[2]));
                params.add(new BasicNameValuePair("\"state\"", s[3]));
                params.add(new BasicNameValuePair("\"Minority\"", s[4]));
                params.add(new BasicNameValuePair("\"Women\"", s[5]));
                params.add(new BasicNameValuePair("\"offset\"",s[6]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));
                //getting JS
                // this is the data received from server and is displayed using list viewON Object
                //Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/facultyserver.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("count");

                if (success == 1) {

                    JSONArray Ja=jsonO.getJSONArray("list");
                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();

                    if (count!=0){

                    for(int i=0;i<count;i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_facultyDataItems.J_ins_name=Jo.getString("name");
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
                        ArrayList<Integer> used = new ArrayList<Integer>();
                        for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                            for (int j = 0; j < count; j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("name")) && used.contains(j) == false) {
                                    data[i][k][0] = J_facultyDataItems.J_facid = Jo.getString("facid");
                                    data[i][k][1] = J_facultyDataItems.J_name = Jo.getString("name");
                                    data[i][k][2] = J_facultyDataItems.J_appointment = Jo.getString("appointment");
                                    data[i][k][3] = J_facultyDataItems.J_designation = Jo.getString("designation");
                                    data[i][k][4] = J_facultyDataItems.J_gender = Jo.getString("gender");
                                    data[i][k][5] = J_facultyDataItems.J_ins_name = Jo.getString("ins_name");
                                    data[i][k][6] = J_facultyDataItems.J_joindate = Jo.getString("joindate");
                                    data[i][k][7] = J_facultyDataItems.J_specialisation = Jo.getString("specialisation");
                                    if(jsonO.getString("total_rows")!="null"){

                                        totalEntries=jsonO.getString("total_rows");

                                    }else{

                                        totalEntries="previousOne";

                                    }
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                    }else {
                        categoriesNames.put(-1,"-1");
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
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"program\"", s[1]));
                params.add(new BasicNameValuePair("\"level\"", s[2]));
                params.add(new BasicNameValuePair("\"institutiontype\"", s[3]));
                params.add(new BasicNameValuePair("\"state\"", s[4]));
                params.add(new BasicNameValuePair("\"Minority\"", s[5]));
                params.add(new BasicNameValuePair("\"Women\"", s[6]));
                params.add(new BasicNameValuePair("\"offset\"",s[7]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));
//                 getting JSON Object
//                 Note that create product url accepts POST method

                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/approvedinstitute.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                if (success == 1) {
                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0){

                    for(int i=0;i<Ja.length();i++){
                        JSONObject Jo=Ja.getJSONObject(i);
                        J_approvedInstitutions.J_district=Jo.getString("c_name");
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
                        ArrayList<Integer> used = new ArrayList<Integer>();
                        for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                            for (int j = 0; j < Ja.length(); j++) {
                                JSONObject Jo = Ja.getJSONObject(j);
                                if (categoriesNames.get(i).equals(Jo.getString("c_name")) && used.contains(j) == false) {
                                    data[i][k][0] = J_approvedInstitutions.J_aicteid = Jo.getString("c_id");
                                    data[i][k][1] = J_approvedInstitutions.J_name = Jo.getString("c_name");
                                    data[i][k][2] = J_approvedInstitutions.J_address = Jo.getString("c_adds");
                                    data[i][k][3] = J_approvedInstitutions.J_district = Jo.getString("c_dist");
                                    data[i][k][4] = J_approvedInstitutions.J_institutionType = Jo.getString("c_type");
                                    data[i][k][5] = J_approvedInstitutions.J_women = Jo.getString("c_women");
                                    data[i][k][6] = J_approvedInstitutions.J_minority = Jo.getString("c_minior");
                                    if(jsonO.getString("total_rows")!="null"){

                                        totalEntries=jsonO.getString("total_rows");

                                    }else{

                                        totalEntries="previousOne";

                                    }
                                    used.add(j);
                                    break;
                                }
                            }
                        }
                    }
                    }else {
                        categoriesNames.put(-1,"-1");
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
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"offset\"",s[1]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/closedcourses.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < count; i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_closedCoursesDataItems.J_state = Jo.getString("collegename");
                            if (categories.containsKey(J_closedCoursesDataItems.J_state)) {
                                categorySize = categories.get(J_closedCoursesDataItems.J_state);
                                ++categorySize;
                                categories.put(J_closedCoursesDataItems.J_state, categorySize);
                            } else {
                                categories.put(J_closedCoursesDataItems.J_state, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_closedCoursesDataItems.J_state);
                            }
                        }

                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }

                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("collegename")) && used.contains(j) == false) {
                                        data[i][k][0] = J_closedCoursesDataItems.J_aicteid = Jo.getString("colid");
                                        data[i][k][1] = J_closedCoursesDataItems.J_insName = Jo.getString("collegename");
                                        data[i][k][2] = J_closedCoursesDataItems.J_insType = Jo.getString("type");
                                        data[i][k][3] = J_closedCoursesDataItems.J_state = Jo.getString("state");
                                        data[i][k][4] = J_closedCoursesDataItems.J_district = Jo.getString("district");
                                        data[i][k][5] = J_closedCoursesDataItems.J_courseId = Jo.getString("corid");
                                        data[i][k][6] = J_closedCoursesDataItems.J_university = Jo.getString("university");
                                        data[i][k][7] = J_closedCoursesDataItems.J_level = Jo.getString("corlevel");
                                        data[i][k][8] = J_closedCoursesDataItems.J_course = Jo.getString("corname");
                                        data[i][k][9] = J_closedCoursesDataItems.J_shift = Jo.getString("corshift");
                                        data[i][k][10] = J_closedCoursesDataItems.J_fullOrPartTime = Jo.getString("corfullpart");
                                        if(jsonO.getString("total_rows")!="null"){

                                            totalEntries=jsonO.getString("total_rows");

                                        }else{

                                            totalEntries="previousOne";

                                        }
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
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
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"offset\"",s[1]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/closedinstitutes.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("JSONobj",jsonO.toString());

                int success=jsonO.getInt("success");
                int count=jsonO.getInt("objects");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < count; i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_closedInstitutesDataItems.J_state = Jo.getString("collegename");
                            if (categories.containsKey(J_closedInstitutesDataItems.J_state)) {
                                categorySize = categories.get(J_closedInstitutesDataItems.J_state);
                                ++categorySize;
                                categories.put(J_closedInstitutesDataItems.J_state, categorySize);
                            } else {
                                categories.put(J_closedInstitutesDataItems.J_state, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_closedInstitutesDataItems.J_state);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 7; ++k) {
                                    data[i][j] = new String[7];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("collegename")) && used.contains(j) == false) {
                                        data[i][k][0] = J_closedInstitutesDataItems.J_aicteId = Jo.getString("colid");
                                        data[i][k][1] = J_closedInstitutesDataItems.J_instituteName = Jo.getString("collegename");
                                        data[i][k][2] = J_closedInstitutesDataItems.J_state = Jo.getString("state");
                                        data[i][k][3] = J_closedInstitutesDataItems.J_district = Jo.getString("district");
                                        data[i][k][4] = J_closedInstitutesDataItems.J_institute = Jo.getString("type");
                                        data[i][k][5] = J_closedInstitutesDataItems.J_address = Jo.getString("address");
                                        data[i][k][6] = J_closedInstitutesDataItems.J_city = Jo.getString("City");
                                        if(jsonO.getString("total_rows")!="null"){

                                            totalEntries=jsonO.getString("total_rows");

                                        }else{

                                            totalEntries="previousOne";

                                        }
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
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

    protected class json_nriPio extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"year\"", s[0]));
                params.add(new BasicNameValuePair("\"q1\"",s[1]));
                //params.add(new BasicNameValuePair("\"offset\"",s[2]));
                List<NameValuePair> actual=new ArrayList<>();
                String paramsnew="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                paramsnew=paramsnew.replaceAll("=",":");
                actual.add(new BasicNameValuePair("paramsSent",paramsnew));

//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/nripiofc.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("jsonObj",jsonO.toString());

                int success=jsonO.getInt("success");
                //int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < Ja.length(); i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_NriPioFciDataItems.J_state = Jo.getString("collegename");
                            if (categories.containsKey(J_NriPioFciDataItems.J_state)) {
                                categorySize = categories.get(J_NriPioFciDataItems.J_state);
                                ++categorySize;
                                categories.put(J_NriPioFciDataItems.J_state, categorySize);
                            } else {
                                categories.put(J_NriPioFciDataItems.J_state, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_NriPioFciDataItems.J_state);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("collegename")) && used.contains(j) == false) {

                                        data[i][k][0] = J_NriPioFciDataItems.J_aicteId = Jo.getString("colid");
                                        data[i][k][1] = J_NriPioFciDataItems.J_instituteName = Jo.getString("collegename");
                                        data[i][k][2] = J_NriPioFciDataItems.J_state = Jo.getString("state");
                                        data[i][k][3] = J_NriPioFciDataItems.J_district = Jo.getString("district");
                                        data[i][k][4] = J_NriPioFciDataItems.J_institute = Jo.getString("type");
                                        data[i][k][5] = J_NriPioFciDataItems.J_program = Jo.getString("program");
                                        data[i][k][6] = J_NriPioFciDataItems.J_university = Jo.getString("university");
                                        data[i][k][7] = J_NriPioFciDataItems.J_level = Jo.getString("corlevel");
                                        data[i][k][8] = J_NriPioFciDataItems.J_nameOfCourses = Jo.getString("corname");
                                        data[i][k][9] = J_NriPioFciDataItems.J_approvedIntake = Jo.getString("intake");
                                        //data[i][k][10]=J_NriPioFciDataItems.J_nriQuotaSeats=Jo.getString("foruniversity");
                                        if(jsonO.getString("total_rows")!="null"){

                                            totalEntries=jsonO.getString("total_rows");

                                        }else{

                                            totalEntries="previousOne";

                                        }
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
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

    protected class json_courseDetails extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"aicteid\"", s[0]));
                params.add(new BasicNameValuePair("\"year\"",s[1]));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));
//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/approvedcourse.php", "POST",actual);
                Log.d("paramsSent",actual.toString());
                Log.d("jsonObj",jsonO.toString());

                int success=jsonO.getInt("success");
                //int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("list");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < Ja.length(); i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_courseDetails.J_course = Jo.getString("Course");
                            if (categories.containsKey(J_courseDetails.J_course)) {
                                categorySize = categories.get(J_courseDetails.J_course);
                                ++categorySize;
                                categories.put(J_courseDetails.J_course, categorySize);
                            } else {
                                categories.put(J_courseDetails.J_course, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_courseDetails.J_course);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("Course")) && used.contains(j) == false) {
                                        data[i][k][0] = J_courseDetails.J_course = Jo.getString("Course");
                                        data[i][k][1] = J_courseDetails.J_programme = Jo.getString("Program");
                                        data[i][k][2] = J_courseDetails.J_university = Jo.getString("University");
                                        data[i][k][3] = J_courseDetails.J_courseLevel = Jo.getString("Level");
                                        data[i][k][4] = J_courseDetails.J_shift = Jo.getString("Shift");
                                        //data[i][k][5] = J_courseDetails.J_fullPartTime = Jo.getString("");
                                        data[i][k][6] = J_courseDetails.J_intake = Jo.getString("Intake");
                                        data[i][k][7] = J_courseDetails.J_enrolment = Jo.getString("Enroll");
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
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

    protected class json_facultyDetails extends AsyncTask<String, Void, Void>{

        @Override
        protected Void doInBackground(String... s) {
            try {
                 /*
                   s[0] year
                 */

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("\"aicteid\"", s[0]));
                String sudo="{"+params.toString().substring(1,params.toString().length()-1)+"}";
                sudo=sudo.replace('=',':');
                List<NameValuePair>actual=new ArrayList<>();
                actual.add(new BasicNameValuePair("paramsSent",sudo));
//                 getting JSON Object
//                 Note that create product url accepts POST method
                JSONObject jsonO =JSONParser.makeHttpRequest("http://anurag.webutu.com/college/faculty.php", "POST", actual);
                Log.d("paramsSent",actual.toString());
                Log.d("jsonObj",jsonO.toString());

                /*int success=jsonO.getInt("success");
                //int count=jsonO.getInt("count");

                if (success == 1) {

                    int categorySize=0;
                    int nameCount=-1;
                    categories=new LinkedHashMap<String,Integer>();
                    categoriesNames=new LinkedHashMap<>();
                    JSONArray Ja=jsonO.getJSONArray("records");

                    if (Ja.length()!=0) {

                        for (int i = 0; i < Ja.length(); i++) {
                            JSONObject Jo = Ja.getJSONObject(i);
                            J_facultyDetails.J_name = Jo.getString("");
                            if (categories.containsKey(J_facultyDetails.J_name)) {
                                categorySize = categories.get(J_facultyDetails.J_name);
                                ++categorySize;
                                categories.put(J_facultyDetails.J_name, categorySize);
                            } else {
                                categories.put(J_facultyDetails.J_name, 1);
                                ++nameCount;
                                categoriesNames.put(nameCount, J_facultyDetails.J_name);
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            if (i == 0) {
                                data = new String[categories.size()][][];
                            }
                            for (int j = 0; j < categories.get(categoriesNames.get(i)); ++j) {
                                if (j == 0) {
                                    data[i] = new String[categories.get(categoriesNames.get(i))][];
                                }
                                for (int k = 0; k < 11; ++k) {
                                    data[i][j] = new String[11];
                                }
                            }
                        }
                        for (int i = 0; i < categories.size(); ++i) {
                            ArrayList<Integer> used = new ArrayList<Integer>();
                            for (int k = 0; k < categories.get(categoriesNames.get(i)); ++k) {
                                for (int j = 0; j < Ja.length(); j++) {
                                    JSONObject Jo = Ja.getJSONObject(j);
                                    if (categoriesNames.get(i).equals(Jo.getString("")) && used.contains(j) == false) {
                                        data[i][k][0] = J_facultyDetails.J_fid = Jo.getString("");
                                        data[i][k][1] = J_facultyDetails.J_name = Jo.getString("");
                                        data[i][k][2] = J_facultyDetails.J_gender = Jo.getString("");
                                        data[i][k][3] = J_facultyDetails.J_designation = Jo.getString("");
                                        data[i][k][4] = J_facultyDetails.J_joiningDate = Jo.getString("");
                                        data[i][k][5] = J_facultyDetails.J_specialisationArea = Jo.getString("");
                                        data[i][k][6] = J_facultyDetails.J_appointmentType = Jo.getString("");
                                        used.add(j);
                                        break;
                                    }
                                }
                            }
                        }
                    }else {
                        categoriesNames.put(-1,"-1");
                    }
                } else {
                    str= "error in data fetch please try latter";
                }*/


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