package com.example.authorizationtemplate.domain.interactors.registration;


import com.example.authorizationtemplate.domain.interactors.base.BaseInteractor;
import com.example.authorizationtemplate.domain.models.AddUserData;

public interface RegistrationInteractor extends BaseInteractor {

    void execute(AddUserData userData);

    void subscribeToCallback(Callback callback);

    interface Callback{
        void onRegistrationSucceeded();
        void onUserAlreadyExists();
    }
}
