package com.example.authorizationtemplate.data;

import com.github.ivanshafran.sharedpreferencesmock.SPMockBuilder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SettingsTest {

    private Settings settings;

    @Before
    public void setUp() {
        SPMockBuilder spMockBuilder = new SPMockBuilder();
        settings = Settings.getInstance(spMockBuilder.createContext());
    }

    @Test
    public void testSetAccessToken() {
        String ACCESS_TOKEN_MOCK = "access5345fdgFJ878dsgdtu67RAEGD46T52FVF";
        settings.setAccessToken(ACCESS_TOKEN_MOCK);
        Assert.assertEquals(settings.getAccessToken(), ACCESS_TOKEN_MOCK);
    }

    @Test
    public void testSetRefreshToken() {
        String REFRESH_TOKEN_MOCK = "refresh5345fdgFJ878dsgdtu67RAEGD46T52FVF";
        settings.setRefreshToken(REFRESH_TOKEN_MOCK);
        Assert.assertEquals(settings.getRefreshToken(), REFRESH_TOKEN_MOCK);
    }

    @Test
    public void testSetTokenDateExpired() {
        String DATE_EXPIRED = "2019-01-01";
        settings.setTokenDateExpired(DATE_EXPIRED);
        Assert.assertEquals(settings.getTokenDateExpired(), DATE_EXPIRED);
    }
}
