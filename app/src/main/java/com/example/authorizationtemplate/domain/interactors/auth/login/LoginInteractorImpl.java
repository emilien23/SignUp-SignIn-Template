package com.example.authorizationtemplate.domain.interactors.auth.login;



import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.data.network.wrapper.ResolverCallbackWrapper;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.interactors.auth.AuthListener;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.authorizationtemplate.GlobalNavigator.ACTION_LOGIN;


public class LoginInteractorImpl implements LoginInteractor, AuthListener {


    private CompositeDisposable compositeDisposable;
    private LoginInteractor.Callback callback;
    private GlobalNavigator globalNavigator;
    private AuthRepository authRepository;
    private Resolution resolution;

    public LoginInteractorImpl(AuthRepository authRepository, GlobalNavigator globalNavigator, Resolution resolution) {
        this.resolution = resolution;
        this.globalNavigator = globalNavigator;
        this.authRepository = authRepository;
        this.compositeDisposable = new CompositeDisposable();
        subscribeToAuthRepos();
    }

    public void subscribeToCallback(Callback callback){
        this.callback = callback;
    }

    public void unsubscribe(){
        compositeDisposable.clear();
        this.callback = null;
    }

    public void execute(String email, String password){
        compositeDisposable.add(authRepository.login(new LoginRequest(email, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResolverCallbackWrapper<TokenResponse>(resolution) {
                    @Override
                    protected void onSuccess(TokenResponse response) {
                        callback.onLoginSucceeded();
                        authRepository.saveLoginData(response);
                    }
                }));
    }

    private void subscribeToAuthRepos() {
        authRepository.subscribeUnauthorized(this);
    }

    @Override
    public void onUnauthorized() {
        globalNavigator.navigateTo(ACTION_LOGIN);
    }

}
