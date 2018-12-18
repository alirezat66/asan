package ir.greencode.advicelawAndroid.retrofit.reqobject;

import java.util.List;

public class InsertReq {
    String token;
    int userId;
    int catId;
    List<String> documents;
    String desc;

    public InsertReq(String token, int userId, int catId, List<String> documents, String desc) {
        this.token = token;
        this.userId = userId;
        this.catId = catId;
        this.documents = documents;
        this.desc = desc;
    }

    public String getToken() {
        return token;
    }

    public int getUserId() {
        return userId;
    }

    public int getCatId() {
        return catId;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public String getDesc() {
        return desc;
    }
}
