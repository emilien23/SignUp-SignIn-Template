package com.example.authorizationtemplate.data.network.auth;

import com.example.authorizationtemplate.data.Settings;
import com.example.authorizationtemplate.data.network.NetworkService;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.utils.DateUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.text.ParseException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DateUtils.class)
public class AuthHolderTest {

    private final String ACCESS_TOKEN_MOCK = "access5345fdgFJ878dsgdtu67RAEGD46T52FVF";
    private final String DATE_EXPIRED_MOCK = "2019-01-12";

    @Mock
    private NetworkService.CommonApi api;

    @Mock
    private Settings settings;

    private AuthHolder authHolder;

    @Before
    public void setUp() {
        authHolder = spy(new AuthHolder(api, settings));
        PowerMockito.mockStatic(DateUtils.class);
    }

    @Test
    public void testGetToken() {
        doReturn(ACCESS_TOKEN_MOCK).when(settings).getAccessToken();

        String token = authHolder.getToken();
        verify(settings).getAccessToken();
        Assert.assertEquals(ACCESS_TOKEN_MOCK, token);
    }

    @Test
    public void testSetLoginData() {
        TokenResponse tokenResponse = mock(TokenResponse.class);
        authHolder.setLoginData(tokenResponse);
        verify(settings).setAccessToken(tokenResponse.getAccessToken());
        verify(settings).setTokenDateExpired(tokenResponse.getDatetimeExpired());
    }

    @Test
    public void testClearLoginData() {
        authHolder.clearLoginData();
        verify(settings).clearSettings();
    }

    @Test
    public void testIsTokenExpired_False() throws ParseException {
        doReturn(ACCESS_TOKEN_MOCK).when(settings).getAccessToken();
        BDDMockito.given(DateUtils.hasDatePassed(anyString(), anyString())).willReturn(false);

        Boolean expired = authHolder.isTokenExpired();

        verify(settings).getAccessToken();
        Assert.assertEquals(false, expired);
    }

    @Test
    public void testIsTokenExpired_True_DateExpired() throws ParseException {
        doReturn(ACCESS_TOKEN_MOCK).when(settings).getAccessToken();
        doReturn(DATE_EXPIRED_MOCK).when(settings).getTokenDateExpired();
        BDDMockito.given(DateUtils.hasDatePassed(anyString(), anyString())).willReturn(true);

        Boolean expired = authHolder.isTokenExpired();

        verify(settings).getAccessToken();
        verify(settings).getTokenDateExpired();
        Assert.assertEquals(true, expired);
    }

    @Test
    public void testIsTokenExpired_True_TokenIsNull() {
        doReturn(null).when(settings).getAccessToken();

        Boolean expired = authHolder.isTokenExpired();

        verify(settings).getAccessToken();
        verify(settings, never()).getTokenDateExpired();
        Assert.assertEquals(true, expired);
    }

    @Test
    public void testIsTokenExpired_True_ParseException() throws ParseException  {
        doReturn(ACCESS_TOKEN_MOCK).when(settings).getAccessToken();
        doReturn(DATE_EXPIRED_MOCK).when(settings).getTokenDateExpired();
        BDDMockito.given(DateUtils.hasDatePassed(anyString(), anyString())).willThrow(ParseException.class);

        Boolean expired = authHolder.isTokenExpired();

        verify(settings).getAccessToken();
        verify(settings).getTokenDateExpired();
        Assert.assertEquals(true, expired);
    }

    @Test
    public void testRefresh() {
        SessionListener sessionListener = mock(SessionListener.class);

        authHolder.subscribeToSessionExpired(sessionListener);
        authHolder.refresh();

        verify(authHolder).subscribeToSessionExpired(sessionListener);
    }

    @Test
    public void testRefresh_ListenerIsNull() {
        SessionListener sessionListener = mock(SessionListener.class);

        authHolder.refresh();

        verify(authHolder, never()).subscribeToSessionExpired(sessionListener);
    }
}
