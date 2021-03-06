package com.buildboard.http;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.buildboard.BuildConfig;
import com.buildboard.constants.AppConfiguration;
import com.buildboard.constants.AppConstant;
import com.buildboard.modules.home.modules.mailbox.inbox.models.DeleteMessageRequest;
import com.buildboard.modules.home.modules.mailbox.inbox.models.InboxMessagesResponse;
import com.buildboard.modules.home.modules.mailbox.inbox.models.SendMessageRequest;
import com.buildboard.modules.home.modules.mailbox.inbox.models.SendMessageResponse;
import com.buildboard.modules.home.modules.mailbox.models.MessagesResponse;
import com.buildboard.modules.home.modules.mailbox.inbox.models.TrashMessageResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ConsumerRelatedResponse;
import com.buildboard.modules.home.modules.mailbox.modules.models.ContractorRelatedResponse;
import com.buildboard.modules.home.modules.marketplace.contractor_projecttype.models.ContractorByProjectTypeResponse;
import com.buildboard.modules.home.modules.marketplace.contractors.models.ProjectsDetailResponse;
import com.buildboard.modules.home.modules.marketplace.models.MarketPlaceContractorResponse;
import com.buildboard.modules.home.modules.marketplace.models.MarketplaceConsumerResponse;
import com.buildboard.modules.home.modules.marketplace.models.contractorprofile.ContractorProfileResponse;
import com.buildboard.modules.home.modules.marketplace.models.projectbyprojecttype.ProjectByProjectTypeResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.ChangePasswordRequest;
import com.buildboard.modules.home.modules.profile.consumer.models.ChangePasswordResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.LogoutResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.ProfileResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.addaddress.AddAddressRequest;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.addaddress.AddAddressResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.getaddress.GetAddressesResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.addresses.primaryaddress.PrimaryAddressResponse;
import com.buildboard.modules.home.modules.profile.consumer.models.reviews.ReviewsResponse;
import com.buildboard.modules.home.modules.profile.contractor.models.GetBusinessDocumentsResponse;
import com.buildboard.modules.home.modules.profile.contractor.models.GetPreviousWorkResponse;
import com.buildboard.modules.home.modules.projects.models.ProjectAllTypeResponse;
import com.buildboard.modules.home.modules.projects.models.ProjectFormResponse;
import com.buildboard.modules.home.modules.projects.models.ProjectsResponse;
import com.buildboard.modules.login.forgotpassword.models.ForgotPasswordRequest;
import com.buildboard.modules.login.forgotpassword.models.ForgotPasswordResponse;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenResponse;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.login.LoginResponse;
import com.buildboard.modules.login.models.sociallogin.SocialLoginRequest;
import com.buildboard.modules.login.models.sociallogin.SocialLoginResponse;
import com.buildboard.modules.login.resetpassword.model.ResetPasswordResponse;
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
import com.buildboard.preferences.AppPreference;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataManager implements AppConstant, AppConfiguration {

    private static Retrofit retrofit = null;
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    private static class ApiClientSingleton {
        private static final DataManager INSTANCE = new DataManager();
    }

    public static DataManager getInstance() {
        return ApiClientSingleton.INSTANCE;
    }

    public IApiInterface getDataManager() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(IApiInterface.class);
    }

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(interceptor)
            .build();


    public interface DataManagerListener {
        void onSuccess(Object response);

        void onError(Object error);
    }

    public void getAccessToken(GetAccessTokenRequest getAccessTokenRequest, final DataManagerListener dataManagerListener) {
        Call<GetAccessTokenResponse> call = getDataManager().getAccessToken(getAccessTokenRequest);
        call.enqueue(new Callback<GetAccessTokenResponse>() {
            @Override
            public void onResponse(Call<GetAccessTokenResponse> call, Response<GetAccessTokenResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) &&
                        response.body().getTokenDataObject().size() > 0)
                    dataManagerListener.onSuccess(response.body().getTokenDataObject().get(0));
                else
                    dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(Call<GetAccessTokenResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getContractorList(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ContractorListResponse> call = getDataManager().getContractorList(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN));
        call.enqueue(new Callback<ContractorListResponse>() {
            @Override
            public void onResponse(Call<ContractorListResponse> call, Response<ContractorListResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(Call<ContractorListResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void saveBusinessInfo(Activity activity, BusinessInfoRequest businessInfoRequest, final DataManagerListener dataManagerListener) {
        Call<BusinessInfoResponse> call = getDataManager().saveBusinessInfo(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), businessInfoRequest);
        call.enqueue(new Callback<BusinessInfoResponse>() {
            @Override
            public void onResponse(Call<BusinessInfoResponse> call, Response<BusinessInfoResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) &&
                        response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessInfoResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updateConsumer(Activity activity, CreateConsumerRequest createConsumerRequest, final DataManagerListener dataManagerListener) {
        Call<CreateConsumerResponse> call = getDataManager().updateConsumer(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),AppPreference.getAppPreference(activity).getString(SESSION_ID), createConsumerRequest);
        call.enqueue(new Callback<CreateConsumerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateConsumerResponse> call, @NonNull Response<CreateConsumerResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<CreateConsumerResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
    public void createConsumer(Activity activity, CreateConsumerRequest createConsumerRequest, final DataManagerListener dataManagerListener) {
        Call<CreateConsumerResponse> call = getDataManager().createConsumer(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), createConsumerRequest);
        call.enqueue(new Callback<CreateConsumerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateConsumerResponse> call, @NonNull Response<CreateConsumerResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<CreateConsumerResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void login(Activity activity, LoginRequest loginRequest, final DataManagerListener dataManagerListener) {
        Call<LoginResponse> call = getDataManager().login(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getMarketplaceConsumer(Activity activity, final DataManagerListener dataManagerListener) {
        Call<MarketplaceConsumerResponse> call = getDataManager().getMarketplaceConsumer(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<MarketplaceConsumerResponse>() {
            @Override
            public void onResponse(@NonNull Call<MarketplaceConsumerResponse> call, @NonNull Response<MarketplaceConsumerResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<MarketplaceConsumerResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getMarketplaceContractor(Activity activity, final DataManagerListener dataManagerListener) {
        Call<MarketPlaceContractorResponse> call = getDataManager().getMarketplaceContractor(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<MarketPlaceContractorResponse>() {
            @Override
            public void onResponse(@NonNull Call<MarketPlaceContractorResponse> call, @NonNull Response<MarketPlaceContractorResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<MarketPlaceContractorResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getContractorByProjectType(Activity activity, String contractorTypeId, String role, final DataManagerListener dataManagerListener) {
        Call<ContractorByProjectTypeResponse> call = getDataManager().getContractorsByProjectType(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), role, contractorTypeId);
        call.enqueue(new Callback<ContractorByProjectTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<ContractorByProjectTypeResponse> call, @NonNull Response<ContractorByProjectTypeResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ContractorByProjectTypeResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getNearByProjects(Activity activity, String projectId, final DataManagerListener dataManagerListener) {
        Call<ProjectsDetailResponse> call = getDataManager().getNearByProjectsDetails(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                projectId, AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ProjectsDetailResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProjectsDetailResponse> call, @NonNull Response<ProjectsDetailResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ProjectsDetailResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
    public void forgotPassword(Activity activity, ForgotPasswordRequest forgotPasswordRequest, final DataManagerListener dataManagerListener) {
        Call<ForgotPasswordResponse> call = getDataManager().forgotPassword(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                forgotPasswordRequest);
        call.enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponse> call, @NonNull Response<ForgotPasswordResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ForgotPasswordResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void uploadImage(Activity activity, RequestBody file, RequestBody fileType, MultipartBody.Part image, final DataManagerListener dataManagerListener) {
        Call<ImageUploadResponse> call = getDataManager().uploadImage(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                file, fileType, image);
        call.enqueue(new Callback<ImageUploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<ImageUploadResponse> call, @NonNull Response<ImageUploadResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ImageUploadResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void logout(Activity activity, final DataManagerListener dataManagerListener) {
        Call<LogoutResponse> call = getDataManager().logout(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<LogoutResponse>() {
            @Override
            public void onResponse(@NonNull Call<LogoutResponse> call, @NonNull Response<LogoutResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<LogoutResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void resetPassword(Activity activity, String apiKey, String newPassword, final DataManagerListener dataManagerListener) {
        Call<ResetPasswordResponse> call = getDataManager().resetPassword(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), apiKey, newPassword);
        call.enqueue(new Callback<ResetPasswordResponse>() {
            @Override
            public void onResponse(Call<ResetPasswordResponse> call, Response<ResetPasswordResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ResetPasswordResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void activateUser(Activity activity, String apiKey, final DataManagerListener dataManagerListener) {
        Call<ActivateUserResponse> call = getDataManager().activateUser(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), apiKey);
        call.enqueue(new Callback<ActivateUserResponse>() {
            @Override
            public void onResponse(Call<ActivateUserResponse> call, Response<ActivateUserResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ActivateUserResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getSocialLogin(Activity activity, SocialLoginRequest socialLoginRequest, final DataManagerListener dataManagerListener) {
        Call<SocialLoginResponse> call = getDataManager().getSocialLogin(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), socialLoginRequest);
        call.enqueue(new Callback<SocialLoginResponse>() {
            @Override
            public void onResponse(Call<SocialLoginResponse> call, Response<SocialLoginResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<SocialLoginResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void saveWorkType(Activity activity, WorkTypeRequest workTypeRequest, final DataManagerListener dataManagerListener) {
        Call<ContractorListResponse> call = getDataManager().saveWorkType(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), workTypeRequest);
        call.enqueue(new Callback<ContractorListResponse>() {
            @Override
            public void onResponse(Call<ContractorListResponse> call, Response<ContractorListResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ContractorListResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getProfile(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ProfileResponse> call = getDataManager().getProfile(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null
                        && response.body().getStatus().equals(SUCCESS)
                        && response.body().getDatas() != null
                        && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ProfileResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getProjectsList(Activity activity, String status, int page, final DataManagerListener dataManagerListener) {
        Call<ProjectsResponse> call = getDataManager().getProjectsList(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), status, page);
        call.enqueue(new Callback<ProjectsResponse>() {
            @Override
            public void onResponse(Call<ProjectsResponse> call, Response<ProjectsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas() != null && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ProjectsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void storeContractorDocuments(Activity activity, BusinessDocumentsRequest businessDocumentsRequest, final DataManagerListener dataManagerListener) {
        Call<BusinessDocumentsResponse> call = getDataManager().storeContractorDocuments(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), businessDocumentsRequest);
        call.enqueue(new Callback<BusinessDocumentsResponse>() {
            @Override
            public void onResponse(Call<BusinessDocumentsResponse> call, Response<BusinessDocumentsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessDocumentsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getAllTypeOfProjectList(Activity activity,final DataManagerListener dataManagerListener){
        Call<ProjectAllTypeResponse> call = getDataManager().getAllTypeOfProjectsList(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ProjectAllTypeResponse>() {
            @Override
            public void onResponse(Call<ProjectAllTypeResponse> call, Response<ProjectAllTypeResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }
                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(Call<ProjectAllTypeResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
    public void storePrevWork(Activity activity, PreviousWorkRequest previousWorkRequest, final DataManagerListener dataManagerListener) {
        Call<BusinessDocumentsResponse> call = getDataManager().storePrevWork(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), previousWorkRequest);
        call.enqueue(new Callback<BusinessDocumentsResponse>() {
            @Override
            public void onResponse(Call<BusinessDocumentsResponse> call, Response<BusinessDocumentsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessDocumentsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void saveContractorImage(Activity activity, SaveContractorImageRequest saveContractorImageRequest, final DataManagerListener dataManagerListener) {
        Call<SaveContractorImageResponse> call = getDataManager().saveContractorImage(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), saveContractorImageRequest);
        call.enqueue(new Callback<SaveContractorImageResponse>() {
            @Override
            public void onResponse(Call<SaveContractorImageResponse> call, Response<SaveContractorImageResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas() !=null && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas());
                else dataManagerListener.onError(response.body().getErrorObject());
            }

            @Override
            public void onFailure(@NonNull Call<SaveContractorImageResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getProjectDetails(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ProjectAllTypeResponse> call = getDataManager().getConsumerProjectDetails(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ProjectAllTypeResponse>() {
            @Override
            public void onResponse(Call<ProjectAllTypeResponse> call, Response<ProjectAllTypeResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ProjectAllTypeResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getAddresses(Activity activity, final DataManagerListener dataManagerListener) {
        Call<GetAddressesResponse> call = getDataManager().getAddresses(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<GetAddressesResponse>() {
            @Override
            public void onResponse(Call<GetAddressesResponse> call, Response<GetAddressesResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0).getData());
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<GetAddressesResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void addAddress(Activity activity, AddAddressRequest addAddressRequest, final DataManagerListener dataManagerListener) {
        Call<AddAddressResponse> call = getDataManager().addAddress(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), addAddressRequest);
        call.enqueue(new Callback<AddAddressResponse>() {
            @Override
            public void onResponse(Call<AddAddressResponse> call, Response<AddAddressResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<AddAddressResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void makePrimaryAddress(Activity activity, String id, final DataManagerListener dataManagerListener) {
        Call<PrimaryAddressResponse> call = getDataManager().makePrimaryAddress(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), id);
        call.enqueue(new Callback<PrimaryAddressResponse>() {
            @Override
            public void onResponse(Call<PrimaryAddressResponse> call, Response<PrimaryAddressResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<PrimaryAddressResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getReviews(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ReviewsResponse> call = getDataManager().getReviews(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ReviewsResponse>() {
            @Override
            public void onResponse(Call<ReviewsResponse> call, Response<ReviewsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<ReviewsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void deleteAddress(Activity activity, String id, final DataManagerListener dataManagerListener) {
        Call<PrimaryAddressResponse> call = getDataManager().deleteAddress(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), id);
        call.enqueue(new Callback<PrimaryAddressResponse>() {
            @Override
            public void onResponse(Call<PrimaryAddressResponse> call, Response<PrimaryAddressResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<PrimaryAddressResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getMessages(Activity activity, final DataManagerListener dataManagerListener) {
        Call<MessagesResponse> call = getDataManager().getMessages(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<MessagesResponse>() {
            @Override
            public void onResponse(Call<MessagesResponse> call, Response<MessagesResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<MessagesResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getInboxMessages(Activity activity, String receiverId, int pageNumber, final DataManagerListener dataManagerListener) {
        Call<InboxMessagesResponse> call = getDataManager().getInboxMessages(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), receiverId, pageNumber);
        call.enqueue(new Callback<InboxMessagesResponse>() {
            @Override
            public void onResponse(Call<InboxMessagesResponse> call, Response<InboxMessagesResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<InboxMessagesResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getRelatedConsumer(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ConsumerRelatedResponse> call = getDataManager().getRelatedConsumer(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ConsumerRelatedResponse>() {
            @Override
            public void onResponse(Call<ConsumerRelatedResponse> call, Response<ConsumerRelatedResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ConsumerRelatedResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getRelatedContractor(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ContractorRelatedResponse> call = getDataManager().getRelatedContractor(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ContractorRelatedResponse>() {
            @Override
            public void onResponse(Call<ContractorRelatedResponse> call, Response<ContractorRelatedResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ContractorRelatedResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getBusinessInfo(Activity activity, final DataManagerListener dataManagerListener) {
        Call<BusinessInfoResponse> call = getDataManager().getBusinessInfo(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<BusinessInfoResponse>() {
            @Override
            public void onResponse(Call<BusinessInfoResponse> call, Response<BusinessInfoResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) &&
                        response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessInfoResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void sendMessage(Activity activity, SendMessageRequest sendMessageRequest, final DataManagerListener dataManagerListener) {
        Call<SendMessageResponse> call = getDataManager().sendMessage(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), sendMessageRequest);
        call.enqueue(new Callback<SendMessageResponse>() {
            @Override
            public void onResponse(Call<SendMessageResponse> call, Response<SendMessageResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<SendMessageResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void changePasswordCall(Activity activity, ChangePasswordRequest changePasswordRequest, final DataManagerListener dataManagerListener) {
        Call<ChangePasswordResponse> call = getDataManager().changePassword(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID),
                changePasswordRequest);

        call.enqueue(new Callback<ChangePasswordResponse>() {
            @Override
            public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData() != null && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
    public void getContractorProfile(Activity activity, String id, final DataManagerListener dataManagerListener) {
        Call<ContractorProfileResponse> call = getDataManager().getContractorProfile(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), id);
        call.enqueue(new Callback<ContractorProfileResponse>() {
            @Override
            public void onResponse(Call<ContractorProfileResponse> call, Response<ContractorProfileResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS)
                        && response.body().getData().get(0).getContractorInfo() != null)
                    dataManagerListener.onSuccess(response.body().getData().get(0).getContractorInfo());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ContractorProfileResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updateBusinessInfo(Activity activity, BusinessInfoRequest businessInfoRequest, final DataManagerListener dataManagerListener) {
        Call<BusinessInfoResponse> call = getDataManager().updateBusinessInfo(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), businessInfoRequest);
        call.enqueue(new Callback<BusinessInfoResponse>() {
            @Override
            public void onResponse(Call<BusinessInfoResponse> call, Response<BusinessInfoResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) &&
                        response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessInfoResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updateContractorImage(Activity activity, SaveContractorImageRequest saveContractorImageRequest, final DataManagerListener dataManagerListener) {
        Call<SaveContractorImageResponse> call = getDataManager().updateContractorImage(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), saveContractorImageRequest);
        call.enqueue(new Callback<SaveContractorImageResponse>() {
            @Override
            public void onResponse(Call<SaveContractorImageResponse> call, Response<SaveContractorImageResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas() !=null && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body().getDatas());
                else dataManagerListener.onError(response.body().getErrorObject());
            }

            @Override
            public void onFailure(@NonNull Call<SaveContractorImageResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getTrash(Activity activity, final DataManagerListener dataManagerListener) {
        Call<MessagesResponse> call = getDataManager().getTrash(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<MessagesResponse>() {
            @Override
            public void onResponse(Call<MessagesResponse> call, Response<MessagesResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<MessagesResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void setTrashMessage(Activity activity, DeleteMessageRequest deleteMessageRequest, final DataManagerListener dataManagerListener) {
        Call<TrashMessageResponse> call = getDataManager().setTrashMessage(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), deleteMessageRequest);
        call.enqueue(new Callback<TrashMessageResponse>() {
            @Override
            public void onResponse(Call<TrashMessageResponse> call, Response<TrashMessageResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<TrashMessageResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void setDeleteMessage(Activity activity, DeleteMessageRequest deleteMessageRequest, final DataManagerListener dataManagerListener) {
        Call<TrashMessageResponse> call = getDataManager().setDeleteMessage(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), deleteMessageRequest);
        call.enqueue(new Callback<TrashMessageResponse>() {
            @Override
            public void onResponse(Call<TrashMessageResponse> call, Response<TrashMessageResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError().getMessage());
            }

            @Override
            public void onFailure(@NonNull Call<TrashMessageResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getSelectedProjectById(Activity activity,String selectedProjectId, final DataManagerListener dataManagerListener) {
        Call<ProjectFormResponse> call = getDataManager().getConsumerSelectedProjectById(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),selectedProjectId);
        call.enqueue(new Callback<ProjectFormResponse>() {
            @Override
            public void onResponse(Call<ProjectFormResponse> call, Response<ProjectFormResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ProjectFormResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getContractorWorkType(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ContractorListResponse> call = getDataManager().getContractorWorkType(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<ContractorListResponse>() {
            @Override
            public void onResponse(Call<ContractorListResponse> call, Response<ContractorListResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ContractorListResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updateContractorWorkType(Activity activity, WorkTypeRequest workTypeRequest, final DataManagerListener dataManagerListener) {
        Call<ContractorListResponse> call = getDataManager().updateContractorWorkType(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), workTypeRequest);
        call.enqueue(new Callback<ContractorListResponse>() {
            @Override
            public void onResponse(Call<ContractorListResponse> call, Response<ContractorListResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getDatas().size() > 0)
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ContractorListResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getContractorDocuments(Activity activity, final DataManagerListener dataManagerListener) {
        Call<GetBusinessDocumentsResponse> call = getDataManager().getContractorDocuments(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<GetBusinessDocumentsResponse>() {
            @Override
            public void onResponse(Call<GetBusinessDocumentsResponse> call, Response<GetBusinessDocumentsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getBusinessDocuments() != null)
                    dataManagerListener.onSuccess(response.body().getBusinessDocuments());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<GetBusinessDocumentsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updateContractorDocuments(Activity activity, BusinessDocumentsRequest businessDocumentsRequest, final DataManagerListener dataManagerListener) {
        Call<BusinessDocumentsResponse> call = getDataManager().updateContractorDocuments(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), businessDocumentsRequest);
        call.enqueue(new Callback<BusinessDocumentsResponse>() {
            @Override
            public void onResponse(Call<BusinessDocumentsResponse> call, Response<BusinessDocumentsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessDocumentsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getPrevWork(Activity activity, final DataManagerListener dataManagerListener) {
        Call<GetPreviousWorkResponse> call = getDataManager().getPrevWork(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID));
        call.enqueue(new Callback<GetPreviousWorkResponse>() {
            @Override
            public void onResponse(Call<GetPreviousWorkResponse> call, Response<GetPreviousWorkResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getPreviousWorks().size() > 0)
                    dataManagerListener.onSuccess(response.body().getPreviousWorks());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<GetPreviousWorkResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void updatePrevWork(Activity activity, PreviousWorkRequest previousWorkRequest, final DataManagerListener dataManagerListener) {
        Call<BusinessDocumentsResponse> call = getDataManager().updatePrevWork(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), previousWorkRequest);
        call.enqueue(new Callback<BusinessDocumentsResponse>() {
            @Override
            public void onResponse(Call<BusinessDocumentsResponse> call, Response<BusinessDocumentsResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData());
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<BusinessDocumentsResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getProjectByProjectType(Activity activity, String projectTypeId, final DataManagerListener dataManagerListener) {
        Call<ProjectByProjectTypeResponse> call = getDataManager().getProjectByProjectType(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN),
                AppPreference.getAppPreference(activity).getString(SESSION_ID), projectTypeId);
        call.enqueue(new Callback<ProjectByProjectTypeResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProjectByProjectTypeResponse> call, @NonNull Response<ProjectByProjectTypeResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }

                if (response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS) && response.body().getData().size() > 0)
                    dataManagerListener.onSuccess(response.body().getData().get(0));
                else dataManagerListener.onError(response.body().getError());
            }

            @Override
            public void onFailure(@NonNull Call<ProjectByProjectTypeResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
}