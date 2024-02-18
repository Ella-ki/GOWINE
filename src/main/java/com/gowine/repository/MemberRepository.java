package com.gowine.repository;

import com.gowine.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpLE;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
