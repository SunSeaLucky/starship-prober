package org.example.warshipmanagement.controller;


import org.example.warshipmanagement.domain.Param;
import org.example.warshipmanagement.domain.Result;
import org.example.warshipmanagement.domain.User;
import org.example.warshipmanagement.service.UserService;
import org.example.warshipmanagement.util.TokenHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;

    @Autowired
    TokenHelper tokenHelper;

    @PostMapping("/register")
    public Result requestMethodName(String username,
                                    String time,
                                    String random,
                                    String sign,
                                    String avatar,
                                    String email) {
        User u = userService.findUserByName(username);
        if (u != null)
            return Result.fail("Username has been used! Change your username.");
        userService.addUser(username, sign, random, avatar, email, time);
        return Result.success("Register successfully!");
    }

    @GetMapping("/getUserParam")
    public Result getMethodName(@RequestParam String username) {
        User u = userService.findUserByName(username);
        if (u == null)
            return Result.fail("No such user!");
        return Result.success(new Param(u.getRandom(), u.getRegister_time()));
    }

    @PostMapping("/login")
    public Result postMethodName(String username,
                                 String time,
                                 String random,
                                 String sign) {

        User u = userService.login(username, time, random, sign);

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", u.getUsername());

        return u == null ? Result.fail("Incorrect username or password!")
                : Result.success(new String(tokenHelper.generateToken(claims)));
    }

    @GetMapping("getUser")
    public Result getUser(@RequestParam String username) {
        return Result.success(userService.findUserByName(username));
    }

    @GetMapping("/getWarshipNumber")
    public Result<Integer> getWarshipNumber(@RequestParam String username) {
        return Result.success(userService.getWarshipNumber(username));
    }

    // LVC means last visit changed
    @GetMapping("/getLVCWarshipNumber")
    public Result<Integer> getLVCWarshipNumber(@RequestParam String username) {
        Random r = new Random();
        Integer res = r.nextInt(10000) + 1;
        return Result.success(res);
    }

    @GetMapping("/getWarshipBattleRatio")
    public Result<Double> getWarshipBattleRatio(@RequestParam String username) {
        return Result.success(userService.getWarshipBattleRatio(username));
    }

    @GetMapping("/getLVCWarshipBattleRatio")
    public Result<Double> getLVCWarshipBattleRatio(@RequestParam String username) {
        Random r = new Random();
        Double res = r.nextDouble(5) + 1;
        return Result.success(Double.parseDouble(String.format("%.2f", res)));
    }

    @GetMapping("/getCrewNumber")
    public Result<Integer> getCrewNumber(@RequestParam String username) {
        return Result.success(userService.getCrewNumber(username));
    }

    @GetMapping("/getLVCCrewNumber")
    public Result<Integer> getLVCCrewNumber(@RequestParam String username) {
        Random r = new Random();
        Integer res = r.nextInt(10000) + 1;
        return Result.success(res);
    }

    @GetMapping("/getMessage")
    public Result<Integer> getMessage(@RequestParam String username) {
        return Result.success(userService.getMessage(username));
    }

    @GetMapping("/getLVCMessage")
    public Result<Integer> getLVCMessage(@RequestParam String username) {
        Random r = new Random();
        Integer res = r.nextInt(10000) + 1;
        return Result.success(res);
    }

    @PostMapping("/commitPersonalInformation")
    public Result commitPersonalInformation(@RequestParam String username,
                                            @RequestParam Boolean sex,
                                            @RequestParam String born_time,
                                            @RequestParam String serving_country,
                                            @RequestParam String born_country) {
        userService.commitPersonalInformation(username, sex, born_time, serving_country, born_country);
        return Result.success("修改成功");
    }

}
