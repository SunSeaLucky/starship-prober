package org.example.warshipmanagement.controller;


import org.example.warshipmanagement.domain.Result;
import org.example.warshipmanagement.domain.Warship;
import org.example.warshipmanagement.service.WarshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/warship")
public class WarshipController {
    @Autowired
    private WarshipService warshipService;

    @GetMapping("/getAllMyWarships")
    Result<List<Warship>> getAllMyWarships(@RequestParam String username) {
        return Result.success(warshipService.findAllMyWarships(username));
    }

    @GetMapping("/getAllWarships")
    Result<List<Warship>> getAllWarships() {
        return Result.success(warshipService.findAllWarships());
    }

    @GetMapping("/buyWarship")
    Result<String> buyWarship(@RequestParam String username, @RequestParam String warshipName) throws Exception {
        warshipService.buyWarship(username, warshipName);
        return Result.success("购买成功！");
    }


    @GetMapping("/saleWarship")
    Result<String> saleWarship(@RequestParam String username, @RequestParam String warshipName) throws Exception {
        warshipService.saleWarship(username, warshipName);
        return Result.success("售卖成功！");
    }

    @GetMapping("/updateWarship")
    Result<String> updateWarshipAgent(@RequestParam String warshipName, @RequestParam String agentName) {
        warshipService.updateWarship(warshipName, agentName);
        return Result.success("代理修改成功！");
    }

}
