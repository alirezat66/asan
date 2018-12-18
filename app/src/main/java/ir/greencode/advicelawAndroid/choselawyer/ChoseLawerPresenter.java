package ir.greencode.advicelawAndroid.choselawyer;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.retrofit.respObject.WaitedAdviceDetial;
import ir.greencode.advicelawAndroid.retrofit.reqobject.GetWaitedDetailsReq;

public class ChoseLawerPresenter {
    ChoseLawyerInterface myInterface;
    POJOModel model;
    public ChoseLawerPresenter(ChoseLawyerInterface myInterface) {
        this.myInterface = myInterface;
        model  = new POJOModel(this);
    }


    public void readyLawyer(WaitedAdviceDetial detail) {
        myInterface.onReadyLawyer(detail);
    }

    public void getDetails(GetWaitedDetailsReq getWaitedDetailsReq) {
        model.getWaitingDetail(getWaitedDetailsReq);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void getActiveDetail(GetWaitedDetailsReq req) {
        model.getActiveDatail(req);
    }

    public void cancelReq(CancelReq req) {
        model.cancelReq(req);
    }

    public void onSuccessCancel() {
        myInterface.onSuccessCancel();
    }

    public void cancelOffer(CancelReq req) {
        model.cancelOffer(req);
    }
}
