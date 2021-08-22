package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class AccountNotFoundException extends BankException {
    public AccountNotFoundException() {
        super(ErrorCode.ACCOUNT_NOT_FOUND);
    }
}
