package com.example.authorizationtemplate.domain.entities;

public class PasswordUtils {

    public static boolean equalsPasswords(String pass1, String pass2){
        return pass1.equals(pass2);
    }
}
