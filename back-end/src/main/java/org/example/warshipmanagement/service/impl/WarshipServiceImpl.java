package org.example.warshipmanagement.service.impl;


import lombok.extern.java.Log;
import org.example.warshipmanagement.domain.Warship;
import org.example.warshipmanagement.mapper.UserMapper;
import org.example.warshipmanagement.mapper.WarshipMapper;
import org.example.warshipmanagement.service.UserService;
import org.example.warshipmanagement.service.WarshipService;
import org.example.warshipmanagement.service.WeaponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class WarshipServiceImpl implements WarshipService {
    @Autowired
    WarshipMapper warshipMapper;

    @Autowired
    UserService userService;

    @Override
    public List<Warship> findAllMyWarships(String username) {
        return warshipMapper.findAllMyWarships(username);
    }

    @Override
    public List<Warship> findAllWarships() {
        return warshipMapper.findAllWarships();
    }

    @Override
    public Warship findWarshipById(String id) {
        return warshipMapper.findWarshipById(id);
    }

    @Override
    public Warship findWarshipByName(String name) {
        return warshipMapper.findWarshipByName(name);
    }

    @Override
    @Transactional
    public void buyWarship(String username, String warshipName) throws Exception {

        Integer newBalance = userService.findUserByName(username).getBalance() - findWarshipByName(warshipName).getValuation();
        if (newBalance < 0) throw new Exception("余额不足");
        warshipMapper.buyWarship(username, findWarshipByName(warshipName).getId());
        userService.updateUser(username, newBalance.toString());
    }

    @Override
    @Transactional
    public void saleWarship(String username, String warshipName) throws Exception {
        Integer newBalance = userService.findUserByName(username).getBalance() + findWarshipByName(warshipName).getValuation();
        if (newBalance < 0) throw new Exception("余额不足");
        warshipMapper.saleWarship(username, Integer.toString(findWarshipByName(warshipName).getId()));
        userService.updateUser(username, newBalance.toString());
    }

    @Override
    public void updateWarship(String warshipName, String agentName) {
        warshipMapper.updateWarship(agentName, Integer.toString(findWarshipByName(warshipName).getId()));
    }

    @Override
    public Warship findWarshipByWeapon(String code) {
        return warshipMapper.findWarshipByWeapon(code);
    }


}
