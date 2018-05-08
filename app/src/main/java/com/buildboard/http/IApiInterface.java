package com.buildboard.http;

import com.buildboard.modules.login.apimodels.GetAccessTokenRequest;
import com.buildboard.modules.login.apimodels.GetAccessTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IApiInterface {

    @POST("basic-auth")
    Call<GetAccessTokenResponse> getAccessToken(@Body GetAccessTokenRequest getAccessTokenRequest);
}