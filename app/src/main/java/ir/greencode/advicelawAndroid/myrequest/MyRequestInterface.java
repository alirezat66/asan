package ir.greencode.advicelawAndroid.myrequest;

import java.util.List;

import ir.greencode.advicelawAndroid.retrofit.respObject.MyRequest;

public interface MyRequestInterface {

    void onError(String message);

    void onListReady(List<MyRequest> posts);
}
