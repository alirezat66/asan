package ir.greencode.advicelawAndroid.retrofit.reqobject;

public class AuthReq {
    String mobile;

    public AuthReq(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }
}
