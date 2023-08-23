package com.example.UserServer.entity;

import io.hypersistence.utils.hibernate.type.json.JsonStringType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@Table(name = "member")
@Entity
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Type(type="json")
    @Column(columnDefinition = "json")
    private Map<String, String> profile;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum Authority {
        ROLE_USER, ROLE_ADMIN
    }
    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = createdAt;
    }
    @PreUpdate
    public void updatedAt(){
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public Member(String email, String password, Authority authority, Map<String, String> profile) {
        this.email = email;
        this.password = password;
        this.authority = authority;
        this.profile = profile;
    }
}
