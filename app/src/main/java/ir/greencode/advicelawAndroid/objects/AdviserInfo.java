package ir.greencode.advicelawAndroid.objects;

import java.util.ArrayList;
import java.util.List;

public class AdviserInfo {
    int id;
    String fName;
    String lName;
    ArrayList<String> type;
    String lastEd;
    int votes;
    String score;
    int payEachHour;
    String jobTitle;


        public int getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public List<String> getType() {
        return type;
    }

    public String getLastEd() {
        return lastEd;
    }

    public int getVotes() {
        return votes;
    }

    public String getScore() {
        return score;
    }

    public int getPayEachHour() {
        return payEachHour;
    }

    public String getJobTitle() {
        return jobTitle;
    }
}
