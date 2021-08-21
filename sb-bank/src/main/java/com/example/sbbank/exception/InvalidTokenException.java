package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class InvalidTokenException extends BankException {
    public InvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
