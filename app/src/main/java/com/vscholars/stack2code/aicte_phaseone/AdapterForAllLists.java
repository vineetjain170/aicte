package com.vscholars.stack2code.aicte_phaseone;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vineet_jain on 8/6/17.
 */

public class AdapterForAllLists extends BaseAdapter{
    Context context;
    String message;
    String[][] data;
    AdapterForAllLists(Context context,String message, String[][] data){
        this.context=context;
        this.message=message;
        this.data=data;
    }
    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=((Activity)context).getLayoutInflater();
        if(message.equals("approved_institutes")){

            convertView=layoutInflater.inflate(R.layout.label_approved_institutions,null);
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

            aicteId.setText(data[position][0]);
            name.setText(data[position][1]);
            address.setText(data[position][2]);
            district.setText(data[position][3]);
            institutionType.setText(data[position][4]);
            women.setText(data[position][5]);
            minority.setText(data[position][6]);
            courseDetails.setText(data[position][7]);
            facultyDetails.setText(data[position][8]);

            return convertView;

        }
        else if (message.equals("nri/pio-fn-ciwg/tp")){
            convertView=layoutInflater.inflate(R.layout.label_other_courses,null);
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

            aicteId.setText(data[position][0]);
            institutionName.setText(data[position][1]);
            state.setText(data[position][2]);
            district.setText(data[position][3]);
            institutionType.setText(data[position][4]);
            program.setText(data[position][5]);
            university.setText(data[position][6]);
            level.setText(data[position][7]);
            nameOfCourses.setText(data[position][8]);
            approvedIntake.setText(data[position][9]);
            quotaSeats.setText(data[position][10]);

            return convertView;

        }
        else if (message.equals("faculty")){
            convertView=layoutInflater.inflate(R.layout.label_faculties,null);
            TextView facultyId,name,gender,designation,joiningDate,specialisationArea,appointmentType,institutionName;

            facultyId=(TextView)convertView.findViewById(R.id.x_label_faculties_facultyid);
            name=(TextView)convertView.findViewById(R.id.x_label_faculties_name);
            gender=(TextView)convertView.findViewById(R.id.x_label_faculties_gender);
            designation=(TextView)convertView.findViewById(R.id.x_label_faculties_designation);
            joiningDate=(TextView)convertView.findViewById(R.id.x_label_faculties_joining_date);
            specialisationArea=(TextView)convertView.findViewById(R.id.x_label_faculties_specialisation_area);
            appointmentType=(TextView)convertView.findViewById(R.id.x_label_faculties_appointment_type);
            institutionName=(TextView)convertView.findViewById(R.id.x_label_faculties_institution_name);

            facultyId.setText(data[position][0]);
            name.setText(data[position][1]);
            gender.setText(data[position][2]);
            designation.setText(data[position][3]);
            joiningDate.setText(data[position][4]);
            specialisationArea.setText(data[position][5]);
            appointmentType.setText(data[position][6]);
            institutionName.setText(data[position][7]);

            return convertView;
        }
        else if (message.equals("closed_courses")){
            convertView=layoutInflater.inflate(R.layout.label_closed_courses,null);
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

            aicteId.setText(data[position][0]);
            institutionName.setText(data[position][1]);
            institutionType.setText(data[position][2]);
            state.setText(data[position][3]);
            district.setText(data[position][4]);
            courseId.setText(data[position][5]);
            university.setText(data[position][6]);
            level.setText(data[position][7]);
            course.setText(data[position][8]);
            shift.setText(data[position][9]);
            fullPartTime.setText(data[position][10]);

            return convertView;

        }
        else if (message.equals("closed_institutes")){
            convertView=layoutInflater.inflate(R.layout.label_closed_institutes,null);
            TextView aicteId,institutionName,institutionType,address,state,district,city;

            aicteId=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_aicteid);
            institutionName=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_institution_name);
            institutionType=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_institution_type);
            address=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_address);
            state=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_state);
            district=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_district);
            city=(TextView)convertView.findViewById(R.id.x_label_closed_institutes_city);

            aicteId.setText(data[position][0]);
            institutionName.setText(data[position][1]);
            institutionType.setText(data[position][2]);
            address.setText(data[position][3]);
            state.setText(data[position][4]);
            district.setText(data[position][5]);
            city.setText(data[position][6]);

            return convertView;

        }
        return null;
    }
}
