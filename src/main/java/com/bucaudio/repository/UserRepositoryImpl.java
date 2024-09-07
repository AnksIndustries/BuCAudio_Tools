package com.bucaudio.repository;


import com.bucaudio.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserInfoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public Optional<UserInfo> findByUsername(String email) {
        String query = "SELECT * FROM USERS where email = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(UserInfo.class), email));
    }


    @Override
    public int addUser(UserInfo userInfo) {
        String query = "insert into USERS " +
                "(email, first_name, last_name, phone, password, photo, address, profession, created_at, updated_at, role)" +
                " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(query, userInfo.getEmail(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getPhone(),
                userInfo.getPassword(), userInfo.getPhoto(), userInfo.getAddress(), userInfo.getProfession(), userInfo.getCreatedAt(),
                userInfo.getUpdatedAt(), userInfo.getRole());
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        String query = "update USERS set first_name = ?, last_name = ?, phone = ?, photo = ?, address = ?, created_at = ?, updated_at = ? where email = ?";

        return jdbcTemplate.update(query, userInfo.getFirstName(), userInfo.getLastName(), userInfo.getPhone(), userInfo.getPhoto(),
                userInfo.getAddress(), userInfo.getCreatedAt(), userInfo.getUpdatedAt(), userInfo.getEmail());
    }
}
