package com.example.authorizationtemplate.di.module.main;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.repositories.main.MainRepository;
import com.example.authorizationtemplate.data.repositories.main.MainRepositoryImpl;
import com.example.authorizationtemplate.di.scope.MainScope;
import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractor;
import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractorImpl;
import com.example.authorizationtemplate.presentation.main.MainContract;
import com.example.authorizationtemplate.presentation.main.MainResolution;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainContractModule.class})
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
    public MainRepository provideUserRepository(NetworkService.AuthApi authApi) {
        return new MainRepositoryImpl(authApi);
    }

    @MainScope
    @Provides
    public GetStringInteractor provideGetStringInteractor(MainRepository mainRepository,
                                                          Resolution resolution) {
        return new GetStringInteractorImpl(mainRepository, resolution);
    }

    @MainScope
    @Provides
    public MainContract.View provideMainView() { return mainView; }
}
