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

    private RegistrationUserMapper mapper;

    @Before
    public void setup() {
        mapper = new RegistrationUserMapper();
    }

    @Test
    public void testValidMap() {
        String name = "test";
        String email = "test";
        String password = "test";

        AddUserData addUserData = new AddUserData(name, email, password);
        RegistrationRequest registrationRequest = mapper.map(addUserData);

        assertEquals(registrationRequest.getUsername(), addUserData.getName());
    }
}
