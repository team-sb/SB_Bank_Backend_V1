package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class AccountAlreadyExistsException extends BankException {
    public AccountAlreadyExistsException() {
        super(ErrorCode.ACCOUNT_ALREADY_EXISTS);
    }
}
