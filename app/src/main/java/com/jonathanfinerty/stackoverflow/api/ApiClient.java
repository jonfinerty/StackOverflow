package com.jonathanfinerty.stackoverflow.api;

import com.google.gson.GsonBuilder;
import com.jonathanfinerty.stackoverflow.api.model.UsersResponse;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private final StackOverflowService stackOverflowService;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.stackexchange.com/2.2/")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .client(new OkHttpClient.Builder().build())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        stackOverflowService = retrofit.create(StackOverflowService.class);
    }

    public rx.Observable<UsersResponse> getUsers() {
        return stackOverflowService.getUsers();
    }

}
