package com.QiSheng.ShopBoss.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;


import com.QiSheng.ShopBoss.base.App;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtils {
    private Apis apis;

    private NetUtils(){
        initOkHttp();
    }
    private static class SingleInstance{
        private static NetUtils utils = new NetUtils();
    }
    public static NetUtils getInstance(){
        return SingleInstance.utils;
    }
    private void initOkHttp() {
        //添加日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置缓存时间
        OkHttpClient build = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.client(build).baseUrl("https://www.luoyanglx.com:8443/lianxiang_app/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apis = retrofit.create(Apis.class);
    }

    public Apis getApis() {
        return apis;
    }


    public class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
           String token = SPUtil.getInstance().getData(App.getContext(), SPUtil.FILE_NAME, SPUtil.KEY_TOKEN);
            if(TextUtils.isEmpty(token)){
             return chain.proceed(request);
           }
            Request requestNew = request.newBuilder().addHeader("Content-Type","application/json;charset=UTF-8")
                    .addHeader("token",token)
                    .build();;

            return chain.proceed(requestNew);
        }
    }
    //xiangji
    public RequestBody getRequsetBody(List<File> files, HashMap<String,String> map){
//        if (map.size() < 1){
//            return null;
//        }
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        for (Map.Entry<String,String> entry:map.entrySet()){
            Log.i("xxx","key = "+entry.getKey()+"value = "+entry.getValue());
            builder.addFormDataPart(entry.getKey(),entry.getValue()+"");
        }


        builder.addFormDataPart("doorPrint",files.get(0).getName(),RequestBody.create(MediaType.parse("image/jepg"),files.get(0)));
        builder.addFormDataPart("licensePrint",files.get(1).getName(),RequestBody.create(MediaType.parse("image/jepg"),files.get(1)));

        return builder.build();
    }
}
