package com.buildboard.http;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.buildboard.BuildConfig;
import com.buildboard.constants.AppConfiguration;
import com.buildboard.constants.AppConstant;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenRequest;
import com.buildboard.modules.login.models.getAccessToken.GetAccessTokenResponse;
import com.buildboard.modules.login.models.login.LoginRequest;
import com.buildboard.modules.login.models.login.LoginResponse;
import com.buildboard.modules.signup.models.contractortype.ContractorListResponse;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerRequest;
import com.buildboard.modules.signup.models.createconsumer.CreateConsumerResponse;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorRequest;
import com.buildboard.modules.signup.models.createcontractor.CreateContractorResponse;
import com.buildboard.preferences.AppPreference;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient implements AppConstant, AppConfiguration {

    private static Retrofit retrofit = null;
    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    private static class ApiClientSingleton {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return ApiClientSingleton.INSTANCE;
    }

    public IApiInterface getClient() {
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
//            .addInterceptor(interceptor)
            .build();


    public interface DataManagerListener {
        void onSuccess(Object response);

        void onError(Object error);
    }

    public void getAccessToken(GetAccessTokenRequest getAccessTokenRequest, final DataManagerListener dataManagerListener) {
        Call<GetAccessTokenResponse> call = getClient().getAccessToken(getAccessTokenRequest);
        call.enqueue(new Callback<GetAccessTokenResponse>() {
            @Override
            public void onResponse(Call<GetAccessTokenResponse> call, Response<GetAccessTokenResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }
                if (response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                else
                    dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(Call<GetAccessTokenResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void getContractorList(Activity activity, final DataManagerListener dataManagerListener) {
        Call<ContractorListResponse> call = getClient().getContractorList(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN));
        call.enqueue(new Callback<ContractorListResponse>() {
            @Override
            public void onResponse(Call<ContractorListResponse> call, Response<ContractorListResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }
                if (response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(Call<ContractorListResponse> call, Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void createContractor(Activity activity, CreateContractorRequest createContractorRequest, final DataManagerListener dataManagerListener) {
        Call<CreateContractorResponse> call = getClient().createContractor(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), createContractorRequest);
        call.enqueue(new Callback<CreateContractorResponse>() {
            @Override
            public void onResponse(Call<CreateContractorResponse> call, Response<CreateContractorResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }
                if (response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<CreateContractorResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void createConsumer(Activity activity, CreateConsumerRequest createConsumerRequest, final DataManagerListener dataManagerListener) {
        Call<CreateConsumerResponse> call = getClient().createConsumer(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), createConsumerRequest);
        call.enqueue(new Callback<CreateConsumerResponse>() {
            @Override
            public void onResponse(@NonNull Call<CreateConsumerResponse> call, @NonNull Response<CreateConsumerResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }
                if (response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<CreateConsumerResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }

    public void login(Activity activity, LoginRequest loginRequest, final DataManagerListener dataManagerListener) {
        Call<LoginResponse> call = getClient().login(AppPreference.getAppPreference(activity).getString(ACCESS_TOKEN), loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if (!response.isSuccessful()) {
                    dataManagerListener.onError(response.errorBody());
                    return;
                }
                if (response.body() != null && response.body().getStatus() != null && response.body().getStatus().equals(SUCCESS))
                    dataManagerListener.onSuccess(response.body());
                else dataManagerListener.onError(response.errorBody());
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                dataManagerListener.onError(t);
            }
        });
    }
}
