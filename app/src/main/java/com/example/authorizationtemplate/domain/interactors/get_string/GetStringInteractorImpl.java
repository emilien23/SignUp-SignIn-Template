package com.example.authorizationtemplate.domain.interactors.get_string;

import com.example.authorizationtemplate.data.network.wrapper.ResolverCallbackWrapper;
import com.example.authorizationtemplate.domain.interactors.base.ReactiveInteractor;
import com.example.authorizationtemplate.domain.models.Info;
import com.example.authorizationtemplate.domain.repositories.main.MainRepository;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class GetStringInteractorImpl extends ReactiveInteractor implements GetStringInteractor {

    private MainRepository mainRepository;
    private GetStringInteractor.Callback callback;
    private Resolution resolution;

    public GetStringInteractorImpl(MainRepository mainRepository,
                                   Resolution resolution,
                                   Scheduler threadExecutor,
                                   Scheduler postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.resolution = resolution;
        this.mainRepository = mainRepository;
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {
        Disposable d = mainRepository.getString()
                .subscribeOn(threadExecutorScheduler)
                .observeOn(postExecutionThreadScheduler)
                .subscribeWith(new ResolverCallbackWrapper<Info>(resolution) {
                    @Override
                    protected void onSuccess(Info response) {
                        if(callback != null)
                            callback.onStringDelivered(response);
                        else
                            onError(new NullPointerException());
                    }
                });
        addDisposable(d);
    }

    @Override
    public void unsubscribe() {
        this.callback = null;
        dispose();
    }
}
