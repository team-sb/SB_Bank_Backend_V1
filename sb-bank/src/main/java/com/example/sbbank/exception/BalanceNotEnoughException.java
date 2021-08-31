package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class BalanceNotExistsException extends BankException {
    public BalanceNotExistsException() {
        super(ErrorCode.BALANCE_NOT_EXISTS);
    }
}
