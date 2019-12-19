package com.example.authorizationtemplate.data.network.auth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import static com.example.authorizationtemplate.data.network.auth.AuthInterceptor.ACCESS_TOKEN_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainAuthentificatorTest {

    private static final String MOCK_STORED_TOKEN = "mockStoredToken";
    private static final String MOCK_REQUEST_TOKEN = "mockRequestToken";
    private static final String MOCK_RESPONSE_MESSAGE = "mockMessage";
    private static final Integer MOCK_RESPONSE_CODE_SUCCESS = 200;

    @Mock
    private AuthHolder authHolder;
    @Mock
    private Route route;
    @Mock
    private Request.Builder requestBuilder;
    @Mock
    private Request request;

    private Response response;

    private MainAuthenticator mainAuthenticator;

    @Before
    public void setUp() {
        mainAuthenticator = new MainAuthenticator(authHolder);
        response = makeMockResponseWithHeader();
    }

    @Test
    public void testAuthenticate_headersIsEquals() {
        prepareMock(MOCK_STORED_TOKEN);

        Request returnedRequest = mainAuthenticator.authenticate(route, response);

        verify(authHolder).refresh();
        Assert.assertEquals(returnedRequest, request);
    }

    @Test
    public void testAuthenticate_headersIsNotEquals() {
        prepareMock(MOCK_REQUEST_TOKEN);

        Request returnedRequest = mainAuthenticator.authenticate(route, response);

        verify(authHolder, never()).refresh();
        Assert.assertEquals(returnedRequest, request);
    }

    private void prepareMock(String requestHeader){
        when(authHolder.getToken()).thenReturn(MOCK_STORED_TOKEN);
        when(response.request().header(ACCESS_TOKEN_HEADER)).thenReturn(requestHeader);
        when(response.request().newBuilder()).thenReturn(requestBuilder);
        when(requestBuilder.header(ACCESS_TOKEN_HEADER, authHolder.getToken())).thenReturn(requestBuilder);
        when(requestBuilder.header(ACCESS_TOKEN_HEADER, authHolder.getToken()).build()).thenReturn(request);
    }

    private Response makeMockResponseWithHeader(){
        Response.Builder responseBuilder =
                new Response
                        .Builder()
                        .header(AuthInterceptor.ACCESS_TOKEN_HEADER, MOCK_REQUEST_TOKEN)
                        .code(MOCK_RESPONSE_CODE_SUCCESS)
                        .request(mock(Request.class))
                        .protocol(mock(Protocol.class))
                        .message(MOCK_RESPONSE_MESSAGE);
        return responseBuilder.build();
    }
}
