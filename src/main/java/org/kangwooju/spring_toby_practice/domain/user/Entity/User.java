package org.kangwooju.spring_toby_practice.domain.user.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User {

    private String id;
    private String name;
    private String password;
    private String email;

    private Level level;

}
