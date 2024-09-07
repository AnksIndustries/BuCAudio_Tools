package com.bucaudio.dto;


import com.bucaudio.common.annotation.IsMandatory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @IsMandatory
    private String username;
    @IsMandatory
    private String password;
}
