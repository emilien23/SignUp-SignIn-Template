package com.example.authorizationtemplate.domain.interactors;

import com.example.authorizationtemplate.GlobalNavigator;
import com.example.authorizationtemplate.domain.interactors.auth.login.LoginInteractor;
import com.example.authorizationtemplate.domain.interactors.auth.login.LoginInteractorImpl;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.utils.resolution.Resolution;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Response;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginInteractorTest {

    private final String EMAIL_VALID = "validEmail";
    private final String EMAIL_ERROR = "errorEmail";
    private final String PASSWORD_VALID = "validPassword";
    private final String PASSWORD_ERROR = "errorPassword";
    private final Integer CODE_FORBIDDEN = 403;

    @Mock
    private LoginInteractor.Callback callback;

    @Mock
    private AuthRepository authRepository;
    @Mock
    private GlobalNavigator globalNavigator;
    @Mock
    private Resolution resolution;
    @Mock
    private TokenResponse token;

    private Response<TokenResponse> successResponse;
    private Response<TokenResponse> errorResponse;

    private LoginInteractor loginInteractor;
    private LoginRequest validCredentials, errorCredentials;


    @Before
    public void setUp(){
        makeValidCredentials();
        makeErrorCredentials();
        loginInteractor =
                new LoginInteractorImpl(authRepository, globalNavigator, resolution,
                        Schedulers.trampoline(), Schedulers.trampoline());
        makeMockSuccessResponse();
        makeMockErrorResponse();
    }

    private void makeValidCredentials() {
        validCredentials = new LoginRequest(EMAIL_VALID, PASSWORD_VALID);
    }

    private void makeErrorCredentials() {
        errorCredentials = new LoginRequest(EMAIL_ERROR, PASSWORD_ERROR);
    }

    private void makeMockSuccessResponse(){
        successResponse = Response.success(token);
    }

    private void makeMockErrorResponse(){
        errorResponse = Response.error(
                CODE_FORBIDDEN,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"message\":[\"error\"]}"
                ));
    }

    @Test
    public void testLogin_with_valid_credentials() {
        when(authRepository.login(validCredentials)).thenReturn(Observable.just(successResponse));

        loginInteractor.subscribeToCallback(callback);
        loginInteractor.execute(validCredentials);

        verify(authRepository).saveLoginData(token);
        verify(callback).onLoginSucceeded();

        verifyNoMoreInteractions(resolution);
    }

    @Test
    public void testLogin_with_error_credentials() {
        when(authRepository.login(errorCredentials)).thenReturn(Observable.just(errorResponse));

        loginInteractor.subscribeToCallback(callback);
        loginInteractor.execute(errorCredentials);

        verify(authRepository, never()).saveLoginData(token);
        verify(callback, never()).onLoginSucceeded();
        verify(resolution).onHttpException(CODE_FORBIDDEN);
        verify(resolution, never()).onConnectivityUnavailable();
    }

    @After
    public void after(){ loginInteractor.unsubscribe(); }

    @AfterClass
    public static void afterClass(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
