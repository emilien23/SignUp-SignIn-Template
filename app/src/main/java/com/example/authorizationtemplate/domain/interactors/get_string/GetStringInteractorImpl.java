package com.example.authorizationtemplate.domain.interactors.get_string;

import com.example.authorizationtemplate.data.network.wrapper.ResolverCallbackWrapper;
import com.example.authorizationtemplate.data.repositories.main.MainRepository;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GetStringInteractorImpl implements GetStringInteractor {

    private CompositeDisposable compositeDisposable;
    private MainRepository mainRepository;
    private GetStringInteractor.Callback callback;
    private Resolution resolution;

    public GetStringInteractorImpl(MainRepository mainRepository, Resolution resolution){
        this.resolution = resolution;
        this.compositeDisposable = new CompositeDisposable();
        this.mainRepository = mainRepository;
    }

    @Override
    public void subscribeToCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    public void execute() {
        compositeDisposable.add(mainRepository.getString()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new ResolverCallbackWrapper<String>(resolution) {
                    @Override
                    protected void onSuccess(String response) {
                        callback.onStringDelivered(response);
                    }
                }));
    }

    @Override
    public void unsubscribe() {
        this.callback = null;
    }
}
