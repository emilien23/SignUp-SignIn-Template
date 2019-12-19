package com.example.authorizationtemplate.domain.interactors;

import com.example.authorizationtemplate.domain.interactors.token_expired.TokenExpiredInteractor;
import com.example.authorizationtemplate.domain.interactors.token_expired.TokenExpiredInteractorImpl;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

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
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenExpiredInteractorTest {

    private final Boolean TOKEN_EXPIRED = true;
    private final Boolean TOKEN_NOT_EXPIRED = false;

    @Mock
    private TokenExpiredInteractor.Callback callback;
    @Mock
    private AuthRepository authRepository;

    private TokenExpiredInteractor tokenExpiredInteractor;


    @Before
    public void setUp(){
        tokenExpiredInteractor =
                new TokenExpiredInteractorImpl(authRepository, Schedulers.trampoline(), Schedulers.trampoline());
    }

    @Test
    public void testTokenDateExpired_true() {
        when(authRepository.isTokenDateExpired()).thenReturn(TOKEN_EXPIRED);

        tokenExpiredInteractor.subscribeToCallback(callback);
        tokenExpiredInteractor.execute();

        verify(authRepository).isTokenDateExpired();
        verify(callback).isCheckTokenExpired(TOKEN_EXPIRED);
    }

    @Test
    public void testTokenDateExpired_false() {
        when(authRepository.isTokenDateExpired()).thenReturn(TOKEN_NOT_EXPIRED);

        tokenExpiredInteractor.subscribeToCallback(callback);
        tokenExpiredInteractor.execute();

        verify(authRepository).isTokenDateExpired();
        verify(callback).isCheckTokenExpired(TOKEN_NOT_EXPIRED);
    }

    @After
    public void after(){ tokenExpiredInteractor.unsubscribe(); }

    @AfterClass
    public static void afterClass(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
