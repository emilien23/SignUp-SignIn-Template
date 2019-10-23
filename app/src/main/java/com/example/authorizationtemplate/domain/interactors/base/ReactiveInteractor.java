package com.example.authorizationtemplate.domain.interactors.base;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class ReactiveInteractor {

    protected Scheduler threadExecutorScheduler;
    protected Scheduler postExecutionThreadScheduler;
    private CompositeDisposable disposables = new CompositeDisposable();

    public ReactiveInteractor(Scheduler threadExecutor, Scheduler postExecutionThread) {
        this.threadExecutorScheduler = threadExecutor;
        this.postExecutionThreadScheduler = postExecutionThread;
    }

    protected void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        disposables.add(disposable);
    }
}
