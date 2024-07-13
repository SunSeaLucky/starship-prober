package org.example.warshipmanagement.service;

import org.example.warshipmanagement.domain.Warship;

import java.util.List;

public interface WarshipService {
    //    Integer getWarshipNumber(String username);
    List<Warship> findAllMyWarships(String username);

    List<Warship> findAllWarships();

    Warship findWarshipById(String id);

    Warship findWarshipByName(String name);

    void buyWarship(String username, String warshipName) throws Exception;

    void saleWarship(String username, String warshipName) throws Exception;

    void updateWarship(String id, String agentName);

    Warship findWarshipByWeapon(String code);

}
