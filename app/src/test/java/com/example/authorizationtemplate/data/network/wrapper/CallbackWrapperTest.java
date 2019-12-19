package com.example.authorizationtemplate.data.network.wrapper;

import com.example.authorizationtemplate.utils.exception.NoConnectivityException;
import com.example.authorizationtemplate.utils.resolution.Resolution;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CallbackWrapperTest {

    private final Integer MOCK_ERROR_CODE = 403;
    private final String MOCK_RESPONSE_STRING = "mockString";
    private Throwable throwable;

    private TestCallbackWrapper<String> resolverCallbackWrapper;
    private Response successResponse;
    private Response errorResponse;

    @Mock
    private Resolution resolution;

    @Before
    public void setUp() {
        resolverCallbackWrapper = Mockito.spy(new TestCallbackWrapper<>(resolution));
        successResponse = makeSuccessResponse(MOCK_RESPONSE_STRING);
        errorResponse = makeErrorResponse(MOCK_ERROR_CODE);

        throwable = new Throwable();
    }

    @Test
    public void testOnNextSuccessResponse() {
        resolverCallbackWrapper.onNext(successResponse);
        verify(resolverCallbackWrapper).onSuccess(anyString());
        verify(resolution, never()).onConnectivityUnavailable();
        verify(resolution, never()).onError(throwable);
        verify(resolution, never()).onHttpException(anyInt());
    }

    @Test
    public void testOnNextErrorResponse() {
        resolverCallbackWrapper.onNext(errorResponse);
        verify(resolverCallbackWrapper, never()).onSuccess(anyString());
        verify(resolution, never()).onConnectivityUnavailable();
        verify(resolution, never()).onError(throwable);
        verify(resolution).onHttpException(MOCK_ERROR_CODE);
    }

    @Test
    public void testOnError_ConnectivityUnavailable() {
        throwable = new NoConnectivityException();
        resolverCallbackWrapper.onError(throwable);
        verify(resolverCallbackWrapper, never()).onSuccess(anyString());
        verify(resolution).onConnectivityUnavailable();
        verify(resolution, never()).onError(throwable);
        verify(resolution, never()).onHttpException(MOCK_ERROR_CODE);
    }

    @Test
    public void testOnNextError_HttpException() {
        throwable = new HttpException(errorResponse);
        resolverCallbackWrapper.onError(throwable);
        verify(resolverCallbackWrapper, never()).onSuccess(anyString());
        verify(resolution, never()).onConnectivityUnavailable();
        verify(resolution, never()).onError(throwable);
        verify(resolution).onHttpException(MOCK_ERROR_CODE);
    }

    @Test
    public void testOnNextError_Error() {
        resolverCallbackWrapper.onError(throwable);
        verify(resolverCallbackWrapper, never()).onSuccess(anyString());
        verify(resolution, never()).onConnectivityUnavailable();
        verify(resolution).onError(throwable);
        verify(resolution, never()).onHttpException(MOCK_ERROR_CODE);
    }

    class TestCallbackWrapper<String> extends ResolverCallbackWrapper<String> {

        public TestCallbackWrapper(Resolution resolution){
            super(resolution);
        }
        @Override
        protected void onSuccess(String response) { }
    }

    private <T> Response makeSuccessResponse(T value){
        return Response.success(value);
    }

    private Response makeErrorResponse(Integer code){
        return Response.error(code,
        ResponseBody.create(
                MediaType.parse("application/json"),
                "{\"message\":\"error\"}"));
    }
}
