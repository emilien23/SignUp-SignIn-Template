package com.example.authorizationtemplate.domain.interactors.auth.logout;


import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;

import io.reactivex.disposables.Disposable;

public class LogoutInteractorImpl implements LogoutInteractor {

    private LogoutInteractor.Callback callback;
    private AuthRepository authRepository;
    private Disposable disposable;

    public LogoutInteractorImpl(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void execute() {
        disposable = authRepository
                .logout()
                .subscribe(() -> callback.onLogout());
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void unsubscribe() {
        callback = null;
        if(disposable != null) {
            disposable.dispose();
            disposable = null;
        }
    }
}
