package org.example.warshipmanagement.service.impl;


import lombok.extern.java.Log;
import org.example.warshipmanagement.domain.User;
import org.example.warshipmanagement.mapper.UserMapper;
import org.example.warshipmanagement.service.UserService;
import org.example.warshipmanagement.util.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@Log
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    @Override
    public void addUser(String username, String password, String random, String avatar, String email,
                        String register_time) {
        userMapper.addUser(username, password, random, register_time);
    }

    @Override
    public User login(String username, String time, String random, String sign) {

        if (findUserByName(username) == null)
            return null;

        User u = findUserByName(username);

        if (!HashHelper.Hash(u.getPassword() + time + random).equals(sign)) {
            // log.info("[HashReuslt] " + HashHelper.Hash(u.getPassword() + time + random));
            return null;
        }

        return u;
    }

    @Override
    public Integer getWarshipNumber(String username) {
        return userMapper.getWarshipNumber(username);
    }

    @Override
    public Double getWarshipBattleRatio(String username) {
        User u = userMapper.findUserByName(username);
        if (u == null || u.getBattle_loss_ratio() == null) return 0.0;
        else return u.getBattle_loss_ratio();
    }

    @Override
    public Integer getCrewNumber(String username) {
        User u = userMapper.findUserByName(username);
        if (u == null || u.getCrew_number() == null) return 0;
        else return u.getCrew_number();
    }

    @Override
    public Integer getMessage(String username) {
        User u = userMapper.findUserByName(username);
        if (u == null || u.getMessage() == null) return 0;
        else return u.getMessage();
    }

    @Override
    public void updateUser(String username, String balance) {
        userMapper.updateUser(username, balance);
    }

    @Override
    public void commitPersonalInformation(String username, Boolean sex, String bornTime, String servingCountry, String bornCountry) {

        userMapper.commitPersonalInformation(username, sex, bornTime, servingCountry, bornCountry);
    }

}
