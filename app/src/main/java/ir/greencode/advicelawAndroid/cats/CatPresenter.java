package ir.greencode.advicelawAndroid.cats;

import java.util.List;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.objects.Category;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CatReq;

public class CatPresenter {
    CatInterface myInterface ;
    POJOModel model;
    public CatPresenter(CatInterface myInterface) {
        this.myInterface = myInterface;
    }
    public void getCats(CatReq req){
        model = new POJOModel(this);
        model.getCats(req);
    }

    public void onReadyCats(List<Category> categories) {
        myInterface.onReadyCats(categories);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }
}
