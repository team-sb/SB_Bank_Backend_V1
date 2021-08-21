package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class UserNotFoundException extends BankException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
