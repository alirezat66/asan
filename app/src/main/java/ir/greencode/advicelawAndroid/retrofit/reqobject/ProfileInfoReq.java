package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class ProfileInfoReq {
    String token;
    int id;

    public ProfileInfoReq(String token, int id) {
        this.token = token;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public int getId() {
        return id;
    }
}
