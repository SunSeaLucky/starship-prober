package org.example.warshipmanagement.mapper;


import org.apache.ibatis.annotations.*;
import org.example.warshipmanagement.domain.User;

@Mapper
public interface UserMapper {

    void addUser(@Param("USERNAME") String username, @Param("PASSWORD") String password, @Param("RANDOM") String random, @Param("REGISTER_TIME") String register_time);

    void deleteUser(@Param("USERNAME") String username);

    void updateUser(@Param("USERNAME") String username, @Param("BALANCE") String balance);

    User findUserByName(@Param("USERNAME") String username);

    Integer getWarshipNumber(String username);

    void commitPersonalInformation(@Param("USERNAME") String username,
                                   @Param("SEX") Boolean sex,
                                   @Param("BORN_TIME") String bornTime,
                                   @Param("SERVING_COUNTRY") String servingCountry,
                                   @Param("BORN_COUNTRY") String bornCountry);
}
