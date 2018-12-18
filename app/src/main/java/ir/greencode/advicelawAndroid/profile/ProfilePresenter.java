package ir.greencode.advicelawAndroid.profile;

import ir.greencode.advicelawAndroid.Pojo.POJOModel;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileUpdateReq;

public class ProfilePresenter {
    ProfileInterface myInterface;

    POJOModel model;
    public ProfilePresenter(ProfileInterface myInterface) {
        this.myInterface = myInterface;
        model = new POJOModel(this);
    }

    public void updateProfile(ProfileUpdateReq profileUpdateReq) {
        model.updateProfile(profileUpdateReq);
    }

    public void onSuccess(Profile profile) {
        myInterface.onSuccess(profile);
    }

    public void onError(String message) {
        myInterface.onError(message);
    }
}
