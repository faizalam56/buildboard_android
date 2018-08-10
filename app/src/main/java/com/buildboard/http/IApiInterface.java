package com.buildboard.http;

import com.buildboard.modules.home.modules.mailbox.inbox.models.InboxMessagesResponse;
import com.buildboard.modules.home.modules.mailbox.inbox.models.SendMessageRequest;
import com.buildboard.modules.home.modules.mailbox.inbox.models.SendMessageResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedResponse;
import com.buildboard.modules.home.modules.mailbox.models.MessagesResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ContractorRelatedResponse;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeResponse;
import com.buildboard.modules.home.modules.marketplace.contractors.models.NearByProjectsResponse;
import com.buildboard.modules.home.modules.marketplace.models.MarketPlaceContractorResponse;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceConsumerResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.LogoutResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.ProfileResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.addaddress.AddAddressRequest;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.addaddress.AddAddressResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.getaddress.GetAddressesResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.primaryaddress.PrimaryAddressResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.ReviewsResponse;
import com.buildboard.modules.home.modules.projects.models.ProjectAllTypeResponse;
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
import com.buildboard.modules.signup.contractor.businessdocuments.models.BusinessDocumentsRequest;
import com.buildboard.modules.signup.contractor.businessdocuments.models.BusinessDocumentsResponse;
import com.buildboard.modules.signup.contractor.businessinfo.models.BusinessInfoRequest;
import com.buildboard.modules.signup.contractor.businessinfo.models.BusinessInfoResponse;
import com.buildboard.modules.signup.contractor.previouswork.models.PreviousWorkRequest;
import com.buildboard.modules.signup.contractor.previouswork.models.SaveContractorImageRequest;
import com.buildboard.modules.signup.contractor.previouswork.models.SaveContractorImageResponse;
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
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("consumer")
    Call<CreateConsumerResponse> updateConsumer(@Header("oauth") String oauth, @Header("session") String sessionId,@Body CreateConsumerRequest createConsumerRequest);

    @POST("login")
    Call<LoginResponse> login(@Header("oauth") String oauth, @Body LoginRequest loginRequest);

    @GET("logout")
    Call<LogoutResponse> logout(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("marketplace/consumer")
    Call<MarketplaceConsumerResponse> getMarketplaceConsumer(@Header("oauth") String oauth,@Header("session") String sessionId);

    @GET("marketplace/contractor")
    Call<MarketPlaceContractorResponse> getMarketplaceContractor(@Header("oauth") String oauth, @Header("session") String sessionId);

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

    @POST("marketplace/all-project-types")
    Call<ProjectAllTypeResponse> getAllTypeOfProjectsList(@Header("oauth") String oauth, @Header("session") String sessionId);

    @POST("contractor/profile/prev-doc")
    Call<BusinessDocumentsResponse> storePrevWork(@Header("oauth") String oauth, @Body PreviousWorkRequest previousWorkRequest);

    @POST("contractor/profile/image")
    Call<SaveContractorImageResponse> saveContractorImage(@Header("oauth") String oauth, @Body SaveContractorImageRequest previousWorkRequest);

    @GET("project-type")
    Call<ProjectAllTypeResponse> getConsumerProjectDetails(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("projects/{project_id}?/")
    Call<NearByProjectsResponse> getNearByProjectsDetails(@Header("oauth") String oauth, @Path("project_id") String projectId,@Header("session") String sessionId);

    @GET("consumer/address")
    Call<GetAddressesResponse> getAddresses(@Header("oauth") String oauth, @Header("session") String sessionId);

    @POST("consumer/address")
    Call<AddAddressResponse> addAddress(@Header("oauth") String oauth, @Header("session") String sessionId, @Body AddAddressRequest addAddressRequest);

    @PUT("consumer/address")
    Call<PrimaryAddressResponse> makePrimaryAddress(@Header("oauth") String oauth, @Header("session") String sessionId, @Query("id") String id);

    @GET("reviews")
    Call<ReviewsResponse> getReviews(@Header("oauth") String oauth, @Header("session") String sessionId);

    @DELETE("consumer/address/{id}")
    Call<PrimaryAddressResponse> deleteAddress(@Header("oauth") String oauth, @Header("session") String sessionId, @Path("id") String id);

    @GET("messages")
    Call<MessagesResponse> getMessages(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("contractor/profile/business")
    Call<BusinessInfoResponse> getBusinessInfo(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("messages/{receiver_id}?/")
    Call<InboxMessagesResponse> getInboxMessages(@Header("oauth") String oauth, @Header("session") String sessionId, @Path("receiver_id") String receiverId);

    @GET("related-consumer")
    Call<ConsumerRelatedResponse> getRelatedConsumer(@Header("oauth") String oauth, @Header("session") String sessionId);

    @GET("related-contractor")
    Call<ContractorRelatedResponse> getRelatedContractor(@Header("oauth") String oauth, @Header("session") String sessionId);

    @POST("send-message")
    Call<SendMessageResponse> sendMessage(@Header("oauth") String oauth, @Header("session") String sessionId, @Body SendMessageRequest sendMessageRequest);

    @PUT("contractor/profile/business")
    Call<BusinessInfoResponse> updateBusinessInfo(@Header("oauth") String oauth, @Header("session") String sessionId, @Body BusinessInfoRequest businessInfoRequest);

    @PUT("contractor/profile/image")
    Call<SaveContractorImageResponse> updateContractorImage(@Header("oauth") String oauth, @Header("session") String sessionId, @Body SaveContractorImageRequest previousWorkRequest);
}