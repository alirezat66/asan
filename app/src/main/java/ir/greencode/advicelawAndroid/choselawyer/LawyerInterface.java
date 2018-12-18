package ir.greencode.advicelawAndroid.choselawyer;

import java.util.List;

import ir.greencode.advicelawAndroid.objects.Course;

public interface LawyerInterface {
    public void onReadyList (List<Course> courseList);

    void onError(String str);

    void onSuccessAccept(String paymentUrl);

    void onSuccessCancel();
}
