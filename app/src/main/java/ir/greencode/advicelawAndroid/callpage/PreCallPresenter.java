package ir.greencode.advicelawAndroid.callpage;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.DoneAdviceDetailReq;
import ir.greencode.advicelawAndroid.retrofit.respObject.CallStateResponse;
import ir.greencode.advicelawAndroid.retrofit.respObject.DoneAdviceDetailRes;

public class PreCallPresenter {
    PreCallInterface myInterface;
    POJOModel model;
    public PreCallPresenter(PreCallInterface myInterface) {
        this.myInterface = myInterface;
        this.model =  new POJOModel(this);
    }
    public void getCallState(String token,String offerId){
        model.getCallState(token,offerId);
    }
    public void getDetail(DoneAdviceDetailReq req) {
        model.getDoneDetail(req);
    }

    public void onError(String str) {
        myInterface.onError(str);
    }

    public void onSuccessRes(DoneAdviceDetailRes resp) {
        myInterface.onSuccessRes(resp);
    }

    public void cancelRequest(CancelReq req) {
        model.cancelReq(req);
    }

    public void onSuccessCancel() {
        myInterface.onSuccessCancel();
    }

    public void onreadyCallRes(CallStateResponse res) {
        myInterface.onSuccessCallState(res);
    }
}
