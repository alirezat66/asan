package ir.greencode.advicelawAndroid.profile;

import ir.greencode.advicelawAndroid.database.Profile;

public interface ProfileInterface {

    void onSuccess(Profile profile);

    void onError(String message);
}
