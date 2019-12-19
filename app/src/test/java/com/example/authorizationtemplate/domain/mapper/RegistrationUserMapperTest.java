package com.example.authorizationtemplate.domain.mapper;


import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.RegistrationRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;


@RunWith(MockitoJUnitRunner.class)
public class RegistrationUserMapperTest {

    private static String NAME = "test";
    private static String EMAIL = "test";
    private static String PASSWORD = "test";

    private RegistrationUserMapper mapper;

    @Before
    public void setUp() {
        mapper = new RegistrationUserMapper();
    }

    @Test
    public void testValidMap() {

        AddUserData addUserData = new AddUserData(NAME, EMAIL, PASSWORD);
        RegistrationRequest registrationRequest = mapper.map(addUserData);

        assertEquals(registrationRequest.getUsername(), addUserData.getName());
    }

    @Test
    public void testMap_when_null() {
        AddUserData addUserData = new AddUserData(null, null, null);
        RegistrationRequest registrationRequest = mapper.map(addUserData);

        assertEquals(registrationRequest.getUsername(), addUserData.getName());
    }
}
