package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
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
    int entries;
    int parameters;
    List<String>categoriesNames;
    String[][][] data;
    String message;

    AdapterForAllLists(Context context,String message,int categories,int entries,int parameters, List<String> categoriesNames,String[][][] data){

        this.context=context;
        this.message=message;
        this.categories=categories;
        this.entries=entries;
        this.parameters=parameters;
        this.categoriesNames=categoriesNames;
        this.data=data;

    }

    @Override
    public int getGroupCount() {
        return this.categories;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.entries;
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

            aicteId=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_aicteid);
            name=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_name);
            address=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_address);
            district=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_district);
            institutionType=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_institution_type);
            women=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_women);
            minority=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_minority);
            courseDetails=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_course_details);
            facultyDetails=(TextView)convertView.findViewById(R.id.x_label_approved_institutions_faculty_details);

            aicteId.setText(values[0]);
            name.setText(values[1]);
            address.setText(values[2]);
            district.setText(values[3]);
            institutionType.setText(values[4]);
            women.setText(values[5]);
            minority.setText(values[6]);
            courseDetails.setText(values[7]);
            facultyDetails.setText(values[8]);

            return convertView;

        }else if (message.equals("nri/pio-fn-ciwg/tp")){
            convertView = infalInflater.inflate(R.layout.label_other_courses, null);

            TextView aicteId,institutionName,state,district,institutionType,program,university,level,nameOfCourses,approvedIntake,quotaSeats;

            aicteId=(TextView)convertView.findViewById(R.id.x_label_other_courses_aicteid);
            institutionName=(TextView)convertView.findViewById(R.id.x_label_other_courses_institution_name);
            state=(TextView)convertView.findViewById(R.id.x_label_other_courses_state);
            district=(TextView)convertView.findViewById(R.id.x_label_other_courses_district);
            institutionType=(TextView)convertView.findViewById(R.id.x_label_other_courses_institution_type);
            program=(TextView)convertView.findViewById(R.id.x_label_other_courses_program);
            university=(TextView)convertView.findViewById(R.id.x_label_other_courses_universityboard);
            level=(TextView)convertView.findViewById(R.id.x_label_other_courses_level);
            nameOfCourses=(TextView)convertView.findViewById(R.id.x_label_other_courses_course_name);
            approvedIntake=(TextView)convertView.findViewById(R.id.x_label_other_courses_approved_intake);
            quotaSeats=(TextView)convertView.findViewById(R.id.x_label_other_courses_nri_quota_seats);

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
            quotaSeats.setText(values[10]);

            return convertView;

        }else if (message.equals("faculty")){
            convertView=infalInflater.inflate(R.layout.label_faculties,null);

            TextView facultyId,name,gender,designation,joiningDate,specialisationArea,appointmentType,institutionName;

            facultyId=(TextView)convertView.findViewById(R.id.x_label_faculties_facultyid);
            name=(TextView)convertView.findViewById(R.id.x_label_faculties_name);
            gender=(TextView)convertView.findViewById(R.id.x_label_faculties_gender);
            designation=(TextView)convertView.findViewById(R.id.x_label_faculties_designation);
            joiningDate=(TextView)convertView.findViewById(R.id.x_label_faculties_joining_date);
            specialisationArea=(TextView)convertView.findViewById(R.id.x_label_faculties_specialisation_area);
            appointmentType=(TextView)convertView.findViewById(R.id.x_label_faculties_appointment_type);
            institutionName=(TextView)convertView.findViewById(R.id.x_label_faculties_institution_name);

            facultyId.setText(values[0]);
            name.setText(values[1]);
            gender.setText(values[2]);
            designation.setText(values[3]);
            joiningDate.setText(values[4]);
            specialisationArea.setText(values[5]);
            appointmentType.setText(values[6]);
            institutionName.setText(values[7]);

            return convertView;

        }else if (message.equals("closed_courses")){
            convertView=infalInflater.inflate(R.layout.label_closed_courses,null);

            TextView aicteId,institutionName,institutionType,state,district,courseId,university,level,course,shift,fullPartTime;

            aicteId=(TextView)convertView.findViewById(R.id.x_label_closed_courses_aicteid);
            institutionName=(TextView)convertView.findViewById(R.id.x_label_closed_courses_institution_name);
            institutionType=(TextView)convertView.findViewById(R.id.x_label_closed_courses_institution_type);
            state=(TextView)convertView.findViewById(R.id.x_label_closed_courses_state);
            district=(TextView)convertView.findViewById(R.id.x_label_closed_courses_district);
            courseId=(TextView)convertView.findViewById(R.id.x_label_closed_courses_courseid);
            university=(TextView)convertView.findViewById(R.id.x_label_closed_courses_university);
            level=(TextView)convertView.findViewById(R.id.x_label_closed_courses_level);
            course=(TextView)convertView.findViewById(R.id.x_label_closed_courses_course);
            shift=(TextView)convertView.findViewById(R.id.x_label_closed_courses_shift);
            fullPartTime=(TextView)convertView.findViewById(R.id.x_label_closed_courses_full_part_time);

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

            aicteId=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_aicteid);
            institutionName=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_institution_name);
            institutionType=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_institution_type);
            address=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_address);
            state=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_state);
            district=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_district);
            city=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_city);

            aicteId.setText(values[0]);
            institutionName.setText(values[1]);
            institutionType.setText(values[2]);
            address.setText(values[3]);
            state.setText(values[4]);
            district.setText(values[5]);
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
