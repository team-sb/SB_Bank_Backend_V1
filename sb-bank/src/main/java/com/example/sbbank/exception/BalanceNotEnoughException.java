package com.example.sbbank.exception;

import com.example.sbbank.error.ErrorCode;
import com.example.sbbank.error.exception.BankException;

public class BalanceNotEnoughException extends BankException {
    public BalanceNotEnoughException() {
        super(ErrorCode.BALANCE_NOT_ENOUGH);
    }
}
