package com.example.authorizationtemplate.domain.mapper;

import com.example.authorizationtemplate.domain.models.AddUserData;
import com.example.authorizationtemplate.domain.models.RegistrationRequest;

public class RegistrationUserMapper implements BaseObjectsMapper<AddUserData, RegistrationRequest> {

    @Override
    public  RegistrationRequest map(AddUserData obj) {
        return new RegistrationRequest(obj.getPassword(), obj.getEmail(), obj.getPassword());
    }
}
