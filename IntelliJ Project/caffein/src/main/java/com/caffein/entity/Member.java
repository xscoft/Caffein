package com.caffein.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String pw;

    @Column(unique = true, length = 100, nullable = false)
    private String nickname;

    @Column(unique = true, length = 100, nullable = false)
    private String tel;

    @Column(length = 2)
    @ColumnDefault("0")
    private int sex;

    @Column(length = 2)
    @ColumnDefault("0")
    private int age;

    @Column(name = "auth", length = 100, nullable = false)
    private String auth;

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePw(String pw) {
        this.pw = pw;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeTel(String tel) {
        this.tel = tel;
    }

    public void changeSex(int sex) {
        this.sex = sex;
    }

    public void changeAge(int age) {
        this.age = age;
    }

    public void changeAuth(String auth) {
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}