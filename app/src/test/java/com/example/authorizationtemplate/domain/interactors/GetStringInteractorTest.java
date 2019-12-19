package com.example.authorizationtemplate.domain.interactors;

import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractor;
import com.example.authorizationtemplate.domain.interactors.get_string.GetStringInteractorImpl;

import com.example.authorizationtemplate.domain.repositories.main.MainRepository;
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
public class GetStringInteractorTest {

    private final String RESPONSE_STRING = "responseString";
    private final Integer NOT_FOUND = 404;

    @Mock
    private GetStringInteractor.Callback callback;

    @Mock
    private MainRepository mainRepository;
    @Mock
    private Resolution resolution;

    private Response<String> successResponse;
    private Response<String> errorResponse;

    private GetStringInteractor getStringInteractor;

    @Before
    public void setUp() {
        getStringInteractor =
                new GetStringInteractorImpl(mainRepository, resolution,
                        Schedulers.trampoline(), Schedulers.trampoline());
        makeMockSuccessResponse();
        makeMockErrorResponse();
    }

    private void makeMockSuccessResponse(){
        successResponse = Response.success(RESPONSE_STRING);
    }

    private void makeMockErrorResponse(){
        errorResponse = Response.error(
                NOT_FOUND,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"message\":[\"error\"]}"
                ));
    }

    @Test
    public void testGetString_success() {
        when(mainRepository.getString()).thenReturn(Observable.just(successResponse));

        getStringInteractor.subscribeToCallback(callback);
        getStringInteractor.execute();

        verify(callback).onStringDelivered(successResponse.body());
        verifyNoMoreInteractions(resolution);
    }

    @Test
    public void testGetString_error_404() {
        when(mainRepository.getString()).thenReturn(Observable.just(errorResponse));

        getStringInteractor.subscribeToCallback(callback);
        getStringInteractor.execute();

        verify(callback, never()).onStringDelivered(successResponse.body());
        verify(resolution).onHttpException(NOT_FOUND);
    }

    @After
    public void after(){ getStringInteractor.unsubscribe(); }

    @AfterClass
    public static void afterClass(){
        RxAndroidPlugins.reset();
        RxJavaPlugins.reset();
    }
}
