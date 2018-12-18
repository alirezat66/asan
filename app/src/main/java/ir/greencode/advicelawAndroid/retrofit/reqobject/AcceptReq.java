package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class AcceptReq {
    String token;
    int id;
    int adviserId;
    String responseHourId;
    long callTimestamp;
    double hourCount;

    public AcceptReq(String token, int id, int adviserId, String responseHourId, long callTimestamp, double hourCount) {
        this.token = token;
        this.id = id;
        this.adviserId = adviserId;
        this.responseHourId = responseHourId;
        this.callTimestamp = callTimestamp;
        this.hourCount = hourCount;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public int getAdviserId() {
        return adviserId;
    }

    public String getResponseHourId() {
        return responseHourId;
    }

    public long getCallTimestamp() {
        return callTimestamp;
    }

    public double getHourCount() {
        return hourCount;
    }
}
