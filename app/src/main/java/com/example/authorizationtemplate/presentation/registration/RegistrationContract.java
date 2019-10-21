package com.example.authorizationtemplate.presentation.registration;

import com.example.authorizationtemplate.presentation.base.BasePresenter;
import com.example.authorizationtemplate.presentation.base.BaseView;

public interface RegistrationContract extends BasePresenter {

    interface Presenter extends BasePresenter{
        void setRegistrationData(String name, String email, String password, String confirmPassword);

        void loginButtonClicked();
    }

    interface View extends BaseView {
        void showErrorMessage(String msg);

        void showToastMessage(String msg);
    }
}
