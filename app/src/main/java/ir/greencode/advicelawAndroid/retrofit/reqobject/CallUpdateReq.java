package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class CallUpdateReq {
    String token;
    int userId;
    int callId;
    String status;
    long time;
    int duration;
    String desc;

    public CallUpdateReq(String token, int userId, int callId, String status, long time, int duration, String desc) {
        this.token = token;
        this.userId = userId;
        this.callId = callId;
        this.status = status;
        this.time = time;
        this.duration = duration;
        this.desc = desc;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    public int getCallId() {
        return callId;
    }

    public String getStatus() {
        return status;
    }

    public long getTime() {
        return time;
    }

    public int getDuration() {
        return duration;
    }

    public String getDesc() {
        return desc;
    }
}
