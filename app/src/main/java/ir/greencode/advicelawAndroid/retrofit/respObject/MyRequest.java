package ir.greencode.advicelawAndroid.retrofit.respObject;

public class MyRequest {
    int requestId;
    long  requestTime;
    String title;
    String  subTitle;
    int catId;
    int subCatId;
    String imgCat;
    int adviserCont;
    int state;
    int offerId;

    public int getRequestId() {
        return requestId;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public int getCatId() {
        return catId;
    }

    public int getSubCatId() {
        return subCatId;
    }

    public String getImgCat() {
        return imgCat;
    }

    public int getAdviserCont() {
        return adviserCont;
    }

    public int getState() {
        return state;
    }

    public int getOfferId() {
        return offerId;
    }
}
