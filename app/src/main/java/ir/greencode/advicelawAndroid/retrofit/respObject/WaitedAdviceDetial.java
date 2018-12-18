package ir.greencode.advicelawAndroid.retrofit.respObject;

import java.util.ArrayList;
import java.util.List;

import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.objects.Lawyer;

public class WaitedAdviceDetial {
    int requestId;
    String context;
    List<Lawyer> advisers;
    int adviserCount;
    List<DocumentImg> documents;


    public int getRequestId() {
        return requestId;
    }

    public List<Lawyer> getAdvisers() {
        return advisers;
    }

    public int getAdviserCount() {
        return adviserCount;
    }

    public String getContext() {
        return context;
    }

    public List<DocumentImg> getDocuments() {
        return documents;
    }
}
