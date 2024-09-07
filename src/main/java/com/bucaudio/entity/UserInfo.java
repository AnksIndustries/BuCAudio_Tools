package com.bucaudio.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfo {

    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private String photo;
    private String address;
    private String profession;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String role;
}
