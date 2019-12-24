package com.example.authorizationtemplate.di.module.network;


import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.ConnectivityInterceptor;
import com.example.authorizationtemplate.di.scope.AppScope;

import java.util.Collections;
import java.util.concurrent.Executors;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.authorizationtemplate.di.module.network.NetworkModule.NETWORK_URL;


@Module(includes = {NetworkModule.class})
public class CommonNetworkModule {

    @AppScope
    @Provides
    @Named("common")
    public OkHttpClient provideOkHttpClient(ConnectionSpec spec, MockingInterceptor mockingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(mockingInterceptor)
                .connectionSpecs(Collections.singletonList(spec))
                .build();
    }

    @AppScope
    @Provides
    @Named("common")
    public Retrofit provideRetrofit(@Named("common") OkHttpClient okHttpClient) {
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
    public NetworkService.CommonApi provideCommonApi(@Named("common") Retrofit retrofit) {
        return retrofit.create(NetworkService.CommonApi.class);
    }
}
