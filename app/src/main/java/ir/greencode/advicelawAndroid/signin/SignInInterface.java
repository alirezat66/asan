package ir.greencode.advicelawAndroid.signin;

import ir.greencode.advicelawAndroid.retrofit.respObject.AuthenticationRes;

public interface SignInInterface {
    void onPhoneInvalid();
    void onAuthenticate(AuthenticationRes res);
    void onAuthenticateFaild(String resp);

    void onGCMOK();
}
