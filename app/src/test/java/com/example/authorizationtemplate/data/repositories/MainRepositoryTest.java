package com.example.authorizationtemplate.data.repositories;

import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.network.auth.AuthHolder;
import com.example.authorizationtemplate.data.network.auth.SessionListener;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepositoryImpl;
import com.example.authorizationtemplate.data.repositories.main.MainRepositoryImpl;
import com.example.authorizationtemplate.domain.interactors.auth.AuthListener;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.repositories.main.MainRepository;

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
public class MainRepositoryTest {

    @Mock
    private NetworkService.AuthApi api;

    @Mock
    private Observable<Response<String>> response;

    private MainRepository mainRepository;

    @Before
    public void setUp() {
        mainRepository = new MainRepositoryImpl(api);
        doReturn(response).when(api).getString();
    }

    @Test
    public void testGetString() {
        mainRepository.getString();
        verify(api).getString();
    }
}
