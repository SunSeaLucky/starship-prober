package org.example.warshipmanagement.controller;

import org.example.warshipmanagement.domain.Result;
import org.example.warshipmanagement.domain.Warship;
import org.example.warshipmanagement.service.WeaponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weapon")
public class WeaponController {
    private static final Logger log = LoggerFactory.getLogger(WeaponController.class);

    @Autowired
    WeaponService weaponService;

    @PostMapping("/getAllWarshipWeapons")
    public Result getAllWarshipWeapon(@RequestParam String username) {
        return Result.success(weaponService.getAllWarshipWeapons(username));
    }

    @GetMapping("/updateAffiliatedWarship")
    public Result updateAffiliatedWarship(@RequestParam String username,
                                          @RequestParam String weapon,
                                          @RequestParam String updatedWarship) {
        return Result.success(weaponService.updateAffiliatedWarship(username, weapon, updatedWarship));
    }

    @GetMapping("/saleWeapon")
    public Result saleWeapon(@RequestParam String username,
                             @RequestParam String warship,
                             @RequestParam String weapon) throws Exception {
        return Result.success(weaponService.saleWeapon(username, warship, weapon));
    }

    @GetMapping("/buyWeapon")
    public Result buyWeapon(@RequestParam String username,
                            @RequestParam String warship,
                            @RequestParam String weapon) throws Exception {
        return Result.success(weaponService.buyWeapon(username, warship, weapon));
    }
}
