package com.example.sbbank.payload.response;

import com.example.sbbank.entity.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ShowUserListResponseDto {
    private List<users> usersList;

    @Getter
    @AllArgsConstructor
    public static class users {
        private Integer id;
        private String name;
        private String userName;
        private Authority authority;
        private Integer accountNumber;
    }

}
