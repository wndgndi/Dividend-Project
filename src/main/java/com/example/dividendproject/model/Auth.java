package com.example.dividendproject.model;

import java.util.List;
import lombok.Data;

public class Auth {

    @Data
    public static class SignIn {
        private String username;
        private String password;
    }

    @Data
    public static class SignUp {
        private String username;
        private String password;
        private List<String> roles;

        public MemberEntity toEntity() {
            return MemberEntity.builder()
                .username(this.username)
                .password(this.password)
                .roles(this.roles)
                .build();
        }
    }
}
