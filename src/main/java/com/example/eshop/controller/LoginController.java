package com.example.eshop.controller;

import com.example.eshop.entity.Member;
import com.example.eshop.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class LoginController {

    private final MemberService memberService;

    // 登入
    @PostMapping("/login")
    public Map<String, Object> login(
            @RequestBody Member member,
            HttpSession session) {

        Map<String, Object> result = new HashMap<>();

        Member loginMember = memberService.login(
                member.getUsername(),
                member.getPassword());

        if (loginMember == null) {

            result.put("success", false);
            result.put("message", "帳號或密碼錯誤");

            return result;
        }

        // 登入成功，存入 Session
        session.setAttribute("memberId", loginMember.getMemberId());
        session.setAttribute("username", loginMember.getUsername());
        session.setAttribute("email", loginMember.getEmail());

        result.put("success", true);
        result.put("message", "登入成功");
        result.put("memberId", loginMember.getMemberId());
        result.put("username", loginMember.getUsername());
        result.put("email", loginMember.getEmail());

        return result;
    }

    // 登出
    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {

        session.invalidate();

        Map<String, Object> result = new HashMap<>();

        result.put("success", true);
        result.put("message", "登出成功");

        return result;
    }
}