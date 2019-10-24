package com.example.authorizationtemplate.domain.interactors.auth.logout;


import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class LogoutInteractorImpl extends ReactiveInteractor implements LogoutInteractor {

    private LogoutInteractor.Callback callback;
    private AuthRepository authRepository;

    public LogoutInteractorImpl(AuthRepository authRepository,
                                Scheduler threadExecutor,
                                Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.authRepository = authRepository;
    }

    @Override
    public void execute() {
        Disposable d = authRepository
                .logout()
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler)
                .subscribe(() -> callback.onLogout());
        addDisposable(d);
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void unsubscribe() {
        callback = null;
        dispose();
    }
}
