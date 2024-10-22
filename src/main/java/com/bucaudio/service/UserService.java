package com.bucaudio.service;

import com.bucaudio.common.exception.InvalidUserInformation;
import com.bucaudio.entity.UserInfo;
import com.bucaudio.kalkinso.dto.AddUserRequest;
import com.bucaudio.kalkinso.dto.SessionCreationResponse;
import com.bucaudio.kalkinso.service.KalkinsoServiceBean;
import com.bucaudio.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private KalkinsoServiceBean kalkinsoService;

    @Value("${kalkinso.org.id}")
    private String orgId;

    @Value("${kalkinso.access.key}")
    private String accessKey;


    public UserInfo addUser(UserInfo userInfo) {

        LocalDateTime localDateTime = LocalDateTime.now();
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setCreatedAt(localDateTime);
        userInfo.setUpdatedAt(localDateTime);
        log.debug("Saving user data to BuCAudio dB");
        userInfoRepository.addUser(userInfo);

        log.debug("Establishing connection with Kalkinso...");
        SessionCreationResponse sessionResponse = kalkinsoService.sessionCreation();
        AddUserRequest addUserRequest = constructAddUserRequest(userInfo);

        log.debug("Pushing user registration data towards Kalkinso...");
        kalkinsoService.addUser(addUserRequest, sessionResponse.getToken());

        UserInfo savedData = new UserInfo();
        savedData.setEmail(userInfo.getEmail());
        savedData.setCreatedAt(userInfo.getCreatedAt());
        savedData.setFirstName(userInfo.getFirstName());
        savedData.setLastName(userInfo.getLastName());
        return savedData;
    }

    public UserInfo getUserInfo(String email) {
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUsername(email);
        if (optionalUserInfo.isPresent()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setFirstName(optionalUserInfo.get().getFirstName());
            userInfo.setLastName(optionalUserInfo.get().getLastName());
            userInfo.setEmail(optionalUserInfo.get().getEmail());
            userInfo.setPhone(optionalUserInfo.get().getPhone());
            userInfo.setPhoto(optionalUserInfo.get().getPhoto());
            userInfo.setAddress(optionalUserInfo.get().getAddress());
            userInfo.setProfession(optionalUserInfo.get().getProfession());
            userInfo.setCreatedAt(optionalUserInfo.get().getCreatedAt());
            userInfo.setUpdatedAt(optionalUserInfo.get().getUpdatedAt());
            userInfo.setRole(optionalUserInfo.get().getRole());

            return userInfo;
        } else {
            throw new InvalidUserInformation("User Not found with given email");
        }
    }

    public UserInfo updateUserInfo(UserInfo userInfo) {
        LocalDateTime localDateTime = LocalDateTime.now();
        userInfo.setUpdatedAt(localDateTime);
        userInfoRepository.updateUserInfo(userInfo);
        return userInfo;
    }


    private AddUserRequest constructAddUserRequest(UserInfo userInfo) {
        AddUserRequest addUserRequest = new AddUserRequest();
        addUserRequest.setIpAddress("192.168.0.0"); //TODO: Kept static, need to check
        addUserRequest.setUserId(userInfo.getEmail());
        addUserRequest.setOrgId(orgId);
        addUserRequest.setAccessKey(accessKey);
        addUserRequest.setFirstName(userInfo.getFirstName());
        addUserRequest.setLastName(userInfo.getLastName());
        addUserRequest.setMobile(userInfo.getPhone());
        addUserRequest.setUserRole("Professional");
        return addUserRequest;
    }


}
