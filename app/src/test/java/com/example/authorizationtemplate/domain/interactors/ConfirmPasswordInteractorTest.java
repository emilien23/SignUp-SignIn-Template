package com.example.authorizationtemplate.domain.interactors;


import com.example.authorizationtemplate.domain.interactors.registration.ConfirmPasswordInteractor;
import com.example.authorizationtemplate.domain.interactors.registration.ConfirmPasswordInteractorImpl;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConfirmPasswordInteractorTest {

    private final String PASSWORD = "passwordOne";
    private final String ANOTHER_PASSWORD = "passwordTwo";

    @Mock
    private ConfirmPasswordInteractor.Callback callback;

    private ConfirmPasswordInteractor confirmPasswordInteractor;

    @Before
    public void setUp() {
        confirmPasswordInteractor =
                new ConfirmPasswordInteractorImpl(Schedulers.trampoline(), Schedulers.trampoline());
    }

    @Test
    public void testConfirmPassword_success() {
        confirmPasswordInteractor.subscribeToCallback(callback);
        confirmPasswordInteractor.execute(PASSWORD, PASSWORD);

        verify(callback).onPasswordsMatch();
    }

    @Test
    public void testFirstArg_is_null() {
        confirmPasswordInteractor.subscribeToCallback(callback);
        confirmPasswordInteractor.execute(null, PASSWORD);

        verify(callback).onPasswordsNotMatch();
    }

    @Test
    public void testSecondArg_is_null() {
        confirmPasswordInteractor.subscribeToCallback(callback);
        confirmPasswordInteractor.execute(PASSWORD, null);

        verify(callback).onPasswordsNotMatch();
    }

    @Test
    public void testGetString_error_404() {
        confirmPasswordInteractor.subscribeToCallback(callback);
        confirmPasswordInteractor.execute(PASSWORD, ANOTHER_PASSWORD);

        verify(callback).onPasswordsNotMatch();
    }

    @After
    public void after(){ confirmPasswordInteractor.unsubscribe(); }

    @AfterClass
    public static void afterClass(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
