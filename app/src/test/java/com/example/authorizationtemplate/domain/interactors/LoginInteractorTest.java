package com.example.authorizationtemplate.domain.interactors;


import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.interactors.auth.login.LoginInteractor;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginInteractorTest {

    @Mock
    LoginInteractor.Callback callback;

    @Mock
    private LoginInteractor loginInteractor;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private GlobalNavigator globalNavigator;

    @Mock
    private Resolution resolution;


    @Test
    public void testLogin() {
        String password = "password";
        String email = "email";

        doNothing().when(loginInteractor).subscribeToCallback(callback);
        doNothing().when(loginInteractor).execute(email, password);
        doNothing().when(loginInteractor).unsubscribe();

        loginInteractor.subscribeToCallback(callback);
        loginInteractor.execute(email, password);
        loginInteractor.unsubscribe();

        verify(loginInteractor).subscribeToCallback(callback);
        verify(loginInteractor).execute(email, password);
        verify(loginInteractor).unsubscribe();
    }

}
