package ir.greencode.advicelawAndroid.retrofit;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.fabric.sdk.android.BuildConfig;
import ir.greencode.advicelawAndroid.controler.AppController;
import ir.greencode.advicelawAndroid.utils.Constants;
import io.fabric.sdk.android.services.network.HttpRequest;
import ir.greencode.advicelawAndroid.utils.PreferencesData;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
    static Retrofit retrofit = null;


    public static Retrofit getClient2(final Context con) {

            if (BuildConfig.DEBUG) {



                OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing;



                            ongoing= chain.request().newBuilder();
                            ongoing.addHeader(HttpRequest.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");
                            if(!PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()).equals("")){
                                ongoing.addHeader("token", PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()));

                            }



                        return chain.proceed(ongoing.build());
                    }
                }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();


                retrofit = new Builder().baseUrl(Consts.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();
            } else {

                OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    public Response intercept(Chain chain) throws IOException {

                        Request.Builder ongoing;

                            ongoing= chain.request().newBuilder();
                        if(!PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()).equals("")){
                            ongoing.addHeader("token", PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()));

                        }
                            ongoing.addHeader(HttpRequest.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");


                        return chain.proceed(ongoing.build());
                    }
                }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();


                retrofit = new Builder().baseUrl(Consts.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();
            }

        return retrofit;
    }
    public static Retrofit getClient() {



            if (BuildConfig.DEBUG) {

                OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing;



                            ongoing= chain.request().newBuilder();
                        if(!PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()).equals("")){
                            ongoing.addHeader("token", PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()));

                        }
                            ongoing.addHeader(HttpRequest.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");


                        return chain.proceed(ongoing.build());
                    }
                }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();


                retrofit = new Builder().baseUrl(Consts.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();
            } else {

                OkHttpClient myClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder ongoing;


                            ongoing= chain.request().newBuilder();
                        if(!PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()).equals("")){
                            ongoing.addHeader("token", PreferencesData.getString(Constants.PREF_TOKEN, AppController.getContext()));

                        }
                            ongoing.addHeader(HttpRequest.HEADER_CONTENT_TYPE, "application/json; charset=utf-8");


                        return chain.proceed(ongoing.build());
                    }
                }).connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS).build();


                retrofit = new Builder().baseUrl(Consts.URL_API).addConverterFactory(GsonConverterFactory.create()).client(myClient).build();
            }

        return retrofit;
    }
}
