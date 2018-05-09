package com.buildboard.http;

import android.app.Activity;

import com.buildboard.BuildConfig;
import com.buildboard.constants.AppConfiguration;
import com.buildboard.constants.AppConstant;
import com.buildboard.modules.login.apimodels.GetAccessTokenRequest;
import com.buildboard.modules.login.apimodels.GetAccessTokenResponse;
import com.buildboard.modules.signup.apimodels.ContractorListResponse;
import com.buildboard.preferences.AppPreference;

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

    public static IApiInterface getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(IApiInterface.class);
    }

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
                else dataManagerListener.onError(response.errorBody());
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
}
