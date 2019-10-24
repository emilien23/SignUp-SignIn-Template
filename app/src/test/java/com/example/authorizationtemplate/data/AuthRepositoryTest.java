package com.example.authorizationtemplate.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Completable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthRepositoryTest {

    @Mock
    private AuthRepository repository;

    @Test
    public void testLogout() {
        when(repository.logout()).thenReturn(Completable.complete());

        Completable completable = repository.logout();

        verify(repository).logout();
    }
}
