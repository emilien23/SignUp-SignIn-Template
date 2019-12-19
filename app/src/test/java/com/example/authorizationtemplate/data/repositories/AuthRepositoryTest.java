package com.example.authorizationtemplate.data.repositories;

import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.data.network.auth.SessionListener;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepositoryImpl;
import com.example.authorizationtemplate.domain.interactors.auth.AuthListener;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import retrofit2.Response;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuthRepositoryTest {

    @Mock
    private NetworkService.CommonApi api;

    @Mock
    private AuthHolder authHolder;

    @Mock
    private LoginRequest loginRequest;

    @Mock
    private TokenResponse tokenResponse;

    @Mock
    private Observable<Response<TokenResponse>> response;

    private AuthRepository authRepository;


    @Before
    public void setUp() {
        authRepository = new AuthRepositoryImpl(api, authHolder);
        doReturn(response).when(api).login(loginRequest);
    }

    @Test
    public void testSubscribe() {
        verify(authHolder).subscribeToSessionExpired((SessionListener)authRepository);
    }

    @Test
    public void testLogin() {
        authRepository.login(loginRequest);
        verify(api).login(loginRequest);
    }

    @Test
    public void testLogout() {
        TestObserver logoutObserver = TestObserver.create();
        Completable logoutCompletable = authRepository.logout();
        logoutCompletable.subscribe(logoutObserver);

        verify(authHolder).clearLoginData();
        logoutObserver.assertSubscribed();
        logoutObserver.dispose();
    }

    @Test
    public void testSaveLoginData() {
        authRepository.saveLoginData(tokenResponse);
        verify(authHolder).setLoginData(tokenResponse);
    }

    @Test
    public void testIsTokenDateExpired() {
        authRepository.isTokenDateExpired();
        verify(authHolder).isTokenExpired();
    }

    @Test
    public void testSessionExpiredWasCalled() {
        AuthListener authListener = mock(AuthListener.class);
        SessionListener listener = (SessionListener)authRepository;
        authRepository.subscribeUnauthorized(authListener);
        listener.sessionExpired();
        verify(authListener).onUnauthorized();
    }

    @Test
    public void testSessionExpiredWasNotCalled() {
        AuthListener authListener = mock(AuthListener.class);
        SessionListener listener = (SessionListener)authRepository;
        listener.sessionExpired();
        verify(authListener, never()).onUnauthorized();
    }
}
