package org.example.warshipmanagement.service.impl;

import org.example.warshipmanagement.domain.Weapon;
import org.example.warshipmanagement.mapper.WeaponMapper;
import org.example.warshipmanagement.service.UserService;
import org.example.warshipmanagement.service.WarshipService;
import org.example.warshipmanagement.service.WeaponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeaponServiceImpl implements WeaponService {
    private static final Logger log = LoggerFactory.getLogger(WeaponServiceImpl.class);
    @Autowired
    WeaponMapper weaponMapper;
    @Autowired
    WarshipService warshipService;
    @Autowired
    UserService userService;

    @Override
    public List<Weapon> getAllWarshipWeapons(String username) {
        List<Weapon> weapons = weaponMapper.queryAllWeapons(username);
        weapons.forEach(weapon -> {
//            log.info(weapon.toString());
            weapon.setWarship(warshipService.findWarshipByWeapon(Integer.toString(weapon.getCode())).getName());
        });
        return weapons;
    }

    @Override
    public Weapon findWeaponByID(String id) {
        return weaponMapper.queryWeapon(id);
    }

    @Override
    public Weapon findWeaponByName(String name) {
        return weaponMapper.findWeaponByName(name);
    }

    @Override
    public String updateAffiliatedWarship(String username, String weapon, String updatedWarship) {
        weaponMapper.updateWeapon(username, weapon, updatedWarship);
        return "更新成功";
    }

    @Override
    @Transactional
    public String saleWeapon(String username, String warship, String weapon) throws Exception {
        Integer newBalance = userService.findUserByName(username).getBalance() + findWeaponByName(weapon).getPrice();
        if (newBalance < 0) throw new Exception("余额出现异常，无法完成操作");
        userService.updateUser(username, newBalance.toString());
        weaponMapper.deleteRelationWeapon(username,
                Integer.toString(warshipService.findWarshipByName(warship).getId()),
                Integer.toString(findWeaponByName(weapon).getCode()));
        return "售卖成功";
    }

    @Override
    @Transactional
    public String buyWeapon(String username, String warship, String weapon) throws Exception {
        Integer newBalance = userService.findUserByName(username).getBalance() - findWeaponByName(weapon).getPrice();
        if (newBalance < 0) throw new Exception("余额不足");
        userService.updateUser(username, newBalance.toString());
        weaponMapper.addRelationWeapon(username,
                Integer.toString(warshipService.findWarshipByName(warship).getId()),
                Integer.toString(findWeaponByName(weapon).getCode()));
        return "购买成功";
    }
}
