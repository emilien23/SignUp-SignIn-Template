package com.example.authorizationtemplate.presentation.main;

import com.example.authorizationtemplate.presentation.base.BasePresenter;
import com.example.authorizationtemplate.presentation.base.BaseView;

public interface MainContract extends BasePresenter {

    interface Presenter extends BasePresenter{
        void logoutButtonClicked();
    }

    interface View extends BaseView {

        void showError(String error);

        void showString(String msg);
    }
}
