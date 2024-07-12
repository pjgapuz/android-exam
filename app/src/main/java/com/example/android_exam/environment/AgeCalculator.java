package com.example.android_exam.environment;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {

    public int getAgeFromBirthday(int year, int month, int day) {
        LocalDate now = LocalDate.now();
        LocalDate birthdate = LocalDate.of(year, month, day);
        return Period.between(birthdate, now).getYears();
    }

    public String putBirthday(int year, int month, int day){
        String dateFormat = null;
        switch(month){
            case 1: dateFormat="JAN. ";break;
            case 2: dateFormat="FEB. ";break;
            case 3: dateFormat="MAR. ";break;
            case 4: dateFormat="APR. ";break;
            case 5: dateFormat="MAY ";break;
            case 6: dateFormat="JUN. ";break;
            case 7: dateFormat="JUL. ";break;
            case 8: dateFormat="AUG. ";break;
            case 9: dateFormat="SEPT. ";break;
            case 10: dateFormat="OCT. ";break;
            case 11: dateFormat="NOV. ";break;
            case 12: dateFormat="DEC. ";break;
            default:break;
        }

        dateFormat = dateFormat + day + ", " + year;
        return dateFormat;
    }
}
