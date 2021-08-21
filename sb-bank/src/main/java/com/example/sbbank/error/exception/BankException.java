package com.example.sbbank.error.exception;

import com.example.sbbank.error.ErrorCode;
import lombok.Getter;

@Getter
public class BankException extends RuntimeException {

    private final ErrorCode errorCode;

    public BankException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
