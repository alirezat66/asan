package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class SubCatReq {
    String token;
    long timestamp;
    int id;

    public SubCatReq(String token, long timestamp, int id) {
        this.token = token;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getId() {
        return id;
    }
}
