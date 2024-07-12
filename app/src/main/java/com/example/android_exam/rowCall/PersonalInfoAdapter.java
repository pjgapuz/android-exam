package com.example.android_exam.rowCall;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_exam.R;

import java.util.List;

public class PersonalInfoAdapter extends RecyclerView.Adapter<PersonalInfoAdapter.PersonalInfoHolder> {


    int ITEM = 0;

    int LOADING = 10;
    LayoutInflater inflater;
    List<PersonalInfo> personalInfoList;

    public PersonalInfoAdapter(Context context, List<PersonalInfo> personalInfos){
        this.inflater = LayoutInflater.from(context);
        this.personalInfoList = personalInfos;
    }

    @Override
    public PersonalInfoHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_persons,parent,false);
        return new PersonalInfoHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonalInfoAdapter.PersonalInfoHolder holder, int position) {

        holder.firstNameField.setText(personalInfoList.get(position).getFirstName());
        holder.lastNameField.setText(personalInfoList.get(position).getLastName());
        holder.birthdayDateField.setText(personalInfoList.get(position).getBirthDate());
        holder.ageNumField.setText(personalInfoList.get(position).getAge());
        holder.emailNameField.setText(personalInfoList.get(position).getEmailAddress());
        holder.mobileNumField.setText(personalInfoList.get(position).getPhoneNumber());
//        holder.personContactField.setText(personalInfoList.get(position).getContactPerson());
//        holder.personContactNumField.setText(personalInfoList.get(position).getContactPersonNum());
        Glide.with(inflater.getContext()).load(personalInfoList.get(position).getImageLink()).into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return personalInfoList.size();
    }

    class PersonalInfoHolder extends RecyclerView.ViewHolder {

        TextView firstNameField, lastNameField, birthdayDateField, ageNumField, mobileNumField, emailNameField;
//        TextView personContactField, personContactNumField;
        ImageView profileImage;
        public PersonalInfoHolder(View itemView) {
            super(itemView);

            firstNameField = itemView.findViewById(R.id.firstNamefield);
            lastNameField = itemView.findViewById(R.id.lastNamefield);
            birthdayDateField = itemView.findViewById(R.id.birthdayDatefield);
            ageNumField = itemView.findViewById(R.id.ageNumfield);
            mobileNumField = itemView.findViewById(R.id.mobileNumfield);
            emailNameField = itemView.findViewById(R.id.emailNamefield);
//            personContactField = itemView.findViewById(R.id.contactPersonfield);
//            personContactNumField = itemView.findViewById(R.id.contactPersonNumfield);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
