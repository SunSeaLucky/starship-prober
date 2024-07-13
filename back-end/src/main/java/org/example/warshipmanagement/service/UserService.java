package org.example.warshipmanagement.service;


import org.example.warshipmanagement.domain.User;

public interface UserService {
    User findUserByName(String name);

    void addUser(String username, String password, String random, String avatar, String email, String register_time);


    User login(String username, String time, String random, String sign);

    Integer getWarshipNumber(String username);

    Double getWarshipBattleRatio(String username);

    Integer getCrewNumber(String username);

    Integer getMessage(String username);

    void updateUser(String username, String balance);

    void commitPersonalInformation(String username, Boolean sex, String bornTime, String servingCountry, String bornCountry);
}
