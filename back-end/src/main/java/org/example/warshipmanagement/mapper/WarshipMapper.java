package org.example.warshipmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.warshipmanagement.domain.Warship;
import org.example.warshipmanagement.domain.Weapon;

import java.util.List;

@Mapper
public interface WarshipMapper {

    void deleteWarship(@Param("ID") String id);

    void updateWarship(@Param("ID") String id);

    Warship findWarshipById(@Param("ID") String id);

    Warship findWarshipByName(@Param("NAME") String name);

    Warship findWarshipByWeapon(@Param("CODE") String code);

    List<Warship> findAllMyWarships(@Param("USERNAME") String username);

    List<Warship> findAllWarships();

    List<Weapon> findStarshipWeapons(@Param("USERNAME") String username, @Param("ID") String id);

    void buyWarship(@Param("USERNAME") String username, @Param("ID") Integer id);

    void saleWarship(@Param("USERNAME") String username, @Param("ID") String id);

    void updateWarship(@Param("DUTY_OFFICER") String duty_officer, @Param("ID") String id);
}
