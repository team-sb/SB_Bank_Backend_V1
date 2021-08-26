package com.example.sbbank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserShowLoanResponseDto {

    private List<Loan> loans;

    @Getter
    @AllArgsConstructor
    public static class Loan {
        private Integer money;
        private Double interest;
        private Date expirationDate;
    }

}
