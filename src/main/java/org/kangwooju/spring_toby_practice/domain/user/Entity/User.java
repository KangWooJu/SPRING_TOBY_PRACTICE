package org.kangwooju.spring_toby_practice.domain.user.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class User {

    String id;
    String name;
    String password;
    String email;

    Level level;

}
