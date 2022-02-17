package br.com.forja.bits.donation.utils;

import lombok.Data;

@Data
public class Messages {

    public static final String FAIL_TO_VALIDATE_TOKEN = "Fail to validate token. error: ";
    public static final String INVALID_JWT = "Invalid JWT";
    public static final String FAIL_TO_AUTHENTICATE = "Failed to Authenticate";
    public static final String FAIL_TO_RECAPTCHA_AUTHENTICATION = "Failed to recaptcha authentication";
    public static final String SUCCESS = "Success";
    public static final String FAIL = "Fail";
    public static final String NOT_ALLOWED = "Not Allowed";

    public static final String USER_NOT_FOUND = "User not found";
    public static final String USERS_FOUND = "Users found";
    public static final String USER_CREATED = "User created successfully";
    public static final String EMAIL_ALREADY_IN_USE = "Email already in use";
    public static final String INVALID_EMAIL_OR_PASSWORD = "Failed to authenticate, invalid email or password";
    public static final String FAIL_TO_SAVE_USER = "Fail to save user";



}
