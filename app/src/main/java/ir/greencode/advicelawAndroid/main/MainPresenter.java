package ir.greencode.advicelawAndroid.main;

import android.view.View;

import java.util.List;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.objects.Advice;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileInfoReq;

public class MainPresenter {
    MainInterfacde myInterface;
    ActivityMain view;
    POJOModel model  ;
    public MainPresenter(ActivityMain view, MainInterfacde myInterface) {
        this.myInterface  = myInterface;
        this.view = view;
        model = new POJOModel(this);

    }


    public void getAdviceList(String token) {
        model.getAdviceList(token);
    }

    public void onListReady(List<Advice> adviceList) {
        myInterface.onReadyAdviceList(adviceList);
    }

    public void onErrorReady(String str) {
        myInterface.onError(str);
    }

    public void getUserInfo(ProfileInfoReq profileInfoReq) {
        model.getUserInfo(profileInfoReq);
    }

    public void onSuccessProfile(Profile profile) {
        myInterface.onSuccessProfile(profile);
    }

    public void logout(String token) {
        model.signOut(token);
    }

    public void onSuccessLogout() {
        myInterface.onSuccessLogout();
    }
}
