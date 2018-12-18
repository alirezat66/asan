package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class GetWaitedDetailsReq {
    int requestId;
    String token;

    public int getRequestId() {
        return requestId;
    }

    public String getToken() {
        return token;
    }

    public GetWaitedDetailsReq(int requestId, String token) {
        this.requestId = requestId;
        this.token = token;
    }
}
