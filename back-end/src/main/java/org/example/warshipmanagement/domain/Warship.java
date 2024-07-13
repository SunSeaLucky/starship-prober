package org.example.warshipmanagement.domain;

import lombok.Data;

@Data
public class Warship {
    private int id;
    private String name;
    private String product_country;
    private String duty_officer;
    private String product_date;
    private Integer valuation;
    private Integer status;
    private Integer activity;
    private Boolean verified;
}
