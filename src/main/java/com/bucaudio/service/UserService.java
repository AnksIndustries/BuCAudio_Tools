package com.bucaudio.service;

import com.bucaudio.common.exception.InvalidUserInformation;
import com.bucaudio.entity.UserInfo;
import com.bucaudio.repository.UserInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


    public UserInfo addUser(UserInfo userInfo) {

        LocalDateTime localDateTime = LocalDateTime.now();

        UserInfo savedData = new UserInfo();
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setCreatedAt(localDateTime);
        userInfo.setUpdatedAt(localDateTime);
        userInfoRepository.addUser(userInfo);
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



}
