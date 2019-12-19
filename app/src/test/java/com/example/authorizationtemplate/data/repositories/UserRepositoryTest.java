package com.example.authorizationtemplate.data.repositories;

import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.data.repositories.main.MainRepositoryImpl;
import com.example.authorizationtemplate.data.repositories.user.UserRepositoryImpl;
import com.example.authorizationtemplate.domain.mapper.BaseObjectsMapper;
import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.RegistrationRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.main.MainRepository;
import com.example.authorizationtemplate.domain.repositories.user.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Observable;
import retrofit2.Response;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    private BaseObjectsMapper<AddUserData, RegistrationRequest> mapper;

    @Mock
    private NetworkService.CommonApi api;

    @Mock
    private Observable<Response<TokenResponse>> response;

    @Mock
    private AddUserData addUserData;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        userRepository = new UserRepositoryImpl(api, mapper);
        RegistrationRequest registrationRequest = mapper.map(addUserData);
        doReturn(response).when(api).registration(registrationRequest);
    }

    @Test
    public void testGetString() {
        userRepository.addUser(addUserData);
        verify(api).registration(mapper.map(addUserData));
    }
}
