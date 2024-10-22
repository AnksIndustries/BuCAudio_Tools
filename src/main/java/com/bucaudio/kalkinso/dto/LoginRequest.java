package com.bucaudio.kalkinso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @JsonProperty("org_id")
    private String orgId;

    @JsonProperty("access_key")
    private String accessKey;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("user_id")
    private String userId;
}
