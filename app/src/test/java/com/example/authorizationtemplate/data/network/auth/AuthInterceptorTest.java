package com.example.authorizationtemplate.data.network.auth;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AuthInterceptorTest {

    private static final String MOCK_TOKEN_VALUE_AUTH_HOLDER = "mockTokenValueAuthHolder";
    private static final String MOCK_TOKEN_VALUE_CHAIN = "mockTokenValueChain";
    private static final String MOCK_RESPONSE_MESSAGE = "mockMessage";
    private static final Integer MOCK_RESPONSE_CODE_SUCCESS = 200;

    @Mock
    private AuthHolder authHolder;

    @Mock
    private Interceptor.Chain chain;

    private Response responseWithHeader;

    private AuthInterceptor authInterceptor;

    @Before
    public void setUp() {
        authInterceptor = new AuthInterceptor(authHolder);
        responseWithHeader = makeMockResponseWithHeader();
    }

    @Test
    public void testIntercept_headerValueIsNull() throws IOException {
        when(chain.request()).thenReturn(mock(Request.class));
        when(chain.request().newBuilder()).thenReturn(mock(Request.Builder.class));
        when(chain.request().header(AuthInterceptor.ACCESS_TOKEN_HEADER)).thenReturn(null);
        when(chain.proceed(any())).thenReturn(responseWithHeader);
        when(authHolder.getToken()).thenReturn(MOCK_TOKEN_VALUE_AUTH_HOLDER);

        Response returnedResponse = authInterceptor.intercept(chain);

        verify(chain.request(), atLeastOnce()).header(AuthInterceptor.ACCESS_TOKEN_HEADER);
        verify(chain.request().newBuilder())
                .addHeader(AuthInterceptor.ACCESS_TOKEN_HEADER, MOCK_TOKEN_VALUE_AUTH_HOLDER);
        Assert.assertNotNull(returnedResponse.header(AuthInterceptor.ACCESS_TOKEN_HEADER));
    }

    @Test
    public void testIntercept_headerValueIsNotNull() throws IOException {
        when(chain.request()).thenReturn(mock(Request.class));
        when(chain.request().newBuilder()).thenReturn(mock(Request.Builder.class));
        when(chain.request().header(AuthInterceptor.ACCESS_TOKEN_HEADER)).thenReturn(MOCK_TOKEN_VALUE_AUTH_HOLDER);
        when(chain.proceed(any())).thenReturn(responseWithHeader);

        Response returnedResponse = authInterceptor.intercept(chain);

        verify(chain.request(), atLeastOnce()).header(AuthInterceptor.ACCESS_TOKEN_HEADER);
        verify(chain.request().newBuilder(), never())
                .addHeader(AuthInterceptor.ACCESS_TOKEN_HEADER, MOCK_TOKEN_VALUE_AUTH_HOLDER);
        Assert.assertNotNull(returnedResponse.header(AuthInterceptor.ACCESS_TOKEN_HEADER));
    }

    private Response makeMockResponseWithHeader(){
        Response.Builder responseBuilder =
                new Response
                        .Builder()
                        .header(AuthInterceptor.ACCESS_TOKEN_HEADER, MOCK_TOKEN_VALUE_CHAIN)
                        .code(MOCK_RESPONSE_CODE_SUCCESS)
                        .request(mock(Request.class))
                        .protocol(mock(Protocol.class))
                        .message(MOCK_RESPONSE_MESSAGE);
        return responseBuilder.build();
    }
}
