package ir.greencode.advicelawAndroid.Pojo;

import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallState;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ir.greencode.advicelawAndroid.calling.CallPresenter;
import ir.greencode.advicelawAndroid.callpage.PreCallPresenter;
import ir.greencode.advicelawAndroid.cats.CatPresenter;
import ir.greencode.advicelawAndroid.cats.LawyerPresenter;
import ir.greencode.advicelawAndroid.cats.SubCatPresenter;
import ir.greencode.advicelawAndroid.choselawyer.ChoseLawerPresenter;
import ir.greencode.advicelawAndroid.controler.AppDatabase;
import ir.greencode.advicelawAndroid.database.Profile;
import ir.greencode.advicelawAndroid.insertrequest.InsertRequestPresenter;
import ir.greencode.advicelawAndroid.main.MainPresenter;
import ir.greencode.advicelawAndroid.myrequest.MyRequestPresenter;
import ir.greencode.advicelawAndroid.objects.Advice;
import ir.greencode.advicelawAndroid.objects.Category;
import ir.greencode.advicelawAndroid.objects.Course;
import ir.greencode.advicelawAndroid.objects.DocumentImg;
import ir.greencode.advicelawAndroid.objects.Lawyer;
import ir.greencode.advicelawAndroid.objects.SubCat;
import ir.greencode.advicelawAndroid.profile.ProfileInterface;
import ir.greencode.advicelawAndroid.profile.ProfilePresenter;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AcceptReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CallUpdateReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CancelReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.DoneAdviceDetailReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.InsertReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileInfoReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.ProfileUpdateReq;
import ir.greencode.advicelawAndroid.retrofit.respObject.CallStateResponse;
import ir.greencode.advicelawAndroid.retrofit.respObject.DoneAdviceDetailRes;
import ir.greencode.advicelawAndroid.retrofit.respObject.MyRequest;
import ir.greencode.advicelawAndroid.retrofit.respObject.WaitedAdviceDetial;
import ir.greencode.advicelawAndroid.retrofit.CallerService;
import ir.greencode.advicelawAndroid.retrofit.MyMethods;
import ir.greencode.advicelawAndroid.retrofit.ServerListener;
import ir.greencode.advicelawAndroid.retrofit.reqobject.AuthReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.CatReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.GetWaitedDetailsReq;
import ir.greencode.advicelawAndroid.retrofit.reqobject.SubCatReq;
import ir.greencode.advicelawAndroid.retrofit.respObject.AuthenticationRes;
import ir.greencode.advicelawAndroid.retrofit.respObject.ResultState;
import ir.greencode.advicelawAndroid.signin.SignInPresenter;

public class POJOModel  implements ServerListener{
    MainPresenter mainPresenter;
    ChoseLawerPresenter choseLawerPresenter;
    CatPresenter catPresenter;
    SubCatPresenter subCatPresenter ;
    LawyerPresenter lawyerPresenter;
    SignInPresenter signInPresenter;
    InsertRequestPresenter insertRequestPresenter;
    ProfilePresenter profilePresenter;
    PreCallPresenter preCallPresenter;
    MyRequestPresenter myRequestPresenter;
    CallPresenter callPresenter;
    public POJOModel(MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;

    }

    public POJOModel(ChoseLawerPresenter choseLawerPresenter) {
        this.choseLawerPresenter = choseLawerPresenter;
    }

    public POJOModel(CatPresenter catPresenter) {
        this.catPresenter = catPresenter;

    }


    public POJOModel(SubCatPresenter subCatPresenter) {
        this.subCatPresenter = subCatPresenter;

    }

    public POJOModel(LawyerPresenter lawyerPresenter) {
        this.lawyerPresenter = lawyerPresenter;
        getCourses();
    }

    public POJOModel(SignInPresenter signInPresenter) {
        this.signInPresenter = signInPresenter;
    }

    public POJOModel(InsertRequestPresenter insertRequestPresenter) {
        this.insertRequestPresenter = insertRequestPresenter;
    }

    public POJOModel(ProfilePresenter profilePresenter) {
        this.profilePresenter = profilePresenter;
    }

    public POJOModel(PreCallPresenter preCallPresenter) {
        this.preCallPresenter = preCallPresenter;
    }

    public POJOModel(MyRequestPresenter myRequestPresenter) {
        this.myRequestPresenter = myRequestPresenter;
    }

    public POJOModel(CallPresenter callPresenter) {
        this.callPresenter = callPresenter;


    }

    private void getCourses() {
        Course course = new Course("قضاوت بین المللی CFEED","در این دوره یاد میگیریم که چقدر خوبه آدم در حیطه بین المللی هم حرفی برای گفتن داشته باشه و اطلاعاتی رو ردو بدل کنه و..","");
        Course course1 = new Course("ISO 2009","در این دوره یاد میگیریم که چقدر خوبه آدم در حیطه بین المللی هم حرفی برای گفتن داشته باشه و اطلاعاتی رو ردو بدل کنه و..","");
        List<Course>courses = new ArrayList<>();
        courses.add(course);
        courses.add(course1);
        this.lawyerPresenter.onReadyCourses(courses);
    }


