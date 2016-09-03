package com.jonathanfinerty.stackoverflow.api;

import com.jonathanfinerty.stackoverflow.api.model.UsersResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface StackOverflowService {

    @GET("users?pagesize=20&order=desc&sort=reputation&site=stackoverflow")
    Observable<UsersResponse> getUsers();

}
