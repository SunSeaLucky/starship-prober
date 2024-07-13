package org.example.warshipmanagement.domain;

import lombok.Data;

@Data
public class Weapon {
    private int code;
    private String name;
    private String image;
    private Integer price;
    private Integer category;
    private Integer reviews;
    private Integer status;
    private String warship;
}
