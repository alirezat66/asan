package ir.greencode.advicelawAndroid.retrofit;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface reqInterface {
    @FormUrlEncoded
    @POST("api/v1/user/authentication")
    Call<JsonObject> Authenticate(@Field("mobile") String mobile);
    @FormUrlEncoded
    @POST("api/v1/user/getCategories")
    Call<JsonObject> GetCats(@Field("token") String mobile , @Field("timestamp") long timestamp , @Field("adviserId") String adviserId);
    @FormUrlEncoded
    @POST("api/v1/user/getSubCat")
    Call<JsonObject> GetSubCats(@Field("token") String mobile , @Field("timestamp") long timestamp , @Field("id") int id);


    @FormUrlEncoded
    @POST("api/v1/user/getActiveRequests")
    Call<JsonObject> GetActiveAdvice(@Field("token") String token);

    @FormUrlEncoded
    @POST("api/v1/user/getWaitedRequestDetial")
    Call<JsonObject> GetWaitedRequestDetial(@Field("token") String token, @Field("requestId") int id);



    @FormUrlEncoded
    @POST("api/v1/user/createRequest")
     Call<JsonObject> createRequest(@Field("token") String token, @Field("userId")   int userId, @Field("catId")   int catId, @Field("documents") List<String> documents,@Field("desc") String desc );

    @FormUrlEncoded
    @POST("api/v1/user/createRequest")
    Call<JsonObject> createRequest(@Field("token") String token, @Field("userId")   int userId, @Field("catId")   int catId, @Field("documents")String documents,@Field("desc") String desc );

    @FormUrlEncoded
    @POST("api/v1/user/getAcceptedRequestDetail")
    Call<JsonObject> GetActivatedReqRequestDetial(@Field("token") String token, @Field("requestId") int id);

    @FormUrlEncoded
    @POST("api/v1/user/acceptAdviser")
    Call<JsonObject> acceptAdviser(@Field("token") String token, @Field("id") int id
            ,@Field("adviserId") int adviserId,@Field("responseHours") String responseHourId,@Field("callTimestamp") long callTimestamp,@Field("hourCount") double hourCount);

    @FormUrlEncoded
    @POST("api/v1/user/cancelRequest")
    Call<JsonObject> cancelRequest(@Field("token") String token, @Field("id") int id
            ,@Field("userId") int userId);
    @FormUrlEncoded
    @POST("api/v1/user/getUserInfo")
    Call<JsonObject> getProfileInfo(@Field("token") String token, @Field("id") int id);

    @FormUrlEncoded
    @POST("api/v1/user/updateUser")
    Call<JsonObject> updateProfile(@Field("token") String token, @Field("id") int id,@Field("firstName")String firstName
    ,@Field("lastName") String lastName,@Field("birthDate")long birthDate,@Field("sex")String sex,@Field("email")String email,
                                   @Field("userImg")String userImg,@Field("nationalCode")String nationalCode);
    @FormUrlEncoded
    @POST("api/v1/user/getDoneRequestDetial")
    Call<JsonObject> getDoneRequestDetial(@Field("token") String token, @Field("offerId") int offerId);
    @FormUrlEncoded
    @POST("api/v1/user/getHistoryRequests")
    Call<JsonObject> getMyRequest(@Field("token") String token);

    @FormUrlEncoded
    @POST("api/v1/user/checkActiveCallRequest")
    Call<JsonObject> checkCallState(@Field("token") String token,@Field("offerId")String offerId);


    @FormUrlEncoded
    @POST("api/v1/user/updateCallStatus")
    Call<JsonObject> updateCall(@Field("token") String token,@Field("userId") int id,
                                @Field("callId") int callid,@Field("status") String status
            ,@Field("time") long time,@Field("duration") int duration
            ,@Field("desc") String desc);

    @FormUrlEncoded
    @POST("api/v1/user/cancelOffer")
    Call<JsonObject> cancelOffer(@Field("token") String token, @Field("offerId") int id
            ,@Field("userId") int userId);

    @FormUrlEncoded
    @POST("api/v1/user/sendFCMToken")
    Call<JsonObject> sendGcmToken(@Field("token") String token, @Field("FCMToken") String gcmToken
            );
            @FormUrlEncoded
    @POST("api/v1/user/logout")
    Call<JsonObject> logOut(@Field("token") String token
            );
}
