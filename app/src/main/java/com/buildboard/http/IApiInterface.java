package com.buildboard.http;

import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeResponse;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceConsumerResponse;
import com.buildboard.modules.home.modules.profile.models.LogoutResponse;
import com.buildboard.modules.home.modules.profile.models.ProfileResponse;
import com.buildboard.modules.home.modules.projects.models.ProjectsResponse;
import com.buildboard.modules.login.forgotpassword.models.ForgotPasswordRequest;
import com.buildboard.modules.login.forgotpassword.models.ForgotPasswordResponse;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenResponse;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.login.LoginResponse;
import com.buildboard.modules.login.models.sociallogin.SocialLoginResponse;
import com.buildboard.modules.login.resetpassword.model.ResetPasswordResponse;
import com.buildboard.modules.login.models.sociallogin.SocialLoginRequest;
import com.buildboard.modules.signup.contractor.models.businessdocument.BusinessDocumentsRequest;
import com.buildboard.modules.signup.contractor.models.businessdocument.BusinessDocumentsResponse;
import com.buildboard.modules.signup.contractor.models.businessinfo.BusinessInfoRequest;
import com.buildboard.modules.signup.contractor.models.businessinfo.BusinessInfoResponse;
import com.buildboard.modules.signup.imageupload.models.ImageUploadResponse;
import com.buildboard.modules.signup.models.activateuser.ActivateUserResponse;
import com.buildboard.modules.signup.models.contractortype.ContractorListResponse;
import com.buildboard.modules.signup.models.contractortype.WorkTypeRequest;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApiInterface {

    @POST("basic-auth")
    Call<GetAccessTokenResponse> getAccessToken(@Body GetAccessTokenRequest getAccessTokenRequest);

    @GET("project-type")
    Call<ContractorListResponse> getContractorList(@Header("oauth") String oauth);

    @POST("contractor")
    Call<BusinessInfoResponse> saveBusinessInfo(@Header("oauth") String oauth, @Body BusinessInfoRequest businessInfoRequest);

    @POST("consumer")
    Call<CreateConsumerResponse> createConsumer(@Header("oauth") String oauth, @Body CreateConsumerRequest createConsumerRequest);

    @POST("login")
    Call<LoginResponse> login(@Header("oauth") String oauth, @Body LoginRequest loginRequest);

    @GET("logout")
    Call<LogoutResponse> logout(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("marketplace/consumer")
    Call<MarketplaceConsumerResponse> getMarketplaceConsumer(@Header("oauth") String oauth);

    @GET("marketplace/contractor-by-projectType/{type_of_contractor_id}?/")
    Call<ContractorByProjectTypeResponse> getContractorByProjectType(@Header("oauth") String oauth, @Path("type_of_contractor_id") String contractorTypeId,
                                                                     @Query("page") int page, @Query("radius") float radius, @Query("per_page") int perPage);

    @POST("forgot-password")
    Call<ForgotPasswordResponse> forgotPassword(@Header("oauth") String oauth, @Body ForgotPasswordRequest shd);

    @Multipart
    @POST("media/upload")
    Call<ImageUploadResponse> uploadImage(@Header("oauth") String oauth, @Part("type") RequestBody userType, @Part("file_type") RequestBody type, @Part MultipartBody.Part file);

    @GET("resetpassword/{passkey}/{password}")
    Call<ResetPasswordResponse> resetPassword(@Header("oauth") String oauth, @Path("passkey") String apiKey, @Path("password") String newPassword);

    @POST("user/activate/{passkey}")
    Call<ActivateUserResponse> activateUser(@Header("oauth") String oauth, @Path("passkey") String newPassword);

    @POST("login")
    Call<SocialLoginResponse> getSocialLogin(@Header("oauth") String oauth, @Body SocialLoginRequest socialLoginRequest);

    @POST("contractor/profile/work-type")
    Call<ContractorListResponse> saveWorkType(@Header("oauth") String oauth, @Body WorkTypeRequest workTypeRequest);

    @GET("user/profile")
    Call<ProfileResponse> getProfile(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("projects")
    Call<ProjectsResponse> getProjectsList(@Header("oauth") String oauth, @Header("session") String sessionId, @Query("status") String status, @Query("page") int page);

    @POST("contractor/profile/document")
    Call<BusinessDocumentsResponse> storeContractorDocuments(@Header("oauth") String oauth, @Body BusinessDocumentsRequest businessDocumentsRequest);
}