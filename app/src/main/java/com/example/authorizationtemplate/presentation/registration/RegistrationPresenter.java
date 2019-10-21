package com.example.authorizationtemplate.presentation.registration;

import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.interactors.registration.ConfirmPasswordInteractor;
import com.example.authorizationtemplate.domain.interactors.registration.RegistrationInteractor;

import javax.inject.Inject;

import static com.example.authorizationtemplate.GlobalNavigator.ACTION_LOGIN;
import static com.example.authorizationtemplate.GlobalNavigator.ACTION_MAIN;


public class RegistrationPresenter implements RegistrationContract.Presenter,
        ConfirmPasswordInteractor.Callback,
        RegistrationInteractor.Callback {


    private RegistrationInteractor registrationInteractor;
    private ConfirmPasswordInteractor confirmPasswordInteractor;
    private GlobalNavigator navigator;
    private RegistrationContract.View view;

    private AddUserData addUserData;
    private ResourceProvider resourceProvider;

    @Inject
    public RegistrationPresenter(RegistrationInteractor registrationInteractor,
                                 ConfirmPasswordInteractor confirmPasswordInteractor,
                                 GlobalNavigator navigator, ResourceProvider resourceProvider,
                                 RegistrationContract.View view){
        this.view = view;
        this.registrationInteractor = registrationInteractor;
        this.confirmPasswordInteractor = confirmPasswordInteractor;
        this.navigator = navigator;
        this.resourceProvider = resourceProvider;
    }


    @Override
    public void create() { }

    @Override
    public void resume() {
        registrationInteractor.subscribeToCallback(this);
        confirmPasswordInteractor.subscribeToCallback(this);
    }

    @Override
    public void pause() {
        registrationInteractor.unsubscribe();
        confirmPasswordInteractor.unsubscribe();
    }

    @Override
    public void stop() { }

    @Override
    public void destroy() { view = null; }

    @Override
    public void setRegistrationData(String name, String email, String password, String confirmPassword) {
        addUserData = new AddUserData(name, email, password);
        confirmPasswordInteractor.execute(password, confirmPassword);
    }

    @Override
    public void loginButtonClicked() {
        navigator.navigateTo(ACTION_LOGIN);
    }

    @Override
    public void onPasswordsMatch() {
        if(addUserData != null)
            registrationInteractor.execute(addUserData);
    }

    @Override
    public void onPasswordsNotMatch() { view.showErrorMessage(resourceProvider.getStrings().getPasswordNotMatch()); }

    @Override
    public void onRegistrationSucceeded() { navigator.navigateTo(ACTION_MAIN); }

    @Override
    public void onUserAlreadyExists() { view.showErrorMessage(resourceProvider.getStrings().getUserAlreadyExists()); }
}
