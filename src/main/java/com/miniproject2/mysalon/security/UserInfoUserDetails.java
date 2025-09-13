package com.miniproject2.mysalon.security;

import com.miniproject2.mysalon.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserInfoUserDetails implements UserDetails {

    private String id;
    private String password;
    private List<GrantedAuthority> authorities;
    private User userInfo;
    private Long userNum;

    public UserInfoUserDetails(User userInfo) {
        this.userInfo = userInfo;
        this.id=userInfo.getId();
        this.userNum = userInfo.getUserNum();
        this.password=userInfo.getPassword();
        this.authorities = Stream.of(userInfo.getType())
                 .map(type -> new SimpleGrantedAuthority(type.name()))
                 .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public Long getUserNum() { return userNum; }

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