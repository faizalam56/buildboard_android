package com.buildboard.http;

import com.buildboard.modules.login.apimodels.GetAccessTokenRequest;
import com.buildboard.modules.login.apimodels.GetAccessTokenResponse;
import com.buildboard.modules.signup.apimodels.contractortype.ContractorListResponse;
import com.buildboard.modules.signup.apimodels.createcontractor.CreateContractorRequest;
import com.buildboard.modules.signup.apimodels.createcontractor.CreateContractorResponse;

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

    @POST("contractor")
    Call<CreateContractorResponse> createContractor(@Header("oauth") String oauth, @Body CreateContractorRequest createContractorRequest);
}