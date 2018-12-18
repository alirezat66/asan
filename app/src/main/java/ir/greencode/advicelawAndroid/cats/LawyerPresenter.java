package ir.greencode.advicelawAndroid.cats;

import java.util.List;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.choselawyer.LawyerInterface;
import ir.greencode.advicelawAndroid.objects.Course;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AcceptReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;

public class LawyerPresenter {
    LawyerInterface myInterface;
    POJOModel model;
    public LawyerPresenter(LawyerInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }
    public void getLawyerCourses(){
        model = new POJOModel(this);
    }

    public void onReadyCourses(List<Course> courses) {
        this.myInterface.onReadyList(courses);
    }

    public void acceptLawyer(AcceptReq acceptReq) {

        model.acceptRequest(acceptReq);
    }

    public void onError(String str) {
        myInterface.onError(str);
    }

    public void onSuccessAccept(String paymentUrl) {
        myInterface.onSuccessAccept(paymentUrl);
    }

    public void cancelReq(CancelReq cancelReq) {
        model.cancelReq(cancelReq);
    }

    public void onSuccessCancel() {
        myInterface.onSuccessCancel();
    }

    public void cancelOffer(CancelReq req) {
        model.cancelOffer(req);
    }
}