    public void getAdviceList(String token) {
        CallerService.getAdviceList(this,token);
    }

    public void getCats(CatReq req) {
       CallerService.getCats(this,req);
    }

    public void getSubCats(SubCatReq req) {
        CallerService.getSubCats(this,req);
    }

    @Override
    public void onFailure(int i, String str) {
        if(i== MyMethods.Authentication.getMethodValue()){
            signInPresenter.onErrorReady(str);
        }else if(i == MyMethods.GetCats.getMethodValue()){
            catPresenter.onErrorReady(str);
        }else if(i == MyMethods.GetActiveList.getMethodValue()){
            mainPresenter.onErrorReady(str);
        }else if(i==MyMethods.GetWaitedDetail.getMethodValue()){
            choseLawerPresenter.onErrorReady(str);
        }else if(i==MyMethods.GetActivatedDetail.getMethodValue()){
            choseLawerPresenter.onErrorReady(str);

        }
        else if(i==MyMethods.InsertRequest.getMethodValue()){

                insertRequestPresenter.onError(str);

        }else if(i==MyMethods.AcceptRequest.getMethodValue()){
            lawyerPresenter.onError(str);
        }else if(i==MyMethods.CancelReq.getMethodValue()){
            if(lawyerPresenter!=null) {
                lawyerPresenter.onError(str);
            }else if(choseLawerPresenter!=null){
                choseLawerPresenter.onErrorReady(str);
            }else if(preCallPresenter!=null){
                preCallPresenter.onError(str);
            }

        }else if(i==MyMethods.CancelOffer.getMethodValue()){
            if(lawyerPresenter!=null) {
                lawyerPresenter.onError(str);
            }else if(choseLawerPresenter!=null){
                choseLawerPresenter.onErrorReady(str);
            }else if(preCallPresenter!=null){
                preCallPresenter.onError(str);
            }

        }
        else if(i==MyMethods.ProfileInfo.getMethodValue()){
                if(mainPresenter!=null){

                    mainPresenter.onErrorReady(str);

            }
        }else if(i==MyMethods.UpdateProfile.getMethodValue()){

                profilePresenter.onError(str);

        }else if(i==MyMethods.GetDoneDetial.getMethodValue()){
            preCallPresenter.onError(str);
        }else if(i==MyMethods.GetMyRequest.getMethodValue()){
            myRequestPresenter.onError(str);

        }else if(i==MyMethods.SendGCMToken.getMethodValue()){

                signInPresenter.onErrorReady(str);

        }else if(i==MyMethods.Logout.getMethodValue()){
            mainPresenter.onErrorReady(str);
        }
    }

