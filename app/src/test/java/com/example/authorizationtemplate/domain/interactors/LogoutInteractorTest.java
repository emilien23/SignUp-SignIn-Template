package com.example.authorizationtemplate.domain.interactors;

import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractor;
import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractorImpl;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutInteractorTest {

    @Mock
    private AuthRepository authRepository;
    @Mock
    LogoutInteractor.Callback callback;

    private LogoutInteractorImpl logoutInteractor;

    @Before
    public void setUp(){
        logoutInteractor =
                new LogoutInteractorImpl(authRepository,
                        Schedulers.trampoline(), Schedulers.trampoline());
    }

    @Test
    public void testLogout() {
        when(authRepository.logout()).thenReturn(Completable.complete());

        logoutInteractor.subscribeToCallback(callback);
        logoutInteractor.execute();

        verify(callback).onLogout();
    }

    @After
    public void after(){ logoutInteractor.unsubscribe(); }

    @AfterClass
    public static void afterClass(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
