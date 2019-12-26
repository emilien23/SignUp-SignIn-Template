package com.example.authorizationtemplate.test;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.authorizationtemplate.data.Settings;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.di.module.network.AuthModule;
import com.example.authorizationtemplate.di.module.network.CommonNetworkModule;
import com.example.authorizationtemplate.di.module.network.MockingInterceptor;
import com.example.authorizationtemplate.di.module.network.NetworkModule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public final class AuthRule implements TestRule {

    private CommonNetworkModule commonNetworkModule = new CommonNetworkModule();
    private NetworkModule networkModule = new NetworkModule();
    private AuthModule authModule = new AuthModule();

    public NetworkService.CommonApi api;
    public AuthHolder authHolder;

    @Override public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override public void evaluate() throws Throwable {
                Context context = InstrumentationRegistry
                        .getInstrumentation()
                        .getTargetContext();
                MockingInterceptor mockingInterceptor
                        = new MockingInterceptor(context);

                OkHttpClient okHttpClient = commonNetworkModule
                        .provideOkHttpClient(networkModule.provideConnectionSpec(), mockingInterceptor);

                Retrofit retrofit = commonNetworkModule
                        .provideRetrofit(okHttpClient);

                api = commonNetworkModule.provideCommonApi(retrofit);
                authHolder = authModule.provideAuthHolder(api, Settings.getInstance(context));

                base.evaluate();
            }
        };
    }
}
