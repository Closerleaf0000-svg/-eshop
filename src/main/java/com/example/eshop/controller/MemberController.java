package com.example.eshop.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class MemberController {

    // 查詢目前登入會員
    @GetMapping("/me")
    public Map<String, Object> getCurrentMember(
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        Object userId = session.getAttribute("userId");

        Object username = session.getAttribute("username");

        // 沒有登入
        if (userId == null) {

            result.put("loggedIn", false);

            return result;
        }

        // 已登入
        result.put("loggedIn", true);
        result.put("userId", userId);
        result.put("username", username);

        return result;
    }
}