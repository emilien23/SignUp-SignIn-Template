package com.example.authorizationtemplate.data.network.auth;

import android.content.Context;

import com.example.authorizationtemplate.utils.DateUtils;
import com.example.authorizationtemplate.utils.exception.NoConnectivityException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NetworkUtils.class)
public class ConnectivityInterceptorTest {

    private static final String MOCK_TOKEN_VALUE = "mockToken";
    private static final String MOCK_RESPONSE_MESSAGE = "mockMessage";
    private static final Integer MOCK_RESPONSE_CODE_SUCCESS = 200;

    @Mock
    private Context context;

    @Mock
    private Interceptor.Chain chain;

    private Response response;

    private ConnectivityInterceptor connectivityInterceptor;

    @Before
    public void setUp() {
        connectivityInterceptor = new ConnectivityInterceptor(context);
        response = makeMockResponse();
        PowerMockito.mockStatic(NetworkUtils.class);
    }

    @Test
    public void testIntercept_isOnline() throws IOException {
        BDDMockito.given(NetworkUtils.isOnline(context)).willReturn(true);
        when(chain.request()).thenReturn(mock(Request.class));
        when(chain.request().newBuilder()).thenReturn(mock(Request.Builder.class));
        when(chain.proceed(any())).thenReturn(response);

        Response returnedResponse = connectivityInterceptor.intercept(chain);

        verify(chain.request()).newBuilder();
        verify(chain).proceed(any());
        Assert.assertEquals(returnedResponse, response);
    }

    @Test
    public void testIntercept_isOffline() throws IOException {
        BDDMockito.given(NetworkUtils.isOnline(context)).willReturn(false);
        when(chain.request()).thenReturn(mock(Request.class));
        when(chain.request().newBuilder()).thenReturn(mock(Request.Builder.class));
        when(chain.proceed(any())).thenReturn(response);

        try {
            connectivityInterceptor.intercept(chain);
            fail("NoConnectivityException");
        }catch(NoConnectivityException e){
            assertEquals("Нет подключения к интернету" ,e.getMessage());
        }
        verify(chain.request(), never()).newBuilder();
        verify(chain, never()).proceed(any());
    }

    private Response makeMockResponse(){
        Response.Builder responseBuilder =
                new Response
                        .Builder()
                        .header(AuthInterceptor.ACCESS_TOKEN_HEADER, MOCK_TOKEN_VALUE)
                        .code(MOCK_RESPONSE_CODE_SUCCESS)
                        .request(mock(Request.class))
                        .protocol(mock(Protocol.class))
                        .message(MOCK_RESPONSE_MESSAGE);
        return responseBuilder.build();
    }
}
