package com.example.authorizationtemplate.di.module.network;


import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.data.network.auth.MainAuthenticator;
import com.example.authorizationtemplate.di.scope.AppScope;

import java.util.Collections;
import java.util.concurrent.Executors;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.authorizationtemplate.di.module.network.NetworkModule.NETWORK_URL;


@Module(includes = NetworkModule.class)
public class AuthNetworkModule {

    @AppScope
    @Provides
    public Authenticator provideAuthenticator(AuthHolder authHolder) {
        return new MainAuthenticator(authHolder);
    }

    @AppScope
    @Provides
    @Named("auth")
    public OkHttpClient provideOkHttpClient(MockingInterceptor mockingInterceptor, Authenticator authenticator, ConnectionSpec spec) {
        return new OkHttpClient.Builder()
                .addInterceptor(mockingInterceptor)
                .authenticator(authenticator)
                .connectionSpecs(Collections.singletonList(spec))
                .build();
    }

    @AppScope
    @Provides
    @Named("auth")
    public Retrofit provideRetrofit(@Named("auth") OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(NETWORK_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newFixedThreadPool(3))
                .build();
    }

    @AppScope
    @Provides
    public NetworkService.AuthApi provideAuthApi(@Named("auth") Retrofit retrofit) {
        return retrofit.create(NetworkService.AuthApi.class);
    }

}
