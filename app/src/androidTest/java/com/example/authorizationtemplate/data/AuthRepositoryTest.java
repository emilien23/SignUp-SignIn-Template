package com.example.authorizationtemplate.data;


import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.authorizationtemplate.test.AuthRule;
import com.example.authorizationtemplate.data.repositories.auth.AuthRepositoryImpl;
import com.example.authorizationtemplate.domain.models.LoginRequest;
import com.example.authorizationtemplate.domain.models.TokenResponse;
import com.example.authorizationtemplate.domain.repositories.auth.AuthRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit2.Response;

import static com.example.authorizationtemplate.di.module.network.RequestsHandler.TEST_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class AuthRepositoryTest {

    private final String STUB_ACCESS_TOKEN = "CFplUS9N5oT03WeY6twgG9n4zbi32P04";
    private final String STUB_REFRESH_TOKEN = "CFplUS9NQWRRTVdfbgngG9n4zbgnfgw";
    private final String STUB_DATE_EXPIRED = "2020-12-20 11:11";

    private AuthRepository authRepository;
    @Rule
    public final AuthRule authRule = new AuthRule();

    @Before
    public void setUp() {
        authRepository = new AuthRepositoryImpl(authRule.api, authRule.authHolder);
    }

    @Test
    public void testLogin_success() {
        LoginRequest loginRequest = new LoginRequest("root", "root");
        TokenResponse tokenResponse = authRepository.login(loginRequest).blockingFirst().body();

        assertNotNull(tokenResponse);
        assertEquals(STUB_ACCESS_TOKEN, tokenResponse.getAccessToken());
    }

    @Test
    public void testLogin_error() {
        Settings.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .setAccessToken(TEST_ERROR);
        LoginRequest loginRequest = new LoginRequest(TEST_ERROR, TEST_ERROR);
        Response<TokenResponse> response = authRepository.login(loginRequest).blockingFirst();

        assertFalse(response.isSuccessful());
        assertEquals(TEST_ERROR, response.message());
    }

    @Test
    public void testLogout_success() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        authRepository.logout().doOnComplete(()-> {
                    String accessToken = Settings
                        .getInstance(context)
                        .getAccessToken();
                    assertNull(accessToken);
                    String refreshToken = Settings
                            .getInstance(context)
                            .getRefreshToken();
                    assertNull(refreshToken);
                    String dateExpired = Settings
                            .getInstance(context)
                            .getTokenDateExpired();
                    assertNull(dateExpired);
                });
    }

    @Test
    public void testSaveUserData() {
        TokenResponse tokenResponse = new TokenResponse(STUB_ACCESS_TOKEN, STUB_DATE_EXPIRED, STUB_REFRESH_TOKEN);
        authRepository.saveLoginData(tokenResponse);
        String accessToken = Settings.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .getAccessToken();
        String dateExpired = Settings.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .getTokenDateExpired();
        String refreshToken = Settings.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .getRefreshToken();

        assertEquals(STUB_ACCESS_TOKEN, accessToken);
        assertEquals(STUB_DATE_EXPIRED, dateExpired);
        assertEquals(STUB_REFRESH_TOKEN, refreshToken);
    }

    @Test
    public void testIsTokenExpired_true() {
        String DATE_EXPIRED = "2019-12-20 11:11";
        TokenResponse tokenResponse = new TokenResponse(STUB_ACCESS_TOKEN, DATE_EXPIRED, STUB_REFRESH_TOKEN);
        authRepository.saveLoginData(tokenResponse);
        Boolean isExpired = authRepository.isTokenDateExpired();

        assertEquals(true, isExpired);
    }

    @Test
    public void testIsTokenExpired_false() {
        String DATE_EXPIRED = "2019-12-31 11:38";
        TokenResponse tokenResponse = new TokenResponse(STUB_ACCESS_TOKEN, DATE_EXPIRED, STUB_REFRESH_TOKEN);
        authRepository.saveLoginData(tokenResponse);
        Boolean isExpired = authRepository.isTokenDateExpired();

        assertEquals(false, isExpired);
    }

    @Test
    public void testIsTokenExpired_date_invalid() {
        String DATE_EXPIRED = "esfragar4tgf";
        TokenResponse tokenResponse = new TokenResponse(STUB_ACCESS_TOKEN, DATE_EXPIRED, STUB_REFRESH_TOKEN);
        authRepository.saveLoginData(tokenResponse);
        Boolean isExpired = authRepository.isTokenDateExpired();

        assertEquals(true, isExpired);
    }

    @Test
    public void testIsTokenExpired_date_null() {
        TokenResponse tokenResponse = new TokenResponse(STUB_ACCESS_TOKEN, null, STUB_REFRESH_TOKEN);
        authRepository.saveLoginData(tokenResponse);
        Boolean isExpired = authRepository.isTokenDateExpired();

        assertEquals(true, isExpired);
    }

    @After
    public void onEnd(){
        Settings.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext())
                .clearSettings();
    }
}
