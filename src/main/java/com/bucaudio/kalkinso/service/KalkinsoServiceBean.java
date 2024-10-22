package com.bucaudio.kalkinso.service;

import com.bucaudio.kalkinso.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KalkinsoServiceBean {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${kalkinso.base.url}")
    private String baseUrl;


    public SessionCreationResponse sessionCreation(SessionCreationRequest request) {
        String url = baseUrl + "/auth/login/email";
        return restTemplate.postForObject(url, request, SessionCreationResponse.class);
    }

    public LoginResponse login(LoginRequest request, String token) {
        String url = baseUrl + "/organizations/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", token);
        HttpEntity<LoginRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.postForObject(url, entity, LoginResponse.class);
    }

    public AddUserResponse addUser(AddUserRequest request, String token) {
        String url = baseUrl + "users/organizations/add";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Auth-Token", token);
        HttpEntity<AddUserRequest> entity = new HttpEntity<>(request, headers);
        return restTemplate.postForObject(url, entity, AddUserResponse.class);
    }


}
