package com.bucaudio.repository;


import com.bucaudio.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepository {


    Optional<UserInfo> findByUsername(String email);

    int addUser(UserInfo userInfo);

    int updateUserInfo(UserInfo userInfo);
}
