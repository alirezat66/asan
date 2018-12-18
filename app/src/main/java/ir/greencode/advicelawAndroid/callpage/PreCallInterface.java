package ir.greencode.advicelawAndroid.callpage;

import ir.greencode.advicelawAndroid.retrofit.respObject.CallStateResponse;
import ir.greencode.advicelawAndroid.retrofit.respObject.DoneAdviceDetailRes;

public interface PreCallInterface {
    void onError(String str);
    void onSuccessRes(DoneAdviceDetailRes resp);

    void onSuccessCancel();

    void onSuccessCallState(CallStateResponse res);
}
