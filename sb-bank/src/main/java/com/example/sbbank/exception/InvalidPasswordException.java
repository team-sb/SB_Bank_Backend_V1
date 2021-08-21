package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class InvalidPasswordException extends BankException {
    public InvalidPasswordException() {
        super(ErrorCode.INVALID_PASSWORD);
    }
}
