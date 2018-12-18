package ir.greencode.advicelawAndroid.retrofit.reqobject;

import ir.greencode.advicelawAndroid.utils.Utility;

public class DoneAdviceDetailReq {
    String token;
    int offerId;

    public DoneAdviceDetailReq(int offerId) {
        this.offerId = offerId;
        this.token = Utility.getToken();
    }

    public String getToken() {
        return token;
    }

    public int getOfferId() {
        return offerId;
    }
}
