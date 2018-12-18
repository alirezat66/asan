package ir.greencode.advicelawAndroid.cats;

import java.util.List;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.objects.SubCat;
import ir.greencode.advicelawAndroid.retrofit.reqobject.SubCatReq;

public class SubCatPresenter {
    SubCatInterface myInterface;
    POJOModel model;
    public SubCatPresenter(SubCatInterface myInterface) {
        this.myInterface = myInterface;
    }
    public void getSubCats(SubCatReq req){
        model = new POJOModel(this);
        model.getSubCats(req);

    }

    public void onReadySubCat(List<SubCat> subCats) {
        myInterface.onReadySubCat(subCats);
    }

    public void onErrorReady(String message) {
        myInterface.onError(message);
    }
}
