package com.mthien.yumble.controller;

import com.mthien.yumble.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {
    private final RedisService redisService;

    @PostMapping("/save")
    public String save(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value);
        return "Saved key: " + key + " with value: " + value;
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        String value = redisService.get(key);
        return value != null ? "Value for key " + key + " is " + value : "Key not found!";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        redisService.delete(key);
        return "Deleted key: " + key;
    }
}
