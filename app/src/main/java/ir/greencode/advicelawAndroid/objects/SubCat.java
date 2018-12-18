package ir.greencode.advicelawAndroid.objects;

public class SubCat {
    int catId;
    String catName;

    public SubCat(int id, String subCatName) {
        this.catId = id;
        this.catName = subCatName;
    }

    public int getId() {
        return catId;
    }

    public String getSubCatName() {
        return catName;
    }
}
