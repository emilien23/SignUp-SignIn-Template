package com.example.authorizationtemplate.di.module.network;

import android.content.Context;


import com.example.authorizationtemplate.data.network.auth.ConnectivityInterceptor;
import com.example.authorizationtemplate.di.scope.AppScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.TlsVersion;
import okhttp3.logging.HttpLoggingInterceptor;

@Module(includes = MockModule.class)
public class NetworkModule {

    static final String NETWORK_URL = "https://example.site.com/api/"; //PUT YOUR API ROUTE

    @AppScope
    @Provides
    public ConnectivityInterceptor provideConnectivityInterceptor(@Named("app_context") Context context) {
        return new ConnectivityInterceptor(context);
    }

    @AppScope
    @Provides
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @AppScope
    @Provides
    public ConnectionSpec provideConnectionSpec() {
        return new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256)
                .build();
    }
}
