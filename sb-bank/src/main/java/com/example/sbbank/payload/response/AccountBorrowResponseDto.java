package com.example.sbbank.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBorrowResponseDto {
    private List<Borrow> borrows;

    public static class Borrow {
        private Integer money;
        private Double interest;
        private Date borrowExpirationDate;
    }
}
