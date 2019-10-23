package com.example.authorizationtemplate.domain.interactors;

import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractor;
import com.example.authorizationtemplate.domain.interactors.auth.logout.LogoutInteractorImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogoutInteractorTest {

    @Mock
    LogoutInteractor.Callback callback;

    @Mock
    private LogoutInteractorImpl logoutInteractor;

    @Test
    public void testLogin() {
        doNothing().when(logoutInteractor).subscribeToCallback(callback);
        doNothing().when(logoutInteractor).execute();
        doNothing().when(logoutInteractor).unsubscribe();

        logoutInteractor.subscribeToCallback(callback);
        logoutInteractor.execute();
        logoutInteractor.unsubscribe();

        verify(logoutInteractor).subscribeToCallback(callback);
        verify(logoutInteractor).execute();
        verify(logoutInteractor).unsubscribe();
    }
}
