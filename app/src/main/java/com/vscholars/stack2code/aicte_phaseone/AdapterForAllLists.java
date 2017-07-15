package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class AdapterForAllLists extends BaseExpandableListAdapter{

    Context context;
    int categories;
    int parameters;
    List<String>categoriesNames;
    String[][][] data;
    String message;
    HashMap<String,Integer>enteries;

    AdapterForAllLists(Context context,String message,int categories,int parameters, List<String> categoriesNames,HashMap<String,Integer>enteries,String[][][] data){

        this.context=context;
        this.message=message;
        this.categories=categories;
        this.parameters=parameters;
        this.categoriesNames=categoriesNames;
        this.data=data;
        this.enteries=enteries;
    }

    @Override
    public int getGroupCount() {
        return this.categories;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return enteries.get(categoriesNames.get(groupPosition));
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.categoriesNames.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        String[] parameterValue=new String[this.parameters];
        for (int i=0;i<this.parameters;++i){
            parameterValue[i]=data[groupPosition][childPosition][i];
        }

        return parameterValue;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String groupName=(String) getGroup(groupPosition);
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        convertView=layoutInflater.inflate(R.layout.parent_filter_options,null,false);
        TextView parentTextView=(TextView)convertView.findViewById(R.id.x_parent_filter_options_text_view);
        parentTextView.setText(groupName);
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String[] values=(String[]) getChild(groupPosition,childPosition);

        if (message.equals("approved_institutes")) {

            convertView = infalInflater.inflate(R.layout.label_approved_institutions, null);

            TextView aicteId,name,address,district,institutionType,women,minority,courseDetails,facultyDetails;
            ImageView womenY,womenN,minorityY,minorityN;

            View[] layoutViews={convertView.findViewById(R.id.x_label_approved_institutions_aicteid),
                    convertView.findViewById(R.id.x_label_approved_institutions_name),
                    convertView.findViewById(R.id.x_label_approved_institutions_address),
                    convertView.findViewById(R.id.x_label_approved_institutions_district),
                    convertView.findViewById(R.id.x_label_approved_institutions_institution_type),
                    convertView.findViewById(R.id.x_approved_institutions_womenY),
                    convertView.findViewById(R.id.x_approved_institutions_womenN),
                    convertView.findViewById(R.id.x_approved_institutions_minorityY),
                    convertView.findViewById(R.id.x_approved_institutions_minorityN)};

            aicteId=(TextView)layoutViews[0];
            name=(TextView)layoutViews[1];
            address=(TextView)layoutViews[2];
            district=(TextView)layoutViews[3];
            institutionType=(TextView)layoutViews[4];
            womenY=(ImageView)layoutViews[5];
            womenN=(ImageView)layoutViews[6];
            minorityY=(ImageView)layoutViews[7];
            minorityN=(ImageView)layoutViews[8];
            //women=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_women);
            //minority=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_minority);
            //courseDetails=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_course_details);
            //facultyDetails=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_faculty_details);

            aicteId.setText(values[0]);
            name.setText(values[1]);
            address.setText(values[2]);
            district.setText(values[3]);
            institutionType.setText(values[4]);
            if(values[5].equals("Y")||values[5].equals("y")){

                womenY.setVisibility(View.VISIBLE);
                womenN.setVisibility(View.INVISIBLE);

            }else if (values[5].equals("N")||values[5].equals("n")){

                womenY.setVisibility(View.INVISIBLE);
                womenN.setVisibility(View.VISIBLE);

            }
            if (values[6].equals("Y")||values[6].equals("y")){

                minorityY.setVisibility(View.VISIBLE);
                minorityN.setVisibility(View.INVISIBLE);

            }else if (values[6].equals("N")||values[6].equals("n")){

                minorityN.setVisibility(View.VISIBLE);
                minorityY.setVisibility(View.INVISIBLE);

            }
            //women.setText(values[5]);
            //minority.setText(values[6]);
            //courseDetails.setText(values[7]);
            //facultyDetails.setText(values[8]);

            return convertView;

        }else if (message.equals("nri/pio-fn-ciwg/tp")){
            convertView = infalInflater.inflate(R.layout.label_other_courses, null);

            TextView aicteId,institutionName,state,district,institutionType,program,university,level,nameOfCourses,approvedIntake,quotaSeats;

            View[] layoutViews= new View[]{convertView.findViewById(R.id.x_label_other_courses_aicteid),
                    convertView.findViewById(R.id.x_label_other_courses_institution_name),
                    convertView.findViewById(R.id.x_label_other_courses_state),
                    convertView.findViewById(R.id.x_label_other_courses_district),
                    convertView.findViewById(R.id.x_label_other_courses_institution_type),
                    convertView.findViewById(R.id.x_label_other_courses_program),
                    convertView.findViewById(R.id.x_label_other_courses_universityboard),
                    convertView.findViewById(R.id.x_label_other_courses_level),
                    convertView.findViewById(R.id.x_label_other_courses_course_name),
                    convertView.findViewById(R.id.x_label_other_courses_approved_intake),
                    convertView.findViewById(R.id.x_label_other_courses_nri_quota_seats)};

            aicteId=(TextView)layoutViews[0];
            institutionName=(TextView)layoutViews[1];
            state=(TextView)layoutViews[2];
            district=(TextView)layoutViews[3];
            institutionType=(TextView)layoutViews[4];
            program=(TextView)layoutViews[5];
            university=(TextView)layoutViews[6];
            level=(TextView)layoutViews[7];
            nameOfCourses=(TextView)layoutViews[8];
            approvedIntake=(TextView)layoutViews[9];
            //quotaSeats=(TextView)layoutViews[10];

            aicteId.setText(values[0]);
            institutionName.setText(values[1]);
            state.setText(values[2]);
            district.setText(values[3]);
            institutionType.setText(values[4]);
            program.setText(values[5]);
            university.setText(values[6]);
            level.setText(values[7]);
            nameOfCourses.setText(values[8]);
            approvedIntake.setText(values[9]);
            //quotaSeats.setText(values[10]);

            return convertView;

        }else if (message.equals("faculty")){
            convertView=infalInflater.inflate(R.layout.label_faculties,null);

            TextView facultyId,name,gender,designation,joiningDate,specialisationArea,appointmentType,institutionName;

            View[] layoutViews= new View[]{convertView.findViewById(R.id.x_label_faculties_facultyid),
                    convertView.findViewById(R.id.x_label_faculties_name),
                    convertView.findViewById(R.id.x_label_faculties_gender),
                    convertView.findViewById(R.id.x_label_faculties_designation),
                    convertView.findViewById(R.id.x_label_faculties_joining_date),
                    convertView.findViewById(R.id.x_label_faculties_specialisation_area),
                    convertView.findViewById(R.id.x_label_faculties_appointment_type),
                    convertView.findViewById(R.id.x_label_faculties_institution_name)};

            facultyId=(TextView)layoutViews[0];
            name=(TextView)layoutViews[1];
            gender=(TextView)layoutViews[2];
            designation=(TextView)layoutViews[3];
            joiningDate=(TextView)layoutViews[4];
            specialisationArea=(TextView)layoutViews[5];
            appointmentType=(TextView)layoutViews[6];
            institutionName=(TextView)layoutViews[7];

            facultyId.setText(values[0]);
            name.setText(values[1]);
            gender.setText(values[4]);
            designation.setText(values[3]);
            joiningDate.setText(values[6]);
            specialisationArea.setText(values[7]);
            appointmentType.setText(values[2]);
            institutionName.setText(values[5]);

            return convertView;

        }else if (message.equals("closed_courses")){
            convertView=infalInflater.inflate(R.layout.label_closed_courses,null);

            TextView aicteId,institutionName,institutionType,state,district,courseId,university,level,course,shift,fullPartTime;

            View[] layoutViews= new View[]{convertView.findViewById(R.id.x_label_closed_courses_aicteid),
                    convertView.findViewById(R.id.x_label_closed_courses_institution_name),
                    convertView.findViewById(R.id.x_label_closed_courses_institution_type),
                    convertView.findViewById(R.id.x_label_closed_courses_state),
                    convertView.findViewById(R.id.x_label_closed_courses_district),
                    convertView.findViewById(R.id.x_label_closed_courses_courseid),
                    convertView.findViewById(R.id.x_label_closed_courses_university),
                    convertView.findViewById(R.id.x_label_closed_courses_level),
                    convertView.findViewById(R.id.x_label_closed_courses_course),
                    convertView.findViewById(R.id.x_label_closed_courses_shift),
                    convertView.findViewById(R.id.x_label_closed_courses_full_part_time)};

            aicteId=(TextView)layoutViews[0];
            institutionName=(TextView)layoutViews[1];
            institutionType=(TextView)layoutViews[2];
            state=(TextView)layoutViews[3];
            district=(TextView)layoutViews[4];
            courseId=(TextView)layoutViews[5];
            university=(TextView)layoutViews[6];
            level=(TextView)layoutViews[7];
            course=(TextView)layoutViews[8];
            shift=(TextView)layoutViews[9];
            fullPartTime=(TextView)layoutViews[10];

            aicteId.setText(values[0]);
            institutionName.setText(values[1]);
            institutionType.setText(values[2]);
            state.setText(values[3]);
            district.setText(values[4]);
            courseId.setText(values[5]);
            university.setText(values[6]);
            level.setText(values[7]);
            course.setText(values[8]);
            shift.setText(values[9]);
            fullPartTime.setText(values[10]);

            return convertView;

        }else if (message.equals("closed_institutes")){
            convertView=infalInflater.inflate(R.layout.label_closed_institutes,null);

            TextView aicteId,institutionName,institutionType,address,state,district,city;

            View[] layoutViews= new View[]{convertView.findViewById(R.id.x_label_closed_institutes_aicteid),
                    convertView.findViewById(R.id.x_label_closed_institutes_institution_name),
                    convertView.findViewById(R.id.x_label_closed_institutes_institution_type),
                    convertView.findViewById(R.id.x_label_closed_institutes_address),
                    convertView.findViewById(R.id.x_label_closed_institutes_state),
                    convertView.findViewById(R.id.x_label_closed_institutes_district),
                    convertView.findViewById(R.id.x_label_closed_institutes_city)};


            aicteId=(TextView)layoutViews[0];
            institutionName=(TextView)layoutViews[1];
            institutionType=(TextView)layoutViews[2];
            address=(TextView)layoutViews[3];
            state=(TextView)layoutViews[4];
            district=(TextView)layoutViews[5];
            city=(TextView)layoutViews[6];

            aicteId.setText(values[0]);
            institutionName.setText(values[1]);
            institutionType.setText(values[4]);
            address.setText(values[5]);
            state.setText(values[2]);
            district.setText(values[3]);
            city.setText(values[6]);

            return convertView;

        }

        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