    @Override
    public void onSuccess(int i, JsonObject jsonObject) throws JSONException {
        Gson gson = new Gson();
        JsonObject error =  jsonObject.getAsJsonObject("resultState");
        ResultState errorRes = gson.fromJson(error,ResultState.class);
        if(i== MyMethods.Authentication.getMethodValue()){
            AuthenticationRes res = gson.fromJson(jsonObject,AuthenticationRes.class);

            if(res.getResultState().isSuccess()){
                    signInPresenter.onRespReady(res);
                }else {
                    signInPresenter.onErrorReady(res.getResultState().getMessage());
                }
            }else if(i==MyMethods.GetCats.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Category>>(){}.getType();
                List<Category> posts = gson.fromJson(jsonArray.toString(), listType);
                catPresenter.onReadyCats(posts);
            }else {
                catPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetCallState.getMethodValue()){
            if(errorRes.isSuccess()){

                JsonObject object = jsonObject.get("result").getAsJsonObject();
                CallStateResponse res = gson.fromJson(object, CallStateResponse.class);

                preCallPresenter.onreadyCallRes(res);
            }else {
                preCallPresenter.onError(errorRes.getMessage());
            }
        }
        else if(i==MyMethods.GetSubCat.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<SubCat>>(){}.getType();
                List<SubCat> posts = gson.fromJson(jsonArray.toString(), listType);
                subCatPresenter.onReadySubCat(posts);
            }else {
                subCatPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i == MyMethods.GetActiveList.getMethodValue()){

            if(errorRes.isSuccess()) {
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<Advice>>() {
                }.getType();
                List<Advice> posts = gson.fromJson(jsonArray.toString(), listType);

                mainPresenter.onListReady(posts);
            }else {
                mainPresenter.onErrorReady(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetWaitedDetail.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                WaitedAdviceDetial res = gson.fromJson(obj,WaitedAdviceDetial.class);
                choseLawerPresenter.readyLawyer(res);
            }
        }else if(i==MyMethods.GetActivatedDetail.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                WaitedAdviceDetial res = gson.fromJson(obj,WaitedAdviceDetial.class);
                choseLawerPresenter.readyLawyer(res);
            }
        }
        else if(i==MyMethods.InsertRequest.getMethodValue()){
            if(errorRes.isSuccess()){
                insertRequestPresenter.onSuccess();
            }else {
                insertRequestPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.AcceptRequest.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                String paymentUrl = obj.get("paymentUrl").getAsString();
                lawyerPresenter.onSuccessAccept(paymentUrl);
            }else {
                lawyerPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.CancelReq.getMethodValue()){
            if(errorRes.isSuccess()){
                if(lawyerPresenter!=null) {
                    lawyerPresenter.onSuccessCancel();
                }else if(choseLawerPresenter!=null){
                    choseLawerPresenter.onSuccessCancel();
                }else if(preCallPresenter!=null){
                    preCallPresenter.onSuccessCancel();
                }
            }else {
                if(lawyerPresenter!=null) {
                    lawyerPresenter.onError(errorRes.getMessage());
                }else if(choseLawerPresenter!=null){
                    choseLawerPresenter.onErrorReady(errorRes.getMessage());
                }else if(preCallPresenter!=null){
                    preCallPresenter.onError(errorRes.getMessage());
                }

            }
        }else if(i==MyMethods.CancelOffer.getMethodValue()){
            if(errorRes.isSuccess()){
                if(lawyerPresenter!=null) {
                    lawyerPresenter.onSuccessCancel();
                }else if(choseLawerPresenter!=null){
                    choseLawerPresenter.onSuccessCancel();
                }else if(preCallPresenter!=null){
                    preCallPresenter.onSuccessCancel();
                }
            }else {
                if(lawyerPresenter!=null) {
                    lawyerPresenter.onError(errorRes.getMessage());
                }else if(choseLawerPresenter!=null){
                    choseLawerPresenter.onErrorReady(errorRes.getMessage());
                }else if(preCallPresenter!=null){
                    preCallPresenter.onError(errorRes.getMessage());
                }

            }
        }
        else if(i==MyMethods.ProfileInfo.getMethodValue()){
            if(errorRes.isSuccess()){
                if(mainPresenter!=null){
                    JsonObject obj = jsonObject.get("result").getAsJsonObject();
                    Profile profile = gson.fromJson(obj,Profile.class);
                    mainPresenter.onSuccessProfile(profile);
                }
            }else {
                if(mainPresenter!=null){

                    mainPresenter.onErrorReady(errorRes.getMessage());
                }
            }
        }else if(i==MyMethods.UpdateProfile.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject obj = jsonObject.get("result").getAsJsonObject();
                Profile profile = gson.fromJson(obj,Profile.class);
                profilePresenter.onSuccess(profile);
            }else {
                profilePresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetDoneDetial.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonObject object = jsonObject.get("result").getAsJsonObject();
                DoneAdviceDetailRes resp = gson.fromJson(object,DoneAdviceDetailRes.class);
                preCallPresenter.onSuccessRes(resp);
            }else {
                preCallPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.GetMyRequest.getMethodValue()){
            if(errorRes.isSuccess()){
                JsonArray jsonArray = jsonObject.get("result").getAsJsonArray();
                Type listType = new TypeToken<List<MyRequest>>(){}.getType();
                List<MyRequest> posts = gson.fromJson(jsonArray.toString(), listType);
                myRequestPresenter.onListReady(posts);
            }else {
                myRequestPresenter.onError(errorRes.getMessage());
            }
        }else if(i==MyMethods.SendGCMToken.getMethodValue()){
            if(errorRes.isSuccess()){
                signInPresenter.onGCMOK();
            }
        }else if(i==MyMethods.Logout.getMethodValue()){
            if(errorRes.isSuccess()){
                mainPresenter.onSuccessLogout();
            }
        }
    }

    public void callAuthentication(AuthReq authReq) {
        CallerService.signUp(this,authReq);
    }

    public void getWaitingDetail(GetWaitedDetailsReq getWaitedDetailsReq) {
        CallerService.getAdviceDetail(this,getWaitedDetailsReq);
    }

    public void insertRequest(InsertReq insertReq) {
        CallerService.insertRequest(this,insertReq);
    }

    public void getActiveDatail(GetWaitedDetailsReq req) {
        CallerService.getActiveAdviceDetail(this,req);
    }

    public void acceptRequest(AcceptReq acceptReq) {
        CallerService.acceptRequest(this,acceptReq);
    }

    public void cancelReq(CancelReq req) {
        CallerService.cancelReq(this,req);
    }

    public void getUserInfo(ProfileInfoReq profileInfoReq) {
        CallerService.getProfileInfo(this,profileInfoReq);
    }

    public void updateProfile(ProfileUpdateReq profileUpdateReq) {
        CallerService.updateProfile(this,profileUpdateReq);
    }

    public void getDoneDetail(DoneAdviceDetailReq req) {
        CallerService.getDoneDetial(this,req);
    }

    public void getMyRequest(String token) {
        CallerService.getMyRequests(this,token);
    }

    public void cancelOffer(CancelReq req) {
        CallerService.cancelOffer(this,req);
    }
    public void updateCall(CallUpdateReq req) {
        CallerService.updateCall(this,req);
    }

    public void getCallState(String token, String offerId) {
        CallerService.getCallState(this,token,offerId);
    }

    public void sendGCM(String token, String gcmToken) {
        CallerService.sendGCMToken(this,token,gcmToken);
    }

    public void signOut(String token) {
        CallerService.logOut(this,token);
    }
}
