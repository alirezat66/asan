package ir.greencode.advicelawAndroid.myrequest;

import java.util.List;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.retrofit.respObject.MyRequest;

public class MyRequestPresenter {
    MyRequestInterface myInterface;
    POJOModel model;
    public MyRequestPresenter(MyRequestInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }

    public void getMyRequest(String token) {
        model.getMyRequest(token);
    }

    public void onError(String message) {
        myInterface.onError(message);
    }

    public void onListReady(List<MyRequest> posts) {
        myInterface.onListReady(posts);
    }
}
