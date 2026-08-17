package com.example.eshop.mapper;

import com.example.eshop.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    // 依帳號查詢會員
    Optional<Member> findByUsername(String username);

    // 依 Email 查詢會員
    Optional<Member> findByEmail(String email);

    // 檢查 Email 是否已存在
    boolean existsByEmail(String email);

    // 新增會員
    int insert(Member member);

}