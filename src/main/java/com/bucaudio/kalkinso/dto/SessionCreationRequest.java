package com.bucaudio.kalkinso.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCreationRequest {

    private String email;
    private String password;
}
