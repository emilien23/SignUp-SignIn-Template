package com.example.authorizationtemplate.domain.interactors.get_string;

import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;

public interface GetStringInteractor extends BaseInteractor {

    void subscribeToCallback(Callback callback);

    void execute();

    interface Callback{
        void onStringDelivered(String msg);
    }
}
