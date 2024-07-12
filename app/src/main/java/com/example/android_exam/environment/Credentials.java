package com.example.android_exam.environment;

public class Credentials {



    public String getUrlDomain() {
        return "https://randomuser.me/api/?results=10";
    }

    public String getNewFresher() {
        return "https://randomuser.me/api/?results=2";
    }

    public String getNextPage(int page){
        return "https://randomuser.me/api/?page="+page+"&results=10";
    }
}