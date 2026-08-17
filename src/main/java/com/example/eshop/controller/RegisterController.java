package com.example.eshop.controller;

import com.example.eshop.entity.Member;
import com.example.eshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class RegisterController {

    private final MemberService memberService;

    // 會員註冊
    @PostMapping("/register")
    public Map<String, Object> register(
            @RequestBody Map<String, String> request) {

        Map<String, Object> result = new HashMap<>();

        // 取得前端資料
        String username = request.get("username");
        String password = request.get("password");
        String confirmPassword = request.get("confirmPassword");
        String email = request.get("email");
        String phone = request.get("phone");

        // 檢查帳號
        if (username == null || username.isBlank()) {

            result.put("success", false);
            result.put("message", "帳號不可空白");

            return result;
        }

        // 檢查密碼
        if (password == null || password.isBlank()) {

            result.put("success", false);
            result.put("message", "密碼不可空白");

            return result;
        }

        // 確認密碼
        if (!password.equals(confirmPassword)) {

            result.put("success", false);
            result.put("message", "兩次輸入的密碼不一致");

            return result;
        }

        // 檢查帳號是否存在
        if (memberService.isUsernameTaken(username)) {

            result.put("success", false);
            result.put("message", "這個帳號已經被使用了");

            return result;
        }

        // 檢查 Email
        if (memberService.isEmailTaken(email)) {

            result.put("success", false);
            result.put("message", "這個 Email 已經被註冊過了");

            return result;
        }

        // 建立 Member
        Member member = new Member();
        member.setUsername(username);
        member.setPassword(password);
        member.setEmail(email);
        member.setPhone(phone);

        // 寫入資料庫
        memberService.register(member);

        // 註冊成功
        result.put("success", true);
        result.put("message", "註冊成功");

        return result;
    }
}