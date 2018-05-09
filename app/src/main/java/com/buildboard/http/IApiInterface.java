package com.buildboard.http;

import com.buildboard.modules.login.apimodels.GetAccessTokenRequest;
import com.buildboard.modules.login.apimodels.GetAccessTokenResponse;
import com.buildboard.modules.signup.apimodels.ContractorListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IApiInterface {

    @POST("basic-auth")
    Call<GetAccessTokenResponse> getAccessToken(@Body GetAccessTokenRequest getAccessTokenRequest);

    @GET("contractor-type")
    Call<ContractorListResponse> getContractorList(@Header("oauth") String oauth);
}