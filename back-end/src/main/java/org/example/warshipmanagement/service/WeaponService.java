package org.example.warshipmanagement.service;

import org.example.warshipmanagement.domain.Weapon;

import java.util.List;

public interface WeaponService {
    List<Weapon> getAllWarshipWeapons(String username);

    Weapon findWeaponByID(String id);

    Weapon findWeaponByName(String name);

    String updateAffiliatedWarship(String username, String weapon, String updatedWarship);

    String saleWeapon(String username, String warship, String weapon) throws Exception;

    String buyWeapon(String username, String warship, String weapon) throws Exception;
}
