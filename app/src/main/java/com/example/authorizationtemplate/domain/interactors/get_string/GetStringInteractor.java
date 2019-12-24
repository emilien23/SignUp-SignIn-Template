package com.example.authorizationtemplate.domain.interactors.get_string;

import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;
import com.example.authorizationtemplate.domain.models.Info;

public interface GetStringInteractor extends BaseInteractor {

    void subscribeToCallback(Callback callback);

    void execute();

    interface Callback{
        void onStringDelivered(Info msg);
    }
}
