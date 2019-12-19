package com.example.authorizationtemplate.domain.interactors;

import com.example.authorizationtemplate.IntegerProvider;
import com.example.authorizationtemplate.ResourceProvider;
import com.example.authorizationtemplate.domain.interactors.registration.RegistrationInteractor;
import com.example.authorizationtemplate.domain.interactors.registration.RegistrationInteractorImpl;
import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;
import com.example.authorizationtemplate.domain.repositories.user.UserRepository;
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
public class RegistrationInteractorTest {

    private final String NAME_VALID = "validName";
    private final String NAME_ERROR = "errorError";
    private final String EMAIL_VALID = "validEmail";
    private final String EMAIL_ERROR = "errorEmail";
    private final String PASSWORD_VALID = "validPassword";
    private final String PASSWORD_ERROR = "errorPassword";
    private final Integer CODE_ALREADY_EXISTS = 403;

    @Mock
    private RegistrationInteractor.Callback callback;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthRepository authRepository;
    @Mock
    private ResourceProvider resourceProvider;
    @Mock
    private IntegerProvider integerProvider;
    @Mock
    private Resolution resolution;
    @Mock
    private TokenResponse token;

    private Response<TokenResponse> successResponse;
    private Response<TokenResponse> errorResponse;

    private RegistrationInteractor registrationInteractor;
    private AddUserData userDataValid, userDataError;


    @Before
    public void setUp(){
        makeValidCredentials();
        makeErrorCredentials();
        registrationInteractor =
                new RegistrationInteractorImpl(userRepository, authRepository, resolution,resourceProvider,
                        Schedulers.trampoline(), Schedulers.trampoline());
        makeMockSuccessResponse();
        makeMockErrorResponse();
    }

    private void makeValidCredentials() {
        userDataValid = new AddUserData(NAME_VALID, EMAIL_VALID, PASSWORD_VALID);
    }

    private void makeErrorCredentials() {
        userDataError = new AddUserData(NAME_ERROR, EMAIL_ERROR, PASSWORD_ERROR);
    }

    private void makeMockSuccessResponse(){
        successResponse = Response.success(token);
    }

    private void makeMockErrorResponse(){
        errorResponse = Response.error(
                CODE_ALREADY_EXISTS,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"message\":[\"error\"]}"
                ));
    }

    @Test
    public void testRegistration_with_valid_credentials() {
        when(userRepository.addUser(userDataValid)).thenReturn(Observable.just(successResponse));

        registrationInteractor.subscribeToCallback(callback);
        registrationInteractor.execute(userDataValid);

        verify(authRepository).saveLoginData(token);
        verify(callback).onRegistrationSucceeded();

        verifyNoMoreInteractions(resolution);
    }

    @Test
    public void testRegistration_with_error_credentials() {
        when(userRepository.addUser(userDataError)).thenReturn(Observable.just(errorResponse));
        when(resourceProvider.getIntegers()).thenReturn(integerProvider);
        when(integerProvider.getUserAlreadyExist()).thenReturn(CODE_ALREADY_EXISTS);

        registrationInteractor.subscribeToCallback(callback);
        registrationInteractor.execute(userDataError);

        verify(callback).onUserAlreadyExists();
        verify(authRepository, never()).saveLoginData(token);
        verify(callback, never()).onRegistrationSucceeded();
        verify(resolution, never()).onConnectivityUnavailable();
    }

    @After
    public void after(){ registrationInteractor.unsubscribe(); }

    @AfterClass
    public static void afterClass(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
