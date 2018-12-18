package ir.greencode.advicelawAndroid.objects;

import java.util.ArrayList;
import java.util.List;

import ir.greencode.advicelawAndroid.utils.ToJsonClass;

public class Lawyer extends ToJsonClass{
    int id;
    List<String>jobTitle;
    String fName;
    String lName;
    int totalAdvice;
    double averageScore;
    int allPosibleAverageScore;
    int payEachHour;
    String score;
    int rate ;
    int yearOfWork;
    List<String>locations;
    List<Course>Documents;
    List<FreeTime>freeTimes;
    String userImg;

    public String getUserImg() {
        return userImg;
    }

    public List<String> getJobTitle() {
        return jobTitle;
    }

    public List<FreeTime> getFreeTimes() {
        return freeTimes;
    }

    public int getId() {
        return id;
    }


    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getTotalAdvice() {
        return totalAdvice;
    }


    public double getAverageScore() {
        return averageScore;
    }

    public double getAllPosibleAverageScore() {
        return allPosibleAverageScore;
    }

    public int getPayEachHour() {
        return payEachHour;
    }

    public String getScore() {
        return score;
    }

    public int getRate() {
        return rate;
    }


    public int getYearOfWork() {
        return yearOfWork;
    }

    public List<String> getLocations() {
        return locations;
    }

    public List<Course> getDocuments() {
        return Documents;
    }
}
