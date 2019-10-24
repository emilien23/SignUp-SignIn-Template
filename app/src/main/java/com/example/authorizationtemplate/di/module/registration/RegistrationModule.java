package com.example.authorizationtemplate.di.module.registration;

import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.repositories.user.UserRepositoryImpl;
import com.example.authorizationtemplate.di.module.navigation.NavigationModule;
import com.example.authorizationtemplate.di.scope.RegistrationScope;
import com.example.authorizationtemplate.domain.interactors.registration.ConfirmPasswordInteractor;
import com.example.authorizationtemplate.domain.interactors.registration.ConfirmPasswordInteractorImpl;
import com.example.authorizationtemplate.domain.interactors.registration.RegistrationInteractor;
import com.example.authorizationtemplate.domain.interactors.registration.RegistrationInteractorImpl;
import com.example.authorizationtemplate.domain.mapper.BaseObjectsMapper;
import com.example.authorizationtemplate.domain.mapper.RegistrationUserMapper;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.repositories.user.UserRepository;
import com.example.authorizationtemplate.presentation.registration.RegistrationContract;
import com.example.authorizationtemplate.presentation.registration.RegistrationResolution;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module(includes = {RegistrationContractModule.class, NavigationModule.class})
public class RegistrationModule {

    private final RegistrationContract.View registrationView;

    public RegistrationModule(RegistrationContract.View registrationView) {
        this.registrationView = registrationView;
    }

    @RegistrationScope
    @Provides
    public Resolution provideResolution(ResourceProvider resourceProvider) {
        return new RegistrationResolution(registrationView, resourceProvider);
    }

    @RegistrationScope
    @Provides
    public BaseObjectsMapper provideBaseObjectsMapper() {
        return new RegistrationUserMapper();
    }

    @RegistrationScope
    @Provides
    public UserRepository provideUserRepository(NetworkService.CommonApi commonApi, BaseObjectsMapper mapper) {
        return new UserRepositoryImpl(commonApi, mapper);
    }

    @RegistrationScope
    @Provides
    public ConfirmPasswordInteractor provideConfirmPasswordInteractor(@Named("executor") Scheduler threadExecutor,
                                                                      @Named("post_execution") Scheduler postExecutionThread) {
        return new ConfirmPasswordInteractorImpl(threadExecutor, postExecutionThread);
    }

    @RegistrationScope
    @Provides
    public RegistrationInteractor provideRegistrationInteractor(UserRepository userRepository, AuthRepository authRepository,
                                                                Resolution resolution, ResourceProvider resourceProvider,
                                                                @Named("executor") Scheduler threadExecutor,
                                                                @Named("post_execution") Scheduler postExecutionThread) {
        return new RegistrationInteractorImpl(userRepository, authRepository,
                resolution, resourceProvider,
                threadExecutor, postExecutionThread);
    }

    @RegistrationScope
    @Provides
    public RegistrationContract.View provideRegistrationView() { return registrationView; }
}
