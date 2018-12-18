package ir.greencode.advicelawAndroid.main;

import java.util.List;

import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.objects.Advice;

public interface MainInterfacde {
    public void onReadyAdviceList(List<Advice> adviceList);

    void onError(String str);

    void onSuccessProfile(Profile profile);

    void onSuccessLogout();
}
