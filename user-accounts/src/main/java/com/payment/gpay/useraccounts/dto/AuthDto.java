package com.payment.gpay.useraccounts.dto;

import lombok.*;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class AuthDto {

    private String username;
    private String password;

}