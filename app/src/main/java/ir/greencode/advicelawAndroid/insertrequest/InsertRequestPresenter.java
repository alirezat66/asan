package ir.greencode.advicelawAndroid.insertrequest;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.retrofit.reqobject.InsertReq;

public class InsertRequestPresenter {
    InsertRequestInterface myInterface;
    POJOModel model;
    public InsertRequestPresenter(InsertRequestInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }

    public void insert(InsertReq insertReq) {
        model.insertRequest(insertReq);
    }

    public void onSuccess() {
        myInterface.onSuccessInsert();
    }

    public void onError(String str) {
        myInterface.onFaildInsert(str);
    }
}
