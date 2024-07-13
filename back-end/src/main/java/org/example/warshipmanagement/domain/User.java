package org.example.warshipmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class User {
    private String username;
    @JsonIgnore
    private String password;
    private String register_time;
    private String random;
    private Boolean sex;
    private String born_country;
    private String serving_country;
    private String born_time;
    private Double battle_loss_ratio;
    private Integer crew_number;
    private Integer message;
    private Integer balance;
}
