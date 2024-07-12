package com.example.android_exam.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Person;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android_exam.R;
import com.example.android_exam.environment.AgeCalculator;
import com.example.android_exam.environment.Credentials;
import com.example.android_exam.rowCall.PersonalInfo;
import com.example.android_exam.rowCall.PersonalInfoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PersonalInfoActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView recyclerView;

    List<PersonalInfo> personalInfos;

    PersonalInfoAdapter personalInfoAdapter;

    SwipeRefreshLayout swipeRefreshLayout;

    ProgressBar progressBar;

    Button backPage;

    int page = 2;

    AgeCalculator ageCalculator;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        recyclerView = findViewById(R.id.recyclerView);
        personalInfos = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.swipeToRefresh);
        progressBar = findViewById(R.id.progressBar);
        backPage = findViewById(R.id.returnBack);


        extractData();

        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManage = (LinearLayoutManager) recyclerView.getLayoutManager();
                if(layoutManage != null && layoutManage.findLastCompletelyVisibleItemPosition() == personalInfoAdapter.getItemCount()-1){
                    addMoreData();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

        backPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    //SwipeRefreshLayout
    @Override
    public void onRefresh() {
        personalInfos.clear();
        personalInfoAdapter.notifyDataSetChanged();
        extractData();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void extractData(){
        Credentials creds = new Credentials();
        ageCalculator = new AgeCalculator();
        String URL_Address = creds.getUrlDomain();
        RequestQueue queue = Volley.newRequestQueue(PersonalInfoActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_Address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    System.out.println(result);

                    for(int i = 0; i<result.length(); i++){
                        PersonalInfo personalInfo = new PersonalInfo();
                        JSONObject index = result.getJSONObject(i);
                        JSONObject nameObj = index.getJSONObject("name");

                        personalInfo.setFirstName(nameObj.getString("first"));
                        personalInfo.setLastName(nameObj.getString("last"));
                        personalInfo.setImageLink("");

                        JSONObject dateOfBirth = index.getJSONObject("dob");
                        String birthdayTime = dateOfBirth.getString("date");
                        String birthday = birthdayTime.substring(0, birthdayTime.indexOf('T'));

                        String[] birthdayArray = birthday.split("-");
                        int birthYear = Integer.parseInt(birthdayArray[0]);
                        int birthMonth = Integer.parseInt(birthdayArray[1]);
                        int birthDay = Integer.parseInt(birthdayArray[2]);


                        personalInfo.setBirthDate(ageCalculator.putBirthday(birthYear,birthMonth,birthDay));
                        personalInfo.setAge(String.valueOf(ageCalculator.getAgeFromBirthday(birthYear,birthMonth,birthDay)));

                        JSONObject pictureObj = index.getJSONObject("picture");
                        personalInfo.setImageLink(pictureObj.getString("medium"));

                        personalInfo.setEmailAddress(index.getString("email"));
                        personalInfo.setPhoneNumber(index.getString("phone"));
                        personalInfos.add(personalInfo);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    personalInfoAdapter = new PersonalInfoAdapter(getApplicationContext(), personalInfos);
                    recyclerView.setAdapter(personalInfoAdapter);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);

    }

    private void addMoreData(){
        Credentials creds = new Credentials();
        ageCalculator = new AgeCalculator();
        String URL_Address = creds.getNextPage(page);
        RequestQueue queue = Volley.newRequestQueue(PersonalInfoActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_Address, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray result = response.getJSONArray("results");
                    System.out.println(result);

                    for(int i = 0; i<result.length(); i++){
                        PersonalInfo personalInfo = new PersonalInfo();
                        JSONObject index = result.getJSONObject(i);
                        JSONObject nameObj = index.getJSONObject("name");

                        personalInfo.setFirstName(nameObj.getString("first"));
                        personalInfo.setLastName(nameObj.getString("last"));
                        personalInfo.setImageLink("");

                        JSONObject dateOfBirth = index.getJSONObject("dob");
                        String birthdayTime = dateOfBirth.getString("date");
                        String birthday = birthdayTime.substring(0, birthdayTime.indexOf('T'));

                        String[] birthdayArray = birthday.split("-");
                        int birthYear = Integer.parseInt(birthdayArray[0]);
                        int birthMonth = Integer.parseInt(birthdayArray[1]);
                        int birthDay = Integer.parseInt(birthdayArray[2]);


                        personalInfo.setBirthDate(ageCalculator.putBirthday(birthYear,birthMonth,birthDay));
                        personalInfo.setAge(String.valueOf(ageCalculator.getAgeFromBirthday(birthYear,birthMonth,birthDay)));

                        JSONObject pictureObj = index.getJSONObject("picture");
                        personalInfo.setImageLink(pictureObj.getString("medium"));

                        personalInfo.setEmailAddress(index.getString("email"));
                        personalInfo.setPhoneNumber(index.getString("phone"));
                        personalInfos.add(personalInfo);
                    }

                    personalInfoAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    page++;

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);


    }



}