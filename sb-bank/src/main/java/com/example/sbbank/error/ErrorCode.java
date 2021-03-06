package com.example.sbbank.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(401, "Invalid Token"),
    INVALID_PASSWORD(401, "Invalid Password"),

    USER_ALREADY_EXISTS(409, "User Already Exists"),
    USER_NOT_FOUND(404, "User Not Found"),

    ACCOUNT_ALREADY_EXISTS(409, "Account Already Exists"),
    ACCOUNT_NOT_FOUND(404, "Account Not Found"),

    BALANCE_NOT_ENOUGH(404, "Balance Not Enough");

    private final int status;
    private final String message;
}
