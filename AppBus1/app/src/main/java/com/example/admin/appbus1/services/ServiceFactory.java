package com.example.admin.appbus1.services;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by giaqu on 1/1/2017.
 */

public class ServiceFactory {

    private Retrofit retrofit;

    public ServiceFactory(String baseUrl){
        retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public <ServiceClass>ServiceClass createService(Class<ServiceClass> serviceClass){
        return retrofit.create(serviceClass);
    }
}
