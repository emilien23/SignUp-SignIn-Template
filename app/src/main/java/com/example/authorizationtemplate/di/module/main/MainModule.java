package com.example.authorizationtemplate.di.module.main;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.repositories.main.MainRepositoryImpl;
import com.example.authorizationtemplate.di.module.navigation.NavigationModule;
import com.example.authorizationtemplate.di.scope.MainScope;
import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractor;
import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractorImpl;
import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractor;
import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractorImpl;
import com.example.authorizationtemplate.domain.interactors.token_expired.TokenExpiredInteractor;
import com.example.authorizationtemplate.domain.interactors.token_expired.TokenExpiredInteractorImpl;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.repositories.main.MainRepository;
import com.example.authorizationtemplate.presentation.main.MainContract;
import com.example.authorizationtemplate.presentation.main.MainResolution;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module(includes = {MainContractModule.class, NavigationModule.class})
public class MainModule {

    private final MainContract.View mainView;

    public MainModule(MainContract.View mainView) {
        this.mainView = mainView;
    }

    @MainScope
    @Provides
    public Resolution provideResolution(ResourceProvider resourceProvider) {
        return new MainResolution(mainView, resourceProvider);
    }

    @MainScope
    @Provides
    public TokenExpiredInteractor provideTokenExpiredInteractor(AuthRepository authRepository,
                                                                @Named("executor") Scheduler threadExecutor,
                                                                @Named("post_execution") Scheduler postExecutionThread) {
        return new TokenExpiredInteractorImpl(authRepository, threadExecutor, postExecutionThread);
    }

    @MainScope
    @Provides
    public LogoutInteractor provideLogoutInteractor(AuthRepository authRepository,
                                                    @Named("executor") Scheduler threadExecutor,
                                                    @Named("post_execution") Scheduler postExecutionThread) {
        return new LogoutInteractorImpl(authRepository, threadExecutor, postExecutionThread);
    }

    @MainScope
    @Provides
    public MainRepository provideUserRepository(NetworkService.AuthApi authApi) {
        return new MainRepositoryImpl(authApi);
    }

    @MainScope
    @Provides
    public GetStringInteractor provideGetStringInteractor(MainRepository mainRepository,
                                                          Resolution resolution,
                                                          @Named("executor") Scheduler threadExecutor,
                                                          @Named("post_execution") Scheduler postExecutionThread) {
        return new GetStringInteractorImpl(mainRepository, resolution, threadExecutor, postExecutionThread);
    }

    @MainScope
    @Provides
    public MainContract.View provideMainView() { return mainView; }
}
