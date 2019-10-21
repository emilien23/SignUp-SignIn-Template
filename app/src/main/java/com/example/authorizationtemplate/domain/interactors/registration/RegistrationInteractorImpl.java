package com.example.authorizationtemplate.domain.interactors.registration;


import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.data.network.wrapper.ResolverCallbackWrapper;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.data.repositories.user.UserRepository;
import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class RegistrationInteractorImpl implements RegistrationInteractor {

    private CompositeDisposable compositeDisposable;
    private UserRepository userRepository;
    private AuthRepository authRepository;
    private RegistrationInteractor.Callback callback;
    private Resolution resolution;
    private ResourceProvider resourceProvider;


    public RegistrationInteractorImpl(UserRepository userRepository,
                                      AuthRepository authRepository,
                                      Resolution resolution,
                                      ResourceProvider resourceProvider){
        this.resourceProvider = resourceProvider;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.resolution = resolution;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void execute(AddUserData addUserData) {
        compositeDisposable.add(userRepository.addUser(addUserData).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                }));
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
        this.callback = null;
    }
}
