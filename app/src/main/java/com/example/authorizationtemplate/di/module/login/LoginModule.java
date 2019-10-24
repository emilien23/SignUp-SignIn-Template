package com.example.authorizationtemplate.di.module.login;

import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.di.module.navigation.NavigationModule;
import com.example.authorizationtemplate.di.scope.LoginScope;
import com.example.authorizationtemplate.domain.interactors.auth.login.LoginInteractor;
import com.example.authorizationtemplate.domain.interactors.auth.login.LoginInteractorImpl;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.presentation.login.LoginContract;
import com.example.authorizationtemplate.presentation.login.LoginResolution;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module(includes = {LoginContractModule.class, NavigationModule.class})
public class LoginModule {

    private final LoginContract.View loginView;

    public LoginModule(LoginContract.View loginView) {
        this.loginView = loginView;
    }

    @LoginScope
    @Provides
    public Resolution provideResolution(ResourceProvider resourceProvider) {
        return new LoginResolution(loginView, resourceProvider);
    }

    @LoginScope
    @Provides
    public LoginInteractor provideLoginInteractor(AuthRepository authRepository,
                                                  GlobalNavigator navigator,
                                                  Resolution resolution,
                                                  @Named("executor") Scheduler threadExecutor,
                                                  @Named("post_execution") Scheduler postExecutionThread) {
        return new LoginInteractorImpl(authRepository, navigator, resolution, threadExecutor, postExecutionThread);
    }

    @LoginScope
    @Provides
    public LoginContract.View provideLoginView() { return loginView; }

}
