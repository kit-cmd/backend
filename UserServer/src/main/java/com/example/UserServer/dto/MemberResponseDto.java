package com.example.UserServer.dto;

import com.example.UserServer.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private Map<String, String> profile;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getEmail(), member.getProfile());
    }
}
