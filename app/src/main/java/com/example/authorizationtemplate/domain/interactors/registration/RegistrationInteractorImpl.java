package com.example.authorizationtemplate.domain.interactors.registration;


import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.wrapper.ResolverCallbackWrapper;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.data.repositories.user.UserRepository;
import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;
import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class RegistrationInteractorImpl extends ReactiveInteractor implements RegistrationInteractor {

    private UserRepository userRepository;
    private AuthRepository authRepository;
    private RegistrationInteractor.Callback callback;
    private Resolution resolution;
    private ResourceProvider resourceProvider;


    public RegistrationInteractorImpl(UserRepository userRepository,
                                      AuthRepository authRepository,
                                      Resolution resolution,
                                      ResourceProvider resourceProvider,
                                      Scheduler threadExecutor,
                                      Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.resourceProvider = resourceProvider;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.resolution = resolution;
    }

    @Override
    public void execute(AddUserData addUserData) {
        Disposable d = userRepository.addUser(addUserData)
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler)
                .subscribeWith(new ResolverCallbackWrapper<TokenResponse>(resolution) {
                    @Override
                    protected void onSuccess(TokenResponse response) {
                        callback.onRegistrationSucceeded();
                        authRepository.saveLoginData(response);
                    }

                    @Override
                    public void onNext(Response<TokenResponse> baseResponse) {
                        if(baseResponse.isSuccessful())
                            onSuccess(baseResponse.body());
                        else
                            if(baseResponse.code() == resourceProvider.getIntegers().getUserAlreadyExist())
                                    callback.onUserAlreadyExists();
                    }
                });
        addDisposable(d);
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void unsubscribe() {
        dispose();
        this.callback = null;
    }
}
