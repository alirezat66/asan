package ir.greencode.advicelawAndroid.retrofit.respObject;

import java.util.List;

import ir.greencode.advicelawAndroid.objects.AdviserInfo;
import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.objects.Lawyer;

public class DoneAdviceDetailRes {
    public int getRequestId() {
        return requestId;
    }


    public List<Lawyer> getAdvisers() {
        return advisers;
    }

    public int getAdviserCount() {
        return adviserCount;
    }

    public List<DocumentImg> getDocuments() {
        return documents;
    }

    public String getCallDate() {
        return callDate;
    }

    public int getPrice() {
        return price;
    }

    public long getTimeOfadvice() {
        return timeOfadvice;
    }

    public String getTimeOfCall() {
        return timeOfCall;
    }

    public int getQualityVote() {
        return qualityVote;
    }

    public String getMainContext() {
        return mainContext;
    }

    public AdviserInfo getAdvicerInfo() {
        return adviserInfo;
    }

    int requestId;
    String mainContext;
    List<Lawyer> advisers;
    int adviserCount;
    List<DocumentImg> documents;
    String callDate;
    int price;
    long timeOfadvice;
    String timeOfCall;
    int qualityVote;
    AdviserInfo adviserInfo;
    String trackingCode;
    double adviceHourCount;
    long timeOfPayment;
    int callId;

    public int getCallId() {
        return callId;
    }

    public long getTimeOfPayment() {
        return timeOfPayment;
    }

    public double getAdviceHourCount() {
        return adviceHourCount;
    }

    public String getTrackingCode() {
        return trackingCode;
    }
}
