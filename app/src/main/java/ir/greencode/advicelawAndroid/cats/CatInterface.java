package ir.greencode.advicelawAndroid.cats;

import java.util.List;

import ir.greencode.advicelawAndroid.objects.Category;

public interface CatInterface {
    public void onReadyCats(List<Category> categories);

    void onError(String str);
}
