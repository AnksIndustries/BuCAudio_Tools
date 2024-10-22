package com.bucaudio.kalkinso.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddUserRequest {

    @JsonProperty("org_id")
    private String orgId;

    @JsonProperty("ip_address")
    private String ipAddress;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("access_key")
    private String accessKey;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("user_role")
    private String userRole;
}
