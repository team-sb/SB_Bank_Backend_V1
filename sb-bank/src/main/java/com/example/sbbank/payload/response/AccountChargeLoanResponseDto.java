package com.example.sbbank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class AccountChargeLoanResponseDto {
        private Integer money;
        private Double interest;
        private Date borrowedDate;
        private Date loanExpirationDate;
}
