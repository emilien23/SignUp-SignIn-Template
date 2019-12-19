package com.example.authorizationtemplate.domain.entities;

public class PasswordUtils {

    public static boolean equalsPasswords(String pass1, String pass2){
        if(pass1 == null)
            return false;
        return pass1.equals(pass2);
    }
}
