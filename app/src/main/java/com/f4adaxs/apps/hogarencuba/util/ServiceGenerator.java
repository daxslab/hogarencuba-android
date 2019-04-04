package com.f4adaxs.apps.hogarencuba.util;

import com.f4adaxs.apps.hogarencuba.config.AppConfig;
import com.f4adaxs.apps.hogarencuba.util.api.LiveDataCallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static  Retrofit retrofit;
    public static <S> S create(Class<S> serviceClass) {
        if(retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.REST_SERVER_API_URL)
                    .client(UnsafeOkHttpClient.getUnsafeOkHttpClientBuilder().build())
                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(serviceClass);
    }
}
