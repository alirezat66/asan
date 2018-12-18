package ir.greencode.advicelawAndroid.cats;

import java.util.List;

import ir.greencode.advicelawAndroid.objects.SubCat;

public interface SubCatInterface {
    public void onReadySubCat(List<SubCat> subCats);

    void onError(String message);
}
