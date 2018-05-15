package com.buildboard.http;

import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeResponse;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceConsumerResponse;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenResponse;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.login.LoginResponse;
import com.buildboard.modules.signup.models.contractortype.ContractorListResponse;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerResponse;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorRequest;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IApiInterface {

    @POST("basic-auth")
    Call<GetAccessTokenResponse> getAccessToken(@Body GetAccessTokenRequest getAccessTokenRequest);

    @GET("contractor-type")
    Call<ContractorListResponse> getContractorList(@Header("oauth") String oauth);

    @POST("contractor")
    Call<CreateContractorResponse> createContractor(@Header("oauth") String oauth, @Body CreateContractorRequest createContractorRequest);

    @POST("consumer")
    Call<CreateConsumerResponse> createConsumer(@Header("oauth") String oauth, @Body CreateConsumerRequest createConsumerRequest);

    @POST("login")
    Call<LoginResponse> login(@Header("oauth") String oauth, @Body LoginRequest loginRequest);

    @GET("marketplace/consumer")
    Call<MarketplaceConsumerResponse> getMarketplaceConsumer(@Header("oauth") String oauth);

    @GET("marketplace/contractor-by-projectType/{type_of_contractor_id}?")
    Call<ContractorByProjectTypeResponse> getContractorByProjectType(@Header("oauth") String oauth, @Path("type_of_contractor_id") String contractorTypeId,
                                                                     @Field("page") int page, @Field("radius") float radius, @Field("per_page") int perPage);

}