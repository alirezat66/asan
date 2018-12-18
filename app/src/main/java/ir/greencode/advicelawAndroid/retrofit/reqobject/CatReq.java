package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class CatReq {
    String token;
    long timestamp;
    String adviserId;

    public CatReq(String token, long timestamp, String adviserId) {
        this.token = token;
        this.timestamp = timestamp;
        this.adviserId = adviserId;
    }

    public String getToken() {
        return token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getAdvicerId() {
        return adviserId;
    }
}
