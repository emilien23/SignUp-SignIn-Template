package com.example.authorizationtemplate.domain.interactors.auth.login;



import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.data.network.wrapper.ResolverCallbackWrapper;
import com.example.authorizationtemplate.domain.interactors.auth.AuthListener;
import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

import static com.example.authorizationtemplate.GlobalNavigator.ACTION_LOGIN;


public class LoginInteractorImpl extends ReactiveInteractor implements LoginInteractor, AuthListener {

    private LoginInteractor.Callback callback;
    private GlobalNavigator globalNavigator;
    private AuthRepository authRepository;
    private Resolution resolution;

    public LoginInteractorImpl(AuthRepository authRepository,
                               GlobalNavigator globalNavigator,
                               Resolution resolution,
                               Scheduler threadExecutor,
                               Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.resolution = resolution;
        this.globalNavigator = globalNavigator;
        this.authRepository = authRepository;
        subscribeToAuthRepos();
    }

    public void subscribeToCallback(Callback callback){
        this.callback = callback;
    }

    public void execute(String email, String password){
        Disposable d = authRepository.login(new LoginRequest(email, password))
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler)
                .subscribeWith(new ResolverCallbackWrapper<TokenResponse>(resolution) {
                    @Override
                    protected void onSuccess(TokenResponse response) {
                        callback.onLoginSucceeded();
                        authRepository.saveLoginData(response);
                    }
                });
        addDisposable(d);
    }

    private void subscribeToAuthRepos() {
        authRepository.subscribeUnauthorized(this);
    }

    @Override
    public void onUnauthorized() {
        globalNavigator.navigateTo(ACTION_LOGIN);
    }

    @Override
    public void unsubscribe() {
        this.callback = null;
        dispose();
    }
}
