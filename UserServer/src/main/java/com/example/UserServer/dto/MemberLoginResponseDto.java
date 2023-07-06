package com.example.UserServer.dto;

import com.example.UserServer.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginResponseDto {
    private String email;
    private Map<String, String> profile;
    private TokenDto tokenDto;

    public static MemberLoginResponseDto of(Member member) {
        return new MemberLoginResponseDto(member.getEmail(), member.getProfile(), null);
    }
    public void setTokenDto(TokenDto tokenDto) {
        this.tokenDto = tokenDto;
    }
}
