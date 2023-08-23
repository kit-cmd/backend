package com.example.UserServer.repository;

import com.example.UserServer.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    //주어진 이메일과 일치하는 회원을 데이터베이스에서 찾아서 반환합니다. Optional은 결과가 존재하지 않을 수도 있는 경우를 처리하기 위해 사용됩니다.
    Optional<Member> findByEmail(String email);
    //주어진 이메일과 일치하는 회원이 데이터베이스에 존재하는지 여부를 확인합니다.(중복확인)
    boolean existsByEmail(String email);
}
