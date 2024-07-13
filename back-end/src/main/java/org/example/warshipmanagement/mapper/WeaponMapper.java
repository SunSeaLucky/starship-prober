package org.example.warshipmanagement.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.warshipmanagement.domain.Weapon;

import java.util.List;

@Mapper
public interface WeaponMapper {
    void addWeapon();

    void deleteWeapon();

    void updateWeapon(@Param("USERNAME") String username,
                      @Param("WEAPON") String weapon,
                      @Param("WARSHIP") String updatedWarship);

    Weapon queryWeapon(@Param("ID") String id);

    Weapon findWeaponByName(@Param("NAME") String name);

    void addRelationWeapon(@Param("USERNAME") String username,
                           @Param("WARSHIP") String warship,
                           @Param("WEAPON") String weapon);

    void deleteRelationWeapon(@Param("USERNAME") String username,
                              @Param("WARSHIP") String warship,
                              @Param("WEAPON") String weapon);


    List<Weapon> queryAllWeapons(@Param("USERNAME") String username);

}
