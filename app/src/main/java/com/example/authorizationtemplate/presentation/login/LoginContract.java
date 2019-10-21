package com.example.authorizationtemplate.presentation.login;


import com.example.authorizationtemplate.presentation.base.BasePresenter;
import com.example.authorizationtemplate.presentation.base.BaseView;

public interface LoginContract extends BasePresenter {

    interface Presenter extends BasePresenter {
        void setAuthData(String email, String password);

        void registrationButtonClicked();
    }

    interface View extends BaseView {
        void showErrorMessage(String msg);

        void showToastMessage(String msg);
    }
}
