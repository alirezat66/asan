package ir.greencode.advicelawAndroid.calling;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CallUpdateReq;

public class CallPresenter {
    POJOModel model;
    public CallPresenter() {
        model = new POJOModel(this);
    }

    public void updateCall(CallUpdateReq req) {
        model.updateCall(req);
    }
}
