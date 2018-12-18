package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class CancelReq {
    String token;
    int id;
    int userId;

    public CancelReq(String token, int id, int userId) {
        this.token = token;
        this.id = id;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }
}
